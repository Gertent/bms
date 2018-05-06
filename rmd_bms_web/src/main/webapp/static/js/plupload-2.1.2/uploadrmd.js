
accessid = '';
accesskey = '';
host = '';
policyBase64 = '';
signature = '';
callbackbody = '';
filename = '';
key = '';
expire = 0;
num = 1;
now = timestamp = Date.parse(new Date()) / 1000; 
serverfilename='';
g_object_name='';
function send_request()
{
	
    var xmlhttp = null;
    if (window.XMLHttpRequest)
    {
        xmlhttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
  
    if (xmlhttp!=null)
    {
    	var date = new Date();
    	var parameter = 'data=1&date='+date.getDate();//自定义参数 1.data上传渠道号
        url = '/oss/getInfo.action';
        xmlhttp.open( "POST", url, false );
        xmlhttp.send( parameter );
        return xmlhttp.responseText
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

function get_signature()
{
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000; 
    console.log('get_signature ...');
    console.log('expire:' + expire.toString());
    console.log('now:', + now.toString());
    if (expire+3 < now)
    {
    	
        console.log('get new sign');
        body = send_request();
        var obj = eval ("(" + body + ")");
        host = obj['host'];
        policyBase64 = obj['policy'];
        accessid = obj['accessid'];
        signature = obj['signature'];
        expire = parseInt(obj['expire']);
        callbackbody = obj['callback'];
        key = obj['dir'];
        serverfilename=obj['filename'];
        return true;
    }
    return false;
};

function get_suffix(filename) {
    pos = filename.lastIndexOf('.');
    suffixex = '';
    if (pos != -1) {
        suffixex = filename.substring(pos);
    }
    //return filename=='';
    return suffixex;
}

function set_upload_param(up, filename, ret)
{
    var ret = get_signature();
    if (ret == true){
    	g_object_name = key;
    	/*alert(filename);*/
        if(filename!=''){
        	alert('assssss');
        	suffix = get_suffix(filename);
        	g_object_name = key + serverfilename + suffix;
        }
        alert(g_object_name);
        new_multipart_params = {
            'key' : g_object_name,
            'policy': policyBase64,
            'OSSAccessKeyId': accessid, 
            'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
            'callback' : callbackbody,
            'signature': signature,
        };
        up.setOption({
            'url': host,
            'multipart_params': new_multipart_params
        });

        console.log('reset uploader')
    }
}

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //multi_selection: false,
	container: document.getElementById('container'),
	flash_swf_url : 'js/Moxie.swf',
	silverlight_xap_url : 'js/Moxie.xap',
    url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
    filters : {
    	max_file_size : '100mb',
    	mime_types : [
    	    {title : "Image files",extensions : "jpg,gif,png"} 
    	    ]
    	},
	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
            uploader.start();
            return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});
		},
		
		BeforeUpload: function(up, file) {
            set_upload_param(up, file.name, true);
            alert(g_object_name);
        },
        
		UploadProgress: function(up, file) {
			var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);
		},
		FileUploaded: function(up, file, info) {
            console.log('uploaded')
            console.log(info.status)
            if (info.status == 200)
            {
            	document.getElementById("fvalue").value=g_object_name;
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = file.name+'文件上传success';
            }
            else
            {
            	document.getElementById("fvalue").value=g_object_name;
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            } 
		},
		Error: function(up, err){
            set_upload_param(up);
			document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
		}
	}
});

uploader.init();

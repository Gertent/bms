
accessid = ''
accesskey = ''
host = ''
policyBase64 = ''
signature = ''
callbackbody = ''
filename = ''
key = ''
expire = 0
num = 1
now = timestamp = Date.parse(new Date()) / 1000; 

function delete_Object(ObjectName) {
    
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
    	var parameter = 'key='+ObjectName+'&date='+date.getDate();//自定义参数 1.data上传渠道号
        phpUrl = '/oss/delete.action';
        xmlhttp.open( "GET", phpUrl+'?'+parameter, false );
        xmlhttp.send( null );
        document.getElementById('ossfile').innerHTML = '';
        return xmlhttp.responseText;
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

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
        phpUrl = '/oss/get.action'
        xmlhttp.open( "GET", phpUrl+'?'+parameter, false );
        xmlhttp.send( null );
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
    console.log('now:', + now.toString())
    if (expire < now + 15)
    {
        console.log('get new sign')
        body = send_request()
        var obj = eval ("(" + body + ")");
        host = obj['host']
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire'])
        callbackbody = obj['callback'] 
        key = obj['dir']
        return true;
    }
    return false;
};

function set_upload_param(up)
{
    var ret = get_signature();
    if (ret == true)
    {
        new_multipart_params = {
            'key' : key + '${filename}',
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
        //uploader.start();
    }
}

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
	container: document.getElementById('container'),
	flash_swf_url : 'plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'plupload-2.1.2/js/Moxie.xap',
	multi_selection : false,
    //url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
	url : 'http://img1.saharabuy.com/',
	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
            set_upload_param(uploader);
            uploader.start();
            return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(up.files, function (file) { 
		        if (up.files.length <= num) { 
		            return; 
		        } 
		        up.removeFile(file); 
		    });
			document.getElementById('ossfile').innerHTML='';
			plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});
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
            console.log('uploaded');
            console.log(info.status);
            set_upload_param(up);
            if (info.status == 200)
            {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = file.name+'文件上传success';
            }
            else
            {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            } 
		},

		Error: function(up, err) {
            set_upload_param(up);
			document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
		}
	}
});
uploader.init();

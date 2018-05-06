
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
};

function get_suffix(filename) {
    pos = filename.lastIndexOf('.');
    suffixex = '';
    if (pos != -1) {
        suffixex = filename.substring(pos);
    }
    return suffixex;
}

function set_upload_param(up, filename, ret)
{
    var ret = get_signature();
    if (ret == true){
    	g_object_name = key;
        if(filename!=''){
        	suffix = get_suffix(filename);
        	g_object_name = key + serverfilename + suffix;
        }
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

function createup1(cmdselid,filetype){
	alert("test");
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : cmdselid, 
	    multi_selection: false,
		container: document.getElementById('container'),
		flash_swf_url : 'js/Moxie.swf',
		silverlight_xap_url : 'js/Moxie.xap',
	    url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
	    filters : {
	    	max_file_size : '100mb',
	    	mime_types : [
	    	    {title : "Image files",extensions : filetype} 
	    	    ]
	    	},
	
		init: {
			PostInit: function() {
			},
	
			FilesAdded: function(up, files) {
				uploader.start();
			},
	
			BeforeUpload: function(up, file) {
				alert('bef');
				set_upload_param(up, file.name, true);
				alert('bef111');
	        },
	
			UploadProgress: function(up, file) {
	        	alert("okppppppp");
			},
	
			FileUploaded: function(up, file, info) {
				alert("ok");
				pageinsertimage(g_object_name);
			},
	
			Error: function(up, err) {
				set_upload_param(up);
				//document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
			}
		}
	});
	
	uploader.init();

}

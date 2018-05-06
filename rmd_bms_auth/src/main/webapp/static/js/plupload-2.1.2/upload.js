
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
    //now = timestamp = Date.parse(new Date()) / 1000; 
//    console.log('get_signature ...');
//    console.log('expire:' + expire.toString());
//    console.log('now:', + now.toString());
//    if (expire+3 < now)
//    {
//    	
//        console.log('get new sign');
        body = send_request();
        var obj = eval ("(" + body + ")");
        host = obj['host'];
        policyBase64 = obj['policy'];
        accessid = obj['accessid'];
        signature = obj['signature'];
        expire = parseInt(obj['expire']);
        //callbackbody = obj['callback'];
        key = obj['dir'];
        serverfilename=obj['filename'];
        return true;
//    }
//    return false;
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
//        	g_object_name = serverfilename + suffix;
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

        console.log('reset uploader');
    }
}

function createuploader(cmdselid,cmdupload,rettext,ossfile,multifileflag,filetype){
	var count=0;//多次修改进行提示  @author lele
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : cmdselid, 
	    multi_selection: multifileflag,
		container: document.getElementById('container'),
		flash_swf_url : 'js/Moxie.swf',
		silverlight_xap_url : 'js/Moxie.xap',
//	    url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
		url : 'http://img.saharabuy.com/',
	    filters : {
	    	max_file_size : '100mb',
	    	mime_types : [
	    	    {title : "Image files",extensions : filetype} 
	    	    ]
	    	},
		init: {
			PostInit: function() {

	    		document.getElementById(cmdupload.attr('id')).onclick = function() {
	    			uploader.start();
		            return false;
				};
				//document.getElementById(cmdupload.attr('id')).unbind("onclick",function(){}); 
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file){
					document.getElementById(ossfile.attr('id')).innerHTML = '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
					+'</div>'; //+= 修改为"="
				});
			},
			
			BeforeUpload: function(up, file) {
				/*多次修改进行提示  @author lele
				if(count>0 && !window.confirm("多次上传只会以最后一次为准！")) return false;*/
	            set_upload_param(up, file.name, true);
	            var d = document.getElementById(file.id);
	            if(d==null)
	            	return;
				d.getElementsByTagName('b')[0].innerHTML = "<span>正在上传，请稍等...</span>";
	        },
	        
			UploadProgress: function(up, file) {
//				var d = document.getElementById(file.id);
//				d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
//	            
//	            var prog = d.getElementsByTagName('div')[0];
//				var progBar = prog.getElementsByTagName('div')[0]
//				progBar.style.width= 2*file.percent+'px';
//				progBar.setAttribute('aria-valuenow', file.percent);
			},
			FileUploaded: function(up, file, info) {
				var d = document.getElementById(file.id);
				count++;
				d.getElementsByTagName('b')[0].innerHTML="上传成功";
				document.getElementById(rettext.attr('id')).value=g_object_name;
				//rettext.attr('value')=g_object_name;
//	            if (info.status == 200)
//	            {
//	            	
//	                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = file.name+'文件上传success';
//	            }
//	            else
//	            {
//
//	                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
//	            } 
			},
			Error: function(up, err){
				console.log(err);
	            //set_upload_param(up);
				//document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
			}
		}
	});

	uploader.init();
}
/**
 * 描述 : 多图片上传
 * @param cmdselid
 * @param cmdupload
 * @param ossfile
 * @param filetype
 * @param callFlag   1 : 商品详细图片;   2 : 会员资质
 */
function createuploadermuti(cmdselid,cmdupload,ossfile,filetype,callFlag){
	var fileNum = 0; //图片List的计数器
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : cmdselid, 
	    multi_selection: false,
		container: document.getElementById('container'),
		flash_swf_url : 'js/Moxie.swf',
		silverlight_xap_url : 'js/Moxie.xap',
//	    url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
		url : 'http://img.saharabuy.com/',
	    filters : {
	    	max_file_size : '100mb',
	    	mime_types : [
	    	    {title : "Image files",extensions : filetype} 
	    	    ]
	    	},
		init: {
			PostInit: function() {
	    		document.getElementById(cmdupload.attr('id')).onclick = function() {
	    			uploader.start();
		            return false;
				};
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					var pardiv=document.getElementById(ossfile.attr('id'));
					var temp = document.createElement('div');
					if(callFlag == 1){
						temp.innerHTML = '<div id="' + file.id + '">' + file.name 
						+'<input name="goodsPhotoList[' + fileNum + '].photourl" type="text"  id="tpmsvalue'+file.id+'"/><br/><b></b>'
						+'<br/>图片描述：<input name="goodsPhotoList[' + fileNum + '].imgnote" type="text"  id="tpms'+file.id+'" />'
						+'</div>';
					}else if (callFlag == 2){
						temp.innerHTML = '<div id="' + file.id + '">' + file.name 
						+'<input  name="goodsPhotoList[' + fileNum + ']" type="hidden"  id="tpmsvalue'+file.id+'"/><b></b>'
						+'</div>';
					}
					pardiv.appendChild(temp);
					fileNum++;
				});
			},
			
			BeforeUpload: function(up, file) {
				//alert('start upload');
	            set_upload_param(up, file.name, true);
	            var d = document.getElementById(file.id);
				d.getElementsByTagName('b')[0].innerHTML = "<span>正在上传，请稍等!</span>";
	        },
	        
			UploadProgress: function(up, file) {
			},
			FileUploaded: function(up, file, info) {
				var d = document.getElementById(file.id);
				d.getElementsByTagName('b')[0].innerHTML="上传成功";
				document.getElementById("tpmsvalue"+file.id).value=g_object_name;
//	            if (info.status == 200)
//	            {
//	                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = file.name+'文件上传success';
//	            }
//	            else
//	            {
//	                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
//	            } 
			},
			Error: function(up, err){
				console.log(err);
	            //set_upload_param(up);
				//document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
			}
		}
	});

	uploader.init();
}

function createup1(cmdselid,filetype){
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : cmdselid, 
	    multi_selection: false,
		container: document.getElementById('container'),
		flash_swf_url : 'js/Moxie.swf',
		silverlight_xap_url : 'js/Moxie.xap',
//	    url : 'http://zlw-file.oss-cn-beijing.aliyuncs.com',
		url : 'http://img.saharabuy.com/',
	    filters : {
	    	max_file_size : '100mb',
	    	mime_types : [
	    	    {title : "Image files",extensions : filetype} 
	    	    ]
	    	},
	
		init: {
			PostInit: function() {
//	    		document.getElementById(cmdupload.attr('id')).onclick = function() {
//	    			alert(cmdupload.attr('id'));
//	    			uploader.start();
//		            return false;
//				};
			},
	
			FilesAdded: function(up, files) {
				uploader.start();
			},
	
			BeforeUpload: function(up, file) {
				set_upload_param(up, file.name, true);
				/*alert("中国");*/
	        },
	
			UploadProgress: function(up, file) {
			},
	
			FileUploaded: function(up, file, info) {
				pageinsertimage(g_object_name);
			},
	
			Error: function(up, err) {
				console.log(err);
				//set_upload_param(up);
				//document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
			}
		}
	});
	
	uploader.init();

}
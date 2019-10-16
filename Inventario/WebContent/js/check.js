
var load = "checkLogout/Open";
var unload = "checkLogout/Close";

window.onload = fload;
window.onunload = funload;

function fload()
{
	var xmlHttp;	
	if(window.ActiveXObject){
        	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	else 
		if(window.XMLHttpRequest){
	        	xmlHttp=new XMLHttpRequest();
		}	
	var url = top.location.href.split(top.location.href.split("/")[4])[0] + "common/checkOpenSecurity.htm";
	xmlHttp.open("GET",url,true);
	xmlHttp.send(null);
}

function funload()
{
	var xmlHttp;	
	if(window.ActiveXObject){
        	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	else 
		if(window.XMLHttpRequest){
	        	xmlHttp=new XMLHttpRequest();
		}	
	var url = top.location.href.split(top.location.href.split("/")[4])[0] + "common/checkCloseSecurity.htm";
	xmlHttp.open("GET",url,true);
	xmlHttp.send(null);
}
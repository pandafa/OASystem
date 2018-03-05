//验证方法
function checkFromSendMsg(){
	var title = document.getElementById("mtitle").value;
	if(!title){
		alert("请填写标题！");
		event.preventDefault();
		return;
	}
	var context = document.getElementById("mcontext").value;
	if(!context){
		alert("请填写内容！");
		event.preventDefault();
		return;
	}
	var kind = document.getElementById("mkind").value;
	if(kind=='0'){
		alert("请选择类型！");
		event.preventDefault();
		return;
	}

	var showKindG = document.getElementById("kindAcceptg");
	var showKindP = document.getElementById("kindAcceptp");
	var showKindGP = document.getElementById("kindAcceptgp");
	var showKindPerson = document.getElementById("kindAcceptPerson");
	if(showKindG.hidden!=true){
		var temp1 = document.getElementById("macceptg").value;
		if(temp1==-1){
			alert("请选择接受小组！");
			event.preventDefault();
			return;
		}
	}else if(showKindP.hidden!=true){
		var temp1 = document.getElementById("macceptp").value;
		if(temp1==-1){
			alert("请选择接受部门！");
			event.preventDefault();
			return;
		}
	}else if(showKindGP.hidden!=true){
		var temp1 = document.getElementById("macceptpPart").value;
		if(temp1==-1){
			alert("请选择接受部门！");
			event.preventDefault();
			return;
		}
		var temp2 = document.getElementById("macceptgGroup").value;
		if(temp2==-1){
			alert("请选择接受小组！");
			event.preventDefault();
			return;
		}
	}else if(showKindPerson.hidden!=true){
		var jobId = document.getElementById("macceptJobId").value;
		if(!jobId){
			alert("请填写接受者的工号！");
			event.preventDefault();
			return;
		}
	}

}

//获取小组或部门信息
//kind是要获取部门还是小组
//ID  是要更改的id
function getPartOrGroup(id,kind,$withPart = false){
	var toChange = document.getElementById(id);
	var postStr = 'kind='+kind;//获取类型，获取部门还是小组
	postStr += '&userId=';//使用者的jobId
	if(withPart==true){
		var source = document.getElementById("macceptpPart");
		postStr += '&partId='+source.value;//部门的ID
	}
	//异步开始
	var xmlhttp=new XMLHttpRequest();
    if (window.XMLHttpRequest){
        xmlhttp=new XMLHttpRequest();
    }else{
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function(){
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            //删除以前
            while(toChange.length!=1){
                toChange.remove(1);
            }

            var returnStr = xmlhttp.responseText;
            //分组
            var ress = returnStr.split(',');
            for(var i=0;i<ress.length;i++){
                var res = ress[i].split(':');
                var newOption = document.createElement("option");
                newOption.value=res[0];//获取值
                newOption.text=res[1];//获取文字
                toChange.add(newOption);
            }
        }
	}
	xmlhttp.open("POST","ajax2Post.action",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(postStr);

}

//显示填选内容
function showSendAccept(){
	var showKindG = document.getElementById("kindAcceptg");
	var showKindP = document.getElementById("kindAcceptp");
	var showKindGP = document.getElementById("kindAcceptgp");
	var showKindPerson = document.getElementById("kindAcceptPerson");
	showKindG.hidden="true";
	showKindP.hidden="true";
	showKindGP.hidden="true";
	showKindPerson.hidden="true";
	var selectKind = document.getElementById("mkind").value;
	var userKind = document.getElementById("mUserKind").value;
	if(selectKind=='part' && (userKind=='0' || userKind=='1')){
		showKindP.hidden="";//显示
		getPartOrGroup('macceptp','part');//获取数据
	}else if(selectKind=='person'){
		showKindPerson.hidden="";
	}else if(selectKind=='group' && userKind=='0' ){
		showKindGP.hidden="";
		getPartOrGroup('macceptpPart','part');
		var group = document.getElementById('macceptpPart');
		if(group.addEventListener){
			group.addEventListener('change',function(){
				getPartOrGroup('macceptgGroup','group',true);
			});
		}else{
			group.attachEvent("onchange",function(){
				getPartOrGroup('macceptgGroup','group',true);
			});
		}
		
	}else if(selectKind=='group' && (userKind=='2' || userKind=='1')){
		showKindG.hidden="";
		getPartOrGroup('macceptg','group');
	}else if(selectKind=='company'){
	}
	else{
		document.getElementById("mkind").value='0';
		alert("无权限选择");
	}
}

//加载完成
window.addEventListener("load",function(){
	//表单提交验证
	var myFromMsg = document.getElementById("myFormSendNotice");
	if(myFromMsg.addEventListener){
		myFromMsg.addEventListener("submit",function(){
			checkFromSendMsg();
		});
	}else{
		myFromMsg.attachEvent("onsubmit",function(){
			checkFromSendMsg();
		});
	}
	//显示填选内容
	var mySelect = document.getElementById("mkind");
	if(mySelect.addEventListener){
		mySelect.addEventListener("change",function(){
			showSendAccept();
		});
	}else{
		mySelect.attachEvent("onchange",function(){
			showSendAccept();
		});
	}
});
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
	int tuserKindNumber = 1;
--%>
<%--
	//假造数据
	request.setAttribute("userKindNumber", tuserKindNumber);//用户类型 0 1 2 3
--%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>发送公告</title>
		<!-- message/sendNotice.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">

		<script type="text/javascript">
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
			function getPartOrGroup(id,kind){
				var toChange = document.getElementById(id);
				//删除以前
	            while(toChange.length!=1){
	                toChange.remove(1);
	            }
				var postStr = 'kind='+kind;//获取类型，获取部门还是小组
				postStr += '&userId=';//使用者的jobId
				if(arguments[2]!=undefined && arguments[2]==true){
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
			    console.log(postStr);
			    if(kind=='part'){
			    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getAllPartsAjax.do",true);
			    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			    	xmlhttp.send(postStr);
			    }
			    else if(kind='group'){
			    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getGroupAjax.do",true);
			    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			    	xmlhttp.send(postStr);
			    }
			    
	
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
				if(selectKind=='part' && userKind=='0'){
					showKindP.hidden="";//显示
					getPartOrGroup('macceptp','part');//获取数据
				}else if(selectKind=='person'){
					showKindPerson.hidden="";
				}else if(selectKind=='group' && userKind=='0' ){
					showKindGP.hidden="";
					getPartOrGroup('macceptpPart','part');
					var group = document.getElementById('macceptpPart');
					group.onchange=function(){
						if(group.value!=-1){
							getPartOrGroup('macceptgGroup','group',true);
						}
					}
				}else if(selectKind=='group' && userKind=='1'){
					showKindG.hidden="";
					getPartOrGroup('macceptg','group');
				}else if(selectKind=='company'){
				}else if((selectKind=='part' && userKind=='1') || (selectKind=='group' && userKind=='2')){
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
						if(mySelect.value!='-1'){
							showSendAccept();
						}
					});
				}else{
					mySelect.attachEvent("onchange",function(){
						if(mySelect.value!='-1'){
							showSendAccept();
						}
					});
				}
			});
	
		</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>发布公告</span>
				</div>
				<div class="panel-body">
					<h3 align="center">发布公告</h3>
					<form action="${pageContext.request.contextPath}/message/sendNoticeForm.do" method="post" id="myFormSendNotice" class="form-horizontal">
						<input type="hidden" id="mUserKind" value="${userKindNumber }" />
						<div class="form-group">
							<label for="mtitle" class="col-sm-2 control-label">公告标题</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="mtitle" name="mtitle" placeholder="填写公告的标题">
							</div>
						</div>
						<!--消息类型-->
						<div class="form-group">
							<label for="mkind" class="col-sm-2 control-label">公告类型</label>
							<div class="col-sm-10 ">
								<select id="mkind" name="mkind">
									<option value="0" selected="selected">——选择类型——</option>
									<option value="group">小组公告</option>
									<option value="part">部门公告</option>
									<option value="company">公司公告</option>
								</select>
							</div>
						</div>
						<!--接受者 01-->
						<div id="kindAcceptg" class="form-group" hidden="true">
							<label for="maccept" class="col-sm-2 control-label">接受者</label>
							<div class="col-sm-10 ">
								<span>小组：</span>
								<select id="macceptg" name="macceptg">
									<option value="-1">——选择小组——</option>
								</select>
							</div>
						</div>
						<!--接受者 02-->
						<div id="kindAcceptp" class="form-group" hidden="true">
							<label for="maccept" class="col-sm-2 control-label">接受者</label>
							<div class="col-sm-10 ">
								<span>部门：</span>
								<select id="macceptp" name="macceptp">
									<option value="-1">——选择部门——</option>
								</select>
							</div>
						</div>
						<!--接受者 03-->
						<div id="kindAcceptgp" class="form-group" hidden="true">
							<label for="maccept" class="col-sm-2 control-label">接受者</label>
							<div class="col-sm-10 ">
								<span>部门：</span>
								<select id="macceptpPart" name="macceptpPart">
									<option value="-1">——选择部门——</option>
								</select>
								<span>小组：</span>
								<select id="macceptgGroup" name="macceptgGroup">
									<option value="-1">——选择小组——</option>
								</select>
							</div>
						</div>
						<!--接受者 04-->
						<div id="kindAcceptPerson" class="form-group" hidden="true">
							<label for="maccept" class="col-sm-2 control-label">接受者</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="macceptJobId" id="macceptJobId" placeholder="填写接受者的工号">
							</div>
						</div>

						<div class="form-group">
							<label for="mcontext" class="col-sm-2 control-label">公告内容</label>
							<div class="col-sm-10">
								<textarea class="form-control" id="mcontext" name="mcontext" placeholder="请填写公告内容。最多500个字符" rows="5" maxlength="500"></textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input value="发送" type="submit" class="btn btn-success" />
								<input value="重置" type="reset" class="btn btn-default" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
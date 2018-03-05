<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>用户管理——修改</title>
		<!-- manager/userManagerEdit.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
		<script type="text/javascript">
			//验证方法
			function checkFromUserInfoEdit(){
				var name = document.getElementById("userName").value;
				if(!name){
					alert("请填写姓名！");
					event.preventDefault();
					return;
				}
				var name = document.getElementById("jobId").value;
				if(!name){
					alert("请填写工号！");
					event.preventDefault();
					return;
				}
				var name = document.getElementById("cardId").value;
				if(!name){
					alert("请填写身份证号码！");
					event.preventDefault();
					return;
				}
			}
			
			//获取小组
            function getGroup(){
                var sourcee = document.getElementById('part');
                var toChange = document.getElementById('group');
                var xmlhttp=new XMLHttpRequest();
                if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                if(xmlhttp.readyState==4 && xmlhttp.status==200){
                    //删除以前
                    while(toChange.length!=0){
                        toChange.remove(0);
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
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getGroupAjax.do",true);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("partId="+sourcee.value);

            }
	
			//加载完成
			window.addEventListener("load",function(){
				//表单提交验证
				var myFormLogin = document.getElementById("myFormUserInfoEdit");
				if(myFormLogin.addEventListener){
					myFormLogin.addEventListener("submit",function(){
						checkFromUserInfoEdit();
					});
				}else{
					myFormLogin.attachEvent("onsubmit",function(){
						checkFromUserInfoEdit();
					});
				}
				
				//
                var part = document.getElementById("part");
				if(part.addEventListener){
					part.addEventListener("change",function(){
						getGroup();
					});
				}else{
					part.attachEvent("onchange",function(){
						getGroup();
					});
				}
			});
		</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>用户管理——修改</span>
				</div>
				<div class="panel-body">
					<div class="container-fluid">
						<h3 align="center">用户信息修改</h3>
						<br />
						<form action="${pageContext.request.contextPath}/manage/changeUserInfoAllFrom.do" method="post" id="myFormUserInfoEdit" class="form-horizontal">
							<table class="table">
								<tr>
									<td>姓名</td>
									<td colspan="2"><input type="text" class="form-control" value="${umeName }" id="userName" name="userName" /></td>
									<td>性别</td>
									<td colspan="2">
										<select class="form-control" id="userSex" name="userSex">
											<option value="0" ${umeSex==0 ? 'selected="selected"':'' }>男</option>
											<option value="1" ${umeSex==1 ? 'selected="selected"':'' }>女</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>工号</td>
									<td colspan="2"><input type="text" class="form-control" value="${umeJobId }" id="jobId" name="jobId" readonly="readonly" /></td>
									<td>身份证号</td>
									<td colspan="2"><input type="text" class="form-control" value="${umeCardId }" id="cardId" name="cardId" /></td>
								</tr>
								<tr>
									<td>部门</td>
									<td colspan="2">
										<select class="form-control" name="part" id="part">
											<c:forEach items="${umePartList }" var="map">
												<option value="${map.get('id') }" ${umePart==map.get('id') ? 'selected="selected"':'' }>${map.get('name') }</option>
											</c:forEach>
										</select>
									</td>
									<td>分组</td>
									<td colspan="2">
										<select class="form-control" name="group" id="group">
											<c:forEach items="${umeGroupList }" var="map">
												<option value="${map.get('id') }" ${umeGroup==map.get('id') ? 'selected="selected"':'' }>${map.get('name') }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>电话</td>
									<td colspan="2"><input type="tel" class="form-control" value="${umeTel }" name="tel" id="tel" /></td>
									<td>邮箱</td>
									<td colspan="2"><input type="email" class="form-control" value="${umeEmail }" name="email" id="email" /></td>
								</tr>
								<tr>
									<td>地址</td>
									<td colspan="5"><input type="text" class="form-control" value="${umeAddr }" name="addr" id="addr" /></td>
								</tr>
								<tr>
									<td>用户状态</td>
									<td colspan="2">
										<select class="form-control" name="userStatus" id="userStatus">
											<option value="1" ${umeStatue==1 ? 'selected="selected"':'' }>正常使用</option>
											<option value="2" ${umeStatue==2 ? 'selected="selected"':'' }>禁用</option>
											<option value="0" ${umeStatue==0 ? 'selected="selected"':'' }>未激活</option>
											<option value="3" ${umeStatue==3 ? 'selected="selected"':'' }>异常（禁止登陆）</option>
											<option value="4" ${umeStatue==4 ? 'selected="selected"':'' }>冻结（15分钟）</option>
											<option value="5" ${umeStatue==5 ? 'selected="selected"':'' }>冻结（30分钟）</option>
											<option value="6" ${umeStatue==6 ? 'selected="selected"':'' }>冻结（24小时）</option>
										</select>
									</td>
									<td>职务</td>
									<td colspan="2">
										<input type="text" class="form-control" value="${umePost }" name="post" id="post" />
									</td>
								</tr>

							</table>
							<div align="center">
								<input type="submit" value="修改" class="btn btn-success" />
								<input type="reset" value="重置" class="btn btn-default" />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
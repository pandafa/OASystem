<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>流程提交——填写</title>
		<!-- procedure/procedureSubmitEdit.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jspFile/js/jquery.shCircleLoader.js" type="text/javascript"></script>
    <script type="text/javascript">
        //验证方法
        function myCheckFromLogin(formName){
            //项目名称检查
            var temp = document.getElementById("ptprocedureName").value;
            if(!temp){
                alert("请填写项目名称！");
                event.preventDefault();
                return;
            }
            //小项检查
            var num = document.getElementById(formName).name;
            for(var i=1;i<=num;i++){
                var blankTemp = document.getElementById("ptsmallName"+i);
                if(blankTemp.placeholder!="必填"){
                    continue;
                }
                if(!blankTemp.value){
                    var title = document.getElementById("ptsmallTitle"+i).innerText;
                    alert("请填写"+title+"！");
                    event.preventDefault();
                    return;
                }
            }
            //文件检查
            var file = document.getElementById("ptsmallFile");
            var n = file.parentElement.id;
            if(n==1 && !file.value){
                alert("请上传文件");
                event.preventDefault();
                return;
            }
            $('#toWait').shCircleLoader({
                duration: 0.95,
                dotsRadius: 41,
                dots: 39,
                color: "red",
                keyframes: "0% {background: red; {prefix}transform: scale(1)} 20% {background: orange; {prefix}transform: scale(.4)} 40% {background: red; {prefix}transform: scale(0)} 50% {background: red; {prefix}transform: scale(1)} 70% {background: orange; {prefix}transform: scale(.4)} 90% {background: red; {prefix}transform: scale(0)} 100% {background: red; {prefix}transform: scale(1)}"
            });
        }

        //加载完成
        window.addEventListener("load",function(){
            //表单提交验证
            var myFormLogin = document.getElementById("myFormPSE");
            
            if(myFormLogin.addEventListener){
                myFormLogin.addEventListener("submit",function(){
                    myCheckFromLogin(myFormLogin.id);
                });
            }else{
                alert(myFormLogin.id);
                myFormLogin.attachEvent("onsubmit",function(){
                    myCheckFromLogin(myFormLogin.id);
                });
            }
        });

    </script>
	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">流程提交——填写</div>
				<div class="panel-body" id="toWait">
					<p class="lead">注意事项：${pseIllustrate }</p>
					<div>
						<h1 align="center">${pseTitle }表</h1>
						<form action="${pageContext.request.contextPath}/procedure/addNewProcedureForm.do" method="post" id="myFormPSE" name="${pseBlankMap.size() }" enctype="multipart/form-data" >
							<input type="hidden" name="modelId" value="${pseModelId }" />
							<table class="table table-bordered">
								<tr>
									<td>${pseProduceName }</td>
									<td colspan="3"><input type="text" name="ptprocedureName" id="ptprocedureName" placeholder="填写名称(必填)" class="form-control" /></td>
								</tr>
								<c:forEach var="mapIndex" varStatus="status" begin="1" end="${pseBlankMap.size() }" step="1">
									<c:choose>
										<c:when test="${mapIndex%2!=0 }">
											<tr>
												<td id="ptsmallTitle${mapIndex }">${pseBlankMap.get(mapIndex).get("name") }</td>
												<td><input type="text" name="ptsmallName${pseBlankMap.get(mapIndex).get('id') }" id="ptsmallName${mapIndex }" placeholder="${pseBlankMap.get(mapIndex).get('must') ? '必填':'选填' }" class="form-control" /></td>
											<c:if test="${mapIndex == pseBlankMap.size() }">
												</tr>
											</c:if>
										</c:when>
										<c:otherwise>
												<td id="ptsmallTitle${mapIndex }">${pseBlankMap.get(mapIndex).get("name") }</td>
												<td><input type="text" name="ptsmallName${pseBlankMap.get(mapIndex).get('id') }" id="ptsmallName${mapIndex }" placeholder="${pseBlankMap.get(mapIndex).get('must') ? '必填':'选填' }" class="form-control" /></td>
											</tr>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
								<c:choose>
									<c:when test="${pseEnclosure==1 }">
										<tr>
											<td>附件（必传）</td>
											<td colspan="3" id="${pseEnclosure }"><input type="file" id="ptsmallFile" name="ptsmallFile" placeholder="附件" class="form-control" /></td>
										</tr>
									</c:when>
									<c:when test="${pseEnclosure==2 }">
										<tr>
											<td>附件（选传）</td>
											<td colspan="3" id="${pseEnclosure }"><input type="file" id="ptsmallFile"  name="ptsmallFile" placeholder="附件" class="form-control" /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<div id="${pseEnclosure }"><input type="file" name="ptsmallFile" style="display:none"/></div>
									</c:otherwise>
								</c:choose>
								
								
							</table>
							<div align="center">
								<input type="submit" class="btn btn-success btn-lg" value="创建" />
								<!-- 创建完，到 procedure/procedureSubmitList.jsp -->
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
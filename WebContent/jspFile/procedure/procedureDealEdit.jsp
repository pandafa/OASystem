<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>需处理的流程——处理</title>
		<!-- procedure/procedureDealEdit.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">

		<script type="text/javascript">
			//验证并提交
			function checkAndSubmit(submitKind){
				//流程编号
				var lc = document.getElementById("plcId").value;
				//过程号
				var sp = document.getElementById("pspId").value;
				//意见
				var textCon = document.getElementById("procedureView").value;
				if(!textCon){
					alert("请填写意见！");
					event.preventDefault();
					return;
				}
				var temp = document.createElement("form");
				temp.action = '${pageContext.request.contextPath}/procedure/dealProcedureForm.do';//提交地址
				temp.method = "post";
				temp.style.display = "none";
				//流程编号
				var opt = document.createElement("textarea");
				opt.name = "proId";
				opt.value = lc;
				temp.appendChild(opt);
				//审批编号
				var opt = document.createElement("textarea");
				opt.name = "spid";
				opt.value = sp;
				temp.appendChild(opt);
				//审批内容
				var opt = document.createElement("textarea");
				opt.name = "content";
				opt.value = textCon;
				temp.appendChild(opt);
				//是否通过
				var opt = document.createElement("textarea");
				opt.name = "pass";
				if(submitKind){
					//通过
					opt.value = "yes";
				}else{
					//不通过
					opt.value = "no";
				}
				temp.appendChild(opt);
				document.body.appendChild(temp);
				temp.submit();
				return temp;
			}
	
			//加载完成
			window.addEventListener("load",function(){

				//通过验证
				var myBtnPass = document.getElementById("btnPass");
				if(myBtnPass.addEventListener){
					myBtnPass.addEventListener("click",function(){
						checkAndSubmit(true);
					});
				}else{
					myBtnPass.attachEvent("onclick",function(){
						checkAndSubmit(true);
					});
				}

				//不通过验证
				var myBtnNoPass = document.getElementById("btnNoPass");
				if(myBtnNoPass.addEventListener){
					myBtnNoPass.addEventListener("click",function(){
						checkAndSubmit(false);
					});
				}else{
					myBtnNoPass.attachEvent("onclick",function(){
						checkAndSubmit(false);
					});
				}
			});
			</script>

	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">
					<span>需处理流程——处理</span>
					<a href="${pageContext.request.contextPath}${pdeBackUrl }" class="btn btn-info pull-right">返回</a>
					<!-- 返回到procedure/procedureDealList.jsp -->
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<div>
						<h1 align="center">${pdeTname }表</h1>
						<p align="right">编号：${pdeId }</p>
						<table class="table table-bordered">
							<tr>
								<td>${pdeProduceNameTitle }</td>
								<td colspan="3">
									<p>${pdeProduceName }</p>
								</td>
							</tr>
							<tr>
								<td>提交人</td>
								<td>
									<p>${pdeCreatePerson }</p>
								</td>
								<td>申请时间</td>
								<td>
									<p>${pdeCreateDate }</p>
								</td>
							</tr>
							<tr>
								<td>提交人所属部门</td>
								<td>
									<p>${pdePart }</p>
								</td>
								<td>提交人所属小组</td>
								<td>
									<p>${pdeGroup }</p>
								</td>
							</tr>
							<c:forEach var="mapIndex" varStatus="status" begin="1" end="${pdeBlankMap.size() }">
								<c:choose>
									<c:when test="${mapIndex%2!=0 }">
										<c:if test="${mapIndex!=pdeBlankMap.size() }">
											<tr>
												<td>${pdeBlankMap.get(status.index).get('title') }</td>
												<td>
													<p>${pdeBlankMap.get(status.index).get('content') }</p>
												</td>
										</c:if>
										<c:if test="${mapIndex==pdeBlankMap.size() }">
											<tr>
												<td>${pdeBlankMap.get(status.index).get("title") }</td>
												<td>
													<p>${pdeBlankMap.get(status.index).get('content') }</p>
												</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
											<td>${pdeBlankMap.get(status.index).get("title") }</td>
											<td>
												<p>${pdeBlankMap.get(status.index).get('content') }</p>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:forEach varStatus="status" begin="1" end="${pdeProcedureMap.size() }">
								<c:if test="${pdeProcedureMap.get(status.index).get('isWorked')==true }">
									<!-- 审批完 -->
									<tr>
										<td>${pdeProcedureMap.get(status.index).get('title') }</td>
										<td colspan="3">
											<p>意见：${pdeProcedureMap.get(status.index).get('content') }</p>
											<p align="right">姓名：${pdeProcedureMap.get(status.index).get('name') }</p>
											<p align="right">时间：${pdeProcedureMap.get(status.index).get('time') }</p>
										</td>
									</tr>
								</c:if>
								<c:if test="${pdeProcedureMap.get(status.index).get('isWorked')==false && pdeProcedureMap.get(status.index).get('needWork')==false}">
									<!-- 未审批 -->
									<tr>
										<td>${pdeProcedureMap.get(status.index).get('title') }</td>
										<td colspan="3">
											<p>意见：（待审批）</p>
											<p align="right">姓名：</p>
											<p align="right">时间：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</p>
										</td>
									</tr>
								</c:if>
								<c:if test="${pdeProcedureMap.get(status.index).get('isWorked')==false && pdeProcedureMap.get(status.index).get('needWork')==true}">
									<!-- 待审批 -->
									<tr>
										<td>${pdeProcedureMap.get(status.index).get('title') }</td>
										<td colspan="3"><textarea id="procedureView" placeholder="请填写意见。" rows="5" class="form-control"></textarea>
											<div>
												<input type="button" id="btnPass" value="通过" class="btn btn-success" />
												<input type="button" id="btnNoPass" value="不通过" class="btn btn-danger" />
											</div>
											<div>
												<input type="hidden" id="pspId" value="${pdeProcedureMap.get(status.index).get('spId') }" />
												<input type="hidden" id="plcId" value="${pdeProcedureMap.get(status.index).get('proId') }" />
											</div>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
						<c:if test="${pdeShowFileName!=null }">
							<div>
								<span>附件：</span>
								<a href="${pageContext.request.contextPath}/procedure/downFile.do?realFile=${pdeFileName }&showFile=${pdeShowFileName }">${pdeShowFileName }</a>
								<!-- 到下载页面 -->
							</div>
						</c:if>
						<div>
							<span>备注：</span>
							<p>${pdeRemark }</p>
						</div>
					</div>
				</div>
			</div>

		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
	//从服务器中出来的东西
	//用于单纯测试JSP
	//列表
	List<Map<String,String>> tfilesList = null;//一页5个
	String tcurrentFile = "group";
	int tallPage = 5;
	int tcurrentPage = 1;
	int tcanUpload = 1;
--%>
<%--
	//用于单纯测试JSP
	//假造数据
	tfilesList = new ArrayList<Map<String,String>>();
	for(int i=0;i<5;i++){
		Map<String,String> map = new HashMap<String,String>();
		map.put("fileId", "55101"+i);
		map.put("showFileName", "文件名啊"+i);
		map.put("source", "小组一");
		map.put("size", "215KB");
		map.put("updateDate", "2017年10月1"+i+"日11:19:02");
		tfilesList.add(map);
	}
	
	request.setAttribute("fhFilesList", tfilesList);//文件列表
	request.setAttribute("fhCurrentFile", tcurrentFile);//当前的文件目录
	request.setAttribute("fhCanUpload", tcanUpload);//是否可以上传，1为可以
	request.setAttribute("allPage", tallPage);
	request.setAttribute("currentPage", tcurrentPage);
--%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>文件仓库</title>
		<!-- file/fileHome.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript">
			window.onload=function(){
				var canChangePart  = ${fhCanSelectPart ==-1 ? 'true':'false'};
				var canChangeGroup = ${fhCanSelectGroup==-1 ? 'true':'false'};
				//可变部门
				if(canChangePart){
					getPartA();
				}
				//不变部门，变小组
				if(!canChangePart && canChangeGroup){
					getGroupA();
				}
				//可变部门和小组
				if(canChangePart && canChangeGroup){
					document.getElementById('selectPart').onchange=function(){
						getGroupA();
					}
				}
				
			}
			
			//获取部门信息
			function getPartA(){
				var toChange = document.getElementById('selectPart');
				//删除以前
			    while(toChange.length!=0){
			        toChange.remove(0);
			    }
				var postStr = 'kind=part';//获取类型，获取部门
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
		    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getAllPartsAjax.do",true);
		    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		    	xmlhttp.send(postStr);
			}
			
			//获取小组信息
			function getGroupA(){
				var toChange = document.getElementById('selectGroup');
				//删除以前
			    while(toChange.length!=0){
			        toChange.remove(0);
			    }
				var postStr = 'kind=group';//获取类型，获取小组
				var source = document.getElementById('selectPart');
				postStr += '&partId='+source.value;//部门的ID
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
		    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getGroupAjax.do",true);
		    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		    	xmlhttp.send(postStr);
			    
			}
			
		</script>
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>文件仓库</span>
				</div>
				<div class="panel-body">
					<div>
						<ul class="nav nav-pills">
							<li role="presentation" ${fhCurrentFile=='company' ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/file/fileHome.do?kind=company">公司文件</a>
							</li>
							<li role="presentation" ${fhCurrentFile=='part' ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/file/fileHome.do?kind=part">部门文件</a>
							</li>
							<li role="presentation" ${fhCurrentFile=='group' ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/file/fileHome.do?kind=group">小组文件</a>
							</li>
							<c:if test="${fhCanUpload=='1' }">
								<li role="presentation" class="pull-right">
									<button class="btn btn-info" data-toggle="modal" data-target="#myModal">
								  		<span class="glyphicon glyphicon-open" aria-hidden="true"></span> 上传
								  	</button>
								</li>
							</c:if>
						</ul>
					</div>
					<div>
						<table class="table table-striped">
							<tr>
								<th colspan="3">文件名</th>
								<th>所属</th>
								<th>大小</th>
								<th>修改日期</th>
								<th>上传者</th>
								<th colspan="1">操作</th>
							</tr>
							<c:forEach items="${fhFilesList }" var="map">
								<tr>
									<td colspan="3">
										${map.get('showFileName') }
									</td>
									<td>
										${map.get('source') }
									</td>
									<td>
										<c:choose>
											<c:when test="${map.get('size')=='0' }">
												-
											</c:when>
											<c:otherwise>
												${map.get('size') }
											</c:otherwise>
										</c:choose>
										
									</td>
									<td>
										${map.get('updateDate') }
									</td>
									<td>
										${map.get('uploadPersonName') }
									</td>
									<td colspan="1">
										<a href="${pageContext.request.contextPath}/file/downFile.do?fileId=${map.get('fileId') }" class="btn btn-default btn-lg">
											<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
										</a>
										<a href="${pageContext.request.contextPath}/file/delFile.do?fileId=${map.get('fileId') }" class="btn btn-default btn-lg">
											<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
							
						</table>
					</div>
				</div>
				<div class="panel-footer"></div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传文件</h4>
					</div>
					<form action="${pageContext.request.contextPath}/file/uploadFileFrom.do" method="post" class="form-horizontal" enctype="multipart/form-data">
						<div class="modal-body">
							<c:choose>
								<c:when test="${fhFileKind=='company' }">
									<p>上传到：公司</p>
								</c:when>
								<c:when test="${fhFileKind=='part' }">
									<p>上传到：部门</p>
									<!-- 部门 -->
									<div class="form-group">
										<label for="selectPart" class="col-sm-2 control-label">部门</label>
										<div class="col-sm-10">
											<select id="selectPart" name="selectPart" class="form-control" ${fhCanSelectPart!=-1 ? 'readonly="readonly"':'' }>
												<c:if test="${fhCanSelectPart!=-1 }">
													<option value="${fhCanSelectPart }" selected="selected">${fhPartName }</option>
												</c:if>
											</select>
										</div>
									</div>
								</c:when>
								<c:when test="${fhFileKind=='group' }">
									<p>上传到：小组</p>
									<!-- 部门 -->
									<div class="form-group">
										<label for="selectPart" class="col-sm-2 control-label">部门</label>
										<div class="col-sm-10">
											<select id="selectPart" name="selectPart" class="form-control" ${fhCanSelectPart!=-1 ? 'readonly="readonly"':'' }>
												<c:if test="${fhCanSelectPart!=-1 }">
													<option value="${fhCanSelectPart }" selected="selected">${fhPartName }</option>
												</c:if>
											</select>
										</div>
									</div>
									<!-- 小组 -->
									<div class="form-group">
										<label for="selectGroup" class="col-sm-2 control-label">小组</label>
										<div class="col-sm-10">
											<select id="selectGroup" name="selectGroup" class="form-control" ${fhCanSelectGroup!=-1 ? 'readonly="readonly"':'' }>
												<c:if test="${fhCanSelectGroup!=-1 }">
													<option value="${fhCanSelectGroup }" selected="selected">${fhGroupName }</option>
												</c:if>
											</select>
										</div>
									</div>
								</c:when>
							</c:choose>
							<div class="form-group">
								<label for="file" class="col-sm-2 control-label">文件</label>
								<div class="col-sm-10">
									<input type="file" name="file" class="form-control" />
								</div>
							</div>
							<input type="hidden" name="fileKind" value="${fhFileKind }" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<button type="submit" class="btn btn-primary">上传</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
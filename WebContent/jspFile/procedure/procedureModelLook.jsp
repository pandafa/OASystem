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
		<title>流程列表——查看</title>
		<!-- procedure/procedureModelLook.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>
	    <script src="${pageContext.request.contextPath}/jspFile/js/go.js"></script>
	    <script src="${pageContext.request.contextPath}/jspFile/js/goSamples.js"></script>
	    <script src="${pageContext.request.contextPath}/jspFile/js/SerpentineLayout.js"></script>
	    <script type="text/javascript">
    window.onload = function() {

        var $ = go.GraphObject.make;
        
            myDiagram =
              $(go.Diagram, "myDiagramDiv",  // create a Diagram for the DIV HTML element
                {
                  initialContentAlignment: go.Spot.Center,
                  isTreePathToChildren: false,  // links go from child to parent
                  layout: $(SerpentineLayout)  // defined in SerpentineLayout.js
                });
        
            myDiagram.nodeTemplate =
              $(go.Node, go.Panel.Auto,
                $(go.Shape, { figure: "RoundedRectangle", fill: "white" },
                  new go.Binding("fill", "color")),
                $(go.TextBlock, { margin: 4 },
                  new go.Binding("text", "key")));
        
            myDiagram.linkTemplate =
              $(go.Link, go.Link.Orthogonal,
                { corner: 5 },
                $(go.Shape),
                $(go.Shape, { toArrow: "Standard" }));
        
            var model = new go.TreeModel();
            model.nodeParentKeyProperty = "next";
            model.nodeDataArray = [
            	<% 
            		Map<Integer,Map<String,Object>> mainMap = (Map<Integer,Map<String,Object>>)request.getAttribute("pseModelProcessMap");
            		if(mainMap!=null){
            	%>
            		{ key: "开始提交", next: "<%=mainMap.get(1).get("key") %>", color: "coral" },
            	<%
            			for(int i=1;i<mainMap.size();i++){
        		%>
        			{ key: "<%=mainMap.get(i).get("key") %>", next: "<%=mainMap.get(i+1).get("key") %>", color: "<%=mainMap.get(i).get("color") %>" },
        		<%
            			}
            	%>
            		{ key: "<%=mainMap.get(mainMap.size()).get("key") %>", next: "审批成功，结束", color: "coral" },
            		{ key: "审批成功，结束", color: "orange" }
            	<%
            		}
            	%>
            ];
            myDiagram.model = model;
        
  }
    </script>
	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">流程提交——填写</div>
				<div class="panel-body">
					<p class="lead">注意事项：${pseIllustrate }</p>
					<div>
						<h1 align="center">${pseTitle }表</h1>
							<table class="table table-bordered">
								<tr>
									<td>${pseProduceName }</td>
									<td colspan="3"><input type="text" disabled="disabled" placeholder="填写名称(必填)" class="form-control" /></td>
								</tr>
								<c:forEach var="mapIndex" varStatus="status" begin="1" end="${pseBlankMap.size() }" step="1">
									<c:choose>
										<c:when test="${mapIndex%2!=0 }">
											<tr>
												<td id="ptsmallTitle${mapIndex }">${pseBlankMap.get(mapIndex).get("name") }</td>
												<td><input type="text" disabled="disabled" placeholder="${pseBlankMap.get(mapIndex).get('must') ? '必填':'选填' }" class="form-control" /></td>
											<c:if test="${mapIndex == pseBlankMap.size() }">
												</tr>
											</c:if>
										</c:when>
										<c:otherwise>
												<td id="ptsmallTitle${mapIndex }">${pseBlankMap.get(mapIndex).get("name") }</td>
												<td><input type="text" disabled="disabled" placeholder="${pseBlankMap.get(mapIndex).get('must') ? '必填':'选填' }" class="form-control" /></td>
											</tr>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
								<c:if test="${pseEnclosure==1 }">
									<tr>
										<td>附件（必传）</td>
										<td colspan="3" id="${pseEnclosure }"><input type="file" disabled="disabled" placeholder="附件" class="form-control" /></td>
									</tr>
								</c:if>
								<c:if test="${pseEnclosure==2 }">
									<tr>
										<td>附件（选传）</td>
										<td colspan="3" id="${pseEnclosure }"><input type="file" disabled="disabled" placeholder="附件" class="form-control" /></td>
									</tr>
								</c:if>
								
							</table>
							<div align="center">
								<div id="myDiagramDiv" style="border: solid 1px black;height:400px"></div>
							</div>
					</div>
				</div>
			</div>

		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
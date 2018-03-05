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
		<title>群组管理——成员</title>
		<!-- manager/groupManagerMember.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript">
			function liveModel(jobId){
				var load = document.getElementById('modalLoad');
				var readly = document.getElementById('modalReadly');
                $('#myModal').modal('show');
                var outTime = setTimeout(function(){
                    $('#myModal').modal('hide');
                    alert('请求超时，请重新尝试。');
                },6000);
                if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200){
                    	clearTimeout(outTime);
                        var returnStr = xmlhttp.responseText;
                        //分组
                        console.log(returnStr);
                        document.getElementById('modelShowPost').value="";
                        var ress = returnStr.split(',');
                        for(var i=0;i<ress.length;i++){
                            var res = ress[i].split(':');
                            switch(res[0]){
                                case 'jobId':
                                    document.getElementById('modelShowJobId').setAttribute('value',res[1]);
                                    break;
                                case 'cardId':
                                    document.getElementById('modelShowCardId').value=res[1];
                                    break;
                                case 'name':
                                    document.getElementById('modelShowName').value=res[1];
                                    break;
                                case 'post':
                                    document.getElementById('modelShowPost').value=res[1];
                                    break;
                                case 'canShen':
                                    if(res[1]=='true'){
                                        changeModelSHen(true,jobId);
                                    }else{
                                        changeModelSHen(false,jobId);
                                    }
                                    break;
                            }
                        }
                        changeModelPart(jobId);
                        changeModelGroug(jobId);
                        document.getElementById('modelShowPart').onchange=function(jobId){
                        	//更改小组的选项
                        	changeModelGroupByPart('modelShowPart');
                        };
                        
                        load.hidden='true';//隐藏等待界面
                        readly.hidden='';//显示填选
                    }
                }
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getUserBaseInfoAjax.do",true);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("jobId="+jobId);
                
            }
			
			//异步获取某个部门的全部小组
			function changeModelGroupByPart(id){
				var source = document.getElementById('modelShowPart');
				if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200){
                    	var toChange = document.getElementById('modelShowGroup');
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
                            toChange.add(newOption,null);
                        }
                    }
                }
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getGroupAjax.do",false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("partId="+source.value);
			}
			
            
            //更改中，初始化部门
            function changeModelPart(jobId){
                if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200){
                        var toChange = document.getElementById("modelShowPart");
                        //删除以前
                        while(toChange.length!=0){
                            toChange.remove(0);
                        }
                        var returnStr = xmlhttp.responseText;
                        //分组
                        var ress = returnStr.split(',');
                        if(ress.length==1){
                            //不可变
                            toChange.disabled='disabled';
                            var newOption = document.createElement("option");
                            newOption.value=ress[0].split(':')[0];//获取值
                            newOption.text=ress[0].split(':')[1];//获取文字
							newOption.selected='selected';
							document.getElementById("modelShowPart2").value=newOption.value;
                            toChange.add(newOption,null);
                        }else{
							//可变
							toChange.removeAttribute('disabled');
                            var currentValue= ress[0].split(':')[0];
                            var currentName = ress[0].split(':')[1];
                            for(var i=1;i<ress.length;i++){
                                var res = ress[i].split(':');
                                var newOption = document.createElement("option");
                                if(res[0]==currentValue){
                                    //是当前
                                    newOption.selected='selected';
                                }
                                newOption.value=res[0];//获取值
                                newOption.text=res[1];//获取文字
                                toChange.add(newOption,null);
                            }
                        }
                        
                    }
                }
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getUserPartOrGroupAjax.do",false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("jobId="+jobId+"&kind=part");
            }
            
          //更改中，初始化小组
            function changeModelGroug(jobId){
                if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200){
                        var toChange = document.getElementById("modelShowGroup");
                        //删除以前
                        while(toChange.length!=0){
                            toChange.remove(0);
                        }
                        var returnStr = xmlhttp.responseText;
                        //分组
                        var ress = returnStr.split(',');
                        if(ress.length==1){
                            //不可变
							toChange.disabled='disabled';
                            var newOption = document.createElement("option");
                            newOption.value=ress[0].split(':')[0];//获取值
                            newOption.text=ress[0].split(':')[1];//获取文字
							newOption.selected='selected';
							document.getElementById("modelShowGroup2").value=newOption.value;
                            toChange.add(newOption,null);
                        }else{
							//可变
							toChange.removeAttribute('disabled');
                            var currentValue= ress[0].split(':')[0];
                            var currentName = ress[0].split(':')[1];
                            for(var i=1;i<ress.length;i++){
                                var res = ress[i].split(':');
                                var newOption = document.createElement("option");
                                if(res[0]==currentValue){
                                    //是当前
                                    newOption.selected='selected';
                                }
                                newOption.value=res[0];//获取值
                                newOption.text=res[1];//获取文字
                                toChange.add(newOption,null);
                            }
                        }
                    }else if(xmlhttp.status==404){
                    	$('#myModal').modal('hide');
                    	alert('获取失败，请重新获取！');
                    }
                    
                }
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getUserPartOrGroupAjax.do",false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("jobId="+jobId+"&kind=group");
            }
          	
          //更改中，初始化用户身份
            function changeModelSHen(canChance,jobId){
                if (window.XMLHttpRequest){
                    xmlhttp=new XMLHttpRequest();
                }else{
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200){
						var toChange = document.getElementById("modelShowUserKind");
                        if(!canChance){
							toChange.disabled='disabled';
							document.getElementById("modelShowPost").setAttribute('readOnly','readOnly');
						}else{
							toChange.removeAttribute('disabled');
							document.getElementById("modelShowPost").removeAttribute('readOnly');
						}
						while(toChange.length!=0){
                            toChange.remove(0);
                        }
                        var returnStr = xmlhttp.responseText;
                        //分组
                        var ress = returnStr.split(',');
                        var res = ress[0].split(':');
                        
                        for(var i=1;i<ress.length;i++){
                            var res = ress[i].split(':');
                            var newOption = document.createElement("option");
                            newOption.value=res[0];//获取值
                            newOption.text=res[1];//获取文字
                            toChange.add(newOption,null);
                        }
                        var res = ress[0].split(':');
                        for(var i=0;i<toChange.length;i++){
                            if(toChange.options[i].value==res[0]){
								//如果是当前，就选定
								if(!canChance){
									document.getElementById("modelShowUserKind2").value=res[0];
								}
                                toChange.options[i].selected='selected';
                                toChange.options[i].setAttribute('selected','selected');
                                console.log(toChange.options[i]);
                            }
                        }
                    }
                }
                xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getUserPartOrGroupAjax.do",false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send("jobId="+jobId+"&kind=shen");
            }
		</script>
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>群组管理——成员</span>
					<a href="#" class="pull-right btn btn-info">返回</a>
					<!-- 转到manager/groupManager.jsp -->
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<h3 align="center">成员管理</h3>
					<c:if test="${gmmPartList!=null }">
						<ul class="nav nav-tabs">
							<c:forEach items="${gmmPartList }" var="map">
								<li role="presentation" ${map.get('partId')==gmmCurrentPart ? 'class="active"':'' }>
									<a href="${pageContext.request.contextPath}/manage/groupManagerMember.do?partId=${map.get('partId') }">${map.get('partName') }</a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<ul class="nav nav-tabs">
						<c:forEach items="${gmmGroupList }" var="map">
							<li role="presentation" ${map.get('groupId')==gmmCurrentGroup ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/manage/groupManagerMember.do?partId=${gmmCurrentPart }&groupId=${map.get('groupId') }">${map.get('groupName') }</a>
							</li>
						</c:forEach>
						
					</ul>
					<table class="table table-hover">
						<tr>
							<th>序号</th>
							<th>工号</th>
							<th>身份证号</th>
							<th>姓名</th>
							<th>加入时间</th>
							<th>部门</th>
							<th>小组</th>
							<th>职务</th>
							<th>身份</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${gmmPersonList }" var="map" varStatus="status">
							<tr>
								<td>${status.index+1+(currentPage-1)*10 }</td>
								<td>${map.get('jobId') }</td>
								<td>${map.get('cardId') }</td>
								<td>${map.get('name') }</td>
								<td>${map.get('joinDate') }</td>
								<td>${map.get('part') }</td>
								<td>${map.get('ggroup') }</td>
								<td>${map.get('post') }</td>
								<td>${map.get('identity') }</td>
								<td>
									<a data-toggle="modal" onclick="liveModel('${map.get('jobId') }')">
										<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
						
					</table>
					<div align="center">
						<nav aria-label="Page navigation">
							<ul class="pagination">
							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/manage/groupManagerMember.do?page=${currentPage-1 }&partId=${gmmCurrentPart }&groupId=${gmmCurrentGroup }" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</c:if>
								<c:if test="${currentPage==1 }">
									<a aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</c:if>
							</li>
							<c:forEach var="pageNumber" begin="1" end="${allPage }">
								<li class="${pageNumber==currentPage ? 'active':'' }">
									<a href="${pageContext.request.contextPath}/manage/groupManagerMember.do?page=${pageNumber }&partId=${gmmCurrentPart }&groupId=${gmmCurrentGroup }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/manage/groupManagerMember.do?page=${currentPage+1 }&partId=${gmmCurrentPart }&groupId=${gmmCurrentGroup }" aria-label="Previous">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</c:if>
								<c:if test="${currentPage==allPage }">
									<a aria-label="Previous">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</c:if>
							</li>
						</ul>
						</nav>
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
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
						<h4 class="modal-title" id="myModalLabel">编辑成员</h4>
					</div>
					<div id="modalLoad" >
						<div class="modal-body">
						<p>加载数据中...</p>
						<div class="progress">
						  <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
						  </div>
						</div>
						</div>
					</div>
					<div id="modalReadly" hidden="true">
					<form class="form-horizontal" action="${pageContext.request.contextPath}/manage/changeMemberForm.do">
						<input type="hidden" name="currentPage" value="${currentPage }" />
						<input type="hidden" name="currentPartId" value="${gmmCurrentPart }" />
						<input type="hidden" name="currentGroupId" value="${gmmCurrentGroup }" />
						<div class="modal-body">
							<!-- 工号 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">工号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="modelShowJobId" value="" name="modelShowJobId" readonly />
								</div>
							</div>
							<!-- 身份证号 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">身份证号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="modelShowCardId" disabled readonly />
								</div>
							</div>
							<!-- 姓名 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">姓名</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="modelShowName" disabled readonly />
								</div>
							</div>
							<!-- 部门 -->
							<div class="form-group">
								<label for="modelShowPart" class="col-sm-2 control-label">部门</label>
								<div class="col-sm-10">
									<select id="modelShowPart" name="modelShowPart" class="form-control">
									</select>
								</div>
								<input type="hidden" name="modelShowPart2" id="modelShowPart2" value="" />
							</div>
							<!-- 小组 -->
							<div class="form-group">
								<label for="modelShowGroup" class="col-sm-2 control-label">小组</label>
								<div class="col-sm-10">
									<select id="modelShowGroup" name="modelShowGroup" class="form-control">
									</select>
								</div>
								<input type="hidden" name="modelShowGroup2" id="modelShowGroup2" value="" />
							</div>
							<!-- 身份 -->
							<div class="form-group">
								<label for="modelShowUserKind" class="col-sm-2 control-label">身份</label>
								<div class="col-sm-10">
									<select id="modelShowUserKind" name="modelShowUserKind" class="form-control">
									</select>
								</div>
								<input type="hidden" name="modelShowUserKind2" id="modelShowUserKind2" value="" />
							</div>
							<!-- 职务 -->
							<div class="form-group">
								<label for="modelShowPost" class="col-sm-2 control-label">职务</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="modelShowPost" id="modelShowPost" />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<button type="submit" class="btn btn-primary">修改</button>
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
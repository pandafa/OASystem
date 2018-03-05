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
		<title>流程模板编辑</title>
		<!-- procedure/procedureModelEdit.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript">
			//全局
			var blankAddNumber = 0;
			var processAddNumber=0;
	
			//验证方法
			function checkFromLogin(){
			    var temp = document.getElementById("pname");
			    if(!temp.value){
			        alert("请填写流程名称！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var temp = document.getElementById("pdescribe");
			    if(!temp.value){
			        alert("请填写流程简介！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var temp = document.getElementById("pillustration");
			    if(!temp.value){
			        alert("请填写填写说明！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var temp = document.getElementById("premark");
			    if(!temp.value){
			        alert("请填写填写备注！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var temp = document.getElementById("ptableTitle");
			    if(!temp.value){
			        alert("请填写表格题头！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var temp = document.getElementById("pprocedureTitle");
			    if(!temp.value){
			        alert("请填写项目名称！");
			        event.preventDefault();
			        temp.focus();
			        return;
			    }
			    var nump = document.getElementById("processNumber").value;
			    var numb = document.getElementById("blankNumber").value;
			    console.log("流程个数："+nump);
			    console.log("填选个数："+numb);
			    if(nump==0 || numb==0){
			        if(nump!=0){
			            alert("填写项不可为零！");
			        }else if(numb!=0){
			            alert("审批过程不可为零！");
			        }else{
			            alert("填写项和审批过程均不可为零！");
			        }
			        event.preventDefault();
			        return;
			    }
			    var i=0;
			    for(var j=1;j<=blankAddNumber;j++){
			        var tempEle = document.getElementById("pinputName"+j);
			        if(tempEle){
			            i++;
			            if(!tempEle.value){
			                alert("请填写填选项名称！");
			                event.preventDefault();
			                tempEle.focus();
			                return;
			            }
			            if(i==numb){
			                j+=blankAddNumber;
			            }
			        }
			    }
			    i=0;
			    for(var j=1;j<=processAddNumber;j++){
			        var tempEle = document.getElementById("pprocessName"+j);
			        if(tempEle){
			            i++;
			            if(!tempEle.value){
			                alert("请填写流程名称！");
			                event.preventDefault();
			                tempEle.focus();
			                return;
			            }
			            var tempPart = document.getElementById("ppart"+j);
			            if(tempPart.value==0){
			                alert("请选择部门！");
			                event.preventDefault();
			                return;
			            }
			            var tempPart = document.getElementById("pgroup"+j);
			            if(tempPart.value==0){
			                alert("请选择分组！");
			                event.preventDefault();
			                return;
			            }
			            var tempPart = document.getElementById("pperson"+j);
			            if(tempPart.value==0){
			                alert("请选择人员！");
			                event.preventDefault();
			                return;
			            }
			            if(i==nump){
			                j=processAddNumber;
			            }
			        }
			    }
			    
			}
	
			//更改填选项数目
			function changeBlankNumber(){
			    var num =  document.getElementById("tableBlank").getElementsByTagName("tr").length-1;
			    var nn = document.getElementById("blankNumber");
			    nn.value=num;
			    document.getElementById('blankNumberEnd').value=blankAddNumber;
			    console.log("填选项--->"+document.getElementById("blankNumber").value);
			}
	
			//更改流程数目
			function changeProcessNumber(){
			    var num =  document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
			    var nn = document.getElementById("processNumber");
			    nn.value=num;
			    document.getElementById('processNumberEnd').value=processAddNumber;
			    console.log("流程--->"+document.getElementById("processNumber").value);
			}
			function myElemoveDel(elementName){
			    var temp = document.getElementById(elementName);
			    temp.remove();
			    changeBlankNumber();
			    changeProcessNumber();
			}
	
			//填选项添加
			function myAddBlank(){
			    var tableBlank = document.getElementById("tableBlank");
			    var num =  ++blankAddNumber;//要添加的序号
			    console.log(num);
			    var newEle = document.createElement("tr");
			    newEle.id='blank'+num;
				newEle.innerHTML=''
			+'												<td>'+num+'</td>'
			+'												<td><input type="text" name="pinputName'+num+'" id="pinputName'+num+'" placeholder="填写'+num+'名称的标题"></td>'
			+'												<td>'
			+'													<select name="pinputMust'+num+'" id="pinputMust'+num+'">'
			+'														<option value="1">是</option>'
			+'														<option value="0">否</option>'
			+'													</select>'
			+'												</td>'
			+'												<td>'
			+'													<div class="btn btn-danger" onclick="myElemoveDel(\'blank'+num+'\')">'
			+'													<span class="glyphicon glyphicon-minus-sign"'
			+'														aria-hidden="true">&nbsp;删除</span>'
			+'													</div>'
			+'												</td>';
			    
			    var numIndex = document.getElementById("tableBlank").getElementsByTagName("tr").length-1;
			    if(tableBlank.getElementsByTagName("tbody").length!=0){
			    	tableBlank.getElementsByTagName("tbody")[0].appendChild(newEle);
			    }else{
			    	console.log('没有tbody');
			    	tableBlank.appendChild(newEle);
			    }
			    changeBlankNumber();
			    
			}
	
			//流程添加
			function myAddProcess(){
			    var tableBlank = document.getElementById("tableProcess");
			    var num =  ++processAddNumber;//要添加的序号
			    console.log(num);
			    var newEle = document.createElement("tr");
			    newEle.id='process'+num;
				newEle.innerHTML=''
			+'                    <td>'+num+'</td>'
			+'                    <td><input type="text" id="pprocessName'+num+'" name="pprocessName'+num+'" placeholder="填写过程'+num+'的标题"></td>'
			+'                    <td>'
			+'                        <select id="ppart'+num+'" name="ppart'+num+'">'
			+'                            <option value="0">——请选择——</option>'
			+'                        </select>'
			+'                    </td>'
			+'                    <td>'
			+'                        <select id="pgroup'+num+'" name="pgroup'+num+'">'
			+'                            <option value="0">——请选择——</option>'
			+'                        </select>'
			+'                    </td>'
			+'                    <td>'
			+'                        <select id="pperson'+num+'" name="pperson'+num+'">'
			+'                            <option value="0">——请选择——</option>'
			+'                        </select>'
			+'                    </td>'
			+'                    <td>'
			+'                        <div class="btn btn-danger" onclick="myElemoveDel(\'process'+num+'\')">'
			+'                        <span class="glyphicon glyphicon-minus-sign"'
			+'                            aria-hidden="true">&nbsp;删除</span>'
			+'                        </div>'
			+'                    </td>';
			    
			    var numIndex = document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
			    if(tableBlank.getElementsByTagName("tbody").length!=0){
			    	tableBlank.getElementsByTagName("tbody")[0].appendChild(newEle);
			    }else{
			    	console.log('没有tbody');
			    	tableBlank.appendChild(newEle);
			    }
			    addAjax(num);
			    changeProcessNumber();
			}
	
			//加载完成
			window.addEventListener("load",function(){
	
			    blankAddNumber = document.getElementById("tableBlank").getElementsByTagName("tr").length-1;
			    processAddNumber=document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
			    changeProcessNumber();
			    changeBlankNumber();
			    //表单提交验证
			    var myFormLogin = document.getElementById("myFormPME");
			    if(myFormLogin.addEventListener){
			        myFormLogin.addEventListener("submit",function(){
			            checkFromLogin();
			        });
			    }else{
			        myFormLogin.attachEvent("onsubmit",function(){
			            checkFromLogin();
			        });
			    }
	
			    //填选项添加按钮
			    var myBtnBlank = document.getElementById("btnAddBlank");
			    if(myBtnBlank.addEventListener){
			        myBtnBlank.addEventListener("click",function(){
			            myAddBlank();
			        });
			    }else{
			        myBtnBlank.attachEvent("onclick",function(){
			            myAddBlank();
			        });
			    }
	
			    //流程添加按钮
			    var myBtnProcess = document.getElementById("btnAddProcess");
			    if(myBtnProcess.addEventListener){
			        myBtnProcess.addEventListener("click",function(){
			            myAddProcess();
			        });
			    }else{
			        myBtnProcess.attachEvent("onclick",function(){
			            myAddProcess();
			        });
			    }
			    
			  	console.log('000-->'+processAddNumber);
			  	for(var i=1;i<=processAddNumber;i++){
			  		var tempI = i;
			  		addAjax(tempI);
			  		getPartA(tempI,false);
			  		getGroupA(tempI,false);
			  		getUserA(tempI,false);
			  		
			  	}
			});
			
			function addAjax(tempI){
				
				//填写完“过程名称”后，加载“审批部门”
				var myInputShenName = document.getElementById('pprocessName'+tempI);
		  		myInputShenName.onchange=function(){
					if(myInputShenName.value.trim().length>0){
					    getPartA(tempI,true);
					}
				}
			    //填写完“审批部门”后，加载“审批小组”
			    var myInputShenPart = document.getElementById('ppart'+tempI);
			    myInputShenPart.onchange=function(){
					if(myInputShenPart.value.trim().length>0){
						getGroupA(tempI,true);
					}
				}
			  	//填写完“审批小组”后，加载“审批人”
			    var myInputShenGroup = document.getElementById('pgroup'+tempI);
			    myInputShenGroup.onchange=function(){
					if(myInputShenGroup.value.trim().length>0){
						getUserA(tempI,true);
					}
				}
			}
			
	
			//获取部门信息
			function getPartA(num,needRemove){
				console.log(num);
				var toChange = document.getElementById('ppart'+num);
				//删除以前
			    while(toChange.length!=1 && needRemove){
			        toChange.remove(1);
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
			function getGroupA(num,needRemove){
				var toChange = document.getElementById('pgroup'+num);
				//删除以前
			    while(toChange.length!=1 && needRemove){
			        toChange.remove(1);
			    }
				var postStr = 'kind=group';//获取类型，获取小组
				var source = document.getElementById('ppart'+num);
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
			
			//获取人员信息
			function getUserA(num,needRemove){
				var toChange = document.getElementById('pperson'+num);
				//删除以前
			    while(toChange.length!=1 && needRemove){
			        toChange.remove(1);
			    }
				var sourceP = document.getElementById('ppart'+num);
				var postStr = 'partId='+sourceP.value;//部门的ID
				var sourceG = document.getElementById('pgroup'+num);
				postStr += '&groupId='+sourceG.value;//部门的ID
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
		    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getShenUserAjax.do",true);
		    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		    	xmlhttp.send(postStr);
			}

		</script>	
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">流程模板编辑</div>
				<div class="panel-body">
					<h3>模板编辑简要帮助</h3>
<pre>“流程名称”：即流程的主名字。
“流程简介”：阐述本流程的介绍。
“填写说明”：会在提交申请的时候显示。
“备注”    ：会在查看提交的流程中最下方的备注显示。
“表格题头”：会在查看提交的流程的详细中的题头显示。
“项目名称”：会在提交后的列表中的“项目名称”一栏中显示，即流程的副名字。
“填选项”  ：是自定义需要填写的内容。
“审批过程”：是对过程的审批流程，有顺序的。
（注：所有表格不可为空）</pre>
					<!--表单开始-->
					<form action="${pageContext.request.contextPath}/procedure/addNewModelForm.do" method="post" id="myFormPME" class="form-horizontal">
						<input type="hidden" id="processNumber" name="processNumber" value="0" />
						<input type="hidden" id="blankNumber" name="blankNumber" value="0" />
						<input type="hidden" id="processNumberEnd" name="processNumberEnd" value="0" />
						<input type="hidden" id="blankNumberEnd" name="blankNumberEnd" value="0" />
						<input type="hidden" id="modeId" name="modeId" value="${pmeIsNew==true ? '-1':pmeModelId }" />
						<!--流程名称-->
						<div class="form-group">
							<label for="pname" class="col-sm-2 control-label">流程名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" value="${pmeName }" name="pname" id="pname" placeholder="流程的名称">
							</div>
						</div>
						<!-- 流程简介-->
						<div class="form-group">
							<label for="pdescribe" class="col-sm-2 control-label">流程简介</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="pdescribe" id="pdescribe" placeholder="流程的简介。最多200个字符" rows="5" maxlength="200">${pmeIntroduction }</textarea>
							</div>
						</div>
						<!-- 填写说明-->
						<div class="form-group">
							<label for="pillustration" class="col-sm-2 control-label">填写说明</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="pillustration" id="pillustration" placeholder="流程的填写说明。最多500个字符" rows="5" maxlength="500">${pmeIllustrate }</textarea>
							</div>
						</div>
						<!-- 填写备注-->
						<div class="form-group">
							<label for="premark" class="col-sm-2 control-label">填写备注</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="premark" id="premark" placeholder="流程的备注。最多500个字符" rows="5" maxlength="500">${pmeRemark }</textarea>
							</div>
						</div>
						<!-- 表格题头 -->
						<div class="form-group">
							<label for="ptableTitle" class="col-sm-2 control-label">表格题头</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" value="${pmeTitle }" name="ptableTitle" id="ptableTitle" placeholder="表格的题目">
							</div>
						</div>
						<!--项目名称-->
						<div class="form-group">
							<label for="pprocedureTitle" class="col-sm-2 control-label">项目名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" value="${pmeProduceName }" name="pprocedureTitle" id="pprocedureTitle" placeholder="填写名称的标题">
							</div>
						</div>
						<!--附件-->
						<div class="form-group">
							<label for="pattachmentNeed" class="col-sm-2 control-label">上传附件</label>
							<div class="col-sm-10">
								<select class="form-control" name="pattachmentNeed" id="pattachmentNeed">
									<option value="0" ${pmeEnclosure==0 ? 'selected="selected"':'' }>不可上传</option>
									<option value="2" ${pmeEnclosure==2 ? 'selected="selected"':'' }>允许上传</option>
									<option value="1" ${pmeEnclosure==1 ? 'selected="selected"':'' }>必须上传</option>
								</select>
							</div>
						</div>
						<hr />
						<!--填选1-->
						<div class="form-group">

							<div class="container-fluid">
								<h3>填选项</h3>
								<table id="tableBlank" class="table table-striped">
									<tr>
										<th>序号</th>
										<th>填选名称</th>
										<th>是否必填</th>
										<th>操作${pmeIsNew }</th>
									</tr>
									<c:choose>
										<c:when test="${pmeIsNew }">
											<tr id="blank1">
												<td>1</td>
												<td><input type="text" name="pinputName1" id="pinputName1" placeholder="填写1名称的标题"></td>
												<td>
													<select name="pinputMust1" id="pinputMust1">
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
												</td>
												<td>
													<div class="btn btn-danger" onclick="myElemoveDel('blank1')">
													<span class="glyphicon glyphicon-minus-sign"
														aria-hidden="true">&nbsp;删除</span>
													</div>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach var="mapIndex" begin="1" end="${pmeBlankMap.size() }">
												<tr id="blank${mapIndex }">
													<td>${mapIndex }</td>
													<td><input type="text" value="${pmeBlankMap.get(mapIndex).get('name') }" name="pinputName${mapIndex }" id="pinputName${mapIndex }" placeholder="填写1名称的标题aaaa"></td>
													<td>
														<select name="pinputMust${mapIndex }" id="pinputMust${mapIndex }">
															<option value="1" ${pmeBlankMap.get(mapIndex).get('must')==true  ? 'selected="selected"':'' }>是</option>
															<option value="0" ${pmeBlankMap.get(mapIndex).get('must')==false ? 'selected="selected"':'' }>否</option>
														</select>
													</td>
													<td>
														<div class="btn btn-danger" onclick="myElemoveDel('blank${mapIndex}')">
														<span class="glyphicon glyphicon-minus-sign"
															aria-hidden="true">&nbsp;删除</span>
														</div>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									
								</table>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-4">
								<div id="btnAddBlank" class="btn btn-success btn-block">
									<span class="glyphicon glyphicon-plus-sign" aria-hidden="true">&nbsp;添加填选项</span>
								</div>
							</div>
						</div>
						<hr />

						<!-- 流程审批人 -->
						<div class="form-group">
							<div class="container-fluid">
								<h3>审批过程</h3>
								<table id="tableProcess" class="table table-striped">
									<tr>
										<th>过程顺序</th>
										<th>过程名称</th>
										<th>审批部门</th>
										<th>审批分组</th>
										<th>审批人</th>
										<th>操作</th>
									</tr>
									<c:choose>
										<c:when test="${pmeIsNew }">
											<tr id="process1">
												<td>1</td>
												<td><input type="text" id="pprocessName1" name="pprocessName1" placeholder="填写过程1的标题"></td>
												<td>
													<select id="ppart1" name="ppart1">
														<option value="0">——请选择——</option>
													</select>
												</td>
												<td>
													<select id="pgroup1" name="pgroup1">
														<option value="0">——请选择——</option>
													</select>
												</td>
												<td>
													<select id="pperson1" name="pperson1">
														<option value="0">——请选择——</option>
													</select>
												</td>
												<td>
													<div class="btn btn-danger" onclick="myElemoveDel('process1')">
													<span class="glyphicon glyphicon-minus-sign"
														aria-hidden="true">&nbsp;删除</span>
													</div>
												</td>
											</tr>
										</c:when>
										<c:otherwise>${pmeProcessMap.size() }
											<c:forEach var="mapIndex" begin="1" end="${pmeProcessMap.size() }">
												<tr id="process${mapIndex }">
													<td>${mapIndex }</td>
													<td><input type="text" name="pprocessName${mapIndex }" id="pprocessName${mapIndex }" value="${pmeProcessMap.get(mapIndex).get('name') }" placeholder="填写过程1的标题--"></td>
													<td>
														<select id="ppart${mapIndex }" name="ppart${mapIndex }">
															<option value="0">——请选择——</option>
															<option value="${pmeProcessMap.get(mapIndex).get('part') }" selected="selected">${pmeProcessMap.get(mapIndex).get('partName') }</option>
														</select>
													</td>
													<td>
														<select id="pgroup${mapIndex }" name="pgroup${mapIndex }">
															<option value="0">——请选择——</option>
															<option value="${pmeProcessMap.get(mapIndex).get('group') }" selected="selected">${pmeProcessMap.get(mapIndex).get('groupName') }</option>
														</select>
													</td>
													<td>
														<select id="pperson${mapIndex }" name="pperson${mapIndex }">
															<option value="0">——请选择——</option>
															<option value="${pmeProcessMap.get(mapIndex).get('personId') }" selected="selected">${pmeProcessMap.get(mapIndex).get('personName') }</option>
														</select>
													</td>
													<td>
														<div class="btn btn-danger" onclick="myElemoveDel('process${mapIndex }')">
														<span class="glyphicon glyphicon-minus-sign"
															aria-hidden="true">&nbsp;删除</span>
														</div>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									
									
								</table>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-4">
								<div id="btnAddProcess" class="btn btn-success btn-block">
									<span class="glyphicon glyphicon-plus-sign" aria-hidden="true">&nbsp;添加流程</span>
								</div>
							</div>
						</div>
						<hr />

						<div class="form-group">
							<div class="col-sm-offset-1 col-sm-5">
								<input type="submit" value="创建" class="btn btn-warning btn-block" />
								<!-- 创建后返回procedure/procedureModel.jsp -->
							</div>
							<div class="col-sm-5">
								<input type="reset" value="重置" class="btn btn-default btn-block"/>
							</div>
						</div>

					</form>
					<!--表单结束-->
				</div>
			</div>

		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
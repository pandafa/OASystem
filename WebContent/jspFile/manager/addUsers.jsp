<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>添加新用户</title>
		<!-- manager/addUsers.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
					<script type="text/javascript">
						var allNum = 0;
						//验证方法
						function checkFromAdd(){
							var addNum = document.getElementById("myTableAddUser").getElementsByTagName("tr").length-1;
							var i=1;
							var findNum = 0;
							while(findNum!=addNum){
								var temp = document.getElementById("jobId"+i);
								if(temp!=null ){
									//存在
									findNum++;

									var ttemp1 = document.getElementById("jobId"+i).value;
									if(!ttemp1){
										alert("请填写工号！");
										event.preventDefault();
										return;
									}
									var ttemp2 = document.getElementById("cardId"+i).value;
									console.log("ttemp2:"+ttemp2);
									if(!ttemp2){
										alert("请填写身份证号码！");
										event.preventDefault();
										return;
									}
									var ttemp3 = document.getElementById("name"+i).value;
									if(!ttemp3){
										alert("请填写姓名！");
										event.preventDefault();
										return;
									}
									var ttemp4 = document.getElementById("part"+i).value;
									if(ttemp4==0){
										alert("请选择部门！");
										event.preventDefault();
										return;
									}
								}
								i++;
							}
							document.getElementById('userNumberAll').value=addNum;
							document.getElementById('userNumberEnd').value=allNum;
						}
						
						//ajax异步请求，判断jobId是否重复，并获取部门
						function checkJobIdAndGetPart(id){
							//console.log(id+'++-');
	                        var jobId = document.getElementById('jobId'+id);
	                        jobId.parentElement.className='has-error';
	                        var xmlhttp=new XMLHttpRequest();
	                        if (window.XMLHttpRequest){
	                            xmlhttp=new XMLHttpRequest();
	                        }else{
	                            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	                        }
	                        xmlhttp.onreadystatechange=function(){
	                            if(xmlhttp.readyState==4 && xmlhttp.status==200){
	                                var toChange = document.getElementById("part"+id);
	                                //删除以前
	                                while(toChange.length!=1){
	                                    toChange.remove(1);
	                                }
	                                //获取返回值
	                                var returnStr = xmlhttp.responseText;
	                                if(returnStr=='yes'){
	                                    //重复了
	                                	jobId.parentElement.className='has-error';
	                                    alert('“'+jobId.value+'”已存在，请重新输入');
	                                }else{
	                                    //没有重复，可以分组
	                                    jobId.parentElement.className='';
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
	                        }
	                        xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getPartsAjax.do",true);
	                        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=utf-8");
	                        xmlhttp.send("jobId="+jobId.value);//发送中含有jobId
	                    }
						

						function addUser(){
							var myTable = document.getElementById("myTableAddUser");
							var num = ++allNum;
							var newEle = myTable.insertRow(-1);
							newEle.id='newUser'+num;
							newEle.innerHTML=''
+'								<td>'+num+'</td>'
+'								<td><div><input type="text" placeholder="工号" name="jobId'+num+'" id="jobId'+num+'" class="form-control" /></div></td>'
+'								<td><input type="text" placeholder="身份证号码" name="cardId'+num+'" id="cardId'+num+'" class="form-control" /></td>'
+'								<td><input type="text" placeholder="姓名" name="name'+num+'" id="name'+num+'" class="form-control" /></td>'
+'								<td>'
+'									<select name="sex'+num+'" id="sex'+num+'" class="form-control">'
+'										<option value="man">男</option>'
+'										<option value="faman">女</option>'
+'									</select>'
+'								</td>'
+'								<td>'
+'									<select name="part'+num+'" id="part'+num+'" class="form-control">'
+'										<option value="0" selected="selected">——选择——</option>'
+'									</select>'
+'								</td>'
+'								<td><span onclick="delNewUser(\'newUser'+num+'\')" class="glyphicon glyphicon-trash" aria-hidden="true"></span></td>';
							var numIndex = document.getElementById("myTableAddUser").getElementsByTagName("tr").length-1;
							var toAdd = document.getElementById("jobId"+num);
	                        if(toAdd.addEventListener){
	                            toAdd.addEventListener("change",function(){
	                                checkJobIdAndGetPart(num);
	                            });
	                        }else{
	                            toAdd.attachEvent("onchange",function(){
	                                checkJobIdAndGetPart(num);
	                            });
	                        }
						}

						function delNewUser(userTempKind){
							var delEle = document.getElementById(userTempKind);
							delEle.remove();
						}
				
						//加载完成  
						window.addEventListener("load",function(){
							allNum =  document.getElementById("myTableAddUser").getElementsByTagName("tr").length-1;
							//表单提交验证
							var myFormAdd = document.getElementById("myFormAddUser");
							if(myFormAdd.addEventListener){
								myFormAdd.addEventListener("submit",function(){
									checkFromAdd();
								});
							}else{
								myFormAdd.attachEvent("onsubmit",function(){
									checkFromAdd();
								});
							}

							//添加新用户
							var myBtnAdd = document.getElementById("btnAddNewUser");
							if(myBtnAdd.addEventListener){
								myBtnAdd.addEventListener("click",function(){
									addUser();
								});
							}else{
								myBtnAdd.attachEvent("onclick",function(){
									addUser();
								});
							}
							
							//添加第一个的监听
							var toAdd = document.getElementById('jobId1');
	                        if(toAdd.addEventListener){
	                            toAdd.addEventListener("change",function(){
	                                checkJobIdAndGetPart('1');
	                            });
	                        }else{
	                            toAdd.attachEvent("onchange",function(){
	                                checkJobIdAndGetPart('1');
	                            });
	                        }
						});
					</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>添加新用户</span>
				</div>
				<div class="panel-body">
					<h3 align="center">添加新用户</h3>
					<br />
					<form id="myFormAddUser" action="${pageContext.request.contextPath}/manage/addUsersFrom.do" method="post" class="form">
						<input type="text" id="userNumberAll" name="userNumberAll" hidden="true" />
						<input type="text" id="userNumberEnd" name="userNumberEnd" hidden="true" />
						<table id="myTableAddUser" class="table">
							<tr>
								<th>序号</th>
								<th>工号</th>
								<th>身份证号</th>
								<th>姓名</th>
								<th>性别</th>
								<th>部门</th>
								<th>操作</th>
							</tr>
							<tr id="newUser1">
								<td>1</td>
								<td><div class=""><input type="text" placeholder="工号" name="jobId1" id="jobId1" class="form-control" /></div></td>
								<td><input type="text" placeholder="身份证号码" name="cardId1" id="cardId1" class="form-control" /></td>
								<td><input type="text" placeholder="姓名" name="name1" id="name1" class="form-control" /></td>
								<td>
									<select name="sex1" id="sex1" class="form-control">
										<option value="male">男</option>
										<option value="famale">女</option>
									</select>
								</td>
								<td>
									<select name="part1" id="part1" class="form-control">
										<option value="0" selected="selected">——选择——</option>
									</select>
								</td>
								<td><span onclick="delNewUser('newUser1')" class="glyphicon glyphicon-trash" aria-hidden="true"></span></td>
							</tr>
						</table>
						<div align="center">
							<input type="submit" value="全部创建" class="btn btn-success" />
							<input type="button" value="添加" id="btnAddNewUser" class="btn btn-info" />
						</div>
					</form>
				</div>
				<div class="panel-footer"></div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
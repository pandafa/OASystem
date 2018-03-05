<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>提示信息</title>
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<script type="text/javascript">
		onload = function checkMainPrompt() {
			var btn1 = document.getElementById('btnJump1');
			var btn2 = document.getElementById('btnJump2');
			if(btn1.addEventListener){
				btn1.addEventListener('click',function(){
					myJump();
				});
				btn2.addEventListener('click',function(){
					myJump();
				});
			}else{
				btn1.attachEvent('onclick',function(){
					myJump();
				});
				btn2.attachEvent('onclick',function(){
					myJump();
				});
			}
			$('#myBasePromptModal').modal('show')
		}
		function myJump(){
			var myUrl = document.getElementById('jumpUrl').value;
			location.href = '${pageContext.request.contextPath}'+myUrl;
		}
	</script>

	<body>

		<input value="${baseUrl }" id="jumpUrl" disabled="disabled" hidden="hidden" />
		<div class="modal fade" id="myBasePromptModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button id="btnJump1" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="">提示信息</h4>
					</div>
					<div class="modal-body">
						<div>
							<p>${baseContent }</p>
						</div>
					</div>
					<div class="modal-footer">
						<button id="btnJump2" type="button" id="" class="btn btn-primary" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>
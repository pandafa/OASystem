//全局
var blankAddNumber = 0;
var processAddNumber=0;

//验证方法
function checkFromLogin(){
    var temp = document.getElementById("pname").value;
    if(!temp){
        alert("请填写流程名称！");
        event.preventDefault();
        return;
    }
    var temp = document.getElementById("pdescribe").value;
    if(!temp){
        alert("请填写流程简介！");
        event.preventDefault();
        return;
    }
    var temp = document.getElementById("pillustration").value;
    if(!temp){
        alert("请填写填写说明！");
        event.preventDefault();
        return;
    }
    var temp = document.getElementById("premark").value;
    if(!temp){
        alert("请填写填写备注！");
        event.preventDefault();
        return;
    }
    var temp = document.getElementById("ptableTitle").value;
    if(!temp){
        alert("请填写表格题头！");
        event.preventDefault();
        return;
    }
    var temp = document.getElementById("pprocedureTitle").value;
    if(!temp){
        alert("请填写项目名称！");
        event.preventDefault();
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
    console.log("填选项--->"+document.getElementById("blankNumber").value);
}

//更改流程数目
function changeProcessNumber(){
    var num =  document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
    var nn = document.getElementById("processNumber");
    nn.value=num;
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
//    tableBlank.innerHTML +=''
//+'                                          <tr id="blank'+num+'">'
+'												<td>'+num+'</td>'
+'												<td><input type="text" name="pinputName'+num+'" id="pinputName'+num+'" placeholder="填写'+num+'名称的标题"></td>'
+'												<td>'
+'													<select name="pinputName'+num+'" id="pinputName'+num+'">'
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
//+'                                          </tr>';
    
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
//+'                <tr id="process'+num+'">'
+'                    <td>'+num+'</td>'
+'                    <td><input type="text" id="pprocessName'+num+'" placeholder="填写过程'+num+'的标题"></td>'
+'                    <td>'
+'                        <select id="ppart'+num+'" name="ppart'+num+'">'
+'                            <option value="0">——请选择——</option>'
+'                            <option value="1">部门1</option>'
+'                            <option value="2">部门2</option>'
+'                        </select>'
+'                    </td>'
+'                    <td>'
+'                        <select id="pgroup'+num+'" name="pgroup'+num+'">'
+'                            <option value="0">——请选择——</option>'
+'                            <option value="1">分组1</option>'
+'                            <option value="2">分组2</option>'
+'                        </select>'
+'                    </td>'
+'                    <td>'
+'                        <select id="pperson'+num+'" name="pperson'+num+'">'
+'                            <option value="0">——请选择——</option>'
+'                            <option value="1"=>人员1</option>'
+'                            <option value="2">人员2</option>'
+'                        </select>'
+'                    </td>'
+'                    <td>'
+'                        <div class="btn btn-danger" onclick="myElemoveDel(\'process'+num+'\')">'
+'                        <span class="glyphicon glyphicon-minus-sign"'
+'                            aria-hidden="true">&nbsp;删除</span>'
+'                        </div>'
+'                    </td>';
//+'                </tr>';
    
    var numIndex = document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
    if(tableBlank.getElementsByTagName("tbody").length!=0){
    	tableBlank.getElementsByTagName("tbody")[0].appendChild(newEle);
    }else{
    	console.log('没有tbody');
    	tableBlank.appendChild(newEle);
    }
    changeProcessNumber();
}

//加载完成
window.addEventListener("load",function(){

    changeProcessNumber();
    changeBlankNumber();
    blankAddNumber = document.getElementById("tableBlank").getElementsByTagName("tr").length-1;
    processAddNumber=document.getElementById("tableProcess").getElementsByTagName("tr").length-1;
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
    
  //填写完“过程名称”后，加载“审批部门”
    var myInputShenName = document.getElementById("pprocessName1");
    myInputShenName.onchange=function(){
		if(myInputShenName.value.trim().length>0){
			getPartOrGroup('ppart1','part');
		}
	}
    
});

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

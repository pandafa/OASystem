package action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.UserInfo;
import service.GroupService;
import service.PartService;
import service.UserService;
import util.CodeOfLogin;

@Controller
public class AjaxAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	
	private SimpleDateFormat sdf;
	
	public AjaxAction(){
		super();
		sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
	}
	
	/**
	 * 获取验证码
	 * 存codeStr，验证码字符。
	 * @param req
	 * @return 验证码所在的路径
	 */
	@RequestMapping(value="ajax/getLoginCodeAjax.do")
	@ResponseBody
	public String getLoginCode(HttpServletRequest req){
		HttpSession session = req.getSession();
		String codeStr = "";//验证码答案
		String codeCon = "";//验证码内容
		String path = "img/loginCode";//验证码路径
		
		ServletContext application = req.getServletContext();
		String realPath = application.getRealPath("img/loginCode");
		String fileName = new Date().getTime()+".jpg";
		File file = new File(realPath+File.separator+fileName);//File.separator
		int num1 = (int)(Math.random()*10);
		int num2 = (int)(Math.random()*10);
		int fu = (int)(new Date().getTime()%2);
		if(fu==1){
			codeStr = (num1+num2)+"";
			codeCon = num1+"+"+num2;
		}else{
			codeStr = (num1-num2)+"";
			codeCon = num1+"-"+num2;
		}
		codeCon += "=?";
		try {
			CodeOfLogin.outputImage(600,200,file,codeCon);
		} catch (IOException e) {
			e.printStackTrace();
			return "error,jpg";
		}
		String name = fileName;//验证码文件名
		session.setAttribute("codeStr", codeStr);
		return path+"/"+name;
	}
	
	/**
	 * 获取用户类型列表
	 * @param req
	 * @return Map<Integer, String>类型的列表
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, String> getUserKinds(HttpServletRequest request){
		return (Map<Integer, String>)request.getServletContext().getAttribute("app_userKindMap");
	}
	
	/**
	 * 异步，获取某部门的全部人员名字和JobId
	 * @param partId
	 * @param groupId
	 * @return 
	 */
	@RequestMapping(value="ajax/getShenUserAjax.do")
	@ResponseBody
	public String getShenUser(String partId,String groupId){
		String res = "";
		if(partId!=null && partId.length()!=0){
			if(groupId!=null && groupId.length()!=0){
				List<Map<String,Object>> list = userServer.getAllUserNameAndJobIdOfGroup(Integer.parseInt(partId),Integer.parseInt(groupId));
				if(list!=null && list.size()!=0){
					for(int i=0;i<list.size();i++){
						if(i!=0){
							res += ",";
						}
						res += (String)list.get(i).get("jobId");
						res += ":";
						int userKind = (int)list.get(i).get("kind");
						switch(userKind){
							case UserInfo.KIND_MANAGER_WEB:
								res += (String)list.get(i).get("name")+"（网站管理员，";
								break;
							case UserInfo.KIND_MANAGER_PART:
								res += (String)list.get(i).get("name")+"（部门管理员，";
								break;
							case UserInfo.KIND_MANAGER_GROUP:
								res += (String)list.get(i).get("name")+"（小组管理员，";
								break;
							default:
								res += (String)list.get(i).get("name")+"（";
								break;
						}
						if(list.get(i).get("post")==null){
							res += "暂无职务）";
						}else{
							res += (String)list.get(i).get("post")+"）";
						}
						
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * 异步，jobId是否重复
	 * @param jobId 需要查找的用户jobId
	 * @return 如果数据库中有就返回“yes”，否则为“no”
	 */
	@RequestMapping(value="ajax/hasUserAjax.do")
	@ResponseBody
	public String hasUser(String jobId){
		String res = "no";
		if(jobId!=null){
			if(userServer.hasUserByJobId(jobId)){
				res = "yes";
			}
		}
		return res;
	}
	
	/**
	 * 异步，jobId是否重复， 获取部门列表。
	 * 重复就返回“yes”。不重复则返回所有部门的id和名字，格式为：“id1:name1,id2:name2”
	 * @param jobId 新输入的jobId
	 * @param response
	 * @return
	 */
	@RequestMapping("ajax/getPartsAjax.do")
	@ResponseBody
	public String getParts(String jobId,HttpServletResponse response)  {
		String res = "";
		if(jobId!=null){
			if(userServer.hasUserByJobId(jobId)){
				return "yes";
			}
		}else{
			//没有获得
		}
		res = getAllPartsToStr();
		return res;
	}
	
	/**
	 * 获取所有部门的id和名字
	 * @return
	 */
	@RequestMapping("ajax/getAllPartsAjax.do")
	@ResponseBody
	public String getAllParts()  {
		String res = "";
		res = getAllPartsToStr();
		return res;
	}
	
	/**
	 * 获取部门列表。返回所有部门的id和名字，格式为：“id1:name1,id2:name2”
	 * @return
	 */
	private String getAllPartsToStr(){
		//获取名字和id
		String res = "";
		List<Map<String, Object>> list = partService.getAllPartsAndNames();
		if(list==null || list.size()==0){
			return res;
		}
		//处理数据格式
		for(int i=0;i<list.size();i++){
			if(i!=0){
				res += ",";
			}
			res += list.get(i).get("id");
			res += ":"+list.get(i).get("name");
		}
		return res;
	}
	
	/**
	 * 获取某个部门的全部小组的ID及其名字
	 * @param partId 某个部门的ID
	 * @param response
	 * @return
	 */
	@RequestMapping("ajax/getGroupAjax.do")
	@ResponseBody
	public String getGroups(String partId,HttpServletResponse response)  {
		String res = "";
		//获取名字和id
		List<Map<String, Object>> list = groupService.getAllGroupsOfPartNameAndId(Integer.parseInt(partId));
		if(list==null || list.size()==0){
			return res;
		}
		//处理数据格式
		for(int i=0;i<list.size();i++){
			if(i!=0){
				res += ",";
			}
			res += list.get(i).get("id");
			res += ":"+list.get(i).get("name");
		}
		return res;
	}
	
	/**
	 * 异步，获取用户的基本信息。包括：jobId、cardId、name和获取者是否可以更改被获取者权限。
	 * 权限是否为“true”和“false”
	 * @param jobId
	 * @param req
	 * @return
	 */
	@RequestMapping("ajax/getUserBaseInfoAjax.do")
	@ResponseBody
	public String getUserBaseInfo(String jobId,HttpServletRequest req)  {
		String returnStr = "";
		Map<String, Object> map = userServer.getPersonInfoAllByJobId(jobId);
		System.out.println(map);
		if(map!=null){
			returnStr +=  "jobId:"+(String)map.get("jobId");
			returnStr += ",cardId:"+(String)map.get("cardId");
			returnStr += ",name:"+(String)map.get("name");
			if(map.get("post")!=null){
				returnStr += ",post:"+(String)map.get("post");
			}
			if((int)map.get("kind")>userServer.getUserKindByJobId((String)req.getSession().getAttribute("userJobId"))){
				returnStr += ",canShen:true";
			}else{
				returnStr += ",canShen:false";
			}
			System.out.println(returnStr);
		}
		return returnStr;
	}
	
	/**
	 * 异步，获取某个用户所在的部门并附加全部部门
	 * 或用户所在的小组并附加全部部门里的小组
	 * 或用户的权限和全部权限
	 * @param jobId
	 * @param kind
	 * @param req
	 * @return
	 */
	@RequestMapping("ajax/getUserPartOrGroupAjax.do")
	@ResponseBody
	public String getUserPartOrGroup(String jobId,String kind,HttpServletRequest req){
		String returnStr = "";
		String userJobId = (String)req.getSession().getAttribute("userJobId");
		int partId = userServer.getUserPartByJobId(jobId);
		if(kind.equals("part")){
			returnStr += partId+":"+partService.getNameById(partId);
			if(userServer.getUserKindByJobId(userJobId)==UserInfo.KIND_MANAGER_WEB){
				//网站管理员可以改变部门
				List<Map<String, Object>> list = partService.getAllPartsAndNames();
//				System.out.println(list);
				for(Map<String, Object> m:list){
					returnStr += ","+(int)m.get("id")+":"+(String)m.get("name");
				}
			}else{
				//不可以改变
			}
		}else if(kind.equals("group")){
			int groupId = userServer.getUserGroupByJobId(jobId);
			returnStr += groupId+":"+groupService.getNameById(partId, groupId);
			if(userServer.getUserKindByJobId(userJobId)==UserInfo.KIND_MANAGER_WEB ||
				(userServer.getUserKindByJobId(userJobId)==UserInfo.KIND_MANAGER_PART && userServer.getUserPartByJobId(userJobId)==partId)){
				//网站管理员可以改变小组
				//本部门的部门管理员也可以改变小组
				List<Map<String, Object>> list = groupService.getAllGroupsOfPartNameAndId(partId);
//				System.out.println(list);
				for(Map<String, Object> m:list){
					returnStr += ","+(int)m.get("id")+":"+(String)m.get("name");
				}
			}else{
				//不可以改变
			}
		}else if(kind.equals("shen")){
			int userKind = userServer.getUserKindByJobId(jobId);
			returnStr += userKind+":";
			switch(userKind){
			case UserInfo.KIND_MANAGER_WEB:
				returnStr +="网站管理员";
				break;
			case UserInfo.KIND_MANAGER_PART:
				returnStr +="部门管理员";
				break;
			case UserInfo.KIND_MANAGER_GROUP:
				returnStr +="小组管理员";
				break;
			case UserInfo.KIND_MEMBER:
				returnStr +="普通成员";
				break;
			}
			Map<Integer, String> map = getUserKinds(req);
			Iterator<Integer> it = map.keySet().iterator();
			while(it.hasNext()){
				int tempNum = it.next();
				returnStr += ","+tempNum+":"+map.get(tempNum);
			}
		}
		return returnStr;
	}
	
	public UserService getUserServer() {
		return userServer;
	}

	public void setUserServer(UserService userServer) {
		this.userServer = userServer;
	}

	public PartService getPartService() {
		return partService;
	}

	public void setPartService(PartService partService) {
		this.partService = partService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
}

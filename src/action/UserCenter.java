package action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.UserInfo;
import service.GroupService;
import service.PartService;
import service.UserKindService;
import service.UserService;
import util.JumpPrompt;

@Controller
public class UserCenter {
	@Autowired
	private UserService userServer;
	@Autowired
	private UserKindService userKindService;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	
	/**
	 * 到修改个人密码页面
	 * @return
	 */
	@RequestMapping("/changePassword.do")
	public ModelAndView viewChangePassword(){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","user/changePassword.jsp");
		model.put("myPageTitle","修改个人密码");
		model.put("myPageNav","10");
		
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 到个人信息页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/personInfo.do")
	public ModelAndView viewInfo(HttpServletRequest req){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","user/persionInfo.jsp");
		model.put("myPageTitle","个人信息");
		model.put("myPageNav","11");
		
		HttpSession session = req.getSession();
		Map<String,Object> infoMap = userServer.getPersonInfoAllByJobId((String)session.getAttribute("userJobId"));
		if(infoMap==null){
			return JumpPrompt.jumpOfModelAndView("/home.do", "获取基本信息错误！/(ㄒoㄒ)/~~/(ㄒoㄒ)/~~<br/>"
					+ "（建议联系管理员）");
		}
		String tsex = "";
		if((int)infoMap.get("sex") == UserInfo.SEX_MALE){
			tsex = "男";
		}else{
			tsex = "女";
		}
		int groupId = (int)infoMap.get("ggroup");
		int partId = (int)infoMap.get("part");
		//获取小组名 groupService
		String groupName = groupService.getNameById(partId, groupId);
		//获取部门名 partService
		String partName = partService.getNameById(partId);
		//获取附加项
		Map<String,Object> res = userServer.getOtherInfoByJobId((String)infoMap.get("jobId"));
		
		model.put("piName", (String)infoMap.get("name"));
		model.put("piSex", tsex);
		model.put("piJobId", (String)infoMap.get("jobId"));
		model.put("piCardId", (String)infoMap.get("cardId"));
		model.put("piPart", partName);
		model.put("piGroup", groupName);
		if(infoMap.get("post")!=null){
			model.put("piPost", (String)infoMap.get("post"));
		}
		if(res!=null){
			model.put("piTel", (String)res.get("tel"));
			model.put("piEmail", (String)res.get("email"));
			model.put("piAddr", (String)res.get("addr"));
		}
		
		return new ModelAndView("baseJsp",model);
	}
	
	
	/**
	 * 自己修改自己的个人信息
	 * @param tel
	 * @param email
	 * @param addr
	 * @param req
	 * @return
	 */
	@RequestMapping("/changePersonInfoForm.do")
	public ModelAndView changeInfo(String tel,String email,String addr,
			HttpServletRequest req){
		boolean res = false;
		String baseContent = null;
		String baseUrl = "/personInfo.do";
		String jobId = (String)req.getSession().getAttribute("userJobId");
		try {
			res = userServer.changeMyPersonInfoByJobId(jobId, tel, email, addr);
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(baseUrl, "修改个人信息失败。/(ㄒoㄒ)/~~");
		}
		//处理结果
		if(!res){
			baseContent = "修改个人信息失败。/(ㄒoㄒ)/~~";
		}else{
			baseContent = "修改个人信息成功！";
		}
		return JumpPrompt.jumpOfModelAndView(baseUrl, baseContent);
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword2
	 * @param req
	 * @return
	 */
	@RequestMapping("/changeMyPasswordForm.do")
	public ModelAndView changePwd(String oldPassword,String newPassword,String newPassword2,
			HttpServletRequest req){
		boolean res = false;
		String baseContent = null;
		String baseUrl = "/changePassword.do";
		if(newPassword.equals(newPassword2)){
			try {
				res = userServer.changeMyPassword((String)req.getSession().getAttribute("userJobId"), oldPassword, newPassword);
			} catch (Exception e) {
				e.printStackTrace();
				return JumpPrompt.jumpOfModelAndView(baseUrl, "修改密码失败。/(ㄒoㄒ)/~~");
			}
		}
		//处理结果
		if(!res){
			baseContent = "修改密码失败。/(ㄒoㄒ)/~~";
		}else{
			baseContent = "修改密码成功！";
		}
		return JumpPrompt.jumpOfModelAndView(baseUrl, baseContent);
	}
	
	
	
	
	public UserService getUserServer() {
		return userServer;
	}
	public void setUserServer(UserService userServer) {
		this.userServer = userServer;
	}
	public UserKindService getUserKindService() {
		return userKindService;
	}
	public void setUserKindService(UserKindService userKindService) {
		this.userKindService = userKindService;
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
	
}

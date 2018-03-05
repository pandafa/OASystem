package action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import bean.UserInfo;
import service.RemindService;
import service.UserKindService;
import service.UserService;
import util.JumpPrompt;

/**
 * 登录、退出部分
 * @author DELL
 *
 */
@Controller
public class LogAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private UserKindService userKindService;
	@Autowired
	private RemindService remindService;

	public UserService getUserServer() {
		return userServer;
	}

	public void setUserServer(UserService userServer) {
		this.userServer = userServer;
	}
	
	/**
	 * 默认访问页面，到登录页面
	 * @return
	 */
	@RequestMapping("/welcome.do")
	public String viewWelcome(HttpServletRequest req){
		HttpSession session = req.getSession();
		if(session!=null && session.getAttribute("userJobId")!=null){
			//如果登录过
			return "redirect:/home.do";
		}else{
			//没有登录过
			return "login";
		}
		
	}
	
	/**
	 * 到忘记密码页面
	 * @return
	 */
	@RequestMapping("/forgetPassword.do")
	public String viewForgetPassword(){
		return "forgetPassword";
	}
	
	/**
	 * 登录成功后看到的页面
	 * @param request
	 */
	@RequestMapping("/home.do")
	public ModelAndView viewHome(HttpServletRequest req){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","home.jsp");
		model.put("myPageTitle","欢迎");
		model.put("myPageNav","-1");
		return new ModelAndView("baseJsp",model);
	}

	/**
	 * 登录操作
	 * @param username
	 * @param password
	 * @param code
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping("/loginForm.do")
	public ModelAndView loginCheck(String username,String password,String code,
			HttpServletRequest request,HttpServletResponse res){
		String codeStr = (String)request.getSession().getAttribute("codeStr");
		if(codeStr==null){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "请先获取验证码");
		}else{
			if(!codeStr.equals(code)){
				return JumpPrompt.jumpOfModelAndView("/welcome.do", "验证码错误，登录失败！");
			}
		}
		int status = userServer.getUserStatusByJobId(username);
		if(status==-1){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "用户名或密码错误，登录失败！");
		}else if(status==UserInfo.STATUS_NO_ACTIVITY){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！账户未激活，请先重置一次密码来进行激活账户。");
		}else if(status==UserInfo.STATUS_DISABLE ){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！账户被禁用，请联系管理员。");
		}else if(status==UserInfo.STATUS_ABNORMAL ){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！账户异常，禁止登陆，请联系管理员。");
		}else if(status==UserInfo.STATUS_FROZEN_15_MINUTE ){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！由于多次输入密码错误，账户被冻结15分钟，15分钟后可以重新登录。如需立即登录，请联系管理员。");
		}else if(status==UserInfo.STATUS_FROZEN_30_MINUTE ){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！由于被冻结15分钟后，再次多次输入密码错误，账户被冻结30分钟，30分钟后可以重新登录。如需立即登录，请联系管理员。");
		}else if(status==UserInfo.STATUS_FROZEN_24_HOUR ){
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！由于被冻结30分钟后，再次多次输入密码错误，账户被冻结24小时，24小时后可以重新登录。如需立即登录，请联系管理员。");
		}
		UserInfo info = userServer.checkLogin(username, password);
		if(info!=null){
			//登陆成功
			request.getSession().removeAttribute("codeStr");
			try {
				userServer.changeUserPasswordErrorTimes(info.getJobId(),0);
			} catch (Exception e) {
				e.printStackTrace();
				return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！（服务器异常）");
			}
			HttpSession session = request.getSession();
			session.setAttribute("userKindName", userKindService.getNameById(info.getKind()));
			session.setAttribute("userName", info.getName());
			session.setAttribute("userKind", info.getKind());
			session.setAttribute("userpart", info.getPart());
			session.setAttribute("usergroup", info.getGroup());
			session.setAttribute("userJobId", info.getJobId());
			session.setAttribute("userCardId", info.getCardId());
			session.setAttribute("userSex", info.getSex());
			
			int msg = remindService.getRemindMsgNumberById(info.getJobId());
//			System.out.println("----"+info.getJobId());
			int procedure = remindService.getRemindProcedureNumberById(info.getJobId());
			int notice = remindService.getRemindNoticeNumberById(info.getJobId());
//			System.out.println("msg:"+msg);
//			System.out.println("procedure:"+procedure);
//			System.out.println("notice:"+notice);
			session.setAttribute("myPageNeedDeal", procedure);
			session.setAttribute("myPageNotice", notice);
			session.setAttribute("myPageMessage", msg);
			session.setAttribute("myPageMessagePrompt", msg+procedure+notice);
			return new ModelAndView("redirect:/home.do");
		}else{
			//登录失败
			//获取密码错误次数
			int times = userServer.getUserPasawordErrorTimes(username);
			times++;
			//按级别冻结
			try{
				if(times>=9){
					userServer.changeUserStatusByJobId(username, UserInfo.STATUS_FROZEN_24_HOUR);
				}else if(times>=6){
					userServer.changeUserStatusByJobId(username, UserInfo.STATUS_FROZEN_30_MINUTE);
				}else if(times>=3){
					userServer.changeUserStatusByJobId(username, UserInfo.STATUS_FROZEN_15_MINUTE);
				}
				//保存错误次数
				userServer.changeUserPasswordErrorTimes(username,times);
			} catch (Exception e) {
				e.printStackTrace();
				return JumpPrompt.jumpOfModelAndView("/welcome.do", "登录失败！（服务器异常02）");
			}
			return JumpPrompt.jumpOfModelAndView("/welcome.do", "用户名或密码错误，登录失败！（已连续输入错误"+times+"次）");
		}
	}
	

	/**
	 * 忘记密码操作
	 * @param jobId
	 * @param cardId
	 * @param username
	 * @param newPassword1
	 * @param newPassword2
	 * @return
	 */
	@RequestMapping("/forgetPasswordForm.do")
	public ModelAndView forgetPassword(String jobId,String cardId,String username,
			String newPassword1,String newPassword2){
		if(jobId!=null){
			//有传入
			if(newPassword1.equals(newPassword2)){
				//两次密码相同
				boolean res;
				try {
					res = userServer.forgetPassword(jobId, cardId, username, newPassword1);
				} catch (Exception e) {
					e.printStackTrace();
					return JumpPrompt.jumpOfModelAndView("/forgetPassword.do", "重置密码失败！（服务器异常）");
				}
				if(res){
					//修改成功
					return new ModelAndView("redirect:/welcome.do");
				}
			}
		}
		return JumpPrompt.jumpOfModelAndView("/forgetPassword.do", "重置密码失败！");
	}
	
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:/welcome.do";
	}
	
	/**
	 * 刷新提示的数字
	 * @param req
	 * @return
	 */
	@RequestMapping("/refreshRemind.do")
	@ResponseBody
	public String refreshRemindNumber(HttpServletRequest req){
		HttpSession session = req.getSession();
		int msg = remindService.getRemindMsgNumberById((String)session.getAttribute("userJobId"));
		int procedure = remindService.getRemindProcedureNumberById((String)session.getAttribute("userJobId"));
		String res = "msg:"+msg+",procedure:"+procedure+",all:"+(procedure+msg);
		return res;
	}
}

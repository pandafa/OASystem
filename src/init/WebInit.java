package init;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.ServletContextAware;

import bean.MessageKind;
import bean.UserKind;
import service.MsgKindService;
import service.UserKindService;

@Lazy(false)
public class WebInit implements InitializingBean,ServletContextAware{
	
	private ServletContext servletContext;
	
	@Autowired
	private MsgKindService msgKindService;
	@Autowired
	private UserKindService userKindService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<Integer, String> ukMap = null;
		Map<String, Integer> mkMap = null;
		//获取用户类型
		List<UserKind> ukList = userKindService.getAllUserKind();
		if(ukList!=null && ukList.size()!=0){
			ukMap = new HashMap<>();
			for(UserKind u : ukList){
				ukMap.put(u.getId(), u.getName());
			}
		}
		//获取消息类型
		List<MessageKind> mkList = msgKindService.getAllUserKind();
		if(mkList!=null && mkList.size()!=0){
			mkMap = new HashMap<>();
			for(MessageKind m : mkList){
				mkMap.put(m.getName(), m.getId());
			}
		}
		System.out.println("初始化开始------------");
//		System.out.println(ukMap);
//		System.out.println(mkMap);
		System.out.println("初始化结束-----------");
		servletContext.setAttribute("app_userKindMap",ukMap);
		servletContext.setAttribute("app_msgKindMap",mkMap);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public MsgKindService getMsgKindService() {
		return msgKindService;
	}

	public void setMsgKindService(MsgKindService msgKindService) {
		this.msgKindService = msgKindService;
	}

	public UserKindService getUserKindService() {
		return userKindService;
	}

	public void setUserKindService(UserKindService userKindService) {
		this.userKindService = userKindService;
	}

}

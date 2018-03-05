package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bean.Message;
import bean.UserInfo;
import service.GroupService;
import service.MessageService;
import service.PartService;
import service.RemindService;
import service.UserService;
import util.JumpPrompt;

@Controller
public class MessageAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RemindService remindService;
	
	private SimpleDateFormat sdf;
	
	
	public MessageAction(){
		super();
//		System.out.println(new Date().toString()+"我是MessageAction");
		sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
	}	
	
	/**
	 * 查看消息详细
	 * @param msgId
	 * @param req
	 * @return
	 */
	@RequestMapping("message/lookMessage.do")
	public ModelAndView viewLookMsg(String msgId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取上一个地址
		String beforUrl = req.getHeader("REFERER");
		beforUrl = beforUrl.substring(beforUrl.indexOf("message/"));
		if(msgId==null  || msgId.length()==0){
			return JumpPrompt.jumpOfModelAndView("/"+beforUrl, "查看消息请求错误。（缺少参数）");
		}
		
		String title = null;
		String kind = null;
		String person = null;
		String date = null;
		String content = null;
		
		//获取结果
		Message msg = messageService.getMessageInfoByMessageId(msgId);
		if(msg==null){
			return JumpPrompt.jumpOfModelAndView("/"+beforUrl, "查看消息错误。（无此消息）");
		}
		title = msg.getTitle();
		date = sdf.format(msg.getSendDate());
		content = msg.getContent();
		person = userServer.getUserNameById(msg.getSendPerson());
		if(person==null){
			person = "查无此人";
		}
		
		if(msg.getKind() == Message.KIND_MESSAGE_PART){
			kind = "部门消息";
		}else if(msg.getKind() == Message.KIND_MESSAGE_GROUP){
			kind = "小组消息";
		}else if(msg.getKind() == Message.KIND_MESSAGE_PERSON){
			kind = "个人消息";
		}else if(msg.getKind() == Message.KIND_MESSAGE_COMPANY){
			kind = "公司消息";
		}else if(msg.getKind() == Message.KIND_MESSAGE_SYSTEM){
			kind = "系统消息";
		}else if(msg.getKind() == Message.KIND_NOTICE_PART){
			kind = "部门公告";
		}else if(msg.getKind() == Message.KIND_NOTICE_GROUP){
			kind = "小组公告";
		}else if(msg.getKind() == Message.KIND_NOTICE_COMPANY){
			kind = "公司公告";
		}else{
			kind = "未知的类型";
		}
		
		
		
		//封装数据
		boolean isMag = false;
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/messagePlazaDetail.jsp");
		if(beforUrl.startsWith("message/personMessageList.do")){
			model.put("myPageTitle","消息详情");
			model.put("myPageNav","7");
			model.put("pmdIsNotice", 0);//是消息
			isMag = true;
		}else{
			model.put("myPageTitle","公告详情");
			model.put("myPageNav","6");
			model.put("pmdIsNotice", 1);//是公告
			isMag = false;
		}
		model.put("pmdPreUrl", beforUrl);//上一个网址
		model.put("pmdTitle", title);//标题
		model.put("pmdKind", kind);//分类
		model.put("pmdPerson", person);//发送人
		model.put("pmdDate", date);//时间
		model.put("pmdContent", content);//内容
		
		//在提醒中删除
		try {
			remindService.takeIdRead(jobId, msg.getId(), isMag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到公告列表
	 * @param req
	 * @return
	 */
	@RequestMapping("message/noticeList.do")
	public ModelAndView viewNoticeList(String kind,String page,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
//		int userKind = userServer.getUserKindByJobId(jobId);
		List<Map<String,String>> msgList = null;
		String msgKind = null;
		int allPage = 0;
		int currentPage = 0;
		
		if(kind==null  || kind.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/messagePlaza.do", "非法请求");
		}
		//当前页
		if(page==null  || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//类型和总页数
		if(kind.equals("g")){
			msgKind = "小组";
			allPage = messageService.getNoticeOfGroupPageNumberByJobId(jobId);
		}else if(kind.equals("p")){
			msgKind = "部门";
			allPage = messageService.getNoticeOfPartPageNumberByJobId(jobId);
		}else{
			msgKind = "公司";
			allPage = messageService.getNoticeOfCompanyPageNumberByJobId();
		}
		//防止页数非法
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		//获取信息列表
		List<Message> tempList = null;
		if(kind.equals("g")){
			tempList = messageService.getNoticeInfoOfGroupToPageByJobId(jobId, currentPage);
		}else if(kind.equals("p")){
			tempList = messageService.getNoticeInfoOfPartToPageByJobId(jobId, currentPage);
		}else{
			tempList = messageService.getNoticeInfoOfCompanyToPageByJobId(currentPage);
		}
		//处理数据
		msgList = new ArrayList<>();
		if(tempList!=null){
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", tempList.get(i).getId()+"");
				map.put("title", tempList.get(i).getTitle());
				map.put("date", sdf.format(tempList.get(i).getSendDate()));
				if(remindService.isRead(jobId,tempList.get(i).getId(),false)){
					map.put("isRead", "1");//已读
				}else{
					map.put("isRead", "0");//未读
				}
				msgList.add(map);
			}
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/messagePlazaList.jsp");
		model.put("myPageTitle","公告广场——列表");
		model.put("myPageNav","6");
		model.put("mplMsgList", msgList);//消息列表
		model.put("mplMsgKind", msgKind);//消息种类
		model.put("mplMsgKindStr", kind);
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 到公告页面
	 * @param req
	 * @return
	 */
	@RequestMapping("message/messagePlaza.do")
	public ModelAndView viewMessagePlaza(HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		//int userKind = userServer.getUserKindByJobId(jobId);
		
		List<Map<String,String>> partNoticeList = null;
		List<Map<String,String>> companyNoticeList = null;
		List<Map<String,String>> groupNoticeList = null;
		
		List<Message> tempList = null;
		
		
		//部门
		tempList = messageService.getNoticeInfoOfPartToPageByJobId(jobId, 1);
		partNoticeList = new ArrayList<Map<String,String>>();
		if(tempList!=null){
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", tempList.get(i).getId()+"");
				map.put("title", tempList.get(i).getTitle());
				map.put("date", sdf.format(tempList.get(i).getSendDate()));
				if(remindService.isRead(jobId,tempList.get(i).getId(),false)){
					map.put("isRead", "1");//已读
				}else{
					map.put("isRead", "0");//未读
				}
				partNoticeList.add(map);
			}
		}
		
		//公司
		tempList = messageService.getNoticeInfoOfCompanyToPageByJobId(1);
		companyNoticeList = new ArrayList<Map<String,String>>();
		if(tempList!=null){
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", tempList.get(i).getId()+"");
				map.put("title", tempList.get(i).getTitle());
				map.put("date", sdf.format(tempList.get(i).getSendDate()));
				if(remindService.isRead(jobId,tempList.get(i).getId(),false)){
					map.put("isRead", "1");//已读
				}else{
					map.put("isRead", "0");//未读
				}
				companyNoticeList.add(map);
			}
		}
		
		//小组
		tempList = messageService.getNoticeInfoOfGroupToPageByJobId(jobId, 1);
		groupNoticeList = new ArrayList<Map<String,String>>();
		if(tempList!=null){
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", tempList.get(i).getId()+"");
				map.put("title", tempList.get(i).getTitle());
				map.put("date", sdf.format(tempList.get(i).getSendDate()));
				if(remindService.isRead(jobId,tempList.get(i).getId(),false)){
					map.put("isRead", "1");//已读
				}else{
					map.put("isRead", "0");//未读
				}
				groupNoticeList.add(map);
			}
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/messagePlaza.jsp");
		model.put("myPageTitle","广场");
		model.put("myPageNav","6");
		
		model.put("mpCompanyMsgList", companyNoticeList);
		model.put("mpPartMsgList", partNoticeList);
		model.put("mpNoticeMsgList", groupNoticeList);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 到个人消息列表
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("message/personMessageList.do")
	public ModelAndView viewPersonMsgList(String page,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		//int userKind = userServer.getUserKindByJobId(jobId);
//		System.out.println(jobId);
		List<Map<String,String>> msgList = null;//一页5个
		int allPage = 0;
		int currentPage = 0;
		//当前页
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		
		//获取消息总数
		allPage = messageService.getAllMessagePageNumberByJobId(jobId);
		//防止错误页数
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		//获取消息
		List<Message> temp = messageService.getAllMessageInfoOfPageByJobId(jobId, currentPage);
		if(temp!=null && temp.size()!=0){
			msgList = new ArrayList<>();
			for(int i=0;i<temp.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", temp.get(i).getId()+"");
				map.put("title", temp.get(i).getTitle());
				map.put("sendPerson", userServer.getUserNameById(temp.get(i).getSendPerson()));
				switch(temp.get(i).getKind()){
					case Message.KIND_MESSAGE_SYSTEM:
						map.put("source", "系统");
						break;
					case Message.KIND_MESSAGE_COMPANY:
						map.put("source", "公司");
						break;
					case Message.KIND_MESSAGE_PART:
						map.put("source", "部门");
						break;
					case Message.KIND_MESSAGE_GROUP:
						map.put("source", "小组");
						break;
					case Message.KIND_MESSAGE_PERSON:
						map.put("source", "个人");
						break;
					default:
						map.put("source", "未知");
						break;
				}
				map.put("date", sdf.format(temp.get(i).getSendDate()));
				//是否阅读
				if(remindService.isRead(jobId,temp.get(i).getId(),true)){
					map.put("status", "1");//已读
				}else{
					map.put("status", "0");//未读
				}
				msgList.add(map);
			}
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/personalMessageList.jsp");
		model.put("myPageTitle","个人消息——列表");
		model.put("myPageNav","7");
		model.put("pmlMsgList", msgList);//消息列表
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 发送消息
	 * @param req
	 * @return
	 */
	@RequestMapping("message/sendMessage.do")
	public ModelAndView viewSendMessage(HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		int userKind = userServer.getUserKindByJobId(jobId);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/sendMessage.jsp");
		model.put("myPageTitle","发送消息");
		model.put("myPageNav","8");
		model.put("userKindNumber",userKind);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 发送公告
	 * @param req
	 * @return
	 */
	@RequestMapping("message/sendNotice.do")
	public ModelAndView viewSendNotice(HttpServletRequest req){//
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		int userKind = userServer.getUserKindByJobId(jobId);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","message/sendNotice.jsp");
		model.put("myPageTitle","发布公告");
		model.put("myPageNav","9");
		model.put("userKindNumber",userKind);
		return new ModelAndView("baseJsp",model);
	}
	
	
	/**
	 * 发送消息表单操作
	 * @param req
	 * @return
	 */
	@RequestMapping("message/sendMessageForm.do")
	public ModelAndView sendMsg(HttpServletRequest req){
		//标题
		String title = req.getParameter("mtitle");
		if(title==null || title.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（标题内容没有写）");
		}
		//发送的内容
		String content = req.getParameter("mcontext");
		if(content==null || content.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（发送的内容没有写）");
		}
		//消息类型
		String msgKind = req.getParameter("mkind");
		if(msgKind==null || msgKind.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（消息类型没有选择）");
		}
		boolean result = false;
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		
		Message msg = new Message();
		msg.setTitle(title);
		msg.setContent(content);
		msg.setSendPerson(jobId);
		try{
			if(msgKind.equals("person")){
				//个人消息
				//接受者jobId
				String accept = req.getParameter("macceptJobId");
				if(!userServer.hasUserByJobId(accept)){
					return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（没有工号为“"+accept+"”的成员）");
				}
				//发送  accept接受者
				msg.setAcceptPerson(accept);
				msg.setKind(Message.KIND_MESSAGE_PERSON);
				result = messageService.sendOneMsgToPerson(msg,true);
			}else if(msgKind.equals("group")){
				//小组消息
				int acceptPart = -1;
				int acceptGroup = -1;
				if(userKind==UserInfo.KIND_MANAGER_WEB){//网站管理员
					acceptPart = Integer.parseInt(req.getParameter("macceptpPart"));
					acceptGroup = Integer.parseInt(req.getParameter("macceptgGroup"));
				}else if(userKind==UserInfo.KIND_MANAGER_PART){//部门管理员
					acceptPart = userServer.getUserPartByJobId(jobId);
					acceptGroup = Integer.parseInt(req.getParameter("macceptg"));
				}else if(userKind==UserInfo.KIND_MANAGER_GROUP){//小组管理员
					acceptPart = userServer.getUserPartByJobId(jobId);
					acceptGroup = userServer.getUserGroupByJobId(jobId);
				}else{
					return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（无此权限@_@）");
				}
				//发送消息 acceptPart部门 ，acceptGroup小组
				msg.setKind(Message.KIND_MESSAGE_GROUP);
				msg.setAcceptPart(acceptPart);
				msg.setAcceptGroup(acceptGroup);
				result = messageService.sendOneMsgToGroup(msg,true);
			}else if(msgKind.equals("part")){
				//部门消息
				int acceptPart = -1;
				if(userKind==UserInfo.KIND_MANAGER_WEB){
					acceptPart = Integer.parseInt(req.getParameter("macceptp"));
				}else if(userKind==UserInfo.KIND_MANAGER_PART){
					acceptPart = userServer.getUserPartByJobId(jobId);
				}else{
					return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（无此权限@_@）");
				}
				//发送   acceptPart部门
				msg.setKind(Message.KIND_MESSAGE_PART);
				msg.setAcceptPart(acceptPart);
				result = messageService.sendOneMsgToPart(msg,true);
			}else if(msgKind.equals("company")){
				//公司消息
				if(userKind!=UserInfo.KIND_MANAGER_WEB){
					return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（无此权限@_@）");
				}
				//发送
				msg.setKind(Message.KIND_MESSAGE_COMPANY);
				result = messageService.sendOneMsgToAll(msg,true);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。（服务器异常）");
		}
		if(result){
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "成功消息发送！");
		}else{
			return JumpPrompt.jumpOfModelAndView("/message/sendMessage.do", "消息发送失败。");
		}
	}
	
	
	/**
	 * 发送公告
	 * @param req
	 * @return
	 */
	@RequestMapping("message/sendNoticeForm.do")
	public ModelAndView sendNotice(HttpServletRequest req){
		//标题
		String title = req.getParameter("mtitle");
		if(title==null || title.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（标题内容没有写）");
		}
		//发送的内容
		String content = req.getParameter("mcontext");
		if(content==null || content.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（发送的内容没有写）");
		}
		//消息类型
		String msgKind = req.getParameter("mkind");
		if(msgKind==null || msgKind.length()==0){
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（消息类型没有选择）");
		}
		boolean result = false;
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		
		Message msg = new Message();
		msg.setTitle(title);
		msg.setContent(content);
		msg.setSendPerson(jobId);
		try{
			if(msgKind.equals("group")){
				//小组公告
				int acceptPart = -1;
				int acceptGroup = -1;
				if(userKind==UserInfo.KIND_MANAGER_WEB){//网站管理员
					acceptPart = Integer.parseInt(req.getParameter("macceptpPart"));
					acceptGroup = Integer.parseInt(req.getParameter("macceptgGroup"));
				}else if(userKind==UserInfo.KIND_MANAGER_PART){//部门管理员
					acceptPart = userServer.getUserPartByJobId(jobId);
					acceptGroup = Integer.parseInt(req.getParameter("macceptg"));
				}else if(userKind==UserInfo.KIND_MANAGER_GROUP){//小组管理员
					acceptPart = userServer.getUserPartByJobId(jobId);
					acceptGroup = userServer.getUserGroupByJobId(jobId);
				}else{
					return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（无此权限@_@）");
				}
				//发送消息 acceptPart部门 ，acceptGroup小组
				msg.setKind(Message.KIND_NOTICE_PART);
				msg.setAcceptPart(acceptPart);
				msg.setAcceptGroup(acceptGroup);
				result = messageService.sendOneMsgToGroup(msg,false);
			}else if(msgKind.equals("part")){
				//部门公告
				int acceptPart = -1;
				if(userKind==UserInfo.KIND_MANAGER_WEB){
					acceptPart = Integer.parseInt(req.getParameter("macceptp"));
				}else if(userKind==UserInfo.KIND_MANAGER_PART){
					acceptPart = userServer.getUserPartByJobId(jobId);
				}else{
					return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（无此权限@_@）");
				}
				//发送   acceptPart部门
				msg.setKind(Message.KIND_NOTICE_PART);
				msg.setAcceptPart(acceptPart);
				result = messageService.sendOneMsgToPart(msg,false);
			}else if(msgKind.equals("company")){
				//公司公告
				if(userKind!=UserInfo.KIND_MANAGER_WEB){
					return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（无此权限@_@）");
				}
				//发送
				msg.setKind(Message.KIND_NOTICE_COMPANY);
				result = messageService.sendOneMsgToAll(msg,false);
			}else{
				return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（类型错误）");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。（服务器异常）");
		}
		if(result){
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "成功消息发送！");
		}else{
			return JumpPrompt.jumpOfModelAndView("/message/sendNotice.do", "消息发送失败。");
		}
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

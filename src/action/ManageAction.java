package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bean.Group;
import bean.Part;
import bean.UserInfo;
import service.GroupService;
import service.PartService;
import service.UserService;
import util.JumpPrompt;

@Controller
public class ManageAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	
	private SimpleDateFormat sdf;
	
	public ManageAction(){
		super();
		sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
	}

	/**
	 * 转到用户管理——修改页面（网站管理员）
	 * @param jobId
	 * @return
	 */
	@RequestMapping("manage/userManagerEdit.do")
	public ModelAndView viewUserManagerEdit(String jobId,HttpServletRequest req){
		String userJobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		int userKind = userServer.getUserKindByJobId(userJobId);
		if(userKind!=UserInfo.KIND_MANAGER_WEB){
			return JumpPrompt.jumpOfModelAndView("/home.do", "对不起，您无此权限！");
		}
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/userManagerEdit.jsp");
		model.put("myPageTitle","用户管理——修改");
		model.put("myPageNav","12");
		
		//获取用户信息
		UserInfo temp = userServer.getUserInfoByJobId(jobId);
		if(temp!=null){
			model.put("umeName", temp.getName());//姓名
			model.put("umeSex", temp.getSex());//性别
			model.put("umeJobId", jobId);//工号
			model.put("umeCardId", temp.getCardId());//身份证
			model.put("umePart", temp.getPart());//部门
			model.put("umeGroup", temp.getGroup());//小组
			model.put("umeTel", temp.getTel());//电话
			model.put("umeEmail", temp.getEmail());//邮箱
			model.put("umeAddr", temp.getAddr());//地址
			model.put("umeStatue", temp.getStatus());//地址
			model.put("umePost", temp.getPost());//地址
		}
		
		List<Map<String, Object>> partsList = null;
		List<Map<String, Object>> groupsList = null;
		
		partsList = partService.getAllPartsAndNames();
		groupsList = groupService.getAllGroupsOfPartNameAndId(temp.getPart());
		
		model.put("umePartList", partsList);//地址
		model.put("umeGroupList", groupsList);//地址
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到用户管理页面（网站管理员）
	 * @param page
	 * @param partId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/userManager.do")
	public ModelAndView viewUserManager(String page,String partId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		int userKind = userServer.getUserKindByJobId(jobId);
		if(userKind!=UserInfo.KIND_MANAGER_WEB){
			return JumpPrompt.jumpOfModelAndView("/home.do", "对不起，您无此权限！");
		}
		
		List<Map<String,String>> personList = null;//一页10个
		List<Map<String,String>> partList = null;
		int currentPart = 0;
		int allPage = 0;
		int currentPage = 0;
		//现在所看的部门
		if(partId==null || partId.length()==0){
			currentPart = 0;
		}else{
			currentPart = Integer.parseInt(partId);
		}
		//现在所看的页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//获取总页数
		allPage = userServer.getAllPageByPart(currentPart);
		//防止非法页数
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		//获取部门列表
		List<Map<String,Object>> tempList = partService.getAllPartsAndNames();
		if(tempList!=null){
			partList = new ArrayList<Map<String,String>>();
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("partId", ""+(int)tempList.get(i).get("id"));
				map.put("partName", (String)tempList.get(i).get("name"));
				partList.add(map);
			}
		}
		//获取用户列表
		tempList = userServer.getUsersInfoOfPartByPage(currentPart, currentPage);
		if(tempList!=null){
			personList = new ArrayList<Map<String,String>>();
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("jobId", (String)tempList.get(i).get("jobId"));
				map.put("cardId", (String)tempList.get(i).get("cardId"));
				map.put("name", (String)tempList.get(i).get("name"));
				if(tempList.get(i).get("post")==null){
					map.put("post", "无");
				}else{
					map.put("post", (String)tempList.get(i).get("post"));
				}
				if((int)tempList.get(i).get("sex")==UserInfo.SEX_MALE){
					map.put("sex", "男");
				}else{
					map.put("sex", "女");
				}
				int pId = (int)tempList.get(i).get("part");
				map.put("part", partService.getNameById(pId));
				map.put("group", groupService.getNameById(pId, (int)tempList.get(i).get("ggroup")));
				personList.add(map);
			}
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/userManager.jsp");
		model.put("myPageTitle","用户管理");
		model.put("myPageNav","12");
		model.put("umPersonList", personList);//用户列表，最多6个
		model.put("umPartList", partList);//部门列表
		model.put("umCurrentPart", currentPart+"");//当前显示的部门
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到群组管理界面——部门
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/groupManagerPart.do")
	public ModelAndView viewGroupManagerPart(String page,HttpServletRequest req){
		int allPage = 0;
		int currentPage = 0;
		List<Map<String,String>> managePartList = null;
		if(page==null || page.trim().length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page.trim());
		}
		allPage = partService.getAllPage();
		//选择的页数大于总数，改为最大
		if(currentPage>allPage){
			currentPage = allPage;
		}
		//防止非法页数
		if(currentPage<1){
			currentPage = 1;
		}
		//获取部门信息
		managePartList = null;
		List<Part> list = partService.getPartByPage(currentPage);
		if(list!=null && list.size()!=0){
			managePartList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("partId", list.get(i).getId()+"");
				map.put("name", list.get(i).getName());
				map.put("groupNum", ""+partService.getGroupOfPartNumbers(list.get(i).getId()));
				map.put("memberNum", ""+partService.getMemberOfPartNumbers(list.get(i).getId()));
				map.put("createDate", sdf.format(list.get(i).getCreateDate()));
				map.put("createPerson", list.get(i).getCreatePerson());
				managePartList.add(map);
			}
		}
		//转向
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/groupManagerPart.jsp");
		model.put("myPageTitle","群组管理——部门");
		model.put("myPageNav","13");
		model.put("gmpManagePartList", managePartList);//部门列表
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到群组管理界面——小组
	 * @param page
	 * @param partId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/groupManagerGroup.do")
	public ModelAndView viewGroupManagerGroup(String page,String partId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int kind = userServer.getUserKindByJobId(jobId);
		List<Map<String,String>> groupList = null;//一页6个
		List<Map<String,String>> partList = null;
		String currentPart = "";
		int allPage = 0;
		int currentPage = 0;
		
		if(kind==UserInfo.KIND_MANAGER_WEB){
			//看全部
			List<Map<String, Object>> list = partService.getAllPartsAndNames();
			//处理信息，添加部门
			partList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<>();
				map.put("partId", (int)list.get(i).get("id")+"");
				map.put("partName", (String)list.get(i).get("name"));
				partList.add(map);
			}
			//添加部门
			if(partId==null || partId.length()==0){
				partId = partList.get(0).get("partId");
			}
			
		}else{//kind==1
			//看单个
			//添加部门，固定
			partId = userServer.getUserPartByJobId(jobId)+"";
			//添加部门
			partList = new ArrayList<>();
			Map<String,String> map = new HashMap<>();
			map.put("partId", partId);
			map.put("partName", partService.getNameById(Integer.parseInt(partId)));
			partList.add(map);
		}
		currentPart = partId;
		//获取总页数 allPage
		allPage = groupService.getAllPage(Integer.parseInt(partId));
		//页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//选择的页数大于总数，改为最大
		if(currentPage>allPage){
			currentPage = allPage;
		}
		//防止非法页数
		if(currentPage<1){
			currentPage = 1;
		}
		//查找数据 getPartByPage
		List<Group> groupsListOfSQL = groupService.getGroupsOfPartByPage(Integer.parseInt(partId), currentPage);
		//添加数据
		groupList = new ArrayList<>();
		for(int i=0;i<groupsListOfSQL.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("groupId", groupsListOfSQL.get(i).getId()+"");
			map.put("name", groupsListOfSQL.get(i).getName());
			map.put("member", groupService.getMemberNumbersOfGroup(Integer.parseInt(partId), groupsListOfSQL.get(i).getId())+"");
			map.put("createDate", sdf.format(groupsListOfSQL.get(i).getCreateDate()));
			map.put("createPerson", groupsListOfSQL.get(i).getCreatePerson());
			groupList.add(map);
		}

		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/groupManagerGroup.jsp");
		model.put("myPageTitle","群组管理——小组");
		model.put("myPageNav","13");
		model.put("gmgGroupList", groupList);//小组列表，最多10个
		model.put("gmgPartList", partList);//部门列表
		model.put("gmgCurrentPart", currentPart);//当前显示的部门
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	
	/**
	 * 转到群组管理界面——成员
	 * @param page
	 * @param partId
	 * @param groupId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/groupManagerMember.do")
	public ModelAndView viewGroupManagerMember(String page,String partId,String groupId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int kind = userServer.getUserKindByJobId(jobId);
		List<Map<String,String>> personList = null;//一页6个
		List<Map<String,String>> partList = null;
		List<Map<String,String>> groupList = null;
		String currentPart = "";
		String currentGroup = "";
		int allPage = 0;
		int currentPage = 0;
		
		//确定当前页
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		if(kind==UserInfo.KIND_MANAGER_WEB){
			//网站管理员
			//处理信息，添加部门
			List<Map<String, Object>> list = partService.getAllPartsAndNames();
			partList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<>();
				map.put("partId", (int)list.get(i).get("id")+"");
				map.put("partName", (String)list.get(i).get("name"));
				partList.add(map);
			}
			//添加部门
			if(partId==null || partId.length()==0){
				partId = partList.get(0).get("partId");
			}
			//确定部门
			currentPart = partId;
			//添加小组
			list = groupService.getAllGroupsOfPartNameAndId(Integer.parseInt(currentPart));
			groupList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<>();
				map.put("groupId", (int)list.get(i).get("id")+"");
				map.put("groupName", (String)list.get(i).get("name"));
				groupList.add(map);
			}
			if(groupId==null || groupId.length()==0){
				groupId = groupList.get(0).get("groupId");
			}
			//确定小组
			currentGroup = groupId;
		}else if(kind==UserInfo.KIND_MANAGER_PART){
			//部门管理员
			//确定部门
			currentPart = userServer.getUserPartByJobId(jobId)+"";
			//添加小组
			List<Map<String, Object>> list = groupService.getAllGroupsOfPartNameAndId(Integer.parseInt(currentPart));
			groupList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<>();
				map.put("groupId", (int)list.get(i).get("id")+"");
				map.put("groupName", (String)list.get(i).get("name"));
				groupList.add(map);
			}
			if(groupId==null || groupId.length()==0){
				groupId = groupList.get(0).get("groupId");
			}
			//确定小组
			currentGroup = groupId;
		}else{
			//小组管理员
			//确定部门
			currentPart = userServer.getUserPartByJobId(jobId)+"";
			//确定小组
			currentGroup = userServer.getUserGroupByJobId(jobId)+"";
		}
		
		//获取总页数
		allPage = userServer.getAllPageByGroup(Integer.parseInt(currentPart), Integer.parseInt(currentGroup));
		//获取本页
		List<UserInfo> temp = userServer.findUsersGroupOfGroupId(Integer.parseInt(currentPart), 
				Integer.parseInt(currentGroup),currentPage);
		//获取成员信息列表
		personList = new ArrayList<>();
		for(int i=0;i<temp.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("jobId", temp.get(i).getJobId());
			map.put("cardId", temp.get(i).getCardId());
			map.put("name", temp.get(i).getName());
			if(temp.get(i).getPost()==null){
				map.put("post", "无");
			}else{
				map.put("post", temp.get(i).getPost());
			}
			
			map.put("joinDate", sdf.format(temp.get(i).getJoinTime()));
			map.put("part", partService.getNameById(temp.get(i).getPart()));
			map.put("ggroup", groupService.getNameById(temp.get(i).getPart(), temp.get(i).getGroup()));
			switch(temp.get(i).getKind()){
				case UserInfo.KIND_MANAGER_WEB:
					map.put("identity", "网站管理员");
					break;
				case UserInfo.KIND_MANAGER_PART:
					map.put("identity", "部门管理员");
					break;
				case UserInfo.KIND_MANAGER_GROUP:
					map.put("identity", "小组管理员");
					break;
				case UserInfo.KIND_MEMBER:
					map.put("identity", "普通成员");
					break;
				default:
					map.put("identity", "获取失败");
					break;
			}
			personList.add(map);
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/groupManagerMember.jsp");
		model.put("myPageTitle","群组管理——成员");
		model.put("myPageNav","13");
		model.put("gmmPersonList", personList);//用户列表，最多6个
		model.put("gmmPartList", partList);//部门列表
		model.put("gmmGroupList", groupList);//小组列表
		model.put("gmmCurrentPart", currentPart);//当前显示的部门
		model.put("gmmCurrentGroup", currentGroup);//当前显示的小组
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到群组管理界面——分导
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/manageGroup.do")
	public ModelAndView viewManageGroupMain(HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int kind = userServer.getUserKindByJobId(jobId);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/groupManager.jsp");
		model.put("myPageTitle","群组管理分页");
		model.put("myPageNav","13");
		switch(kind){
			case UserInfo.KIND_MANAGER_WEB:
				model.put("gmCanPart",1);
				model.put("gmCanGroup",1);
				model.put("gmCanMember",1);
				break;
			case UserInfo.KIND_MANAGER_PART:
				model.put("gmCanPart",0);
				model.put("gmCanGroup",1);
				model.put("gmCanMember",1);
				break;
			case UserInfo.KIND_MANAGER_GROUP:
				model.put("gmCanPart",0);
				model.put("gmCanGroup",0);
				model.put("gmCanMember",1);
				break;
			case UserInfo.KIND_MEMBER:
				return JumpPrompt.jumpOfModelAndView("/home.do", "对不起，您无此权限！");
			default:
				return JumpPrompt.jumpOfModelAndView("/home.do", "对不起，权限获取异常！");
		}
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转到批量添加用户界面
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/addUser.do")
	public ModelAndView viewAddUser(HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取用户类型
		int userKind = userServer.getUserKindByJobId(jobId);
		if(userKind!=UserInfo.KIND_MANAGER_WEB){
			return JumpPrompt.jumpOfModelAndView("/home.do", "对不起，您无此权限！");
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","manager/addUsers.jsp");
		model.put("myPageTitle","修改个人密码");
		model.put("myPageNav","14");
		return new ModelAndView("baseJsp",model);
	}

	/**
	 * 修改某个用户的全部信息
	 * @param userName
	 * @param userSex
	 * @param cardId
	 * @param jobId
	 * @param part
	 * @param group
	 * @param tel
	 * @param email
	 * @param addr
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/changeUserInfoAllFrom.do")
	public ModelAndView changeUserInfoAll(String userName,String userSex,String cardId,String jobId,
			String part,String group,String tel,String email,String addr,String userStatus,String post,
			HttpServletRequest req){
		String jumpToStr = "/manage/userManagerEdit.do?jobId="+jobId;
		String userJobId = (String)req.getSession().getAttribute("userJobId");
		if(userServer.getUserKindByJobId(userJobId)!=UserInfo.KIND_MANAGER_WEB){
			return JumpPrompt.jumpOfModelAndView(jumpToStr, "权限异常，无法进行操作");
		}
		if(post!=null && post.trim().length()!=0){
		}else{
			post = null;
		}
		UserInfo info = new UserInfo();
		info.setName(userName);
		info.setSex(Integer.parseInt(userSex));
		info.setPost(post);
		info.setCardId(cardId);
		info.setJobId(jobId);
		info.setStatus(Integer.parseInt(userStatus));
		info.setPart(Integer.parseInt(part));
		info.setGroup(Integer.parseInt(group));
		if(tel!=null && tel.length()!=0){
			info.setTel(tel);
		}else{
			info.setTel(null);
		}
		if(email!=null && email.length()!=0){
			info.setEmail(email);
		}else{
			info.setEmail(null);
		}
		if(addr!=null && addr.length()!=0){
			info.setAddr(addr);
		}else{
			info.setAddr(null);
		}
		//更改全部
		try {
			if(userServer.changeUserInfoAllByJobId(info)){
				return JumpPrompt.jumpOfModelAndView(jumpToStr, "成功修改jobId为“"+jobId+"”的用户信息！");
			}else{
				return JumpPrompt.jumpOfModelAndView(jumpToStr, "修改用户信息失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(jumpToStr, "修改用户信息失败。（服务器异常）");
		}
	}
	
	/**
	 * 更改成员
	 * @param modelShowJobId
	 * @param modelShowPart
	 * @param modelShowGroup
	 * @param modelShowUserKind
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/changeMemberForm.do")
	public ModelAndView changeMemberForm(String modelShowJobId,String modelShowPart,String modelShowGroup,
			String modelShowUserKind,String currentPage,String currentPartId,String currentGroupId,String modelShowPost,
			HttpServletRequest req){
		//设置跳转本页面的路径
		String jumpToStr = "/manage/groupManagerMember.do?page="+currentPage;
		if(currentPartId!=null && currentPartId.length()!=0){
			jumpToStr += "&partId="+currentPartId;
		}
		if(currentGroupId!=null && currentGroupId.length()!=0){
			jumpToStr += "&groupId="+currentGroupId;
		}
		if(modelShowPost!=null && modelShowPost.trim().length()!=0){
		}else{
			modelShowPost = null;
		}
		if(modelShowPart==null){
			modelShowPart = req.getParameter("modelShowPart2");
		}
		if(modelShowGroup==null){
			modelShowGroup = req.getParameter("modelShowGroup2");
		}
		if(modelShowUserKind==null){
			modelShowUserKind = req.getParameter("modelShowUserKind2");
		}
		//获取操作者的jobId
		String userJobId = (String)req.getSession().getAttribute("userJobId");
		int partId = userServer.getUserPartByJobId(modelShowJobId);
		boolean result = false;
		try{
			if(userServer.getUserKindByJobId(userJobId)==UserInfo.KIND_MANAGER_WEB){
				//网站管理员
//				System.out.println("更改。id:"+modelShowJobId+",part:"+modelShowPart+",group:"+modelShowGroup+",shen:"+modelShowUserKind);
				if(!modelShowUserKind.equals("0")){
					result = userServer.changeUserBaseInfoWithKind(modelShowJobId, Integer.parseInt(modelShowPart), Integer.parseInt(modelShowGroup), Integer.parseInt(modelShowUserKind),modelShowPost);
				}else{
					result = userServer.changeUserBaseInfoWithoutKind(modelShowJobId, Integer.parseInt(modelShowPart), Integer.parseInt(modelShowGroup),modelShowPost);
				}
			}else if(userServer.getUserKindByJobId(userJobId)==UserInfo.KIND_MANAGER_PART && userServer.getUserPartByJobId(userJobId)==partId){
				//部门管理员
//				System.out.println("更改。id:"+modelShowJobId+",group:"+modelShowGroup+",shen:"+modelShowUserKind);
				if(userServer.getUserKindByJobId(modelShowJobId)<userServer.getUserKindByJobId(userJobId)){
					//改的身份，比自己小，可以改
					result = userServer.changeUserBaseInfoWithKind(modelShowJobId, Integer.parseInt(modelShowPart), Integer.parseInt(modelShowGroup), Integer.parseInt(modelShowUserKind),modelShowPost);
				}else{
					result = userServer.changeUserBaseInfoWithoutKind(modelShowJobId, Integer.parseInt(modelShowPart), Integer.parseInt(modelShowGroup),modelShowPost);
				}
			}else{
				return JumpPrompt.jumpOfModelAndView(jumpToStr, "权限异常，无法进行操作");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(jumpToStr, "操作失败。（服务器异常）");
		}
		if(result){
			return JumpPrompt.jumpOfModelAndView(jumpToStr, "操作成功！");
		}else{
			return JumpPrompt.jumpOfModelAndView(jumpToStr, "操作失败。");
		}
	}
	
	
	/**
	 * 添加小组
	 * @param newGroupName
	 * @param partId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/addGroupForm.do")
	public ModelAndView addGroup(String newGroupName,String partId,HttpServletRequest req){
		String url = req.getHeader("REFERER");
		if(url.indexOf("/manage/")==-1){
			url = "/home.do";
		}else{
			url = url.substring(url.indexOf("/manage/"));
		}
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String userName = userServer.getUserNameById(jobId);
		if(groupService.getGroupByName(Integer.parseInt(partId),newGroupName)!=-1){
			return JumpPrompt.jumpOfModelAndView(url, "添加小组“"+newGroupName+"”失败。（提示：该小组已存在）");
		}
		try{
			if(groupService.addGroup(Integer.parseInt(partId) ,newGroupName, userName)){
				return JumpPrompt.jumpOfModelAndView(url, "添加小组“"+newGroupName+"”成功！");
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "添加小组“"+newGroupName+"”失败。");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(url, "添加小组“"+newGroupName+"”失败。（服务器异常）");
		}
	}
	
	/**
	 * 删除小组
	 * @param GroupId
	 * @param partId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/delGroupForm.do")
	public ModelAndView delGroup(String GroupId,String partId,HttpServletRequest req){
		String url = req.getHeader("REFERER");
		if(url.indexOf("/manage/")==-1){
			url = "/home.do";
		}else{
			url = url.substring(url.indexOf("/manage/"));
		}
		try {
			if(groupService.delGroup(Integer.parseInt(partId),Integer.parseInt(GroupId))){
				return JumpPrompt.jumpOfModelAndView(url, "删除成功！");
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "删除失败。（无法删除含有成员的小组或“暂无小组”）");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(url, "删除失败。（服务器异常）");
		}
	}
	
	/**
	 * 添加部门
	 * @param newPartName
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/addPartForm.do")
	public ModelAndView addPart(String newPartName,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String userName = userServer.getUserNameById(jobId);
		if(partService.getPartByName(newPartName)!=-1){
			return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "添加部门“"+newPartName+"”失败。（提示：该部门已存在）");
		}
		try {
			if(partService.addPart(newPartName, userName)){
				return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "添加部门“"+newPartName+"”成功！");
			}else{
				return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "添加部门“"+newPartName+"”失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "添加部门“"+newPartName+"”失败。（服务器异常）");
		}
	}
	
	/**
	 * 删除部门
	 * @param partId
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/delPartForm.do")
	public ModelAndView delPart(String partId,HttpServletRequest req){
		try {
			if(partService.getMemberOfPartNumbers(Integer.parseInt(partId))!=0){
				return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "删除失败。（无法删除含成员的小组）");
			}
			if(partService.delPart(Integer.parseInt(partId))){
				return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "删除成功！");
			}else{
				return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "删除失败。（无法删除含有小组和成员的部门，除了默认小组）");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/manage/groupManagerPart.do", "删除失败。（服务器异常）");
		}
	}
	
	/**
	 * 批量添加用户
	 * @param req
	 * @return
	 */
	@RequestMapping("manage/addUsersFrom.do")
	public ModelAndView addUser(HttpServletRequest req){
		int all = Integer.parseInt(req.getParameter("userNumberAll"));//需要添加的个数
		int end = Integer.parseInt(req.getParameter("userNumberEnd"));//循环查找的结束
		if(all!=0){
			List<UserInfo> users = new ArrayList<UserInfo>();
			for(int i=1;i<=end;i++){
				String jobId = req.getParameter("jobId"+i);
				if(jobId!=null && jobId.trim().length()!=0){
					//确认有内容，获取
					UserInfo user = new UserInfo();
					user.setJobId(jobId.trim());
					user.setCardId(req.getParameter("cardId"+i));
					user.setName(req.getParameter("name"+i));
					if(req.getParameter("sex"+i).equals("male")){
						user.setSex(UserInfo.SEX_MALE);
					}else{
						user.setSex(UserInfo.SEX_FAMALE);
					}
					int partId = Integer.parseInt(req.getParameter("part"+i));
					user.setPart(partId);
					user.setGroup(groupService.getGroupByName(partId, "暂无小组"));//无小组
					user.setKind(UserInfo.KIND_MEMBER); //最低权限
					user.setStatus(UserInfo.STATUS_NO_ACTIVITY);
					users.add(user);
//					System.out.println("Action:("+i+")"+user.getKind());
					//数量够了，退出循环
					if(users.size()==all){
						break;
					}
				}
			}
			//批量创建用户
			boolean creatRes;
			try {
				creatRes = userServer.createNewUsers(users);
			} catch (Exception e) {
				e.printStackTrace();
				return JumpPrompt.jumpOfModelAndView("/manage/addUser.do", "批量创建失败。（服务器异常）");
			}
			if(creatRes){
				//批量创建成功
//				System.out.println("批量创建成功");
				return JumpPrompt.jumpOfModelAndView("/manage/addUser.do", "成功批量创建"+users.size()+"个用户！");
			}else{
				//批量创建失败
//				System.out.println("批量创建失败");
				return JumpPrompt.jumpOfModelAndView("/manage/addUser.do", "批量创建失败。");
			}
		}
		//没有东西
		return JumpPrompt.jumpOfModelAndView("/manage/addUser.do", "没有待添加的用户信息。");
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
	
	

}

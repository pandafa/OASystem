package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import bean.ModelOption;
import bean.ModelProcedure;
import bean.ModelShen;
import bean.ProcedureOption;
import bean.ProcedureShen;
import bean.ProcedureSubmit;
import bean.UserInfo;
import service.GroupService;
import service.PartService;
import service.ProcedureService;
import service.UserService;
import util.DownWord;
import util.JumpPrompt;

@RequestMapping("procedure/")
@Controller
public class ProcedureAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ProcedureService procedureService;
	
	private SimpleDateFormat sdf;
	private String filePath = "upload/file/procedure";
	
	public ProcedureAction(){
		super();
		sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
	}
	
	/**
	 * 到查看模板界面
	 * @param modelId
	 * @param req
	 * @return
	 */
	@RequestMapping("modelLook.do")
	public ModelAndView viewModelLook(String modelId,HttpServletRequest req){
		if(modelId==null || modelId.length()==0){
			String url = req.getHeader("REFERER");
			url = url.substring(url.indexOf("/procedure/"));
			return JumpPrompt.jumpOfModelAndView(url, "提交参数错误");
		}
		ModelProcedure modelP = procedureService.getModelInfoAllById(Integer.parseInt(modelId));
		
		String title = modelP.getTitle();//表格题头
		String produceName = modelP.getProjectName();//项目名称
		String illustrate = modelP.getIllustrate();//说明
		int enclosure = modelP.getEnclosure();//是否有附件
		Map<Integer,Map<String,Object>> blankMap = null;//小项  Map<选项的顺序,Map<key,value>>

		//数据
		ModelOption[] mp = modelP.getOption();
		blankMap = new HashMap<Integer,Map<String,Object>>();
		if(mp!=null){
			for(int i=0;i<mp.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", mp[i].getName());
				if(mp[i].getMust()==1){
					map.put("must", true);
				}else{
					map.put("must", false);
				}
				
				map.put("id", mp[i].getId());
				blankMap.put((i+1), map);
			}
		}
		//流程列表
		ModelShen[] ss = modelP.getShen();
		Map<Integer,Map<String,Object>> processMap = new HashMap<>();
//		System.out.println(ss.length);
		for(int i=0;i<ss.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			UserInfo ui = userServer.getUserInfoByJobId(ss[i].getPerson());
			String showStr = ss[i].getName()+"（"+ui.getName()+"，";
			if(ui.getPost()==null){
				showStr += "无职务，";
			}else{
				showStr += ui.getPost()+"，";
			}
			showStr += partService.getNameById(ui.getPart())+"，";
			showStr += groupService.getNameById(ui.getPart(), ui.getGroup())+"）";
			map.put("key", showStr);
			map.put("color", "orange");
			processMap.put(ss[i].getOrder(), map);
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureModelLook.jsp");
		model.put("myPageTitle","查看流程表");
		model.put("myPageNav","3");
		
		model.put("pseModelProcessMap", processMap);
		model.put("pseModelId", modelId);//表格题头
		model.put("pseTitle", title);//表格题头
		model.put("pseProduceName", produceName);//项目名称
		model.put("pseIllustrate", illustrate);//说明
		model.put("pseEnclosure", enclosure);//是否有附件
		model.put("pseBlankMap", blankMap);//小项   Map<选项的顺序,Map<key,value>>
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 需要处理列表页面
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("needToDealList.do")
	public ModelAndView viewNeedToDealList(String page,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		
		List<Map<String,String>> procedureList = null;//一页5个
		int allPage = 0;
		int currentPage = 0;
		
		//现在页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//总页数
		allPage = procedureService.getAllNeedToDealListPage(jobId);
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		
		List<Map<String,Object>> tempList = procedureService.getAllNeedToDealListByPage(jobId,currentPage);
		if(tempList!=null){
			procedureList = new ArrayList<Map<String,String>>();
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", ""+(int)tempList.get(i).get("id"));//编号
				map.put("name", (String)tempList.get(i).get("name"));//流程名称
				map.put("produceName", (String)tempList.get(i).get("projectName"));//项目名称
				map.put("createDate", sdf.format((Date)tempList.get(i).get("createDate")));//创建时间
				map.put("createPerson", userServer.getUserNameById((String)tempList.get(i).get("createPerson")));//创建人
				map.put("updateDate", sdf.format((Date)tempList.get(i).get("updateDate")));//更新时间
				procedureList.add(map);
			}
		}
		
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureDealList.jsp");
		model.put("myPageTitle","需处理的流程——查看列表");
		model.put("myPageNav","4");
		
		model.put("pdlProcedureList", procedureList);//列表
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 流程提交列表页面 
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("submitList.do")
	public ModelAndView viewSubmitList(String page,HttpServletRequest req){
		List<Map<String,String>> procedureModelList = null;//一页5个
		int allPage = 0;
		int currentPage = 0;
		
		
		//现在页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//总页数
		allPage = procedureService.getAllModelPage();
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}

		//获取列表
		List<Map<String,Object>> temp = procedureService.getAllModelByPage(currentPage);
		//处理数据
		procedureModelList = new ArrayList<Map<String,String>>();
		for(int i=0;i<temp.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			String tempUser = (String)temp.get(i).get("createPerson");
//			System.out.println(tempUser);
			map.put("id", ""+(int)temp.get(i).get("id"));
			map.put("name", (String)temp.get(i).get("name"));
			map.put("introduction", (String)temp.get(i).get("introduction"));
			map.put("createDate", sdf.format((Date)temp.get(i).get("createDate")));
			map.put("createPerson", userServer.getUserNameById(tempUser));
			procedureModelList.add(map);
		}

		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureSubmitList.jsp");
		model.put("myPageTitle","流程提交——选择列表");
		model.put("myPageNav","1");
		
		model.put("procedureModelList", procedureModelList);
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 我的流程列表界面
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("myList.do")
	public ModelAndView viewMyList(String page,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		
		List<Map<String,String>> procedureList = null;//一页5个
		int allPage = 0;
		int currentPage = 0;
		
		//现在页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//总页数
		allPage = procedureService.getAllMyProcedurePage(jobId);
		if(currentPage>allPage && allPage!=0){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		
		List<Map<String, Object>> tempList = procedureService.getAllMyProcedureSimpleByPage(currentPage,jobId);
		procedureList = new ArrayList<Map<String,String>>();
		if(tempList!=null){
			for(int i=0;i<tempList.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", ""+(int)tempList.get(i).get("id"));//编号
				map.put("name", (String)tempList.get(i).get("name"));//名称
				map.put("produceName", (String)tempList.get(i).get("projectNameTitle"));//项目名称
				map.put("createDate", sdf.format((Date)tempList.get(i).get("createDate")));//创建时间
				map.put("createPerson", userServer.getUserNameById((String)tempList.get(i).get("createPerson")));//创建人
				map.put("status", ""+(int)tempList.get(i).get("status"));//状态
				procedureList.add(map);
			}
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/myProcedureList.jsp");
		model.put("myPageTitle","我的流程——列表");
		model.put("myPageNav","2");
		
		model.put("mplProcedureList", procedureList);
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 模板列表界面
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("modelList.do")
	public ModelAndView viewModelList(String page,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		int allPage = 0;
		int currentPage = 0;
		boolean canAdd = true;
		List<Map<String,String>> tableList = null;//表格数据
		
		//普通用户无法添加
		if(userKind==UserInfo.KIND_MEMBER){
			canAdd = false;
		}
		
		//现在页数
		if(page==null || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		//总页数
		allPage = procedureService.getAllModelPage();
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}

		//获取列表
		List<Map<String,Object>> temp = procedureService.getAllModelByPage(currentPage);
		//处理数据
		tableList = new ArrayList<Map<String,String>>();
		for(int i=0;i<temp.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			String tempUser = (String)temp.get(i).get("createPerson");
//			System.out.println(tempUser);
			map.put("id", ""+(int)temp.get(i).get("id"));
			map.put("name", (String)temp.get(i).get("name"));
			map.put("introduction", (String)temp.get(i).get("introduction"));
			map.put("createDate", sdf.format((Date)temp.get(i).get("createDate")));
			map.put("createPerson", userServer.getUserNameById(tempUser));
			if(userServer.getUserKindByJobId(tempUser)>userKind){
				map.put("canChange", "1");
				map.put("canDel", "1");
			}else if(tempUser.equals(jobId)){
				map.put("canChange", "1");
				map.put("canDel", "1");
			}else{
				map.put("canChange", "0");
				map.put("canDel", "0");
			}
			
			tableList.add(map);
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureModel.jsp");
		model.put("myPageTitle","流程模板");
		model.put("myPageNav","3");
		
		model.put("pmCanAdd", canAdd);//是否可以添加
		model.put("pmTableList", tableList);//表格数据
		model.put("allPage", allPage);//总页数
		model.put("currentPage", currentPage);//当前页
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 模板编辑
	 * @param modelId
	 * @param req
	 * @return
	 */
	@RequestMapping("modelEdit.do")
	public ModelAndView viewModelEdit(String modelId,HttpServletRequest req){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureModelEdit.jsp");
		model.put("myPageTitle","流程模板编辑");
		model.put("myPageNav","3");
		
		boolean isNew = true;
		if(modelId==null || modelId.length()==0){
			//添加新的
			isNew = true;
		}else{
			//编辑、修改旧的
			isNew = false;
			ModelProcedure mp = procedureService.getModelInfoAllById(Integer.parseInt(modelId));
			
			if(mp==null){
				String url = req.getHeader("REFERER");
				url = url.substring(url.indexOf("/procedure/"));
				return JumpPrompt.jumpOfModelAndView(url, "没有ID为“"+modelId+"”的模板！");
			}
			String name = mp.getName();
			String introduction = mp.getIntroduction();
			String illustrate = mp.getIllustrate();
			String remark = mp.getRemark();
			String title = mp.getTitle();
			String produceName = mp.getProjectName();
			int enclosure = mp.getEnclosure();//上传附件
			Map<Integer,Map<String,Object>> blankMap = null;
			Map<Integer,Map<String,Object>> processMap = null;
			
			blankMap = new HashMap<Integer,Map<String,Object>>();
			processMap = new HashMap<Integer,Map<String,Object>>();
			//填选项列表
			ModelOption[] opts = mp.getOption();
			for(int i=0;i<opts.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", opts[i].getName());
				if(opts[i].getMust()==0){
					map.put("must", false);
				}else{
					map.put("must", true);
				}
				blankMap.put(opts[i].getOrder(), map);
			}
			//流程列表
			ModelShen[] ss = mp.getShen();
//			System.out.println(ss.length);
			for(int i=0;i<ss.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", ss[i].getName());
				map.put("part", ss[i].getPart());
				map.put("partName", partService.getNameById(ss[i].getPart()) );
				map.put("group", ss[i].getGroup());
				map.put("groupName", groupService.getNameById(ss[i].getPart(), ss[i].getGroup()));
				map.put("personId", ss[i].getPerson());
				map.put("personName", userServer.getUserNameById(ss[i].getPerson()));
				processMap.put(ss[i].getOrder(), map);
			}

			model.put("pmeModelId", modelId);
			model.put("pmeName", name);
			model.put("pmeIntroduction", introduction);
			model.put("pmeIllustrate", illustrate);
			model.put("pmeRemark", remark);
			model.put("pmeTitle", title);
			model.put("pmeProduceName", produceName);
			model.put("pmeEnclosure", enclosure);
			model.put("pmeBlankMap", blankMap);//填选项列表
			model.put("pmeProcessMap", processMap);//流程列表
		}
		model.put("pmeIsNew", isNew);//是否是新的
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 我提交的详情
	 * @param submitId
	 * @param req
	 * @return
	 */
	@RequestMapping("mySubmitDetail.do")
	public ModelAndView viewMySumbitDetail(String submitId,HttpServletRequest req){
		String backUrl = req.getHeader("REFERER");
		backUrl = backUrl.substring(backUrl.indexOf("/procedure/"));
		if(submitId==null || submitId.length()==0){
			return JumpPrompt.jumpOfModelAndView("/home.do", "查看失败。（参数错误）");
		}
		
		ProcedureSubmit pSubmit = null;
		pSubmit = procedureService.getMySubmitAllInfoById(Integer.parseInt(submitId));
		if(pSubmit==null){
			return JumpPrompt.jumpOfModelAndView("/procedure/myList.do", "查看失败。（没找到对应的流程）");
		}
		
		Map<Integer,Map<String,String>> tblankMap = null;
		Map<Integer,Map<String,Object>> tprocedureMap = null;
		
		String tname = pSubmit.getName() + "流程";//XXXXX流程表
		String tproduceNameTitle = pSubmit.getProjectName();//项目名称
		String tproduceName = pSubmit.getProjectNameTitle();//项目名称内容
		String tidStr = "A"+pSubmit.getId();//编号
		String tid = ""+pSubmit.getId();//编号
		String tcreatePerson = userServer.getUserNameById(pSubmit.getCreatePerson());//提交人
		String tcreateDate = sdf.format(pSubmit.getCreateDate());//申请时间
		String tremark = pSubmit.getRemark();//备注
		String tshowFileName = pSubmit.getShowFileName();//上传的文件名
		String tfileName = pSubmit.getFileName();//服务器上的文件名
		String tpart = pSubmit.getPartName();//所属部门
		String tgroup = pSubmit.getGroupName();//所属小组
		int status = pSubmit.getStatus();

		//填选
		ProcedureOption[] opts = pSubmit.getOpts();
		if(opts!=null){
			tblankMap = new HashMap<Integer,Map<String,String>>();
			for(int i=0;i<opts.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("title", opts[i].getTitle());
				map.put("content", opts[i].getContent());
				tblankMap.put(opts[i].getOrder(), map);
			}
		}
		
		
		//审批
		ProcedureShen[] shens = pSubmit.getShens();
//		System.out.println(shens);
		tprocedureMap = new HashMap<Integer,Map<String,Object>>();
		for(int i=0;i<shens.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", ""+shens[i].getId());
			map.put("title", shens[i].getName());
			map.put("content", shens[i].getContent());
			map.put("name", shens[i].getUserName());
			if(shens[i].getTime()!=null){
				map.put("time", sdf.format(shens[i].getTime()));
			}else{
				map.put("time", null);
			}
			if(shens[i].getWork()==ProcedureShen.WORK_OK){
				map.put("isWorked", "ok");
			}else if(shens[i].getWork()==ProcedureShen.WORK_PASS){
				map.put("isWorked", "pass");
			}else{
				map.put("isWorked", "no");
			}
			tprocedureMap.put(shens[i].getOrder(), map);
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/myProcedureDetail.jsp");
		model.put("myPageTitle","我的流程——查看");
		model.put("myPageNav","2");
		
		
		model.put("mpdBackUrl", backUrl);//上一级路径
		model.put("mpdStatus", status);//流程状态
		model.put("mpdTname", tname);//XXXXX流程表
		model.put("mpdProduceName", tproduceName);//项目名称
		model.put("mpdProduceNameTitle", tproduceNameTitle);//项目名称内容
		model.put("mpdId", tid);//编号
		model.put("mpdIdStr", tidStr);//编号串
		model.put("mpdCreatePerson", tcreatePerson);//提交人
		model.put("mpdCreateDate", tcreateDate);//申请时间
		model.put("mpdRemark", tremark);//备注
		model.put("mpdShowFileName", tshowFileName);//上传的文件名
		model.put("mpdFileName", tfileName);//服务器上的文件名
		model.put("mpdPart", tpart);//所属部门
		model.put("mpdGroup", tgroup);//所属小组
		model.put("mpdBlankMap", tblankMap);//小项列表
		model.put("mpdProcedureMap", tprocedureMap);//流程列表
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 需要处理详情
	 * @param submitId
	 * @param req
	 * @return
	 */
	@RequestMapping("needToDealDetail.do")
	public ModelAndView viewNeedToDealDetail(String submitId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String backUrl = req.getHeader("REFERER");
		backUrl = backUrl.substring(backUrl.indexOf("/procedure/"));
		if(submitId==null || submitId.length()==0){
			return JumpPrompt.jumpOfModelAndView("/home.do", "查看失败。（参数错误）");
		}
		
		ProcedureSubmit pSubmit = null;
		
		pSubmit = procedureService.getMySubmitAllInfoById(Integer.parseInt(submitId));
		if(pSubmit==null){
			return JumpPrompt.jumpOfModelAndView("/procedure/needToDealList.do", "查看失败。（没找到对应的流程）");
		}
		
		Map<Integer,Map<String,String>> tblankMap = null;
		Map<Integer,Map<String,Object>> tprocedureMap = null;
		
		String tname = pSubmit.getName() + "流程";//XXXXX流程表
		String tproduceName = pSubmit.getProjectName();//项目名称
		String tproduceNameTitle = pSubmit.getProjectNameTitle();//项目名称内容
		String tid = "A"+pSubmit.getId();//编号
		String tcreatePerson = userServer.getUserNameById(pSubmit.getCreatePerson());//提交人
		String tcreateDate = sdf.format(pSubmit.getCreateDate());//申请时间
		String tremark = pSubmit.getRemark();//备注
		String tshowFileName = pSubmit.getShowFileName();//上传的文件名
		String tfileName = pSubmit.getFileName();//服务器上的文件名
		String tpart = pSubmit.getPartName();//所属部门
		String tgroup = pSubmit.getGroupName();//所属小组
		int status = pSubmit.getStatus();

		//填选
		ProcedureOption[] opts = pSubmit.getOpts();
		if(opts!=null){
			tblankMap = new HashMap<Integer,Map<String,String>>();
			for(int i=0;i<opts.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("title", opts[i].getTitle());
				map.put("content", opts[i].getContent());
				tblankMap.put(opts[i].getOrder(), map);
			}
		}
		
		//审批
		ProcedureShen[] shens = pSubmit.getShens();
//		System.out.println(shens);
		tprocedureMap = new HashMap<Integer,Map<String,Object>>();
		for(int i=0;i<shens.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", ""+shens[i].getId());
			map.put("title", shens[i].getName());
			map.put("content", shens[i].getContent());
			map.put("name", shens[i].getUserName());
			if(shens[i].getTime()!=null){
				map.put("time", sdf.format(shens[i].getTime()));
			}else{
				map.put("time", null);
			}
			if(shens[i].getWork()==ProcedureShen.WORK_OK){
				map.put("isWorked", true);
			}else if(shens[i].getWork()==ProcedureShen.WORK_NEED || shens[i].getUserJobId().equals(jobId)){
				map.put("isWorked", false);
				map.put("needWork", true);
				map.put("spId", ""+shens[i].getId());
				map.put("proId", ""+shens[i].getProcedureId());
			}else{
				map.put("isWorked", false);
				map.put("needWork", false); 
			}
			tprocedureMap.put(shens[i].getOrder(), map);
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureDealEdit.jsp");
		model.put("myPageTitle","需处理流程——处理");
		model.put("myPageNav","4");
		
		model.put("pdeBackUrl", backUrl);//上一级路径
		model.put("pdeStatus", status);//流程状态
		model.put("pdeTname", tname);//XXXXX流程表
		model.put("pdeProduceName", tproduceName);//项目名称
		model.put("pdeProduceNameTitle", tproduceNameTitle);//项目名称内容
		model.put("pdeId", tid);//编号
		model.put("pdeCreatePerson", tcreatePerson);//提交人
		model.put("pdeCreateDate", tcreateDate);//申请时间
		model.put("pdeRemark", tremark);//备注
		model.put("pdeShowFileName", tshowFileName);//上传的文件名
		model.put("pdeFileName", tfileName);//服务器上的文件名
		model.put("pdePart", tpart);//所属部门
		model.put("pdeGroup", tgroup);//所属小组
		model.put("pdeBlankMap", tblankMap);//小项列表
		model.put("pdeProcedureMap", tprocedureMap);//流程列表
		return new ModelAndView("baseJsp",model);
	}
	
	
	
	/**
	 * 新的流程提交填写表 
	 * @param modelId
	 * @param req
	 * @return
	 */
	@RequestMapping("submitDetail.do")
	public ModelAndView viewSubmitDetail(String modelId,HttpServletRequest req){
		if(modelId==null || modelId.length()==0){
			String url = req.getHeader("REFERER");
			url = url.substring(url.indexOf("/procedure/"));
			return JumpPrompt.jumpOfModelAndView(url, "提交参数错误");
		}
		ModelProcedure modelP = procedureService.getModelInfoAllById(Integer.parseInt(modelId));
		
		String title = modelP.getTitle();//表格题头
		String produceName = modelP.getProjectName();//项目名称
		String illustrate = modelP.getIllustrate();//说明
		int enclosure = modelP.getEnclosure();//是否有附件
		Map<Integer,Map<String,Object>> blankMap = null;//小项  Map<选项的顺序,Map<key,value>>

		//数据
		ModelOption[] mp = modelP.getOption();
		blankMap = new HashMap<Integer,Map<String,Object>>();
		if(mp!=null){
			for(int i=0;i<mp.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", mp[i].getName());
				if(mp[i].getMust()==1){
					map.put("must", true);
				}else{
					map.put("must", false);
				}
				
				map.put("id", mp[i].getId());
				blankMap.put((i+1), map);
			}
		}
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","procedure/procedureSubmitEdit.jsp");
		model.put("myPageTitle","填写流程表");
		model.put("myPageNav","1");
		
		model.put("pseModelId", modelId);//表格题头
		model.put("pseTitle", title);//表格题头
		model.put("pseProduceName", produceName);//项目名称
		model.put("pseIllustrate", illustrate);//说明
		model.put("pseEnclosure", enclosure);//是否有附件
		model.put("pseBlankMap", blankMap);//小项   Map<选项的顺序,Map<key,value>>
		return new ModelAndView("baseJsp",model);
	}
	
	
	/**
	 * 添加或修改流程模板
	 * @param req
	 * @return
	 */
	@RequestMapping("addNewModelForm.do")
	public ModelAndView addOrEditModel(HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		//获取上一个地址
		String beforUrl = req.getHeader("REFERER");
		
		beforUrl = beforUrl.substring(beforUrl.indexOf("/procedure/"));
//		System.out.println("上一个URI:"+beforUrl);
		int userKind = userServer.getUserKindByJobId(jobId);
		if(userKind==UserInfo.KIND_MEMBER){
			return JumpPrompt.jumpOfModelAndView("/home.do", "没有权限，无法使用。");
		}
		
		
		ModelProcedure procedure = new ModelProcedure();
		ModelOption[] opts = null;
		ModelShen[] shens = null;
		
		String modeId = req.getParameter("modeId");
		boolean isNew = false;
		if(modeId.equals("-1")){
			isNew = true;
		}else{
			procedure.setId(Integer.parseInt(modeId));//设置ID
		}

		String pname = req.getParameter("pname").trim();//流程名称
		if(pname.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“流程名称”不可为空");
		}
		procedure.setName(pname);
		String pdescribe = req.getParameter("pdescribe").trim();//流程简介
		if(pdescribe.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“流程简介”不可为空");
		}
		procedure.setIntroduction(pdescribe);
		String pillustration = req.getParameter("pillustration").trim();//填写说明
		if(pillustration.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“填写说明”不可为空");
		}
		procedure.setIllustrate(pillustration);
		String premark = req.getParameter("premark").trim();//填写备注
		if(premark.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“填写备注”不可为空");
		}
		procedure.setRemark(premark);
		String ptableTitle = req.getParameter("ptableTitle").trim();//表格题头
		if(ptableTitle.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“表格题头”不可为空");
		}
		procedure.setTitle(ptableTitle);
		String pprocedureTitle = req.getParameter("pprocedureTitle").trim();//项目名称
		if(pprocedureTitle.length()==0){
			return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“项目名称”不可为空");
		}
		procedure.setProjectName(pprocedureTitle);
		String pattachmentNeed = req.getParameter("pattachmentNeed");//上传附件（需转为int）
		procedure.setEnclosure(Integer.parseInt(pattachmentNeed));
		procedure.setCreatePerson(jobId);//创建人ID
		
		int processNumber = Integer.parseInt(req.getParameter("processNumber").trim());//流程个数
		int blankNumber = Integer.parseInt(req.getParameter("blankNumber").trim());//填选个数
		int processNumberEnd = Integer.parseInt(req.getParameter("processNumberEnd").trim());//流程个数结束
		int blankNumberEnd = Integer.parseInt(req.getParameter("blankNumberEnd").trim());//填选结束
		
		opts = new ModelOption[blankNumber];
		shens = new ModelShen[processNumber];
		
		int i = 1;
		int all = 0;
		//读取填选项
		while(i<=blankNumberEnd){
			String nameTemp = req.getParameter("pinputName"+i).trim();
			if(nameTemp.length()==0){
				return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“填选项"+(all+1)+"”不可为空");
			}
			if(nameTemp==null || nameTemp.length()==0){
				//不存在
				i++;
				continue;
			}
			all++;
			ModelOption o = new ModelOption();
			o.setName(nameTemp);
			o.setMust(Integer.parseInt(req.getParameter("pinputMust"+i).trim()));
			o.setOrder(all);
			opts[all-1] = o;//存入
			if(all==blankNumber){
				//够了
				break;
			}
			i++;
		}
		procedure.setOption(opts);
		
		i = 1;
		all = 0;
		//读取流程
		while(i<=processNumberEnd){
			String nameTemp = req.getParameter("pprocessName"+i).trim();
			if(nameTemp.length()==0){
				return JumpPrompt.jumpOfModelAndView(beforUrl, "创建失败。“流程"+(all+1)+"”不可为空");
			}
			if(nameTemp==null || nameTemp.length()==0){
				//不存在
				i++;
				continue;
			}
			all++;
			ModelShen s = new ModelShen();
			s.setName(nameTemp);
			s.setPart(Integer.parseInt(req.getParameter("ppart"+i).trim()));
			s.setGroup(Integer.parseInt(req.getParameter("pgroup"+i).trim()));
			s.setPerson(req.getParameter("pperson"+i).trim());
			s.setOrder(all);
			shens[all-1] = s;//存入
			if(all==processNumber){
				//够了
				break;
			}
			i++;
		}
		procedure.setShen(shens);
		
		try{
			if(isNew){
				//进行创建
				if(procedureService.createNewProcedure(procedure)){
					return JumpPrompt.jumpOfModelAndView("/procedure/modelList.do", "创建成功！");
				}else{
					return JumpPrompt.jumpOfModelAndView("/procedure/modelList.do", "创建失败。");
				}
			}else{
				//进行修改
				if(procedureService.updateProcedure(procedure)){
					return JumpPrompt.jumpOfModelAndView("/procedure/modelList.do", "修改成功！");
				}else{
					return JumpPrompt.jumpOfModelAndView("/procedure/modelList.do", "修改成功！");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/procedure/modelList.do", "创建失败。（服务器异常）");
		}
	}
	
	/**
	 * 删除模板
	 * @param modelId
	 * @param req
	 * @return
	 */
	@RequestMapping("delModel.do")
	public ModelAndView delModel(String modelId,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String url = req.getHeader("REFERER");
		url = url.substring(url.indexOf("/procedure/"));
		//获取内容
		String returnStr = "(⊙o⊙)哦？";
		
		String create = procedureService.getUserOfProcedureWhoCreateById(Integer.parseInt(modelId));
		if(userServer.getUserKindByJobId(create)>userServer.getUserKindByJobId(jobId)){
			//高权限
		}else if(create.equals(jobId)){
			//创建者自己
		}else{
			returnStr = "没有删除权限。";
			return JumpPrompt.jumpOfModelAndView(url, returnStr);
		}
		if(modelId!=null && modelId.length()!=0){
			try {
				if(procedureService.delProcedureById(Integer.parseInt(modelId))){
					returnStr = "删除成功！";
				}else{
					returnStr = "删除失败。";
				}
			} catch (Exception e) {
				e.printStackTrace();
				JumpPrompt.jumpOfModelAndView(url, "删除失败。");
			}
		}else{
			returnStr = "删除时，传入信息错误";
		}
		return JumpPrompt.jumpOfModelAndView(url, returnStr);
	}
	
	
	/**
	 * 添加或修改流程模板
	 * @param enclosure
	 * @param req
	 * @return
	 */
	@RequestMapping("addNewProcedureForm.do")
	public ModelAndView addNewProcedure(@RequestParam(value = "ptsmallFile") MultipartFile enclosure,
			HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String url = req.getHeader("REFERER");
		url = url.substring(url.indexOf("/procedure/"));
		
		if(enclosure!=null){
//			System.out.println(enclosure.getOriginalFilename());
//			System.out.println(enclosure.getSize());
		}else{
//			System.out.println("enclosure是空");
		}
		
		
		//最终需要保存的
		ProcedureSubmit procedureSubmit = new ProcedureSubmit();
		//流程模板
		ModelProcedure modelProcedure = new ModelProcedure();
		
		String modelId = req.getParameter("modelId");
		String name = req.getParameter("ptprocedureName");
		
		//获取流程模板
		modelProcedure = procedureService.getModelInfoAllById(Integer.parseInt(modelId));
		procedureSubmit.setName(modelProcedure.getName());
		procedureSubmit.setProjectNameTitle(modelProcedure.getProjectName());
		procedureSubmit.setProjectName(name);
		procedureSubmit.setCreatePerson(jobId);
		int tempPart = userServer.getUserPartByJobId(jobId);
		procedureSubmit.setPartName(partService.getNameById(tempPart));
		procedureSubmit.setGroupName(groupService.getNameById(tempPart, userServer.getUserGroupByJobId(jobId)));
		procedureSubmit.setRemark(modelProcedure.getRemark());
		
		//状态
		procedureSubmit.setStatus(ProcedureSubmit.STATUS_WORKING);
		
		
		
		//获得需要填选 顺序、标题、是否必须
		ModelOption[] opts = modelProcedure.getOption();
		ProcedureOption[] pOpts = new ProcedureOption[opts.length];//序号保存的
		for(int i=0;i<opts.length;i++){
			ProcedureOption o = new ProcedureOption();
			//得到的内容
//			System.out.println(i+":"+req.getParameter("ptsmallName"+opts[i].getId()));
//			System.out.println(opts[i].getId());
			o.setContent(req.getParameter("ptsmallName"+opts[i].getId()));
			o.setOrder(opts[i].getOrder());
			o.setTitle(opts[i].getName());
			if(opts[i].getMust()==1){
				//检查必填
				if(o.getContent()==null){
					//必填的没有填
					return JumpPrompt.jumpOfModelAndView(url, "提交失败。（必填项“"+opts[i].getName()+"”没有填写）");
				}
			}
			pOpts[i]=o;
		}
		procedureSubmit.setOpts(pOpts);
		
		//处理审批
		ModelShen[] shens = modelProcedure.getShen();
		ProcedureShen[] pShens = new ProcedureShen[shens.length];//序号保存的
		for(int i=0;i<shens.length;i++){
			ProcedureShen ps = new ProcedureShen();
			ps.setUserGroup(shens[i].getGroup());
			ps.setUserPart(shens[i].getPart());
			ps.setUserJobId(shens[i].getPerson());
			ps.setName(shens[i].getName());
			ps.setUserName(userServer.getUserNameById(shens[i].getPerson()));
			ps.setOrder(shens[i].getOrder());
			if(shens[i].getOrder()==1){
				ps.setWork(ProcedureShen.WORK_NEED);
			}else{
				ps.setWork(ProcedureShen.WORK_NO);
			}
			pShens[i]=ps;
		}
		procedureSubmit.setShens(pShens);
		
		//缺少传文件名、真文件名
		
		//获取文件
		File file = null;
		if(enclosure.getSize()!=0){
			//有文件
			//上传的文件名
			procedureSubmit.setShowFileName(enclosure.getOriginalFilename());
			ServletContext application = req.getServletContext();
			String realPath = application.getRealPath(filePath);
			int index = enclosure.getOriginalFilename().lastIndexOf(".");
			String suffix = enclosure.getOriginalFilename().substring(index+1);
			//命名格式：jobId_1234567891235646.后缀
			String realFileName = jobId+"_"+new Date().getTime()+"."+suffix;
			String fileName = realPath+File.separator+realFileName;
			//获取文件
			file = new File(fileName);
			try {
				File fileTemp = new File(realPath);
				if(!fileTemp.exists()){
					fileTemp.mkdirs();
				}
				enclosure.transferTo(file);
				procedureSubmit.setFileName(realFileName);
			} catch (IllegalStateException | IOException e) {
				procedureSubmit.setShowFileName(null);
				e.printStackTrace();
			}
		}
		//设置时间
		procedureSubmit.setCreateDate(new Date());
		
		//保存到数据库
		try {
			if(procedureService.procedureSubmit(procedureSubmit)){
				//保存成功
				return JumpPrompt.jumpOfModelAndView("/procedure/submitList.do", "流程提交成功！");
			}else{
				//保存失败
				//删除文件
				if(file!=null){
					file.delete();
				}
				return JumpPrompt.jumpOfModelAndView("/procedure/submitList.do", "流程提交失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView("/procedure/submitList.do", "流程提交失败（服务器异常）");
		}
	}
	
	/**
	 * 处理流程提交
	 * @param proId
	 * @param spid
	 * @param content
	 * @param pass
	 * @param req
	 * @return
	 */
	@RequestMapping("dealProcedureForm.do")
	public ModelAndView dealProcedure(String proId,String spid,String content,String pass,
			HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		String url = "/procedure/needToDealList.do";
		if(proId!=null && proId.length()!=0){
			if(spid!=null && spid.length()!=0){
				if(content!=null && content.length()!=0){
					if(pass!=null && pass.length()!=0){
						
					}else{
						return JumpPrompt.jumpOfModelAndView(url, "审批失败。（缺少意见）");
					}
				}else{
					return JumpPrompt.jumpOfModelAndView(url, "审批失败。（缺少意见内容）");
				}
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "审批失败。（缺少过程号）");
			}
		}else{
			return JumpPrompt.jumpOfModelAndView(url, "审批失败。（缺少流程号）");
		}
		
		ProcedureShen shen = new ProcedureShen();
		shen.setContent(content);
		shen.setProcedureId(Integer.parseInt(proId));
		shen.setId(Integer.parseInt(spid));
		if(pass.equals("yes")){
			shen.setPass(true);
		}else{
			shen.setPass(false);
		}
		shen.setTime(new Date());
		
		//保存到数据库
		
		try {
			if(procedureService.dealOneProcedure(shen,jobId)){
				//成功
				return JumpPrompt.jumpOfModelAndView(url, "审批成功！");
			}else{
				//失败
				return JumpPrompt.jumpOfModelAndView(url, "审批失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JumpPrompt.jumpOfModelAndView(url, "审批失败。（服务器异常）");
		}
	}
	
	/**
	 * /下载word文档
	 * @param submitId
	 * @param request
	 * @param response
	 */
	@RequestMapping("downFileOfWord.do")
	public void downFileToWord(String submitId,HttpServletRequest request,HttpServletResponse response){
		String showFile = "号文档.doc";
		String tempFileName = submitId +"_"+ System.currentTimeMillis();
		if(submitId==null || submitId.length()==0){
			return;
		}
		ProcedureSubmit pSubmit = null;
		pSubmit = procedureService.getMySubmitAllInfoById(Integer.parseInt(submitId));
		if(pSubmit==null){
			return;
		}
		String servletPath = request.getServletContext().getRealPath("/");
		servletPath = servletPath + File.separator + "downFile";
		
		Map<String,String> info = new HashMap<>();
		Map<Integer,Map<String,String>> opt =null;
		Map<Integer,Map<String,String>> shen = null;

		
		String tname = pSubmit.getName() + "流程";//XXXXX流程表
		String tproduceNameTitle = pSubmit.getProjectName();//项目名称
		String tproduceName = pSubmit.getProjectNameTitle();//项目名称内容
		String tid = "A"+pSubmit.getId();//编号
		String tcreatePerson = userServer.getUserNameById(pSubmit.getCreatePerson());//提交人
		String tcreateDate = sdf.format(pSubmit.getCreateDate());//申请时间
		String tremark = pSubmit.getRemark();//备注
		String tshowFileName = pSubmit.getShowFileName();//上传的文件名
		String tpart = pSubmit.getPartName();//所属部门
		String tgroup = pSubmit.getGroupName();//所属小组
		int status = pSubmit.getStatus();
		
		showFile = pSubmit.getId() + showFile;
		info.put(DownWord.REPLACE_ALL_TITLE,tname);
		info.put(DownWord.REPLACE_PROJECT_TITLE_NAME,tproduceName);
		info.put(DownWord.REPLACE_PROJECT_NAME,tproduceNameTitle);
		info.put(DownWord.REPLACE_SUBMIT_PERSON,tcreatePerson);
		info.put(DownWord.REPLACE_SUBMIT_TIME,tcreateDate);
		info.put(DownWord.REPLACE_SUBMIT_PART,tpart);
		info.put(DownWord.REPLACE_SUBMIT_GROUP,tgroup);
		info.put(DownWord.REPLACE_ID,tid);
		info.put(DownWord.REPLACE_FU_JIAN,tshowFileName);
		info.put(DownWord.REPLACE_BEI_ZHU,tremark);
		if(status==ProcedureSubmit.STATUS_PASS){
			status = DownWord.PASS_OK;
		}else if(status==ProcedureSubmit.STATUS_NO_PASS){
			status = DownWord.PASS_NO;
		}else if(status==ProcedureSubmit.STATUS_WORKING){
			status = DownWord.PASS_WORKING;
		}else {
			status = DownWord.PASS_OTHER;
		}
		
		ProcedureOption[] opts = pSubmit.getOpts();
		if(opts!=null){
			opt = new HashMap<Integer,Map<String,String>>();
			for(int i=0;i<opts.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("title", opts[i].getTitle());
				map.put("content", opts[i].getContent());
				opt.put(opts[i].getOrder(), map);
			}
		}
//		for(int i=0;i<7;i++) {
//			Map<String,String> map = new HashMap<>();
//			map.put("title", "选项标题"+i);
//			map.put("content", "我是内容啊"+i);
//			opt.add(map);
//		}
		
//		for(int i=0;i<5;i++) {
//			Map<String,String> map = new HashMap<>();
//			map.put("name", "填写人"+i);
//			map.put("time", "2017年11月14日10:57:4"+i);
//			map.put("title", "流程号"+i);
//			map.put("content", "同意了"+i*6);
//			shen.add(map);
//		}
		//审批
		ProcedureShen[] shens = pSubmit.getShens();
//		System.out.println(shens);
		shen = new HashMap<Integer,Map<String,String>>();
		for(int i=0;i<shens.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("title", shens[i].getName());
			if(shens[i].getTime()!=null){
				map.put("time", sdf.format(shens[i].getTime()));
				map.put("content", shens[i].getContent());
				map.put("name", shens[i].getUserName());
			}else{
				map.put("time", null);
				map.put("content", null);
				map.put("name", null);
			}
			shen.put(shens[i].getOrder(), map);
		}
		
		DownWord deal = new DownWord();
		deal.createFile(servletPath,tempFileName,info, opt, shen,status);
		
		
		File file = new File(servletPath,tempFileName+".doc");
//		System.out.println("\nAction->"+file.getAbsolutePath());
		if(file.exists()){
			response.setContentType("application/octet-stream;charset=UTF-8");// 设置强制下载不打开
			response.addHeader("Content-Length", "" + file.length());//文件长度
			try {
				String encodedFileName = new String(showFile.getBytes("utf-8"),"iso-8859-1");
				response.addHeader("Content-Disposition",  "attachment;fileName=\"" +encodedFileName+"\"" );
			} catch (UnsupportedEncodingException e1) {
				response.addHeader("Content-Disposition",  "attachment;fileName=" +showFile);
				e1.printStackTrace();
			}//设置文件名
			OutputStream os = null;
			FileInputStream fis = null;
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = fis.read(buffer))!=-1){
					os.write(buffer,0,len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(os!=null)
					try {
						os.close();
					}catch (IOException e) {e.printStackTrace();
				}
				if(fis!=null)
					try {
						fis.close();
					}catch (IOException e) {e.printStackTrace();
				}
//				file.delete();
			}
		}
	}
	
	/**
	 * 下载文件
	 * @param showFile
	 * @param realFile
	 * @param req
	 * @param response
	 */
	@RequestMapping("downFile.do")
	private void downFile(String showFile,String realFile,HttpServletRequest req,HttpServletResponse response) {
		if(realFile==null || realFile.length()==0){
			return;
		}
		ServletContext application = req.getServletContext();
		String realPath = application.getRealPath(filePath);
		String fileName = realPath+File.separator+realFile;
		File file = new File(fileName);
		if(file.exists()){
			response.setContentType("application/octet-stream;charset=UTF-8");// 设置强制下载不打开
			response.addHeader("Content-Length", "" + file.length());//文件长度
			try {
				String encodedFileName = new String(showFile.getBytes("utf-8"),"iso-8859-1");
				response.addHeader("Content-Disposition",  "attachment;fileName=\"" +encodedFileName+"\"" );
			} catch (UnsupportedEncodingException e1) {
				response.addHeader("Content-Disposition",  "attachment;fileName=" +showFile);
				e1.printStackTrace();
			}//设置文件名
			OutputStream os = null;
			FileInputStream fis = null;
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = fis.read(buffer))!=-1){
					os.write(buffer,0,len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(os!=null)
					try {
						os.close();
					}catch (IOException e) {e.printStackTrace();
				}
				if(fis!=null)
					try {
						fis.close();
					}catch (IOException e) {e.printStackTrace();
				}
			}
		}
	}

}

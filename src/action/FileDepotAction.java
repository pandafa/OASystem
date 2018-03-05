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
import bean.FileDepot;
import bean.UserInfo;
import service.FileDepotService;
import service.GroupService;
import service.PartService;
import service.UserService;
import util.JumpPrompt;

@Controller
public class FileDepotAction {
	@Autowired
	private UserService userServer;
	@Autowired
	private FileDepotService fileDepotService;
	@Autowired
	private PartService partService;
	@Autowired
	private GroupService groupService;
	
	private SimpleDateFormat sdf;
	
	public FileDepotAction(){
		super();
		sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
	}

	/**
	 * 转到文件仓库页面
	 * @param page
	 * @param kind
	 * @param req
	 * @return
	 */
	@RequestMapping("file/fileHome.do")
	public ModelAndView viewExample(String page,String kind,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		int userPart = userServer.getUserPartByJobId(jobId);
		int userGroup= userServer.getUserGroupByJobId(jobId);
		String userPartName = partService.getNameById(userPart);
		String userGroupName= groupService.getNameById(userPart, userGroup);
		
		List<Map<String,String>> filesList = null;//一页5个
		String currentFile = "group";
		int allPage = 0;
		int currentPage = 0;
		int canUpload = 0;
		int canSelectPart = userPart;
		int canSelectGroup = userGroup;
		
		//当前文件分类
		if(kind==null  || kind.length()==0){
			currentFile = "company";
			kind = "company";
		}else{
			currentFile = kind;
		}
		//当前页
		if(page==null  || page.length()==0){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(page);
		}
		
		//总页数
		if(kind.equals("group")){
			//小组
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canUpload = 1;
				canSelectPart = -1;
				canSelectGroup= -1;
				allPage = fileDepotService.getFileListOfAllPartAndGroupPageNumber();
			}else if(userKind==UserInfo.KIND_MANAGER_PART){
				canUpload = 1;
				canSelectGroup= -1;
				allPage = fileDepotService.getFileListOfPartPageNumber(userPart);
			}else if(userKind==UserInfo.KIND_MANAGER_GROUP){
				canUpload = 1;
				allPage = fileDepotService.getFileListGroupPageNumber(userPart, userGroup);
			}else{
				allPage = fileDepotService.getFileListGroupPageNumber(userPart, userGroup);
			}
		}else if(kind.equals("part")){
			//部门
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canUpload = 1;
				canSelectPart = -1;
				allPage = fileDepotService.getFileListOfAllPartPageNumber();
			}else if(userKind==UserInfo.KIND_MANAGER_PART){
				canUpload = 1;
				allPage = fileDepotService.getFileListOfPartPageNumber(userPart);
			}else{
				allPage = fileDepotService.getFileListOfPartPageNumber(userPart);
			}
		}else{
			//公司
			allPage = fileDepotService.getFileListOfCompanyPageNumber();
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canUpload = 1;
			}
		}


		//防止页数非法
		if(currentPage>allPage){
			currentPage = allPage;
		}else if(currentPage<1){
			currentPage = 1;
		}
		
		List<FileDepot> list = null;
		if(kind.equals("group")){
			//小组
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				list = fileDepotService.getFileListByPageOfAllPartAndGroup(currentPage);
			}else if(userKind==UserInfo.KIND_MANAGER_PART){
				list = fileDepotService.getFileListByPageOfAllGroup(userPart, currentPage);
			}else{
				list = fileDepotService.getFileListByPageOfGroup(userPart, userGroup, currentPage);
			}
		}else if(kind.equals("part")){
			//部门
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				list = fileDepotService.getFileListByPageOfAllPart(currentPage);
			}else{
				list = fileDepotService.getFileListByPageOfPart(userPart, currentPage);
			}
		}else{
			//公司
			list = fileDepotService.getFileListByPageOfCompany(currentPage);
		}
		//数据
		if(list!=null){
			filesList = new ArrayList<Map<String,String>>();
			for(int i=0;i<list.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("fileId", ""+list.get(i).getId());
				map.put("showFileName", list.get(i).getShowFileName());
				//来源
				if(kind.equals("group")){
					//小组
					map.put("source", groupService.getNameById(list.get(i).getPart(), list.get(i).getGroup()));
				}else if(kind.equals("part")){
					//部门
					map.put("source", partService.getNameById(list.get(i).getPart()));
				}else{
					//公司
					map.put("source", "公司");
				}
				//上传者名字
				String uploadName = userServer.getUserNameById(list.get(i).getCreatePerson());
				if(uploadName==null){
					//没找到人的名字
					map.put("uploadPersonName", "未知");
				}else{
					map.put("uploadPersonName", uploadName);
				}
				map.put("size", translateFileSize(list.get(i).getSize()));
				map.put("updateDate", sdf.format(list.get(i).getUpdateDate()));
				filesList.add(map);
			}
		}
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("myPageUrlName","file/fileHome.jsp");
		model.put("myPageTitle","文件仓库");
		model.put("myPageNav","5");
		
		model.put("fhPartName", userPartName);
		model.put("fhGroupName", userGroupName);
		model.put("fhCanSelectPart", canSelectPart);
		model.put("fhCanSelectGroup", canSelectGroup);
		model.put("fhFileKind", kind);//文件类型
		model.put("fhFilesList", filesList);//文件列表
		model.put("fhCurrentFile", currentFile);//当前的文件目录
		model.put("fhCanUpload", canUpload);//是否可以上传，1为可以
		model.put("allPage", allPage);
		model.put("currentPage", currentPage);
		return new ModelAndView("baseJsp",model);
	}
	
	/**
	 * 转换文件大小
	 * @param size
	 * @return
	 */
	private String translateFileSize(long size){
		String resStr = null;
		if((size/1024)<512){
			resStr = (size/1024) + "KB";
		}else if((size/1024/1024)<512){
			resStr = (size/1024/1024) + "MB";
		}else if((size/1024/1024/1024)<512){
			resStr = (size/1024/1024/1024) + "GB";
		}else{
			resStr = (size/1024/1024/1024/1024) + "TB";
		}
		return resStr;
	}
	
	/**
	 * 上传文件到文件仓库
	 * @param uploadFile
	 * @param fileKind
	 * @param selectPart
	 * @param selectGroup
	 * @param req
	 * @return
	 */
	@RequestMapping("file/uploadFileFrom.do")
	public ModelAndView uploadFile(@RequestParam(value = "file") MultipartFile uploadFile,
			String fileKind,String selectPart,String selectGroup,HttpServletRequest req){
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		int userPart = userServer.getUserPartByJobId(jobId);
		int userGroup= userServer.getUserGroupByJobId(jobId);
		String url = req.getHeader("REFERER");
		url = url.substring(url.indexOf("/file/"));
		FileDepot fd = new FileDepot();
		//验证权限、判定类型、设置上传位置
		if(fileKind==null || fileKind.length()==0){
			return JumpPrompt.jumpOfModelAndView(url, "上传失败。（上传类型错误）");
		}else if(fileKind.equals("group")){
			//小组
			fd.setSource(FileDepot.SOURCE_GROUP);
			if(userKind==UserInfo.KIND_MANAGER_WEB || userKind==UserInfo.KIND_MANAGER_PART){
				fd.setPart(Integer.parseInt(selectPart));
				fd.setGroup(Integer.parseInt(selectGroup));
			}else if(userKind==UserInfo.KIND_MANAGER_GROUP){
				fd.setPart(userPart);
				fd.setGroup(userGroup);
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "上传失败。（无权限）");
			}
		}else if(fileKind.equals("part")){
			//部门
			fd.setSource(FileDepot.SOURCE_PART);
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				fd.setPart(Integer.parseInt(selectPart));
			}else if(userKind==UserInfo.KIND_MANAGER_PART){
				fd.setPart(userPart);
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "上传失败。（无权限）");
			}
		}else{
			//公司
			fd.setSource(FileDepot.SOURCE_COMPANY);
			if(userKind!=0){
				return JumpPrompt.jumpOfModelAndView(url, "上传失败。（无权限）");
			}
		}
		File file = null;
		if(uploadFile.getSize()!=0){
			//有文件
			//上传的文件名
			fd.setShowFileName(uploadFile.getOriginalFilename());
			ServletContext application = req.getServletContext();
			String realPath = application.getRealPath("upload/file/fileDepot");
			int index = uploadFile.getOriginalFilename().lastIndexOf(".");
			String suffix = uploadFile.getOriginalFilename().substring(index+1);
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
				uploadFile.transferTo(file);
				fd.setRealFileName(realFileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return JumpPrompt.jumpOfModelAndView(url, "上传失败。（服务器保存文件失败）");
			}
			fd.setCreatePerson(jobId);
			fd.setSize(uploadFile.getSize());
			fd.setUpdateDate(new Date());
			
		}else{
			return JumpPrompt.jumpOfModelAndView(url, "上传失败。（找不到文件）");
		}
		//保存到数据库
		boolean tempB = false;
		try {
			tempB = fileDepotService.addNewFile(fd);
		} catch (Exception e) {
		}
		if(tempB){
			return JumpPrompt.jumpOfModelAndView(url, "上传成功！");
		}else{
			//保存失败，删除文件
			if(file!=null){
				file.delete();
			}
			return JumpPrompt.jumpOfModelAndView(url, "上传失败。（保存数据库失败）");
		}
	}
	
	
	/**
	 * 在文件仓库删除文件
	 * @param fileId
	 * @param req
	 * @return
	 */
	@RequestMapping("file/delFile.do")
	public ModelAndView delFile(String fileId, HttpServletRequest req){
		String url = req.getHeader("REFERER");
		if(url!=null){
			if(url.indexOf("/file/")==-1){
				url = "/home.do";
			}else{
				url = url.substring(url.indexOf("/file/"));
			}
		}else{
			url = "/home.do";
		}
		
		if(fileId==null || fileId.length()==0){
			return JumpPrompt.jumpOfModelAndView(url, "删除失败。（缺少参数）");
		}
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		int userPart = userServer.getUserPartByJobId(jobId);
		int userGroup= userServer.getUserGroupByJobId(jobId);
		
		FileDepot fd = fileDepotService.getOneFileInfoById(Integer.parseInt(fileId));
		if(fd==null){
			return JumpPrompt.jumpOfModelAndView(url, "删除失败。（未找到对应文件）");
		}
		String createUserId = fd.getCreatePerson();
		
		//判断是否可以删除
		boolean canDel = false;
		if(fd.getSource()==FileDepot.SOURCE_COMPANY && userKind==UserInfo.KIND_MANAGER_WEB){
			//公司文件
			canDel = true;
		}else if(fd.getSource()==FileDepot.SOURCE_PART){
			//部门文件
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canDel = true;
			}else if(userKind==UserInfo.KIND_MANAGER_PART && fd.getPart()==userPart){
				canDel = true;
			}
		}else if(createUserId.equals(jobId)){
			canDel = true;
		}else{
			//小组
			if(userKind==0){
				canDel = true;
			}else if(userKind==UserInfo.KIND_MANAGER_PART && fd.getPart()==userPart){
				canDel = true;
			}else if(userKind==UserInfo.KIND_MANAGER_GROUP && fd.getPart()==userPart && fd.getGroup()==userGroup){
				canDel = true;
			}
		}
		if(!canDel){
			return JumpPrompt.jumpOfModelAndView(url, "删除失败。（无权限）");
		}
		
		ServletContext application = req.getServletContext();
		String realPath = application.getRealPath("upload/file/fileDepot");
		String fileName = realPath+File.separator+fd.getRealFileName();
		File file = new File(fileName);
		if(file.exists()){
			//删除数据库
			boolean tempB = false;
			try {
				tempB = fileDepotService.delFileById(Integer.parseInt(fileId));
			} catch (Exception e) {}
			if(!tempB){
				return JumpPrompt.jumpOfModelAndView(url, "删除失败。（数据库删除失败）");
			}
			if(file.delete()){
				return JumpPrompt.jumpOfModelAndView(url, "删除文件“"+fd.getShowFileName()+"”成功！");
			}else{
				return JumpPrompt.jumpOfModelAndView(url, "删除文件“"+fd.getShowFileName()+"”成功！（服务器内删除文件失败）");
			}
		}else{
			return JumpPrompt.jumpOfModelAndView(url, "删除失败。（服务器找不到此文件）");
		}
	}
	
	/**
	 * 在文件仓库下载文件
	 * @param fileId
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("file/downFile.do")
	public ModelAndView downFileMain(String fileId, HttpServletRequest req,HttpServletResponse response){
		String url = req.getHeader("REFERER");
		if(url.indexOf("/file/")==-1){
			url = "/home.do";
		}else{
			url = url.substring(url.indexOf("/file/"));
		}
		if(fileId==null || fileId.length()==0){
			return JumpPrompt.jumpOfModelAndView(url, "下载失败。（缺少参数）");
		}
		String jobId = (String)req.getSession().getAttribute("userJobId");
		int userKind = userServer.getUserKindByJobId(jobId);
		int userPart = userServer.getUserPartByJobId(jobId);
		int userGroup= userServer.getUserGroupByJobId(jobId);
		
		FileDepot fd = fileDepotService.getOneFileInfoById(Integer.parseInt(fileId));
		if(fd==null){
			return JumpPrompt.jumpOfModelAndView(url, "下载失败。（未找到对应文件）");
		}
		String createUserId = fd.getCreatePerson();
		
		//判断是否可以下载
		boolean canDown = false;
		if(fd.getSource()==FileDepot.SOURCE_COMPANY){
			//公司文件
			canDown = true;
		}else if(fd.getSource()==FileDepot.SOURCE_PART){
			//部门文件
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canDown = true;
			}else if(fd.getPart()==userPart){
				canDown = true;
			}
		}else if(createUserId.equals(jobId)){
			canDown = true;
		}else{
			//小组
			if(userKind==UserInfo.KIND_MANAGER_WEB){
				canDown = true;
			}else if(userKind==UserInfo.KIND_MANAGER_PART && fd.getPart()==userPart){
				canDown = true;
			}else if(fd.getPart()==userPart && fd.getGroup()==userGroup){
				canDown = true;
			}
		}
		
		if(!canDown){
			return JumpPrompt.jumpOfModelAndView(url, "下载失败。（无权限）");
		}else{
			if(!downFile(fd,req,response)){
				return JumpPrompt.jumpOfModelAndView(url, "下载失败。（服务器中不存在该文件）");
			}
			return null;
		}
	}
	
	/**
	 * 开始下载文件
	 * @param fd
	 * @param req
	 * @param response
	 */
	private boolean downFile(FileDepot fd,HttpServletRequest req,HttpServletResponse response) {
		ServletContext application = req.getServletContext();
		String realPath = application.getRealPath("upload/file/fileDepot");
		String fileName = realPath+File.separator+fd.getRealFileName();
		File file = new File(fileName);
		if(file.exists()){
			response.setContentType("application/octet-stream;charset=UTF-8");// 设置强制下载不打开
			response.addHeader("Content-Length", "" + file.length());//文件长度
			try {
				String encodedFileName = new String(fd.getShowFileName().getBytes("utf-8"),"iso-8859-1");
				response.addHeader("Content-Disposition",  "attachment;fileName=\"" +encodedFileName+"\"" );
			} catch (UnsupportedEncodingException e1) {
				response.addHeader("Content-Disposition",  "attachment;fileName=" +fd.getShowFileName());
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
			return true;
		}else{
			return false;
		}
	}
}

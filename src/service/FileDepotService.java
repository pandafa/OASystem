package service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.FileDepot;
import dao.FileDepotDao;

@Transactional(readOnly = true)
@Service
public class FileDepotService {
	public static final int PAGE_COMPANY = 5;
	public static final int PAGE_PART = 5;
	public static final int PAGE_GROUP = 5;
	private FileDepotDao fileDepotDao;
	public FileDepotDao getFileDepotDao() {
		return fileDepotDao;
	}
	public void setFileDepotDao(FileDepotDao fileDepotDao) {
		this.fileDepotDao = fileDepotDao;
	}
	
	/**
	 * 获取第几页公司文件列表
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfCompany(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfCompany(begin, end);
	}
	
	/**
	 * 获取公司文件总页数
	 * @return
	 */
	public int getFileListOfCompanyPageNumber(){
		int number = fileDepotDao.getFileOfCompanyNumber();
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 获取部门文件列表
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfPart(int part,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfPart(part,begin, end);
	}
	
	/**
	 * 获取部门文件总页数
	 * @return
	 */
	public int getFileListOfPartPageNumber(int part){
		int number = fileDepotDao.getFileOfPartNumber(part);
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 获取全部的部门文件列表
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfAllPart(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfAllPart(begin, end);
	}
	
	/**
	 * 获取全部的部门文件总页数
	 * @return
	 */
	public int getFileListOfAllPartPageNumber(){
		int number = fileDepotDao.getFileOfAllPartNumber();
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 获取小组文件列表，某个小组
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfGroup(int part,int group,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfGroup(part,group,begin, end);
	}
	
	/**
	 * 获取小组文件总页数，某个小组
	 * @return
	 */
	public int getFileListGroupPageNumber(int part,int group){
		int number = fileDepotDao.getFileOfGroupNumber(part,group);
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 获取小组文件列表，全部小组
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfAllGroup(int part,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfAllGroup(part,begin, end);
	}
	
	/**
	 * 获取小组文件总页数，全部小组
	 * @return
	 */
	public int getFileListOfAllGroupPageNumber(int part){
		int number = fileDepotDao.getFileOfAllGroupNumber(part);
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 获取小组文件列表，全部部门
	 * @param page
	 * @return
	 */
	public List<FileDepot> getFileListByPageOfAllPartAndGroup(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_COMPANY;
		int end = PAGE_COMPANY;
		return fileDepotDao.getFileListFromNumToNumOfAllPartAndGroup(begin, end);
	}
	
	/**
	 * 获取小组文件总页数，全部部门
	 * @return
	 */
	public int getFileListOfAllPartAndGroupPageNumber(){
		int number = fileDepotDao.getFileOfAllPartAndGroupNumber();
		return (int)Math.ceil(1.0*number/PAGE_COMPANY);
	}
	
	/**
	 * 保存上传的文件
	 * @param fd
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean addNewFile(FileDepot fd) throws Exception{
		return fileDepotDao.addNewFile(fd);
	}
	
	/**
	 * 获取一个文件的详细信息
	 * @param fileId
	 * @return
	 */
	public FileDepot getOneFileInfoById(int fileId) {
		return fileDepotDao.getOneFileInfoById(fileId);
	}
	
	/**
	 * 删除一个文件
	 * @param fileId
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean delFileById(int fileId) throws Exception{
		return fileDepotDao.delFileById(fileId);
	}
	
}

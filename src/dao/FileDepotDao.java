package dao;

import java.util.List;
import javax.annotation.Resource;
import bean.FileDepot;
import mapper.FileDepotMapper;

public class FileDepotDao {
	
	@Resource
	private FileDepotMapper mapper;

	
	/**
	 * 获取公司文件的总页数
	 * @return
	 */
	public int getFileOfCompanyNumber() {
		int result = 0;
		result = mapper.fileOfCompanyNumber(FileDepot.SOURCE_COMPANY);
		return result;
	}
	/**
	 * 获取从第几到第几个的公司文件列表
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfCompany(int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileFromNumToNumOfCompany(FileDepot.SOURCE_COMPANY, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取部门文件总数
	 * @param part
	 * @return
	 */
	public int getFileOfPartNumber(int part) {
		int result = 0;
		result = mapper.fileOfPartNumber(FileDepot.SOURCE_PART,part);
		return result;
	}
	
	/**
	 * 获取部门文件列表，从第几到第几个
	 * @param part
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfPart(int part, int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileFromNumToNumOfPart(FileDepot.SOURCE_PART,part, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
		
	}
	
	/**
	 * 获取全部的部门文件总数
	 * @return
	 */
	public int getFileOfAllPartNumber() {
		int result = 0;
		result = mapper.fileOfAllPartNumber(FileDepot.SOURCE_PART);
		return result;
	}
	
	/**
	 * 获取全部的部门文件列表，从第几到第几个
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfAllPart(int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileListFromNumToNumOfAllPart(FileDepot.SOURCE_PART, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取小组文件总数，某个小组
	 * @param part
	 * @param group
	 * @return
	 */
	public int getFileOfGroupNumber(int part, int group) {
		int result = 0;
		result = mapper.fileOfGroupNumber(FileDepot.SOURCE_GROUP,part,group);
		return result;
	}
	
	/**
	 * 获取小组文件列表，某个小组，从第几到第几个
	 * @param part
	 * @param group
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfGroup(int part, int group, int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileListFromNumToNumOfGroup(FileDepot.SOURCE_GROUP,part,group, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取小组文件总数，全部小组
	 * @param part
	 * @return
	 */
	public int getFileOfAllGroupNumber(int part) {
		int result = 0;
		result = mapper.fileOfAllGroupNumber(FileDepot.SOURCE_GROUP,part);
		return result;
	}
	
	/**
	 * 获取小组文件列表，全部小组，从第几到第几个
	 * @param part
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfAllGroup(int part, int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileListFromNumToNumOfAllGroup(FileDepot.SOURCE_GROUP,part, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取小组文件总数，全部部门
	 * @return
	 */
	public int getFileOfAllPartAndGroupNumber() {
		int result = 0;
		result = mapper.fileOfAllPartAndGroupNumber(FileDepot.SOURCE_GROUP);
		return result;
		
	}
	
	/**
	 * 获取小组文件列表，全部部门，从第几到第几个
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<FileDepot> getFileListFromNumToNumOfAllPartAndGroup(int begin, int end) {
		List<FileDepot> result = null;
		result = mapper.fileListFromNumToNumOfAllPartAndGroup(FileDepot.SOURCE_GROUP, begin, end);
		if(result==null || result.size()==0){
			result = null;
		}
		return result;
		
	}
	
	/**
	 * 保存上传的文件
	 * @param fd
	 * @return
	 * @throws Exception 
	 */
	public boolean addNewFile(FileDepot fd) throws Exception {
		int num = mapper.addNewFile(fd);
		if(num==1){
			return true;
		}else{
			throw new Exception();
		}
		
	}
	
	/**
	 * 获取一个文件的详细信息
	 * @param fileId
	 * @return
	 */
	public FileDepot getOneFileInfoById(int fileId) {
		FileDepot result = null;
		result = mapper.findById(fileId);
//		if(result==null){
//			System.out.println("null");
//		}else{
//			System.out.println(result);
//		}
		return result;
		
	}
	
	/**
	 * 删除一个文件
	 * @param fileId
	 * @return
	 * @throws Exception 
	 */
	public boolean delFileById(int fileId) throws Exception {
		int num = mapper.delFileById(fileId);
		if(num==1){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	
}

package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bean.FileDepot;

public interface FileDepotMapper {
	public FileDepot findById(int id);
	
	//获取公司文件的总页数
	public int fileOfCompanyNumber(@Param("source")int source);
	//获取部门文件总数
	public int fileOfPartNumber(@Param("source")int source,@Param("part")int part);
	//获取全部的部门文件总数
	public int fileOfAllPartNumber(@Param("source")int source);
	//获取小组文件总数，某个小组
	public int fileOfGroupNumber(@Param("source")int source,@Param("part")int part,@Param("group")int group);
	//获取小组文件总数，全部小组
	public int fileOfAllGroupNumber(@Param("source")int source,@Param("part")int part);
	//获取小组文件总数，全部部门
	public int fileOfAllPartAndGroupNumber(@Param("source")int source);
	//获取从第几到第几个的公司文件列表
	public List<FileDepot> fileFromNumToNumOfCompany(@Param("source")int source,@Param("begin")int begin,@Param("end")int end);
	//获取部门文件列表，从第几到第几个
	public List<FileDepot> fileFromNumToNumOfPart(@Param("source")int source,@Param("part")int part,@Param("begin")int begin,@Param("end")int end);
	//获取全部的部门文件列表，从第几到第几个
	public List<FileDepot> fileListFromNumToNumOfAllPart(@Param("source")int source,@Param("begin")int begin,@Param("end")int end);
	//获取小组文件列表，某个小组，从第几到第几个
	public List<FileDepot> fileListFromNumToNumOfGroup(@Param("source")int source,@Param("part")int part,@Param("group")int group,@Param("begin")int begin,@Param("end")int end);
	// 获取小组文件列表，全部小组，从第几到第几个
	public List<FileDepot> fileListFromNumToNumOfAllGroup(@Param("source")int source,@Param("part")int part,@Param("begin")int begin,@Param("end")int end);
	//获取小组文件列表，全部部门，从第几到第几个
	public List<FileDepot> fileListFromNumToNumOfAllPartAndGroup(@Param("source")int source,@Param("begin")int begin,@Param("end")int end);
	//保存上传的文件到数据库（可返回主键）
	public int addNewFile(FileDepot fileDepot);
	//删除一个文件
	public int delFileById(int id);
	
}

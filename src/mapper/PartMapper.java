package mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import bean.Part;

public interface PartMapper {
	//通过名字获取部门ID
	public int getPartIdByName(@Param("name")String name);
	//添加新部门，并获取新部门id
	public int addNewPart(Part part);
	//删除部门，根据部门id
	public int delPartById(@Param("id")int partId);
	//部门的全部信息，返回从几到几的信息
	public List<Part> getPartsAllInfoByNumber(@Param("begin")int begin,@Param("end")int end);
	//获取部门的个数
	public int allPartsCount();
	//获取所有的部门id和名字
	public List<Map<String, Object>>  getAllPartsAndNames();
	//根据id获取名字
	public String getNameById(@Param("id")int id);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

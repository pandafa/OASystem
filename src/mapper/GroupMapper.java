package mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bean.Group;

public interface GroupMapper {
	//获取某部门的所有小组名和ID
	public List<Map<String, Object>> getAllGroupsOfPartNameAndId(@Param("partId")int partId);
	//根据部门ID和小组名，获取小组ID
	public int getGroupByName(@Param("partId")int partId,@Param("name")String name);
	//添加小组
	public int addGroup(@Param("partId")int partId,@Param("name")String name,@Param("person")String person,@Param("date")Date date);
	//根据id获取名字
	public String getNameById(@Param("id")int id,@Param("partId")int partId);
	//删除小组
	public int delGroup(@Param("id")int id,@Param("partId")int partId);
	//获取某小组的人数
	public int getMemberNumbersOfGroup(@Param("groupId")int groupId,@Param("partId")int partId);
	//返回从几到几的信息
	public List<Group> getGroupsOfPartAllInfoByNumber(@Param("id")int id,@Param("begin")int begin,@Param("end")int end);
	//获取小组的个数
	public int allGroupsCount(@Param("partId")int partId);
	//删除小组，根据部门id和名字
	public int delGroupByPartIdAndName(@Param("name")String name,@Param("partId")int partId);
	//删除某个部门的全部小组
	public int delAllGroupsByPartId(@Param("partId")int partId);

}

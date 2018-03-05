package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bean.UserKind;

public interface UserKindMapper {
	//返回所有用户类型
	public List<UserKind> getAllUserKind();
	//根据ID获取名字
	public String getNameById(@Param("id")int id);
}

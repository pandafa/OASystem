package dao;

import java.util.List;

import javax.annotation.Resource;
import bean.MessageKind;
import mapper.MessageKindMapper;

public class MsgKindDao {
	
	@Resource
	private MessageKindMapper mapper;
	
	/**
	 * 返回所有用户类型
	 * @return
	 */
	public List<MessageKind> getAllUserKind(){
		List<MessageKind> result = null;
		result = mapper.getAllKinds();
		return result;
	}
}

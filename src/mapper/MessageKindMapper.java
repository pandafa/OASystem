package mapper;

import java.util.List;
import bean.MessageKind;

public interface MessageKindMapper {
	//获取全部种类
	public List<MessageKind> getAllKinds();
}

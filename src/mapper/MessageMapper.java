package mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import bean.Message;

public interface MessageMapper {
	//获取某一个消息的详细
	public Message findById(@Param("id")String id);
	//获取公告列表，从第几个到第几个
	public List<Message> getMessageOfPartFromNumToNum(@Param("kind")int kind,@Param("partId")int partId,
			@Param("begin")int begin,@Param("end")int end);
	//获取公告列表，从第几个到第几个
	public List<Message> getMessageOfCompanyFromNumToNum(@Param("kind")int kind,@Param("begin")int begin,@Param("end")int end);
	
	//获取小组公告列表，从第几个到第几个
	public List<Message> getMessageOfGroupFromNumToNum(@Param("kind")int kind,@Param("part")int part,@Param("group")int group,
			@Param("begin")int begin,@Param("end")int end);
	//获取某人的部门公告页数
	public int getNoticeOfPartNumberByJobId(@Param("part")int part);
	//获取某人的公司公告页数
	public int getNoticeOfCompanyNumberByJobId();
	//获取某人的小组公告页数
	public int getNoticeOfGroupNumberByJobId(@Param("part")int part,@Param("group")int group);
	//获取某人全部消息的  从第几个到第几个  的消息内容全部
	public List<Message> getAllMessageInfoByJobId(@Param("jobId")String jobId, @Param("part")int part,@Param("group")int group,
			@Param("begin")int begin,@Param("end")int end);
	//获取某人的全部消息个数
	public int getAllMessageByJobId(@Param("jobId")String jobId, @Param("part")int part,@Param("group")int group);
	//添加新消息
	public int addNewMessageToAllPerson(Message message);
	//添加新消息
	public int addNewMessageToPart(Message message);
	//添加新消息
	public int addNewMessageToPerson(Message message);
	//添加新消息
	public int addNewMessageToGroup(Message message);
	//添加新消息
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

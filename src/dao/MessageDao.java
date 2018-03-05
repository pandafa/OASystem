package dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import bean.Message;
import mapper.MessageMapper;
import mapper.RemindMapper;
import mapper.UserInfoMapper;

public class MessageDao {

	@Resource
	private MessageMapper mMapper;
	@Resource
	private RemindMapper rMapper;
	@Resource
	private UserInfoMapper uMapper;
	
	/**
	 * 获取某一个消息的详细
	 * @param messageId
	 * @return
	 */
	public Message getMessageInfoByMessageId(String messageId){
		Message result = null;
		result = mMapper.findById(messageId);
		return result;
	}
	
	/**
	 * 获取某人的部门公告列表，从第几个到第几个
	 * @param jobId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Message> getNoticeOfPartFromNumToNum(String jobId,int begin,int end){
		List<Message> result = null;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		result = mMapper.getMessageOfPartFromNumToNum(4, part, begin, end);
		return result;
	}
	
	/**
	 * 获取某人的公司公告列表，从第几个到第几个
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Message> getNoticeOfCompanyFromNumToNum(int begin,int end){
		List<Message> result = null;
		result = mMapper.getMessageOfCompanyFromNumToNum(2, begin, end);
		return result;
	}
	
	/**
	 * 获取某人的小组公告列表，从第几个到第几个
	 * @param jobId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Message> getNoticeOfGroupFromNumToNum(String jobId,int begin,int end){
		List<Message> result = null;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		//获取小组
		int group= uMapper.getUserGroupByJobId(jobId);
		result = mMapper.getMessageOfGroupFromNumToNum(7, part, group, begin, end);
		return result;
		
	}
	
	/**
	 * 获取某人的部门公告页数
	 * @param jobId
	 */
	public int getNoticeOfPartNumberByJobId(String jobId){
		int result = 0;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		result = mMapper.getNoticeOfPartNumberByJobId(part);
		return result;
	}
	
	/**
	 * 获取某人的公司公告页数
	 * @param jobId
	 * @return
	 */
	public int getNoticeOfCompanyNumberByJobId(){
		int result = 0;
		result = mMapper.getNoticeOfCompanyNumberByJobId();
		return result;
	}
	
	/**
	 * 获取某人的小组公告页数
	 * @param jobId
	 */
	public int getNoticeOfGroupNumberByJobId(String jobId){
		int result = 0;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		//获取小组
		int group= uMapper.getUserGroupByJobId(jobId);
		result = mMapper.getNoticeOfGroupNumberByJobId(part, group);
		return result;
	}
	
	/**
	 * 获取某人全部消息的  从第几个到第几个  的消息内容全部
	 * @param jobId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Message> getAllMessageInfoByJobId(String jobId, int begin, int end) {
		List<Message> result = null;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		//获取小组
		int group= uMapper.getUserGroupByJobId(jobId);
		result = mMapper.getAllMessageInfoByJobId(jobId, part, group, begin, end);
		return result;
	}
	
	/**
	 * 获取某人的全部消息个数
	 * @param jobId
	 * @return
	 */
	public int getAllMessageByJobId(String jobId){
		int result = -1;
		//获取部门
		int part = uMapper.getUserPartByJobId(jobId);
		//获取小组
		int group= uMapper.getUserGroupByJobId(jobId);
		result = mMapper.getAllMessageByJobId(jobId, part, group);
		return result;
	}
	
	/**
	 * 向所有人发送
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	public boolean sendOneMsgToAll(Message msg, boolean isMsg) throws Exception{
		boolean result = false;
		int num = 0;
		msg.setSendDate(new Date());
		num = mMapper.addNewMessageToAllPerson(msg);
		if(num!=0){
			num = msg.getId();
			result = addRemindToAll(num, isMsg);
			if(!result){
				throw new Exception();
			}
		}
		return result;
		
	}
	
	/**
	 * 向部门发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	public boolean sendOneMsgToPart(Message msg,boolean isMsg) throws Exception{
		boolean result = false;
		int num = 0;
		msg.setSendDate(new Date());
		num = mMapper.addNewMessageToPart(msg);
		if(num!=0){
			num = msg.getId();
			result = addRemindToPart(num, isMsg, msg.getAcceptPart());
			if(!result){
				throw new Exception();
			}
		}
		return result;
		
	}
	
	/**
	 * 向某人发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	public boolean sendOneMsgToPerson(Message msg,boolean isMsg) throws Exception{
		boolean result = false;
		int num = 0;
		msg.setSendDate(new Date());
		num = mMapper.addNewMessageToPerson(msg);
		if(num!=0){
			num=msg.getId();
			result = addRemindToPerson(num, msg.getAcceptPerson(), isMsg);
			if(!result){
				throw new Exception();
			}
		}
		return result;
	}
	
	/**
	 * 向小组发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	public boolean sendOneMsgToGroup(Message msg,boolean isMsg) throws Exception{
		boolean result = false;
		int num = 0;
		msg.setSendDate(new Date());
		num = mMapper.addNewMessageToGroup(msg);
		if(num!=0){
			num = msg.getId();
			result = addRemindToGroup(num, isMsg, msg.getAcceptGroup(), msg.getAcceptPart());
			if(!result){
				throw new Exception();
			}
		}
		return result;
	}

	/**
	 * 向所有人添加提醒
	 * @param msgId
	 * @param isMsg
	 * @return
	 */
	public boolean addRemindToAll(int msgId,boolean isMsg){
		List<String> list = uMapper.getAllUserJobId();
		if(list==null || list.size()==0){
			return false;
		}else{
			for(String s:list){
				if(!addRemindToPerson(msgId, s, isMsg)){
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 向部门添加提醒
	 * @param msgId
	 * @param isMsg
	 * @param remindPart
	 * @return
	 */
	public boolean addRemindToPart(int msgId,boolean isMsg,int remindPart){
		List<String> list = uMapper.getAllUserJobIdOfPart(remindPart);
		if(list==null || list.size()==0){
			return false;
		}else{
			for(String s:list){
				if(!addRemindToPerson(msgId, s, isMsg)){
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 向小组添加提醒
	 * @param msgId
	 * @return
	 * @throws Exception 
	 */
	public boolean addRemindToGroup(int msgId,boolean isMsg,int remindGroup,int remindPart){
		List<String> list = uMapper.getAllUserJobIdOfGroup(remindPart, remindGroup);
		if(list==null || list.size()==0){
			return false;
		}else{
			for(String s:list){
				if(!addRemindToPerson(msgId, s, isMsg)){
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 向个人添加提醒
	 * @param msgId
	 * @param remindId
	 * @param isMsg
	 * @return
	 * @throws Exception 
	 */
	public boolean addRemindToPerson(int msgId,String remindId,boolean isMsg) {
		int num = 0;
		if(isMsg){
//			System.out.println("msgId:"+msgId+",remindId:"+remindId);
			num = rMapper.addRemind(msgId, remindId, 1);
		}else{
			num = rMapper.addRemind(msgId, remindId, 0);
		}
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
}

package service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import bean.Message;
import dao.MessageDao;

@Transactional(readOnly = true)
@Service
public class MessageService {
	private MessageDao messageDao;
	public static final int PAGE_PERSON_MSG = 10;
	public static final int PAGE_NOTICE = 5;
	public MessageDao getMessageDao() {
		return messageDao;
	}
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	/**
	 * 获取某一个消息的详细
	 * @param messageId
	 * @return
	 */
	public Message getMessageInfoByMessageId(String messageId){
		return messageDao.getMessageInfoByMessageId(messageId);
	}
	
	/**
	 * 获取某人的公司公告列表，第几页
	 * @param jobId
	 * @param page
	 * @return
	 */
	public List<Message> getNoticeInfoOfCompanyToPageByJobId(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NOTICE;
		int end = PAGE_NOTICE;
		return messageDao.getNoticeOfCompanyFromNumToNum(begin, end);
	}
	
	/**
	 * 获取某人的部门公告列表，第几页
	 * @param jobId
	 * @param page
	 * @return
	 */
	public List<Message> getNoticeInfoOfPartToPageByJobId(String jobId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NOTICE;
		int end = PAGE_NOTICE;
		return messageDao.getNoticeOfPartFromNumToNum(jobId,begin, end);
	}
	
	/**
	 * 获取某人的小组公告列表，第几页
	 * @param jobId
	 * @param page
	 * @return
	 */
	public List<Message> getNoticeInfoOfGroupToPageByJobId(String jobId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NOTICE;
		int end = PAGE_NOTICE;
		return messageDao.getNoticeOfGroupFromNumToNum(jobId,begin, end);
	}
	
	/**
	 * 获取某人的公司公告页数
	 * @param jobId
	 * @return
	 */
	public int getNoticeOfCompanyPageNumberByJobId(){
		int number = messageDao.getNoticeOfCompanyNumberByJobId();
		return (int)Math.ceil(1.0*number/PAGE_NOTICE);
	}
	
	/**
	 * 获取某人的部门公告页数
	 * @param jobId
	 * @return
	 */
	public int getNoticeOfPartPageNumberByJobId(String jobId){
		int number = messageDao.getNoticeOfPartNumberByJobId(jobId);
		return (int)Math.ceil(1.0*number/PAGE_NOTICE);
	}
	
	/**
	 * 获取某人的小组公告页数
	 * @param jobId
	 * @return
	 */
	public int getNoticeOfGroupPageNumberByJobId(String jobId){
		int number = messageDao.getNoticeOfGroupNumberByJobId(jobId);
		return (int)Math.ceil(1.0*number/PAGE_NOTICE);
	}
	
	/**
	 * 获取某人全部消息的第几页的消息内容全部
	 * @param jobId
	 * @param page
	 * @return
	 */
	public List<Message> getAllMessageInfoOfPageByJobId(String jobId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_PERSON_MSG;
		int end = PAGE_PERSON_MSG;
		return messageDao.getAllMessageInfoByJobId(jobId,begin, end);
	}
	
	/**
	 * 获取总页数，某人的消息列表
	 * @param jobId
	 * @return
	 */
	public int getAllMessagePageNumberByJobId(String jobId){
		int number = messageDao.getAllMessageByJobId(jobId);
		return (int)Math.ceil(1.0*number/PAGE_PERSON_MSG);
	}
	
	/**
	 * 向所有人发送
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean sendOneMsgToAll(Message msg, boolean isMsg) throws Exception{
		return messageDao.sendOneMsgToAll(msg,isMsg);
	}
	
	/**
	 * 向部门发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean sendOneMsgToPart(Message msg, boolean isMsg) throws Exception{
			return messageDao.sendOneMsgToPart(msg,isMsg);
	}
	
	/**
	 * 向某人发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean sendOneMsgToPerson(Message msg, boolean isMsg) throws Exception{
		return messageDao.sendOneMsgToPerson(msg,isMsg);

	}
	
	/**
	 * 向小组发
	 * @param msg
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean sendOneMsgToGroup(Message msg, boolean isMsg) throws Exception{
		return messageDao.sendOneMsgToGroup(msg,isMsg);
	}
}

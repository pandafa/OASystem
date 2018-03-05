package service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ProcedureDao;
import dao.RemindDao;

@Transactional(readOnly = true)
@Service
public class RemindService {
	private RemindDao remindDao;
	private ProcedureDao procedureDao;
	public RemindDao getRemindDao() {
		return remindDao;
	}
	public void setRemindDao(RemindDao remindDao) {
		this.remindDao = remindDao;
	}
	public ProcedureDao getProcedureDao() {
		return procedureDao;
	}
	public void setProcedureDao(ProcedureDao procedureDao) {
		this.procedureDao = procedureDao;
	}
	/**
	 * 删除一条指定的提醒
	 * @param jobId
	 * @param msgId
	 * @param isMag
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean takeIdRead(String jobId, int msgId, boolean isMag) throws Exception{
		return remindDao.takeIdRead(jobId, msgId, isMag);
	}
	
	/**
	 * 是否阅读了
	 * @param jobId
	 * @param msgId
	 * @param isMag
	 * @return
	 */
	public boolean isRead(String jobId, int msgId, boolean isMag) {
		return remindDao.isRead(jobId,msgId,isMag);
	}
	
	
	/**
	 * 获取某个人需要提醒的消息ID数
	 * @param jobId
	 * @return
	 */
	public int getRemindMsgNumberById(String jobId){
		return remindDao.getNeedRemindMessageNumberByJobId(jobId);
	}
	
	/**
	 * 获取某个人需要提醒的公告ID数
	 * @param jobId
	 * @return
	 */
	public int getRemindNoticeNumberById(String jobId){
		return remindDao.getNeedRemindNoticeByJobId(jobId);
	}
	
	/**
	 * 获取某个人需要提醒的流程ID数
	 * @param jobId
	 * @return
	 */
	public int getRemindProcedureNumberById(String jobId){
		return procedureDao.getNeedRemindProcedureNumberByJobId(jobId);
	}
	
	
}

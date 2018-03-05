package service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import bean.Message;
import bean.ModelProcedure;
import bean.ProcedureShen;
import bean.ProcedureSubmit;
import dao.MessageDao;
import dao.ProcedureDao;

@Transactional(readOnly = true)
@Service
public class ProcedureService {
	public static final int PAGE_MODEL = 5;
	public static final int PAGE_MY = 5;
	public static final int PAGE_DEAL = 5;
	private ProcedureDao procedureDao;
	private MessageDao messageDao;

	
	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public ProcedureDao getProcedureDao() {
		return procedureDao;
	}

	public void setProcedureDao(ProcedureDao procedureDao) {
		this.procedureDao = procedureDao;
	}
	
	/**
	 * 审批一个流程
	 * @param shen
	 * @param jobId
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean dealOneProcedure(ProcedureShen shen, String jobId) throws Exception {
		Map<String,Object> map = procedureDao.dealOneProcedure(shen, jobId);
		if((boolean)map.get("finish")){
			String mtitle = "【系统】您的一个流程已经审核完成，请查看结果。";
			String mcontent = "";
			if((boolean)map.get("result")){
				mcontent = "审核已通过！\\(^o^)/~（详细情况请查看“我的流程”）";
			}else{
				mcontent = "审核未通过。/(ㄒoㄒ)/~~（详细情况请查看“我的流程”）";
			}
			sendOneSystemMessage(mtitle,mcontent,(String)map.get("person"));
		}else{
			// 提醒审批人
			if((boolean)map.get("hasNextPerson")){
				sendOneSystemMessage("【系统】您有一个需要审批的流程","详细情况请查看“需要审批的流程”",(String)map.get("nextPerson"));
			}
		}
		return true;
	}
	
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	private boolean sendOneSystemMessage(String title,String content,String jobId) throws Exception{
		Message msg = new Message();
		msg.setTitle(title);
		msg.setKind(1);
		msg.setSendPerson("0000");
		msg.setSendDate(new Date());
		msg.setContent(content);
		msg.setAcceptPerson(jobId);
//		System.out.println(msg);
//		System.out.println(title+"-"+content+"-"+jobId);
//		System.out.println(messageDao);
//		System.out.println(procedureDao);
		return messageDao.sendOneMsgToPerson(msg, true);
	}
	
	/**
	 * 获取第几页的  需要处理的流程，简略版
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllNeedToDealListByPage(String jobId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_DEAL;
		int end = PAGE_DEAL;
		return procedureDao.getNeedToDealSimpleFromNumToNum(begin, end, jobId);
	}
	
	/**
	 * 获取需要处理的流程的总页数
	 * @return
	 */
	public int getAllNeedToDealListPage(String jobId) {
		int number = procedureDao.getAllNeedToDealNumber(jobId);
		return (int)Math.ceil(1.0*number/PAGE_DEAL);
	}
	
	/**
	 * 获取我的一个提交的全部信息
	 * @param submitId
	 * @return
	 */
	public ProcedureSubmit getMySubmitAllInfoById(int submitId) {
		return procedureDao.getMySubmitAllInfoById(submitId);
	}
	
	/**
	 * 获取第几页的我提交的流程，简略版
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllMyProcedureSimpleByPage(int page,String jobId){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_MY;
		int end = PAGE_MY;
		return procedureDao.getAllMyProcedureSimpleFromNumToNum(begin, end, jobId);
	}
	
	/**
	 * 获取我提交的流程的总页数
	 * @return
	 */
	public int getAllMyProcedurePage(String jobId){
		int number = procedureDao.getAllMyProcedureNumber(jobId);
		return (int)Math.ceil(1.0*number/PAGE_MY);
	}
	
	/**
	 * 流程提交
	 * @param psubmit
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean procedureSubmit(ProcedureSubmit psubmit){
		try{
			Map<String,Object> map = procedureDao.procedureSubmit(psubmit);
			if(map==null){
				return false;
			}else{
				//提醒审批人
				sendOneSystemMessage("【系统】您有一个需要审批的流程","详细情况请查看“需要审批的流程”【流程号：“"+(int)map.get("key")+"”】",(String)map.get("needToRemind"));
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
//			System.out.println("我要回滚了");
			throw new RuntimeException();
		}
	}
	
	/**
	 * 获取流程的创建者
	 * @param id
	 * @return
	 */
	public String getUserOfProcedureWhoCreateById(int id){
		return procedureDao.getUserOfProcedureWhoCreateById(id);
	}
	
	/**
	 * 根据ID，删除一个流程
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean delProcedureById(int id) throws Exception{
		return procedureDao.delProcedureById(id);
	}
	
	/**
	 * 更新模板
	 * @param procedure
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean updateProcedure(ModelProcedure procedure) throws Exception{
		return procedureDao.updateProcedure(procedure);
	}
	
	/**
	 * 获取某一个流程详细
	 * @param modelId
	 * @return
	 */
	public ModelProcedure getModelInfoAllById(int modelId){
		return procedureDao.getModelInfoAllById(modelId);
	}
	
	/**
	 * 获取第几页的模板
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllModelByPage(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_MODEL;
		int end = PAGE_MODEL;
		return procedureDao.getAllModelFromNumToNum(begin, end);
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getAllModelPage(){
		int number = procedureDao.getAllModelNumber();
		return (int)Math.ceil(1.0*number/PAGE_MODEL);
	}

	/**
	 * 创建新的流程
	 * @param procedure
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean createNewProcedure(ModelProcedure procedure) throws Exception {
		return procedureDao.createNewProcedure(procedure);
	}


}

package dao;

import javax.annotation.Resource;
import mapper.RemindMapper;

public class RemindDao {
	
	@Resource
	private RemindMapper mapper;
	
	/**
	 * 删除一条指定的提醒
	 * @param jobId
	 * @param msgId
	 * @param isMag
	 * @return
	 * @throws Exception 
	 */
	public boolean takeIdRead(String jobId, int msgId, boolean isMag) throws Exception{
		boolean result = false;
		int isMsgNum = 0;
		if(isMag){
			isMsgNum = 1;
		}
		if(mapper.hasThisRemind(msgId, jobId, isMsgNum)==1){
			//有，删除
			int num = mapper.delRemindById(msgId, jobId, isMsgNum);
			if(num==1){
				//成功删除
				result = true;
			}else{
				//删除失败
				throw new Exception();
			}
		}else{
			//没有返回
			result = true;
		}
		return result;
	}
	
	/**
	 * 是否阅读
	 * @param jobId
	 * @param msgId
	 * @param isMag
	 * @return
	 */
	public boolean isRead(String jobId, int msgId, boolean isMag) {
		boolean result = true;
		int num = 0;
		if(isMag){
			num = mapper.hasThisRemind(msgId, jobId, 1);
		}else{
			num = mapper.hasThisRemind(msgId, jobId, 0);
		}
		if(num==0){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 获取某个人需要提醒的消息ID数
	 * @param jobId
	 * @return
	 */
	public int getNeedRemindMessageNumberByJobId(String jobId){
		int result = 0;
		result = mapper.getNeedToRemindNumber(1, jobId);
		return result;
	}
	
	/**
	 * 获取某个人需要提醒的公告数
	 * @param jobId
	 * @return
	 */
	public int getNeedRemindNoticeByJobId(String jobId){
		int result = 0;
		result = mapper.getNeedToRemindNumber(0, jobId);
		return result;
	}
		
}

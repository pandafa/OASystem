package service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.MessageKind;
import dao.MsgKindDao;

@Transactional(readOnly = true)
@Service
public class MsgKindService {
	private MsgKindDao msgKindDao;
	public MsgKindDao getMsgKindDao() {
		return msgKindDao;
	}
	public void setMsgKindDao(MsgKindDao msgKindDao) {
		this.msgKindDao = msgKindDao;
	}
	
	/**
	 * 返回所有用户类型
	 * @return
	 */
	public List<MessageKind> getAllUserKind(){
		return msgKindDao.getAllUserKind();
	}
}

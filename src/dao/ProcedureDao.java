package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import bean.ModelOption;
import bean.ModelProcedure;
import bean.ModelShen;
import bean.ProcedureOption;
import bean.ProcedureShen;
import bean.ProcedureSubmit;
import mapper.ModelOptionMapper;
import mapper.ModelProcedureMapper;
import mapper.ModelShenMapper;
import mapper.ProcedureOptionMapper;
import mapper.ProcedureShenMapper;
import mapper.ProcedureSubmitMapper;

public class ProcedureDao {
	
	@Resource
	private ModelOptionMapper moMapper;
	@Resource
	private ModelProcedureMapper mpMapper;
	@Resource
	private ModelShenMapper msMapper;
	@Resource
	private ProcedureOptionMapper poMapper;
	@Resource
	private ProcedureShenMapper psMapper;
	@Resource
	private ProcedureSubmitMapper ppMapper;
	
	/**
	 * 获取某个人需要提醒的流程ID数
	 * @param jobId
	 * @return
	 */
	public int getNeedRemindProcedureNumberByJobId(String jobId){
		int result = 0;
		result = ppMapper.getNumberByUserJobIdAndWork(jobId, ProcedureShen.WORK_NEED);
		return result;
	}
	
	/**
	 * 审批一个流程
	 * @param shen
	 * @param jobId
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> dealOneProcedure(ProcedureShen shen, String jobId) throws Exception{
//		System.out.println(shen);
		Map<String,Object> result = null;
		String good = psMapper.getDealPersonByIds(shen.getId(), shen.getProcedureId());
		int order = psMapper.getOrderByIds(shen.getId(),shen.getProcedureId());
		if(!good.equals(jobId)){
			//不是指定人员处理
			return null;
		}
		int num = 0;
		if(shen.isPass()){
			//通过
			//更新该审批
			shen.setTime(new Date());
			shen.setPass(1);
			shen.setWork(ProcedureShen.WORK_OK);
			num = psMapper.updateAllByIds(shen);
			if(num==1){
				int nextNum = psMapper.getHasThisOrderOfSubmit(shen.getProcedureId(), order+1);
				if(nextNum==1){
					//有下一个，下一个状态改变
					num = psMapper.updateWork(ProcedureShen.WORK_NEED, shen.getProcedureId(), order+1);
					if(num==1){
						//完成，通过，有下一个
						result = new HashMap<>();
						result.put("finish", false);
						result.put("hasNextPerson", true);
						result.put("nextPerson", psMapper.getJobIdByOrderAndProcedureId(shen.getProcedureId(), order+1));
					}
				}else{
					//没有下一个，流程结束
					num = ppMapper.updateStatus(shen.getProcedureId(), ProcedureSubmit.STATUS_PASS);
					if(num==1){
						//完成，通过，无下一个
						result = new HashMap<>();
						result.put("finish", true);
						result.put("result", true);
						result.put("person", ppMapper.getCreatePerson(shen.getProcedureId()));
					}
				}
			}
		}else{
			//不通过
			//更新该审批
			shen.setTime(new Date());
			shen.setPass(0);
			shen.setWork(ProcedureShen.WORK_OK);
			num = psMapper.updateAllByIds(shen);
			if(num==1){
				boolean hasError = false;
				//更改剩余审批为“略过”
				while(true){
					int nextNum = psMapper.getHasThisOrderOfSubmit(shen.getProcedureId(), order+1);
					if(nextNum==1){
						//有下一个，下一个状态改变
						num = psMapper.updateWork(ProcedureShen.WORK_PASS, shen.getProcedureId(), order+1);
						if(num==0){
							//有问题，更新失败
							hasError = true;
							break;
						}
						order++;
					}else{
						//无下一个，退出循环
						break;
					}
				}
				if(!hasError){
					//没问题，继续。流程结束
					num = ppMapper.updateStatus(shen.getProcedureId(), ProcedureSubmit.STATUS_NO_PASS);
					if(num==1){
						//完成，不通过，终结其他审批
						result = new HashMap<>();
						result.put("finish", true);
						result.put("result", false);
						result.put("person", ppMapper.getCreatePerson(shen.getProcedureId()));
					}
				}
			}
		}
		if(result==null){
			throw new Exception();
		}
		return result;
	}
	
	/**
	 * 获取从第几个到第几个  需要处理的流程，简略版
	 * @param begin
	 * @param end
	 * @param jobId
	 * @return
	 */
	public List<Map<String, Object>> getNeedToDealSimpleFromNumToNum(int begin, int end, String jobId) {
		List<Map<String, Object>> result = null;
			//找到需要处理的审批流程id
		List<Map<String, Object>> ids = psMapper.getNeedDealIdsByJobIdFromNumberToNumber(jobId, begin, end);
		if(ids!=null && ids.size()!=0){
			result = new ArrayList<>();
			for(int i=0;i<ids.size();i++){
				Map<String, Object> mapTemp = null;
				//获取主要信息
				mapTemp = ppMapper.getMainInfoById((int)ids.get(i).get("procedureId"));
				if(mapTemp!=null){
					//获取更新时间
					if((int)ids.get(i).get("oorder")==1){
						mapTemp.put("updateDate", mapTemp.get("createDate"));
					}else{
						Timestamp ts = psMapper.getUpdateTime((int)ids.get(i).get("procedureId"),(int)ids.get(i).get("oorder")-1);
						mapTemp.put("updateDate", ts);
					}
					result.add(mapTemp);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取某个人需要处理的总数
	 * @param jobId
	 * @return
	 */
	public int getAllNeedToDealNumber(String jobId) {
		int result = 0;
		result = psMapper.getAllNeedToDealNumber(jobId);
		return result;
	}
	
	/**
	 * 获取我的一个提交的全部信息
	 * @param submitId
	 * @return
	 */
	public ProcedureSubmit getMySubmitAllInfoById(int submitId) {
		ProcedureSubmit result = null;
		
		
		result = ppMapper.findById(submitId);
//		System.out.println("submitId="+submitId);
		List<ProcedureOption> temp1 = poMapper.findBySubmitId(submitId);
//		System.out.println("temp1:");
//		System.out.println(temp1);
		
		ProcedureOption[] opts = new ProcedureOption[temp1.size()];
		for(int i=0;i<temp1.size();i++){
			opts[i] = temp1.get(i);
		}
		result.setOpts(opts);
		List<ProcedureShen> temp2 = psMapper.findBySubmitId(submitId);
//		System.out.println("temp2:");
//		System.out.println(temp2);
		ProcedureShen[] shens = new ProcedureShen[temp2.size()];
		for(int i=0;i<temp2.size();i++){
			shens[i] = temp2.get(i);
		}
		result.setShens(shens);
//		System.out.println(shens);
		return result;
	}
	
	/**
	 * 获取从第几到第几的我提交的流程，简略版
	 * @param begin
	 * @param end
	 * @param jobId
	 * @return
	 */
	public List<Map<String, Object>> getAllMyProcedureSimpleFromNumToNum(int begin, int end, String jobId) {
		List<Map<String, Object>> result = null;
		result = ppMapper.getAllMyProcedureSimpleFromNumToNum(jobId, begin, end);
		return result;
	}
	
	/**
	 * 获取我提交的流程的总数
	 * @param jobId
	 * @return
	 */
	public int getAllMyProcedureNumber(String jobId) {
		int result = 0;
		result = ppMapper.getNumberOfSubmitByCreatePerson(jobId);
		return result;
	}
	
	/**
	 * 保存提交的审批
	 * @param psubmit
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> procedureSubmit(ProcedureSubmit psubmit){
		try{
			Map<String,Object> needReturn = null;
			boolean result = false;
			int key = 0;
			psubmit.setCreateDate(new Date());
			ppMapper.addNewSubmit(psubmit);
			key = psubmit.getId();
			boolean needBack = false;
			if(key>0){
				needReturn = new HashMap<>();
				needReturn.put("key", key);
//				System.out.println("key:"+key);
				ProcedureOption[] opts = psubmit.getOpts();
				if(opts!=null){
					for(int i=0;i<opts.length;i++){
						opts[i].setProcedureId(key);
						if(!addOptionOfSubmit(opts[i])){
							//失败了
							needBack = true;
//							System.out.println("失败编号1");
							break;
						}
					}
				}
				//上面没错，继续
				if(!needBack){
					ProcedureShen[] shens = psubmit.getShens();
					if(shens!=null){
						for(int i=0;i<shens.length;i++){
							if(shens[i].getOrder()==1){
								needReturn.put("needToRemind", shens[i].getUserJobId());
							}
							shens[i].setProcedureId(key);
							if(!addShenOfSubmit(shens[i])){
								//失败了
								needBack = true;
//								System.out.println("失败编号2");
								break;
							}
						}
					}
				}
				if(!needBack){
					//没有错误
					result = true;
				}
			}
			if(!result){
				needReturn = null;
				throw new RuntimeException();
			}
			return needReturn;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
	 * 保存提交的选项
	 * @param opt
	 * @return
	 */
	//---
	public boolean addOptionOfSubmit(ProcedureOption opt){
		int num = poMapper.addNewOption(opt);
//		System.out.println("num:"+num);
		if(num==1){
			return true;
		}else{
//			System.out.println("addOptionOfSubmit"+opt.getOrder()+"错误");
			return false;
		}
	}
	
	/**
	 * 保存提交的审批
	 * @param shen
	 * @return
	 */
	public boolean addShenOfSubmit(ProcedureShen shen){
		int num = psMapper.addNewShen(shen);
		if(num==1){
			return true;
		}else{
//			System.out.println("addShenOfSubmit"+shen.getOrder()+"错误");
			return false;
		}
	}
	
	
	/**
	 * 获取流程的创建者
	 * @param id
	 * @return
	 */
	public String getUserOfProcedureWhoCreateById(int id){
		String result = null;
		result = mpMapper.getUserOfProcedureWhoCreateById(id);
		return result;
	}
	
	/**
	 * 根据ID，删除一个流程
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public boolean delProcedureById(int id) throws Exception{
		boolean resTemp = false;
			
		int num = 0;
		int num2 = 0;

		//查询有几个选项
		num2 = moMapper.getNumberOfOneModel(id);
		//删除选项
		num = moMapper.delAllOptionsByModelId(id);
		//选项全部删除
		if(num==num2){
			//查询有几个过程
			num2 = msMapper.getNumberOfOneModel(id);
			//删除过程
			num = msMapper.delAllByModelId(id);
			//过程全部删除
			if(num==num2){
				num = mpMapper.delById(id);
				if(num==1){
					resTemp = true;
				}
			}
		}
		if(!resTemp){
			throw new Exception();
		}
		return resTemp;
	}
	
	/**
	 * 更新模板
	 * @param procedure
	 * @return
	 * @throws Exception 
	 */
	public boolean updateProcedure(ModelProcedure procedure) throws Exception {
		boolean resTemp = false;
		int num = 0;
		int num2 = 0;
		num = mpMapper.updateModel(procedure);
		//成功修改主要
		if(num==1){
			//查询有几个选项
			num2 = moMapper.getNumberOfOneModel(procedure.getId());
			//删除选项
			num = moMapper.delAllOptionsByModelId(procedure.getId());
			//选项全部删除
			if(num==num2){
				//查询有几个过程
				num2 =msMapper.getNumberOfOneModel(procedure.getId());
				//删除过程
				num = msMapper.delAllByModelId(procedure.getId());
				//过程全部删除
				if(num==num2){
					boolean needBack = false;//是否需要回滚
					//添加选项
					ModelOption[] opts = procedure.getOption();
					for(ModelOption opt:opts){
						if(!addNewOption(opt,procedure.getId())){
							needBack = true;
							break;
						}
					}
					if(!needBack){
						//添加过程
						ModelShen[] shens = procedure.getShen();
						for(ModelShen s:shens){
							if(!addNewShen(s,procedure.getId())){
								needBack = true;
								break;
							}
						}
					}
					if(!needBack){
						resTemp = true;
					}
				}
			}
		}
		//其中一个失败
		if(!resTemp){
			throw new Exception();
		}
		return resTemp;
	}
	
	
	/**
	 * 获取一个流程模板的全部
	 * @param modelId
	 * @return
	 */
	public ModelProcedure getModelInfoAllById(int modelId) {
		ModelProcedure result = null;
		//查找主内容
		result = mpMapper.findById(modelId);
		//查找填选项
		List<ModelOption> tempO = moMapper.getOptionsByProcedureId(modelId);
		if(tempO!=null && tempO.size()!=0){
			ModelOption[] opts = new ModelOption[tempO.size()];
			for(int i=0;i<tempO.size();i++){
				opts[i] = tempO.get(i);
			}
			result.setOption(opts);
			//查找流程
			List<ModelShen> tempS = msMapper.getShensByProcedureId(modelId);
			if(tempS!=null && tempS.size()!=0){
				ModelShen[] ss = new ModelShen[tempS.size()];
				for(int i=0;i<tempS.size();i++){
					ss[i] = tempS.get(i);
				}
				result.setShen(ss);
			}else{
				result = null;
			}
		}else{
			result = null;
		}
		return result;
	}
	
	/**
	 * 从第几到第几的模板，按时间排序
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> getAllModelFromNumToNum(int begin,int end){
		List<Map<String,Object>> result = null;
		result = mpMapper.getAllModelFromNumToNum(begin, end);
		return result;
	}
	
	/**
	 * 获取所有模板的总数
	 * @return
	 */
	public int getAllModelNumber(){
		int result = 0;
		result = mpMapper.getAllModelNumber();
		return result;
	}
	
	/**
	 * 创建新的流程
	 * @param procedure
	 * @return
	 * @throws Exception 
	 */
	public boolean createNewProcedure(ModelProcedure procedure) throws Exception {
		boolean result = false;
		int key = 0;
		procedure.setCreateDate(new Date());
		key = mpMapper.addNewProcedure(procedure);
		//成功添加上了
		if(key!=0){
			boolean needBack = false;
			key = procedure.getId();
			ModelOption[] opts = procedure.getOption();
			for(ModelOption opt:opts){
				if(!addNewOption(opt,key)){
					needBack = true;
					break;
				}
			}
			if(!needBack){
				ModelShen[] shens = procedure.getShen();
				for(ModelShen s:shens){
					if(!addNewShen(s,key)){
						needBack = true;
						break;
					}
				}
				if(!needBack){
					result = true;
				}
			}
		}
		if(!result){
			throw new Exception();
		}
		return result;
	}
	
	/**
	 * 添加一个新的流程
	 * @param s
	 * @param modelId
	 * @return
	 */
	//---
	public boolean addNewShen(ModelShen s,int modelId){
		boolean result = false;
		s.setModelId(modelId);
		int num = msMapper.addNewShen(s);
		if(num==1){
			result = true;
		}
		return result;
	}
	
	/**
	 * 向数据库中添加一个新的填选项
	 * @param opt
	 * @param modelId
	 * @return
	 */
	public boolean addNewOption(ModelOption opt,int modelId){
		boolean result = false;
		opt.setModelId(modelId);
		int num = moMapper.addNewOption(opt);
		if(num==1){
			result = true;
		}
		return result;
	}

	
	
}





















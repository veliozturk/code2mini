package iwb.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import iwb.cache.FrameworkCache;
import iwb.cache.FrameworkSetting;
import iwb.domain.db.Log5JobAction;
import iwb.domain.db.Log5Transaction;
import iwb.domain.db.W5JobSchedule;
import iwb.domain.db.W5Project;
import iwb.domain.db.W5WsServer;
import iwb.domain.helper.W5FormCellHelper;
import iwb.domain.helper.W5GridReportHelper;
import iwb.domain.helper.W5ReportCellHelper;
import iwb.domain.result.M5ListResult;
import iwb.domain.result.W5FormResult;
import iwb.domain.result.W5GlobalFuncResult;
import iwb.domain.result.W5PageResult;
import iwb.domain.result.W5QueryResult;
import iwb.engine.GlobalScriptEngine;
import iwb.engine.QueryEngine;
import iwb.engine.RESTEngine;
import iwb.engine.UIEngine;
import iwb.util.GenericUtil;
import iwb.util.LogUtil;

@Service
public class FrameworkService {


	@Lazy
	@Autowired
	private QueryEngine queryEngine;

	@Lazy
	@Autowired
	private GlobalScriptEngine scriptEngine;


	@Lazy
	@Autowired
	private UIEngine uiEngine;

	@Lazy
	@Autowired
	private RESTEngine restEngine;



	public W5FormResult getFormResultByQuery(Map<String, Object> scd, int formId, int queryId,
			Map<String, String> requestParams) {
		return uiEngine.getFormResultByQuery(scd, formId, queryId, requestParams);
	}

	public W5FormResult getFormResult(Map<String, Object> scd, int formId, int action,
			Map<String, String> requestParams) {
		return uiEngine.getFormResult(scd, formId, action, requestParams);
	}


	public List<W5ReportCellHelper> getGridReportResult(Map<String, Object> scd, int gridId, String gridColumns,
			Map<String, String> requestParams) {
		return queryEngine.getGridReportResult(scd, gridId, gridColumns, requestParams);
	}



	public W5QueryResult executeQuery(Map<String, Object> scd, int queryId, Map<String, String> requestParams) {
		return queryEngine.executeQuery(scd, queryId, requestParams);

	}


	public W5PageResult getPageResult(Map<String, Object> scd, int pageId, Map<String, String> requestParams) {
		return uiEngine.getPageResult(scd, pageId, requestParams);
	}


	public W5GlobalFuncResult executeFunc(Map<String, Object> scd, int dbFuncId, Map<String, String> parameterMap,
			short accessSourceType) {
		return scriptEngine.executeGlobalFunc(scd, dbFuncId, parameterMap, accessSourceType);

	}
	

	public W5GlobalFuncResult executeFuncNT(Map<String, Object> scd, int dbFuncId, Map<String, String> parameterMap,
			short accessSourceType) {
		return scriptEngine.executeGlobalFunc(scd, dbFuncId, parameterMap, accessSourceType);

	}



	public W5GlobalFuncResult postEditGridGlobalFunc(Map<String, Object> scd, int dbFuncId, int dirtyCount,
			Map<String, String> requestParams, String prefix) {
		return scriptEngine.postEditGridGlobalFunc(scd, dbFuncId, dirtyCount, requestParams, prefix);
	}






	public boolean runJob(W5JobSchedule job) {

		job.set_running(true);
		W5GlobalFuncResult res = null;
		String transactionId =  GenericUtil.getTransactionId();
		if(FrameworkSetting.logType>0)LogUtil.logObject(new Log5Transaction(job.getProjectUuid(), "job", transactionId), true);

		Log5JobAction logJob = new Log5JobAction(job.getJobScheduleId(), job.getProjectUuid(), transactionId);
		try {// fonksiyon çalıştırılacak ise
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("_trid_", transactionId);

			Map<String, Object> scd = new HashMap<String, Object>();
			W5Project po = FrameworkCache.getProject(job.getProjectUuid());
			scd.put("projectId", job.getProjectUuid());
			scd.put("locale", job.getLocale());
			scd.put("customizationId", po.getCustomizationId());
			scd.put("userRoleId", job.get_userRoleId());
			scd.put("roleId", job.getExecuteRoleId());
			scd.put("userId", job.getExecuteUserId());
			scd.put("administratorFlag", 1);
			res = scriptEngine.executeGlobalFunc(scd, job.getActionDbFuncId(), requestParams, (short) 7);
			if (FrameworkSetting.debug && res.isSuccess()) {
				System.out.println("Scheduled function is executed (funcId=" + job.getActionDbFuncId() + ")");
			}
		} catch (Exception e) {
			if (FrameworkSetting.debug)
				e.printStackTrace();
			logJob.setError(e.getMessage());
			return false;
		} finally {
			job.set_running(false);
			LogUtil.logObject(logJob, false);
		}
		return res.isSuccess();
	}
	
	
	public boolean runJobNT(W5JobSchedule job) {

		job.set_running(true);
		W5GlobalFuncResult res = null;
		String transactionId =  GenericUtil.getTransactionId();
		if(FrameworkSetting.logType>0)LogUtil.logObject(new Log5Transaction(job.getProjectUuid(), "job", transactionId), true);

		Log5JobAction logJob = new Log5JobAction(job.getJobScheduleId(), job.getProjectUuid(), transactionId);
		try {// fonksiyon çalıştırılacak ise
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("_trid_", transactionId);

			Map<String, Object> scd = new HashMap<String, Object>();
			W5Project po = FrameworkCache.getProject(job.getProjectUuid());
			scd.put("projectId", job.getProjectUuid());
			scd.put("locale", job.getLocale());
			scd.put("customizationId", po.getCustomizationId());
			scd.put("userRoleId", job.get_userRoleId());
			scd.put("roleId", job.getExecuteRoleId());
			scd.put("userId", job.getExecuteUserId());
			scd.put("administratorFlag", 1);
			res = scriptEngine.executeGlobalFunc(scd, job.getActionDbFuncId(), requestParams, (short) 7);
			if (FrameworkSetting.debug && res.isSuccess()) {
				System.out.println("Scheduled function is executed (funcId=" + job.getActionDbFuncId() + ")");
			}
		} catch (Exception e) {
			if (FrameworkSetting.debug)
				e.printStackTrace();
			logJob.setError(e.getMessage());
			return false;
		} finally {
			job.set_running(false);
			LogUtil.logObject(logJob, false);
		}
		return res.isSuccess();
	}





	public void checkAlarms(Map<String, Object> scd) {
		if (true)
			return;
		/*
		 * List<W5FormSmsMailAlarm> l = dao.find(
		 * "from W5FormSmsMailAlarm t where t.status=1 AND t.customizationId=? order by t.alarmDttm"
		 * , scd.get("customizationId")); long d = new Date().getTime();
		 * for(W5FormSmsMailAlarm a:l)if(d-a.getAlarmDttm().getTime()>1000*30)try{
		 * scd.put("userId", a.getInsertUserId()); scd.put("userTip", 2); List l2 =
		 * dao.find(
		 * "from W5FormSmsMail x where x.formSmsMailId=? AND x.customizationId=?" ,
		 * a.getFormSmsMailId(), a.getCustomizationId()); W5GlobalFuncResult rdb = null;
		 * if(l2.size()==1){ W5FormSmsMail fsm = (W5FormSmsMail)l2.get(0); Map m = new
		 * HashMap(); m.put("ptable_id", a.getTableId());m.put("ptable_pk",
		 * a.getTablePk()); switch(fsm.getSmsMailTip()){ case 0://sms
		 * //parameterMap.get("phone"),parameterMap.get("body") //
		 * m.putAll(dao.interprateSmsTemplate(fsm, scd, new HashMap(), a.getTableId(),
		 * a.getTablePk())); // rdb = executeFunc(scd, -631, m, (short)1); break; case
		 * 1://mail //W5Email email= new
		 * W5Email(parameterMap.get("pmail_to"),parameterMap.get("pmail_cc"),
		 * parameterMap.get("pmail_bcc"),parameterMap.get("pmail_subject"),
		 * parameterMap.get("pmail_body"), parameterMap.get("pmail_keep_body_original"),
		 * fileAttachments); // m.put("pmail_setting_id",
		 * FrameworkCache.getAppSettingStringValue(scd, "default_outbox_id")); //
		 * m.putAll(dao.interprateMailTemplate(fsm, scd, new HashMap(), a.getTableId(),
		 * a.getTablePk())); // rdb = executeFunc(scd, -650, m, (short)1); break;
		 * default: break; } Log5Notification n = new Log5Notification(a);
		 * n.set_tableRecordList(dao.findRecordParentRecords(scd,a.getTableId(),
		 * a.getTablePk(),0, true)); UserUtil.publishNotification(n, false);
		 * a.setStatus(rdb== null || rdb.isSuccess() ? (short)0 : (short)2); // 0:done,
		 * 1: active, 2:error sending, 3:canceled } else a.setStatus((short)2); }
		 * catch(Exception e) { a.setStatus((short)2); // 0:done, 1: active, 2:error
		 * sending, 3:canceled } finally{ dao.updateObject(a); } else break;
		 */
	}





	public void sendSms(int customizationId, int userId, String phoneNumber, String message, int tableId, int tablePk) {
		Map<String, String> smsMap = new HashMap<String, String>();
		smsMap.put("customizationId", customizationId + "");
		smsMap.put("userId", userId + "");
		smsMap.put("tableId", tableId + "");
		smsMap.put("tablePk", tablePk + "");
		smsMap.put("phoneNumber", phoneNumber);
		smsMap.put("message", message);

		// messageSender.sendMessage("SEND_SMS","BMPADAPTER", smsMap);

	}

	public W5FormCellHelper reloadFormCell(Map<String, Object> scd, int fcId, String webPageId, String tabId) {
		return uiEngine.reloadFormCell(scd, fcId, webPageId, tabId);
	}




	public M5ListResult getMListResult(Map<String, Object> scd, int listId, Map<String, String> parameterMap) {
		return uiEngine.getMListResult(scd, listId, parameterMap);
	}


	public Map<String, Object> getWsServerMethodObjects(W5WsServer wss) {
		return restEngine.getWsServerMethodObjects(wss);

	}

	public Map REST(Map<String, Object> scd, String name, Map requestParams) throws IOException {
		return restEngine.REST(scd, name, requestParams);
	}

	

	




	public W5GridReportHelper prepareGridReport(Map<String, Object> scd, int gridId, String gridColumns,
			Map<String, String> requestParams) {
		return queryEngine.prepareGridReport(scd, gridId, gridColumns, requestParams);
	}

}
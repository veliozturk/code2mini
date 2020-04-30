package iwb.dao.metadata.rdbms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
//import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import iwb.cache.FrameworkCache;
import iwb.cache.FrameworkSetting;
import iwb.domain.db.M5List;
import iwb.domain.db.W5Form;
import iwb.domain.db.W5FormModule;
import iwb.domain.db.W5Grid;
import iwb.domain.db.W5GridColumn;
import iwb.domain.db.W5List;
import iwb.domain.db.W5ObjectToolbarItem;
import iwb.domain.helper.W5FormCellHelper;
import iwb.domain.result.M5ListResult;
import iwb.domain.result.W5CardResult;
import iwb.domain.result.W5FormResult;
import iwb.domain.result.W5GlobalFuncResult;
import iwb.domain.result.W5GridResult;
import iwb.domain.result.W5ListViewResult;
import iwb.domain.result.W5PageResult;
import iwb.domain.result.W5QueryResult;
import iwb.engine.UIEngine;
import iwb.exception.IWBException;
import iwb.util.GenericUtil;

@Repository
public class PostgreSQLLoader{
	private static Logger logger = Logger.getLogger(PostgreSQLLoader.class);
	@Lazy
	@Autowired
	private UIEngine uiEngine;

	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#getFormResult(java.util.Map, int, int, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getFormResult(java.util.Map, int, int, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getFormResult(java.util.Map, int, int, java.util.Map)
	 */
	
	public W5FormResult getFormResult(Map<String, Object> scd, int formId, int action,
			Map<String, String> requestParams) {
		W5FormResult formResult = new W5FormResult(formId);
		formResult.setScd(scd);
		formResult.setErrorMap(new HashMap());
		formResult.setAction(action);
		formResult.setRequestParams(requestParams);
		formResult.setOutputFields(new HashMap());
		formResult.setPkFields(new HashMap());
		formResult.setOutputMessages(new ArrayList());
		formResult.setExtraFormCells(new ArrayList());
		W5Form f = FrameworkCache.getForm(scd, formId);
		formResult.setForm(f);

		if (formResult.getForm().get_moduleList() != null) { // eger baska turlu
																// render
																// edilecekse
			for (W5FormModule m : formResult.getForm().get_moduleList()) { // form
				switch (m.getModuleTip()) {
				case 5: // grid
					if (formResult.getModuleGridMap() == null)
						formResult.setModuleGridMap(new HashMap());
					formResult.getModuleGridMap().put(m.getObjectId(),
							getGridResult(scd, m.getObjectId(), requestParams, true));
					break;
				case 6: // query4formcell

					break;
				case 10:// mobile list
					if (formResult.getModuleListMap() == null)
						formResult.setModuleListMap(new HashMap());
					formResult.getModuleListMap().put(m.getObjectId(),
							getMListResult(scd, m.getObjectId(), requestParams, true));

				}
			}
		}

		return formResult;
	}

	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#getQueryResult(java.util.Map, int)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getQueryResult(java.util.Map, int)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getQueryResult(java.util.Map, int)
	 */
	
	public W5QueryResult getQueryResult(Map<String, Object> scd, int queryId) {
		if (scd != null && scd.get("customizationId") != null && (Integer) scd.get("customizationId") > 0
				&& GenericUtil.uInt(scd.get("rbac")) != 0)
			switch (queryId) { // tenant user and role conversion
			case 43:
				queryId = 4511;
				break; // lookup_user1
			case 554:
				queryId = 4512;
				break; // lookup_role1
			}
		W5QueryResult queryResult = new W5QueryResult(queryId);
		queryResult.setScd(scd);
		String projectId = FrameworkCache.getProjectId(scd, "8." + queryId);
		queryResult.setQuery(FrameworkCache.getQuery(projectId, queryId));


		switch (queryResult.getQuery().getQuerySourceTip()) {
		case 0:
		case 15:
			if (queryResult.getQuery().getMainTableId() != 0) {
				queryResult.setMainTable(FrameworkCache.getTable(projectId, queryResult.getQuery().getMainTableId()));
			}
		}
		return queryResult;
	}

	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#getPageResult(java.util.Map, int)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getPageResult(java.util.Map, int)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getPageResult(java.util.Map, int)
	 */
	
	public W5PageResult getPageResult(Map<String, Object> scd, int pageId) {

		W5PageResult pr = new W5PageResult(pageId);
		pr.setScd(scd);

		pr.setPage(FrameworkCache.getPage(scd, pageId));

		return pr;
	}

	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#getCardResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getCardResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getCardResult(java.util.Map, int, java.util.Map, boolean)
	 */
	
	public W5CardResult getCardResult(Map<String, Object> scd, int cardId, Map<String, String> requestParams,
			boolean noSearchForm) {
		W5CardResult cr = new W5CardResult(cardId);
		String projectId = FrameworkCache.getProjectId(scd, "930." + cardId);
		cr.setRequestParams(requestParams);
		cr.setScd(scd);

		cr.setCard(FrameworkCache.getCard(projectId, cardId));

		// search Form
		if (!noSearchForm && cr.getCard().get_searchFormId() != 0) {
			W5FormResult searchForm = getFormResult(scd, cr.getCard().get_searchFormId(), 2, requestParams);
			uiEngine.initializeForm(searchForm, false);
			uiEngine.loadFormCellLookups(cr.getScd(), searchForm.getFormCellResults(), cr.getRequestParams(), null);
			cr.setSearchFormResult(searchForm);
		}
		return cr;
	}

	
	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#getGridResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getGridResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getGridResult(java.util.Map, int, java.util.Map, boolean)
	 */
	
	public W5GridResult getGridResult(Map<String, Object> scd, int gridId, Map<String, String> requestParams,
			boolean noSearchForm) {
		W5Grid g = null;
		try {
			String projectId = FrameworkCache.getProjectId(scd, "5." + gridId);
			W5GridResult gridResult = new W5GridResult(gridId);
			gridResult.setRequestParams(requestParams);
			gridResult.setScd(scd);
			g = FrameworkCache.getGrid(projectId, gridId);
			gridResult.setGrid(g);


			// search Form
			if (!noSearchForm && g.get_searchFormId() != 0)
				try {
					W5FormResult searchForm = getFormResult(scd, g.get_searchFormId(), 2, requestParams);
					uiEngine.initializeForm(searchForm, false);
					uiEngine.loadFormCellLookups(scd, searchForm.getFormCellResults(), requestParams, null);
					gridResult.setSearchFormResult(searchForm);
				} catch (Exception e) {
					throw new IWBException("framework", "Load.SearchForm", g.get_searchFormId(), null,
							"[40," + g.get_searchFormId() + "]", e);
				}

			gridResult.setFormCellResultMap(new HashMap());

			for (W5GridColumn column : g.get_gridColumnList())
				if (column.get_formCell() != null) {
					W5FormCellHelper cellResult = new W5FormCellHelper(column.get_formCell());
					gridResult.getFormCellResultMap().put(column.get_formCell().getFormCellId(), cellResult);
				}

			if (!gridResult.getFormCellResultMap().isEmpty())
				uiEngine.loadFormCellLookups(gridResult.getScd(), new ArrayList(gridResult.getFormCellResultMap().values()),
						gridResult.getRequestParams(), null);

			if (!GenericUtil.isEmpty(gridResult.getGrid().get_toolbarItemList()))
				for (W5ObjectToolbarItem ti : gridResult.getGrid().get_toolbarItemList()) {
					if ((ti.getItemTip() == 7 || ti.getItemTip() == 15) && ti.getLookupQueryId() > 0) {

						W5QueryResult lookupQueryResult = getQueryResult(scd, ti.getLookupQueryId());
						lookupQueryResult.setErrorMap(new HashMap());
						lookupQueryResult.setRequestParams(requestParams);
						lookupQueryResult.setOrderBy(lookupQueryResult.getQuery().getSqlOrderby());
						lookupQueryResult.prepareQuery(null);
/*						if (lookupQueryResult.getErrorMap().isEmpty()) {
							dao.runQuery(lookupQueryResult);
							if (gridResult.getExtraOutMap() == null)
								gridResult.setExtraOutMap(new HashMap());
							gridResult.getExtraOutMap().put("_tlb_" + ti.getToolbarItemId(), lookupQueryResult);
						}
*/
					}
				}

			return gridResult;
		} catch (Exception e) {
			throw new IWBException("framework", "Load.Grid", gridId, null,
					"[5," + gridId + "]" + (g != null ? " " + g.getDsc() : ""), e);
		}
	}


	/* (non-Javadoc)
	 * @see iwb.dao.metadata.postgresql.MetadataLoader#setApplicationSettingsValues()
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#setApplicationSettingsValues()
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#setApplicationSettingsValues()
	 */
	
	public void setApplicationSettingsValues() {
		FrameworkSetting.debug = FrameworkCache.getAppSettingIntValue(0, "debug") != 0;


		FrameworkSetting.mq = FrameworkCache.getAppSettingIntValue(0, "mq_flag") != 0;
		// FrameworkSetting.preloadWEngine =
		// FrameworkCache.getAppSettingIntValue(0, "preload_engine");
		FrameworkSetting.chat = FrameworkCache.getAppSettingIntValue(0, "chat_flag") != 0;
		// FrameworkSetting.allowMultiLogin =
		// FrameworkCache.getAppSettingIntValue(0,
		// "allow_multi_login_flag")!=0;
		// FrameworkSetting.profilePicture =
		// FrameworkCache.getAppSettingIntValue(0,
		// "profile_picture_flag")!=0;
		FrameworkSetting.alarm = FrameworkCache.getAppSettingIntValue(0, "alarm_flag") != 0;
		FrameworkSetting.sms = FrameworkCache.getAppSettingIntValue(0, "sms_flag") != 0;
		FrameworkSetting.mail = FrameworkCache.getAppSettingIntValue(0, "mail_flag") != 0;

		FrameworkSetting.vcs = FrameworkCache.getAppSettingIntValue(0, "vcs_flag") != 0;
		FrameworkSetting.vcsServer = FrameworkCache.getAppSettingIntValue(0, "vcs_server_flag") != 0;
		FrameworkSetting.vcsServerClient = FrameworkCache.getAppSettingIntValue(0, "vcs_server_client_flag") != 0;

		// if(FrameworkSetting.preloadWEngine!=0)FrameworkCache.clearPreloadCache();
		// //TODO

		FrameworkSetting.advancedSelectShowEmptyText = FrameworkCache.getAppSettingIntValue(0,
				"advanced_select_show_empty_text") != 0;
		FrameworkSetting.simpleSelectShowEmptyText = FrameworkCache.getAppSettingIntValue(0,
				"simple_select_show_empty_text") != 0;
		FrameworkSetting.cacheTimeoutRecord = FrameworkCache.getAppSettingIntValue(0, "cache_timeout_record") * 1000;
		FrameworkSetting.crudLogSchema = FrameworkCache.getAppSettingStringValue(0, "log_crud_schema",
				FrameworkSetting.crudLogSchema);

		FrameworkSetting.asyncTimeout = FrameworkCache.getAppSettingIntValue(0, "async_timeout", 100);
		//
		// if(MVAUtil.appSettings.get("file_local_path")!=null)MVAUtil.localPath=MVAUtil.appSettings.get("file_local_path");

		FrameworkSetting.onlineUsersAwayMinute = 1000 * 60
				* FrameworkCache.getAppSettingIntValue(0, "online_users_away_minute", 3);
		FrameworkSetting.onlineUsersLimitMinute = 1000 * 60
				* FrameworkCache.getAppSettingIntValue(0, "online_users_limit_minute", 10);
		FrameworkSetting.onlineUsersLimitMobileMinute = 1000 * 60
				* FrameworkCache.getAppSettingIntValue(0, "online_users_limit_mobile_minute", 7 * 24 * 60); // 7
																											// gun
		FrameworkSetting.tableChildrenMaxRecordNumber = FrameworkCache.getAppSettingIntValue(0,
				"table_children_max_record_number", 100);

		FrameworkSetting.mailPassEncrypt = FrameworkCache.getAppSettingIntValue(0, "encrypt_mail_pass") != 0;

		FrameworkSetting.mobilePush = FrameworkCache.getAppSettingIntValue(0, "mobile_push_flag") != 0;
		FrameworkSetting.mobilePushProduction = FrameworkCache.getAppSettingIntValue(0,
				"mobile_push_production_flag") != 0;

		FrameworkSetting.workflow = FrameworkCache.getAppSettingIntValue(0, "approval_flag") != 0;
		FrameworkSetting.liveSyncRecord = FrameworkCache.getAppSettingIntValue(0, "live_sync_record") != 0;

		FrameworkSetting.lookupEditFormFlag = FrameworkCache.getAppSettingIntValue(0, "lookup_edit_form_flag") != 0;
		// PromisSetting.replaceSqlSelectX =
		// PromisCache.getAppSettingIntValue(0,
		// "replace_sql_select_x")!=0;;
	}


	

	/* (non-Javadoc)
	 * @see MetadataLoader#getGlobalFuncResult(java.util.Map, int)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getGlobalFuncResult(java.util.Map, int)
	 */
	
	public W5GlobalFuncResult getGlobalFuncResult(Map<String, Object> scd, int globalFuncId) {
		String projectId = FrameworkCache.getProjectId(scd,
				globalFuncId < -1 ? ("40." + (-globalFuncId)) : ("20." + globalFuncId));


		W5GlobalFuncResult gfr = new W5GlobalFuncResult(globalFuncId);
		gfr.setScd(scd);
		gfr.setGlobalFunc(FrameworkCache.getGlobalFunc(projectId, globalFuncId));

		return gfr;
	}

	/* (non-Javadoc)
	 * @see MetadataLoader#getMListResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getMListResult(java.util.Map, int, java.util.Map, boolean)
	 */
	
	public M5ListResult getMListResult(Map<String, Object> scd, int listId, Map<String, String> requestParams,
			boolean noSearchForm) {
		String projectId = FrameworkCache.getProjectId(scd, "1345." + listId);
		M5ListResult mlr = new M5ListResult(listId);
		mlr.setRequestParams(requestParams);
		mlr.setScd(scd);
		M5List ml = FrameworkCache.getMListView(projectId, listId);
		mlr.setList(ml);


		// search Form
		if (!noSearchForm && ml.get_searchFormId() != 0) {
			W5FormResult searchForm = getFormResult(scd, ml.get_searchFormId(), 10, requestParams);
			uiEngine.initializeForm(searchForm, false);
			uiEngine.loadFormCellLookups(mlr.getScd(), searchForm.getFormCellResults(), mlr.getRequestParams(), null);
			mlr.setSearchFormResult(searchForm);
		}
		return mlr;
	}


	/* (non-Javadoc)
	 * @see MetadataLoader#getListViewResult(java.util.Map, int, java.util.Map, boolean)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#getListViewResult(java.util.Map, int, java.util.Map, boolean)
	 */
	
	public W5ListViewResult getListViewResult(Map<String, Object> scd, int listViewId,
			Map<String, String> requestParams, boolean noSearchForm) {

		W5ListViewResult listViewResult = new W5ListViewResult(listViewId);
		String projectId = FrameworkCache.getProjectId(scd, "936." + listViewId);
		listViewResult.setRequestParams(requestParams);
		listViewResult.setScd(scd);

		W5List d = FrameworkCache.getListView(projectId, listViewId);
		listViewResult.setListView(d);

		// search Form
		if (!noSearchForm && d.get_searchFormId() != 0) {
			W5FormResult searchForm = getFormResult(scd, d.get_searchFormId(), 72, requestParams);
			uiEngine.initializeForm(searchForm, false);
			uiEngine.loadFormCellLookups(listViewResult.getScd(), searchForm.getFormCellResults(),
					listViewResult.getRequestParams(), null);
			listViewResult.setSearchFormResult(searchForm);
		}
		return listViewResult;
	}

	/* (non-Javadoc)
	 * @see MetadataLoader#addProject2Cache(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see MetadataLoader#addProject2Cache(java.lang.String)
	 */


}

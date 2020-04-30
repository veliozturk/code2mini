package iwb.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import iwb.cache.FrameworkCache;
import iwb.cache.FrameworkSetting;
import iwb.cache.LocaleMsgCache;
import iwb.dao.metadata.rdbms.PostgreSQLLoader;
import iwb.domain.db.W5Detay;
import iwb.domain.db.W5Form;
import iwb.domain.db.W5FormCell;
import iwb.domain.db.W5FormModule;
import iwb.domain.db.W5FormSmsMail;
import iwb.domain.db.W5Grid;
import iwb.domain.db.W5GridColumn;
import iwb.domain.db.W5LookUp;
import iwb.domain.db.W5LookUpDetay;
import iwb.domain.db.W5PageObject;
import iwb.domain.db.W5Param;
import iwb.domain.db.W5Query;
import iwb.domain.db.W5QueryParam;
import iwb.domain.db.W5Table;
import iwb.domain.db.W5TableField;
import iwb.domain.db.W5WorkflowStep;
import iwb.domain.db.W5WsMethod;
import iwb.domain.db.W5WsMethodParam;
import iwb.domain.helper.W5FormCellHelper;
import iwb.domain.result.M5ListResult;
import iwb.domain.result.W5CardResult;
import iwb.domain.result.W5FormResult;
import iwb.domain.result.W5GridResult;
import iwb.domain.result.W5ListViewResult;
import iwb.domain.result.W5PageResult;
import iwb.domain.result.W5QueryResult;
import iwb.exception.IWBException;
import iwb.util.GenericUtil;
import iwb.util.UserUtil;

@Component
public class UIEngine {
	@Lazy
	@Autowired
	private PostgreSQLLoader metadataLoader;
	
	@Lazy
	@Autowired
	private QueryEngine queryEngine;


	

	@Lazy
	@Autowired
	private GlobalScriptEngine scriptEngine;
	
	
	@Lazy
	@Autowired
	private RESTEngine restEngine;

	public W5FormResult getFormResultByQuery(Map<String, Object> scd, int formId, int queryId,
			Map<String, String> requestParams) {
		W5FormResult formResult = metadataLoader.getFormResult(scd, formId, 1, requestParams);
		// formResult.getForm().get_formCells().clear();
		if (formId != 1622)
			formResult.getForm().get_moduleList().clear(); // TODO: neden
															// yapilmis???
		formResult.setUniqueId(GenericUtil.getNextId("xfi"));

		W5QueryResult queryResult = queryEngine.executeQuery(scd, queryId, requestParams);
		formResult.setFormCellResults(new ArrayList<W5FormCellHelper>(queryResult.getData().size()));

		short tabOrder = 1;
		for (Object[] d : queryResult.getData()) {
			W5FormCellHelper result = GenericUtil.getFormCellResultByQueryRecord(d);
			if (result.getFormCell().getTabOrder() == 0)
				result.getFormCell().setTabOrder(tabOrder);
			result.getFormCell().setFormCellId(-tabOrder);
			tabOrder++;
			formResult.getFormCellResults().add(result);
		}
		if (queryResult.getQuery().get_queryFields().get(7).getPostProcessTip() == 10) { // tipi
																							// lookup
																							// ise
																							// o
																							// zaman
																							// modulleri
																							// buraya
																							// koy
			for (W5LookUpDetay d : FrameworkCache
					.getLookUp(scd, queryResult.getQuery().get_queryFields().get(7).getLookupQueryId())
					.get_detayList()) {
				W5FormModule m = new W5FormModule();
				m.setFormModuleId(GenericUtil.uInt(d.getVal()));
				m.setLocaleMsgKey(d.getDsc());
				formResult.getForm().get_moduleList().add(m);
			}
		}
		loadFormCellLookups(scd, formResult.getFormCellResults(), requestParams, null);
		return formResult;
	}

	public W5FormResult getFormResult(Map<String, Object> scd, int formId, int action,
			Map<String, String> requestParams) {

		W5FormResult formResult = null;
		try {
			formResult = metadataLoader.getFormResult(scd, formId, action, requestParams);
			formResult.setUniqueId(GenericUtil.getNextId("fi"));
			if(GenericUtil.uInt(requestParams, "_viewMode")!=0)
				formResult.setViewMode(true);
			/*
			 * if(requestParams.containsKey("_log5_log_id")){
			 * if(!FrameworkCache.wTemplates.containsKey(scd.get(
			 * "customizationId")))FrameworkCache.wTemplates.put((Integer)scd.
			 * get("customizationId"),new HashMap());
			 * FrameworkCache.wTemplates.get((Integer)scd.get("customizationId")
			 * ).put(668, (W5Template)dao.find(
			 * "from W5Template t where t.templateId=668 AND t.customizationId=?"
			 * , scd.get("customizationId")).get(0)); }
			 */
			// boolean dev = scd.get("roleId")!=null &&
			// (Integer)scd.get("roleId")==0 &&
			// GenericUtil.uInt(requestParams,"_dev")!=0;
			String projectId = FrameworkCache.getProjectId(scd, "40." + formId);
			W5Table t = null;
			switch (formResult.getForm().getObjectTip()) {
			
			case 5: // formByQuery:
				formResult
						.setQueryResult4FormCell(queryEngine.executeQuery(scd, formResult.getForm().getObjectId(), requestParams));
				formResult.setFormCellResults(new ArrayList());
				for (Object[] d : formResult.getQueryResult4FormCell().getData()) {
					W5FormCellHelper result = GenericUtil.getFormCellResultByQueryRecord(d);
					// result.getFormCell().setTabOrder(tabOrder++);
					formResult.getFormCellResults().add(result);
				}
				break;
			}

			W5Form f = formResult.getForm();
			if (f.getObjectTip() != 2 && (f.getObjectTip()!=11 || action == 0))
				action = 2; // eger table degilse sadece initializeForm olabilir



			/* tableTrigger before Show start */

			/* end of tableTrigger */

			if (f.getObjectTip() != 5)
				switch (action) {
				case 5: // copy
				case 1: // edit
					if(f.getObjectTip() == 11){ //ws method
						if (f.getSourceWsMethodId() != 0) {
							W5WsMethod wsm = FrameworkCache.getWsMethod(scd, f.getSourceWsMethodId());
							if(wsm!=null){
								
								Map r = restEngine.REST(scd, FrameworkCache.getWsClientById(scd, wsm.getWsId()).getDsc()+"."+wsm.getDsc(), requestParams);
								if(r!=null){
									List lfcr = new ArrayList();
									formResult.setFormCellResults(lfcr);
									for(W5FormCell cell:f.get_formCells())if(cell.getActiveFlag()!=0){
										W5FormCellHelper result = new W5FormCellHelper(cell);
										Object o = r.get(cell.getDsc());
										if(o!=null) {
											if(cell.getControlTip()==5) {
												if(o.toString()=="true" || o.toString()=="on" || o.toString()=="yes" || GenericUtil.uInt(o)!=0)
													result.setValue("1");
												else result.setValue("0");
											} else
												result.setValue(o.toString());
										}
										lfcr.add(result);
									}
		
								}
							}
						}
					}

					// eralp istedi, amaci freefieldlarda initial query deger
					// verebilsin
					for (W5FormCellHelper fcx : formResult.getFormCellResults())
						if (fcx.getFormCell().getObjectDetailId() == 0) { // bir
																			// tane
																			// freefield
																			// bulabilirse
																			// gir

							for (W5FormCellHelper fcx2 : formResult.getFormCellResults())
								if (fcx2.getFormCell().getObjectDetailId() == 0)
									switch (fcx2.getFormCell().getInitialSourceTip()) {
									case 0: // yok-sabit
										fcx2.setValue(fcx2.getFormCell().getInitialValue());
										break;
									case 1: // request
										fcx2.setValue(formResult.getRequestParams()
												.get(fcx2.getFormCell().getInitialValue()));
										break;
									case 2:
										Object o = formResult.getScd().get(fcx2.getFormCell().getInitialValue());
										fcx2.setValue(o == null ? null : o.toString());
										break;
									case 3: // app_setting
										fcx2.setValue(FrameworkCache.getAppSettingStringValue(formResult.getScd(),
												fcx2.getFormCell().getInitialValue()));
										break;
									case 4: // SQL

										break;
									}

							break;
						}
					for (W5FormCellHelper fcx : formResult.getFormCellResults())
						if (fcx.getFormCell().getFormCellId() == 6060) { // mail password
							fcx.setValue("************");
						}

					if (action == 1)
						break;
				case 2: // insert
					if (action == 2) {
						initializeForm(formResult, false);

					} 
						

					break;
				default:
					throw new IWBException("framework", "Form", formId, null,
							LocaleMsgCache.get2(0, (String) scd.get("locale"), "wrong_use_of_action") + " (" + action
									+ ")",
							null);
				}

			// form cell lookup load
			loadFormCellLookups(formResult.getScd(), formResult.getFormCellResults(), formResult.getRequestParams(),
					FrameworkSetting.liveSyncRecord && f.getObjectTip() == 2
							? formResult.getUniqueId() : null);

			for (W5FormCellHelper cr : formResult.getFormCellResults())
				if (cr.getFormCell().getControlTip() == 99) { // grid ise bunun
																// icinde var mi
																// editableFormCell
					W5Grid g = (W5Grid) cr.getFormCell().get_sourceObjectDetail();
					W5GridResult gr = new W5GridResult(g.getGridId());
					gr.setRequestParams(formResult.getRequestParams());
					gr.setScd(formResult.getScd());
					gr.setFormCellResultMap(new HashMap());

					for (W5GridColumn column : g.get_gridColumnList())
						if (column.get_formCell() != null) {
							gr.getFormCellResultMap().put(column.get_formCell().getFormCellId(),
									new W5FormCellHelper(column.get_formCell()));
						}

					gr.setGrid(g);
					if (formResult.getModuleGridMap() == null)
						formResult.setModuleGridMap(new HashMap());
					formResult.getModuleGridMap().put(g.getGridId(), gr);

					if (!gr.getFormCellResultMap().isEmpty())
						loadFormCellLookups(gr.getScd(), new ArrayList(gr.getFormCellResultMap().values()),
								gr.getRequestParams(), null);
				}

			if (GenericUtil.uInt(formResult.getRequestParams().get("viewMode")) != 0)
				formResult.setViewMode(true);

			W5WorkflowStep workflowStep = formResult.getApprovalStep();
			/*if (f.getObjectTip() == 2 && action == 1
					&& FrameworkCache.getTable(scd,
							f.getObjectId()) != null
					&& formResult.getApprovalRecord() != null) {
				W5Workflow workflow = FrameworkCache.getWorkflow(projectId,
						formResult.getApprovalRecord().getApprovalId());
				if (workflow != null) {
					workflowStep = formResult.getApprovalStep();//workflow.get_approvalStepMap().get(formResult.getApprovalRecord().getApprovalStepId()).getNewInstance();
					if (workflowStep != null) {
						boolean canCancel = GenericUtil.hasPartInside2(workflow.getAfterFinUpdateUserIds(),
								scd.get("userId")) && formResult.getApprovalRecord().getApprovalActionTip() == 5
								&& formResult.getApprovalRecord().getApprovalStepId() == 998 ? true : false;
						if (workflowStep.getApprovalStepId() != 901 && workflowStep.getUpdatableFields() == null
								&& !canCancel)
							formResult.setViewMode(true);
						formResult.setApprovalStep(workflowStep);
					}
				}
			}*/
			// normal/readonly/disabled show control/ozel kodlama
			int updatableFieldsCount = 0;
			for (W5FormCellHelper cr : formResult.getFormCellResults())
				if (cr.getFormCell().getControlTip() != 0 && cr.getFormCell().getControlTip() != 13
						&& cr.getFormCell().getControlTip() != 100) { // yok ve
																		// hidden
																		// ve
																		// buttondan
																		// baska
					W5TableField tf = f.getObjectTip() == 2
							&& cr.getFormCell().get_sourceObjectDetail() instanceof W5TableField
									? (W5TableField) cr.getFormCell().get_sourceObjectDetail() : null;
					if (formResult.isViewMode() || cr.getHiddenValue() != null
							|| (action == 1 && cr.getFormCell()
									.getControlTip() == 31 /* ozel kodlama */
							&& GenericUtil.uInt(cr.getFormCell().getLookupIncludedValues()) == 1
							&& !GenericUtil.hasPartInside(cr.getFormCell().getLookupIncludedParams(),
									"" + formResult.getScd().get("userId")))
							|| cr.getFormCell().getNrdTip() != 0 // readonly/disabled
							|| (workflowStep != null
									&& cr.getFormCell()
											.get_sourceObjectDetail() != null
									&& !GenericUtil.hasPartInside(workflowStep.getUpdatableFields(),
											"" + ((W5TableField) cr.getFormCell().get_sourceObjectDetail())
													.getTableFieldId())) // approvalStepUpdatable
																			// Table
																			// Fields
							|| (f.getObjectTip() == 2 && action == 1 && tf != null
									&& (tf.getCanUpdateFlag() == 0 || (tf.getAccessUpdateTip() != 0
											&& !GenericUtil.accessControl(scd, tf.getAccessUpdateTip(),
													tf.getAccessUpdateRoles(), tf.getAccessUpdateUsers())
											&& (GenericUtil.isEmpty(tf.getAccessUpdateUserFields())
													))))) { // ction=edit'te
																								// edti
																								// hakki
																								// yok

						String value = cr.getValue();
						cr.setHiddenValue(value == null || value.length() == 0 ? "_" : GenericUtil.stringToJS(value));
						switch (cr.getFormCell().getControlTip()) {
						case 5: // checkbox
							break;
						case 9:
						case 16:
						case 60: // remote Lookups
							// cr.setHiddenValue(null);
							break;
						case 2: // date
							cr.setValue(GenericUtil.uDateStr(value));
							break;
						case 18: // timestamp
							cr.setValue(value);
							break;
						case 10:
						case 61: // autoselect,
									// superboxselect-combo-query-advanced
							if (cr.getLookupQueryResult() != null && cr.getLookupQueryResult().getData() != null
									&& cr.getLookupQueryResult().getData().size() != 0) {
								cr.setValue((String) cr.getLookupQueryResult().getData().get(0)[0]);
								// cr.setHiddenValue(value);
							}
							break;
						case 6: // combo static
							// cr.setHiddenValue(cr.getValue());
							if (cr.getLookupListValues() != null)
								for (W5Detay d : (List<W5Detay>) cr.getLookupListValues()) {
									if (d.getVal().equals(cr.getValue())) {
										cr.setValue(LocaleMsgCache.get2((Integer) scd.get("customizationId"),
												(String) scd.get("locale"), d.getDsc()));
										break;
									}
								}
							break;
						case 7: // combo query
						case 23: // treecombo query
							// cr.setHiddenValue(cr.getValue());
							if (cr.getLookupQueryResult() != null && cr.getLookupQueryResult().getData() != null)
								for (Object[] d : (List<Object[]>) cr.getLookupQueryResult().getData()) {
									if (d[1] != null && d[1].toString().equals(cr.getValue())) {
										cr.setValue(d[0].toString());
										break;
									}
								}
							break;
						case 59: // superboxselect query
						case 15: // lov combo query
							String xval = "";
							if (cr.getLookupQueryResult() != null && cr.getValue() != null) {
								Vector<String> vals = new Vector<String>();
								for (String str : cr.getValue().split(",")) {
									vals.add(str);
								}
								for (Object[] d : (List<Object[]>) cr.getLookupQueryResult().getData()) {
									if (vals.contains(d[1].toString())) {
										xval += cr.getLookupQueryResult().getQuery().get_queryFields().get(0)
												.getPostProcessTip() == 2
														? LocaleMsgCache.get2((Integer) scd.get("customizationId"),
																(String) scd.get("locale"), d[0].toString()) + ", "
														: d[0].toString() + ", ";
									}
								}
							}
							cr.setValue(xval.length() > 2 ? xval.substring(0, xval.length() - 2) : xval);
							break;

						case 58: // superboxselect static
						case 8: // lov combo static
							if (cr.getValue() != null) {
								String xval2 = "";
								String[] arr = cr.getValue().split(",");
								for (int sindex = 0; sindex < arr.length; sindex++) {
									int no = GenericUtil.getIndexNo(arr[sindex], cr.getLookupListValues());
									W5LookUpDetay ld = (W5LookUpDetay) cr.getLookupListValues().get(no);
									xval2 += LocaleMsgCache.get2((Integer) scd.get("customizationId"),
											(String) scd.get("locale"), ld.getDsc()) + " , ";
								}
								cr.setValue(xval2.substring(0, xval2.length() - 2));
							}
							break;
						}

					} else
						updatableFieldsCount++;
				} else if (cr.getFormCell().getControlTip() == 100) { // Buton
																		// ise
																		// butonun
																		// extra
																		// kodunda
																		// local
																		// mesajları
																		// cevirereceğiz
																		// inşallah
					cr.getFormCell().setExtraDefinition(GenericUtil.filterExt(cr.getFormCell().getExtraDefinition(),
							formResult.getScd(), formResult.getRequestParams(), null).toString());
				} else
					cr.setHiddenValue(null);

			if (f.getObjectTip() == 2) { // table ise
				if (updatableFieldsCount == 0)
					formResult.setViewMode(true);
				if (action == 1) { // eidt mode'da ise
					if (FrameworkSetting.alarm /* && !formResult.isViewMode() */
							&& !GenericUtil.isEmpty(f.get_formSmsMailList())) { // readonly
																									// degil
																									// ise
						boolean alarm = false;
						for (W5FormSmsMail i : f.get_formSmsMailList())
							if (i.getAlarmFlag() != 0) {
								alarm = true;
								break;
							}

					}
				}
			}

			return formResult;
		} catch (Exception e) {
			throw new IWBException("framework", "Form", formId, null, "[40," + formId + "]"
					+ (formResult != null && formResult.getForm() != null ? " " + formResult.getForm().getDsc() : ""),
					e);
		}
	}
	
	public W5PageResult getPageResult(Map<String, Object> scd, int pageId, Map<String, String> requestParams) {
		W5PageResult pr = null;
		try {
			boolean developer = scd.get("roleId") != null && (Integer) scd.get("roleId") == 0;
			boolean debugAndDeveloper = FrameworkSetting.debug && developer;
			pr = metadataLoader.getPageResult(scd, pageId);
			pr.setRequestParams(requestParams);
			pr.setPageObjectList(new ArrayList<Object>());
			List<W5PageObject> templateObjectListExt = new ArrayList<W5PageObject>(
					pr.getPage().get_pageObjectList().size() + 5);
			templateObjectListExt.addAll(pr.getPage().get_pageObjectList());

			requestParams.put("_dont_throw", "1");

			for (int i = 1; requestParams.containsKey("_gid" + i) || requestParams.containsKey("_fid" + i)
					|| requestParams.containsKey("_dvid" + i) || requestParams.containsKey("_lvid" + i)
					|| requestParams.containsKey("_gdid" + i) || requestParams.containsKey("_mlid" + i)
					|| requestParams.containsKey("_qid" + i) || requestParams.containsKey("_ggid" + i); i++) { // extra
																												// olarak
																												// _gid1=12&_gid=2
																												// gibi
																												// seyler
																												// soylenebilir
				int objectId = GenericUtil.uInt(requestParams.get("_gid" + i)); // grid
				short objectTip = -1;
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_fid" + i)); // form
					objectTip = -3;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_dvid" + i)); // card
					objectTip = -2;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_lvid" + i)); // list view
					objectTip = -7;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_qid" + i)); // query Result
					objectTip = -4;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_mlid" + i)); // mobile list view
					objectTip = -11;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_gdid" + i)); // graph dashboard
					objectTip = -9;
				}
				if (objectId == 0) {
					objectId = GenericUtil.uInt(requestParams.get("_ggid" + i)); // gauge view
					objectTip = -22;
				}
				W5PageObject o = new W5PageObject();
				o.setObjectTip(objectTip);
				o.setObjectId(objectId);
				templateObjectListExt.add(o);
			}
			
			if(pr.getPage().getTemplateTip()==0 && scd!=null && GenericUtil.uInt(scd.get("dashboardPageId"))!=0) {//html
				W5PageObject o = new W5PageObject();
				o.setObjectTip((short)-31);
				o.setObjectId(GenericUtil.uInt(scd.get("dashboardPageId")));
				templateObjectListExt.add(o);
			}

			int objectCount = 0;
			for (W5PageObject o : templateObjectListExt) {
				boolean accessControl = debugAndDeveloper ? true
						: GenericUtil.accessControl(scd, o.getAccessViewTip(), o.getAccessViewRoles(),
								o.getAccessViewUsers());
				Object obz = null;
				W5Table mainTable = null;
				switch (Math.abs(o.getObjectTip())) {
				case 1: // grid
					W5GridResult gridResult = metadataLoader.getGridResult(scd, o.getObjectId(), requestParams,
							pageId == 298 /* || objectCount!=0 */);
					if (pageId == 298) { // log template
						gridResult.setViewLogMode(true);
					}
					if (o.getObjectTip() < 0) {
						if (GenericUtil.uInt(requestParams, "_gid" + gridResult.getGridId() + "_a") != 0)
							gridResult.setAction(
									GenericUtil.uInt(requestParams, "_gid" + gridResult.getGridId() + "_a"));
						gridResult.setGridId(-gridResult.getGridId());
					}
					mainTable = gridResult.getGrid() != null && gridResult.getGrid().get_query() != null
							? FrameworkCache.getTable(scd, gridResult.getGrid().get_query().getMainTableId())
							: null;
					if (!debugAndDeveloper && mainTable != null
							&& ((mainTable.getAccessViewUserFields() == null
									&& !GenericUtil.accessControl(scd, mainTable.getAccessViewTip(),
											mainTable.getAccessViewRoles(), mainTable.getAccessViewUsers()))))
						obz = gridResult.getGrid().getDsc();
					else {
						if (GenericUtil.uInt(requestParams, "_viewMode") != 0)
							gridResult.setViewReadOnlyMode(true);
						else if (GenericUtil.uInt(requestParams, "_viewMode" + o.getObjectId()) != 0)
							gridResult.setViewReadOnlyMode(true);
						obz = accessControl ? gridResult : gridResult.getGrid().getDsc();
					}
					if (obz instanceof W5GridResult) {
						if(gridResult.getExtraOutMap()==null)gridResult.setExtraOutMap(new HashMap());
						Map m = gridResult.getExtraOutMap();
						gridResult.setTplObj(o);
						gridResult.setExtraOutMap(m);
						m.put("tplId", o.getTemplateId());
						m.put("tplObjId", o.getTemplateObjectId());
					}
					break;
				case 2: // card view
					W5CardResult cardResult = metadataLoader.getCardResult(scd, o.getObjectId(), requestParams,
							objectCount != 0);
					if (o.getObjectTip() < 0)
						cardResult.setDataViewId(-cardResult.getDataViewId());
					mainTable = cardResult.getCard() != null && cardResult.getCard().get_query() != null
							? FrameworkCache.getTable(scd, cardResult.getCard().get_query().getMainTableId())
							: null;
					if (!debugAndDeveloper && mainTable != null
							&& ((mainTable.getAccessViewUserFields() == null
									&& !GenericUtil.accessControl(scd, mainTable.getAccessViewTip(),
											mainTable.getAccessViewRoles(), mainTable.getAccessViewUsers()))))
						obz = cardResult.getCard().getDsc();
					else {
						obz = accessControl ? cardResult : cardResult.getCard().getDsc();
					}
					if (obz instanceof W5CardResult) {
						Map m = new HashMap();
						cardResult.setTplObj(o);
						cardResult.setExtraOutMap(m);
						m.put("tplId", o.getTemplateId());
						m.put("tplObjId", o.getTemplateObjectId());
					}
					break;
				case 7: // list view
					W5ListViewResult listViewResult = metadataLoader.getListViewResult(scd, o.getObjectId(), requestParams,
							objectCount != 0);
					if (o.getObjectTip() < 0)
						listViewResult.setListId(-listViewResult.getListId());
					mainTable = listViewResult.getListView() != null
							&& listViewResult.getListView().get_query() != null
									? FrameworkCache.getTable(scd,
											listViewResult.getListView().get_query().getMainTableId())
									: null;
					if (!debugAndDeveloper && mainTable != null
							&& (( mainTable.getAccessViewUserFields() == null
									&& !GenericUtil.accessControl(scd, mainTable.getAccessViewTip(),
											mainTable.getAccessViewRoles(), mainTable.getAccessViewUsers()))))
						obz = listViewResult.getListView().getDsc();
					else {
						obz = accessControl ? listViewResult : listViewResult.getListView().getDsc();
					}
					break;
				case 3: // form
					W5FormResult formResult = getFormResult(scd, o.getObjectId(),
							requestParams.get("a") != null ? GenericUtil.uInt(requestParams, "a") : 2,
							requestParams);
					if (o.getObjectTip() < 0)
						formResult.setFormId(-formResult.getFormId());
					formResult.setObjectTip(o.getObjectTip()); // render
																// icin
																// gerekecek
					/*
					 * if(PromisSetting.moduleAccessControl!=0 &&
					 * formResult.getForm()!=null &&
					 * formResult.getForm().get_sourceTable()!=null &&
					 * !PromisCache.roleAccessControl(scd,
					 * formResult.getForm().get_sourceTable().getModuleId())
					 * ) obz = formResult.getForm().getDsc(); else
					 */
					obz = accessControl ? formResult : formResult.getForm().getDsc();
					break;
				case 4: // query
				case	10:
					if(FrameworkSetting.metadata && pr.getPage().getTemplateTip()==0 && o.getObjectId()==2822) { //prepare menu from cache
						obz = FrameworkCache.getQueryResult4Menu(scd);
					} else {
						Map paramMap = new HashMap();
						paramMap.putAll(requestParams);
						if (!GenericUtil.isEmpty(o.getPostJsCode())) {
							String[] ar1 = o.getPostJsCode().split("&");
							for (int it4 = 0; it4 < ar1.length; it4++) {
								String[] ar2 = ar1[it4].split("=");
								if (ar2.length == 2 && ar2[0] != null && ar2[1] != null)
									paramMap.put(ar2[0], ar2[1]);
							}
						}
						obz = queryEngine.executeQuery(scd, o.getObjectId(), paramMap);
					}
					break;
				case 8:// component
					obz = FrameworkCache.getComponent(scd, o.getObjectId());//metaDataDao.loadComponent(scd, o.getObjectId(), new HashMap());
					break;
				case 15: // Graph Query
//				case 10: // Badge
				case 22: // Gauge
					obz = metadataLoader.getQueryResult(scd, o.getObjectId());//queryEngine.executeQuery(scd, o.getObjectId(), new HashMap());
					break;
				case 5: // dbFunc
					obz = scriptEngine.executeGlobalFunc(scd, o.getObjectId(), requestParams, (short) 1);
					break;
				case 11: // Mobile List
					obz = metadataLoader.getMListResult(scd, o.getObjectId(), requestParams, false);
					break;
				case 31: // Page
//					obz = pr.getPage().getTemplateTip()==0? getPageResult(scd, o.getObjectId(), new HashMap()):metaDataDao.getPageResult(scd, o.getObjectId());
					obz = getPageResult(scd, o.getObjectId(), new HashMap());
					break;
				case 9: // graph dashboard
/*					W5BIGraphDashboard obz2 = (W5BIGraphDashboard) metadataLoader.getMetadataObject(
							"W5BIGraphDashboard","graphDashboardId",
							o.getObjectId(), scd.get("projectId"), null);
					if (accessControl) {
						obz = obz2;
					} else {
						obz = "graph" + o.getObjectId();
					}*/
					break;
				}
				if (pr.getPage().getTemplateTip() <= 2 && objectCount == 0) { // throw exception for the first object
					if (obz instanceof String)
						throw new IWBException("security", "Module", o.getObjectId(), null,
								"Role Access Control(Page Object)", null);

				}
				if (obz != null)
					pr.getPageObjectList().add(obz);
				objectCount++;
			}
			
			return pr;
		} catch (Exception e) {
			throw new IWBException("framework", "Load.Page", pageId, null,
					"[63," + pageId + "]" + (pr != null && pr.getPage() != null ? " " + pr.getPage().getDsc() : ""), e);
		}
	}
	

	public W5FormCellHelper reloadFormCell(Map<String, Object> scd, int fcId, String webPageId, String tabId) {
		String projectId = FrameworkCache.getProjectId(scd, null);

		int userId = (Integer) scd.get("userId");
		W5FormCell c = null;/*(W5FormCell) metadataLoader.getMetadataObject(
				"W5FormCell","formCellId", fcId, projectId, null)*/;
		if (c == null)
			return null;
		W5FormCellHelper rc = new W5FormCellHelper(c);
		String includedValues = c.getLookupIncludedValues();
		Map<String, String> requestParams = null;
		switch (c.getControlTip()) {
		case 58: // superboxselect
		case 8: // lovcombo static
		case 6: // eger static combobox ise listeyi load et
			W5LookUp lookUp = FrameworkCache.getLookUp(scd, c.getLookupQueryId());
			rc.setLocaleMsgFlag((short) 1);
			requestParams = UserUtil.getTableGridFormCellReqParams(projectId, -c.getLookupQueryId(), userId,
					(String) scd.get("sessionId"), webPageId, tabId, -fcId);
			List<W5LookUpDetay> oldList = FrameworkCache.getLookUp(scd, c.getLookupQueryId()).get_detayList();

			List<W5LookUpDetay> newList = null;
			if (includedValues != null && includedValues.length() > 0) {
				// List<W5LookUpDetay> oldList = lookUp.get_detayList();
				boolean notInFlag = false;
				if (includedValues.charAt(0) == '!') {
					notInFlag = true;
					includedValues = includedValues.substring(1);
				}
				String[] ar1 = includedValues.split(",");
				newList = new ArrayList<W5LookUpDetay>(oldList.size());
				for (W5LookUpDetay p : oldList)
					if ((rc.getValue() != null && p.getVal().equals(rc.getValue())) || p.getActiveFlag() != 0) {
						boolean in = false;
						for (int it4 = 0; it4 < ar1.length; it4++)
							if (ar1[it4].equals(p.getVal())) {
								in = true;
								break;
							}
						if (in ^ notInFlag)
							newList.add(p);
					}
			} else if (requestParams != null && requestParams.get("_lsc" + c.getFormCellId()) != null) {
				String[] lsc = requestParams.get("_lsc" + c.getFormCellId()).split(",");
				newList = new ArrayList<W5LookUpDetay>();
				for (String q : lsc) {
					newList.add(lookUp.get_detayMap().get(q));
				}
			} else {
				newList = new ArrayList<W5LookUpDetay>(oldList.size());
				for (W5LookUpDetay p : oldList)
					if ((rc.getValue() != null && p.getVal().equals(rc.getValue())) || p.getActiveFlag() != 0)
						newList.add(p);
				// newList = lookUp.get_detayList();
			}
			List<W5LookUpDetay> newList2 = new ArrayList<W5LookUpDetay>(newList.size());
			for (W5LookUpDetay ld : newList) {
				newList2.add(ld);
			}
			rc.setLookupListValues(newList2);
			break;
		case 7:
		case 15:
		case 59: // dynamic query, lovcombo, superbox
		case 23:
		case 24:
		case 55: // tree combo and treepanel
			Map paramMap = new HashMap();
			requestParams = UserUtil.getTableGridFormCellReqParams((String) scd.get("projectId"), c.getLookupQueryId(),
					userId, (String) scd.get("sessionId"), webPageId, tabId, -fcId);
			String includedParams = GenericUtil.filterExt(c.getLookupIncludedParams(), scd, requestParams, null)
					.toString();
			if (includedParams != null && includedParams.length() > 2) {
				String[] ar1 = includedParams.split("&");
				for (int it4 = 0; it4 < ar1.length; it4++) {
					String[] ar2 = ar1[it4].split("=");
					if (ar2.length == 2 && ar2[0] != null && ar2[1] != null)
						paramMap.put(ar2[0], ar2[1]);
				}
			}

//			dao.checkTenant(scd);
			W5QueryResult lookupQueryResult = metadataLoader.getQueryResult(scd, c.getLookupQueryId());
			lookupQueryResult.setErrorMap(new HashMap());
			lookupQueryResult.setRequestParams(requestParams);
			lookupQueryResult.setOrderBy(lookupQueryResult.getQuery().getSqlOrderby());
			if (rc.getValue() != null && rc.getValue().length() > 0
					&& GenericUtil.hasPartInside("7,10,61", Short.toString(c.getControlTip())))
				paramMap.put("pmust_load_id", rc.getValue());
			switch (lookupQueryResult.getQuery().getQueryTip()) {
			case 12:
				lookupQueryResult.prepareTreeQuery(paramMap);
				break; // lookup tree query
			default:
				lookupQueryResult.prepareQuery(paramMap);
			}
			rc.setLookupQueryResult(lookupQueryResult);

/*			if (lookupQueryResult.getErrorMap().isEmpty()) {
				dao.runQuery(lookupQueryResult);
				if (tabId != null && lookupQueryResult.getQuery().getMainTableId() != 0) {
					Set<Integer> keys = UserUtil.getTableGridFormCellCachedKeys((String) scd.get("projectId"),
							lookupQueryResult.getQuery().getMainTableId(), userId, (String) scd.get("sessionId"),
							requestParams.get(".w"), tabId, -c.getFormCellId(), requestParams, true);
					for (Object[] o : lookupQueryResult.getData())
						keys.add(GenericUtil.uInt(o[1]));
				}
			}*/

			break;
		}
		return rc;
	}
	
	public M5ListResult getMListResult(Map<String, Object> scd, int listId, Map<String, String> parameterMap) {
		return metadataLoader.getMListResult(scd, listId, parameterMap, false);
	}
	
	public void initializeForm(W5FormResult formResult, boolean onlyFreeFields) {
		W5Form form = formResult.getForm();
		String projectId = FrameworkCache.getProjectId(formResult.getScd(), "40." + formResult.getFormId());
		W5Table t = null;
		switch (form.getObjectTip()) {
		case 2:// table
			t = FrameworkCache.getTable(projectId, form.getObjectId());
			break; // table
		case 1:// grid
			W5Grid g = metadataLoader.getGridResult(formResult.getScd(), form.getObjectId(), new HashMap(), true)
					.getGrid();

			if (g != null) {
				W5Query q = metadataLoader.getQueryResult(formResult.getScd(), g.getQueryId()).getQuery();
				if (q != null)
					t = FrameworkCache.getTable(projectId, q.getMainTableId()); // grid
			}
			break;
		// case 3: t= PromisCache.getTable(formResult.getScd(),
		// form.getObjectId()); break;
		}
		if (formResult.getFormCellResults() == null)
			formResult.setFormCellResults(new ArrayList<W5FormCellHelper>(form.get_formCells().size()));
		if (form.get_formCells() != null)
			formResult.getExtraFormCells().addAll(0, form.get_formCells());
		for (W5FormCell cell : formResult.getExtraFormCells())
			if (!onlyFreeFields || cell.getObjectDetailId() == 0)
				try {
					if (t != null) {
						W5TableField tf = null;
						if (form.getObjectTip() == 2 && cell.get_sourceObjectDetail() != null
								&& cell.get_sourceObjectDetail() instanceof W5TableField) {
							tf = (W5TableField) cell.get_sourceObjectDetail();
						} else if (form.getObjectTip() == 1 && cell.get_sourceObjectDetail() != null) {
							if (cell.get_sourceObjectDetail() instanceof W5QueryParam) {
								W5QueryParam qp = (W5QueryParam) cell.get_sourceObjectDetail();
								if (qp.getRelatedTableFieldId() != 0 && t != null) {
									tf = t.get_tableFieldMap().get(qp.getRelatedTableFieldId());
								}
							} else if (cell.get_sourceObjectDetail() instanceof W5TableField) {
								tf = (W5TableField) cell.get_sourceObjectDetail();
							}
						}
						if (tf != null) {
							if (!GenericUtil.accessControl(formResult.getScd(), tf.getAccessInsertTip(),
									tf.getAccessInsertRoles(), tf.getAccessInsertUsers()))
								continue; // access control
							if (!GenericUtil.accessControl4SessionField(formResult.getScd(),tf.getRelatedSessionField()))
								continue;
						}
					}
					W5FormCellHelper result = new W5FormCellHelper(cell);
					switch (cell.getInitialSourceTip()) {
					case 0: // yok-sabit
						result.setValue(cell.getInitialValue());
						break;
					case 1: // request
						result.setValue(formResult.getRequestParams().get(cell.getInitialValue()));
						break;
					case 2:
						Object o = formResult.getScd().get(cell.getInitialValue());
						result.setValue(o == null ? null : o.toString());
						break;
					case 3: // app_setting
						result.setValue(
								FrameworkCache.getAppSettingStringValue(formResult.getScd(), cell.getInitialValue()));
						break;
					case 4: // SQL

						break;
					case 5: // CustomJS(Rhino)
						Object res = scriptEngine.executeScript(formResult.getScd(), formResult.getRequestParams(),
								cell.getInitialValue(), null, "41i" + cell.getFormCellId());

						if (res != null && ((W5Param) cell.get_sourceObjectDetail()).getParamTip() == 4)
							res = "" + new BigDecimal(res.toString()).intValue();
						result.setValue(res == null ? null : res.toString());

						break;
					case 10: // approvalStates

					}
					formResult.getFormCellResults().add(result);
				} catch (Exception e) {
					throw new IWBException("framework", "Initialize FormElement", cell.getFormCellId(), null,
							"[41," + cell.getFormCellId() + "] " + cell.getDsc(), e);
				}
	}

	public void loadFormCellLookups(Map<String, Object> scd, List<W5FormCellHelper> formCellResults,
			Map<String, String> requestParams, String tabId) {
		String includedValues;
		String projectId = (String) scd.get("projectId");
		// W5Customization cus =
		// FrameworkCache.wCustomizationMap.get(customizationId);
		for (W5FormCellHelper rc : formCellResults)
			try {
				W5FormCell c = rc.getFormCell();
				if (c.getActiveFlag() == 0)
					continue;
				includedValues = c.getLookupIncludedValues();
				Map<String, String> paramMap = new HashMap<String, String>();
				Set<Integer> keys = null;
				switch (c.getControlTip()) {
				case 100: // button
					break;

				case 60: // remote superboxselect
				case 16: // remote query
				case 9: // remote query
					rc.setLookupQueryResult(metadataLoader.getQueryResult(scd, c.getLookupQueryId()));
					// c.set_lookupListCount(c.getLookupQueryId()); // Fake:
					// Normalde Query Id tutulur, ama
					// su anda kac adet column tutuyor
					break;

				case 58: // superboxselect
				case 8: // lovcombo static
				case 6: // eger static combobox ise listeyi load et
					if(c.getLookupQueryId()==0)
						throw new IWBException("framework", "LookUp", 0, null, "LookUp Static not defined for FormElement ["+c.getDsc()+"]", null);
					W5LookUp lookUp = FrameworkCache.getLookUp(scd, c.getLookupQueryId(), "Form(" + c.getFormId() + ")."
							+ c.getDsc() + "-> LookUp not found: " + c.getLookupQueryId());
					rc.setLocaleMsgFlag((short) 1);
					List<W5LookUpDetay> oldList = lookUp.get_detayList();

					List<W5LookUpDetay> newList = null;
					if (includedValues != null && includedValues.length() > 0) {
						boolean notInFlag = false;
						if (includedValues.charAt(0) == '!') {
							notInFlag = true;
							includedValues = includedValues.substring(1);
						}
						String[] ar1 = includedValues.split(",");
						newList = new ArrayList<W5LookUpDetay>(oldList.size());
						for (W5LookUpDetay p : oldList)
							if ((rc.getValue() != null && p.getVal().equals(rc.getValue())) || p.getActiveFlag() != 0) {
								boolean in = false;
								for (int it4 = 0; it4 < ar1.length; it4++)
									if (ar1[it4].equals(p.getVal())) {
										in = true;
										break;
									}
								if (in ^ notInFlag)
									newList.add(p);
							}
					} else if (requestParams.get("_lsc" + c.getFormCellId()) != null) {
						String[] lsc = requestParams.get("_lsc" + c.getFormCellId()).split(",");
						newList = new ArrayList<W5LookUpDetay>();
						for (String q : lsc) {
							newList.add(lookUp.get_detayMap().get(q));
						}
					} else {
						newList = new ArrayList<W5LookUpDetay>(oldList.size());
						for (W5LookUpDetay p : oldList)
							if ((rc.getValue() != null && p.getVal().equals(rc.getValue())) || p.getActiveFlag() != 0)
								newList.add(p);
						// newList = lookUp.get_detayList();
					}
					List<W5LookUpDetay> newList2 = new ArrayList<W5LookUpDetay>(newList.size());
					if (tabId != null)
						keys = UserUtil.getTableGridFormCellCachedKeys((String) scd.get("projectId"),
								-c.getLookupQueryId(), (Integer) scd.get("userId"), (String) scd.get("sessionId"),
								requestParams.get(".w"), tabId, -c.getFormCellId(), requestParams, false);
					for (W5LookUpDetay ld : newList) {
						newList2.add(ld);
					}
					rc.setLookupListValues(newList2);
					break;
				case 10:
				case 61: // advanced select, advancedselect w/ button
					paramMap.put("xid", rc.getValue());
				case 7:
				case 15:
				case 59: // dynamic query, lovcombo, superbox
				case 23:
				case 24:
				case 26:
				case 55: // tree combo and treepanel
					if(c.getLookupQueryId()==0)
						throw new IWBException("framework", "Query", 0, null, "LookUp Query not defined for FormElement ["+c.getDsc()+"]", null);
					String includedParams = GenericUtil.filterExt(c.getLookupIncludedParams(), scd, requestParams, null)
							.toString();
					if (includedParams != null && includedParams.length() > 2) {
						String[] ar1 = includedParams.split("&");
						for (int it4 = 0; it4 < ar1.length; it4++) {
							String[] ar2 = ar1[it4].split("=");
							if (ar2.length == 2 && ar2[0] != null && ar2[1] != null)
								paramMap.put(ar2[0], ar2[1]);
						}
					}

					W5QueryResult lookupQueryResult = metadataLoader.getQueryResult(scd, c.getLookupQueryId());
					lookupQueryResult.setErrorMap(new HashMap());
					lookupQueryResult.setRequestParams(requestParams);
					lookupQueryResult.setOrderBy(lookupQueryResult.getQuery().getSqlOrderby());
					if (lookupQueryResult.getQuery().getQuerySourceTip() != 15)
						switch (lookupQueryResult.getQuery().getQuerySourceTip()) {
						case 1376: // WS Method
							W5WsMethod wsm = FrameworkCache.getWsMethod(projectId,
									lookupQueryResult.getQuery().getMainTableId());

							W5WsMethodParam parentParam = null;
							for (W5WsMethodParam px : wsm.get_params())
								if (px.getOutFlag() != 0 && px.getParamTip() == 10) {
									parentParam = px;
									break;
								}
							Map<String, String> m2 = new HashMap();
							if (requestParams.get("filter[value]") != null) {
								requestParams.put("xdsc", requestParams.get("filter[value]"));
								requestParams.remove("filter[value]");
							}
							for (W5QueryParam qp : lookupQueryResult.getQuery().get_queryParams())
								if (!GenericUtil.isEmpty(requestParams.get(qp.getDsc()))) {
									m2.put(qp.getExpressionDsc(), requestParams.get(qp.getDsc()));
								}
							StringBuilder rc2 = new StringBuilder();
							rc2.append("function _x_(x){\nreturn {").append(lookupQueryResult.getQuery().getSqlSelect())
									.append("\n}}\nvar result=[], q=$.REST('")
									.append(wsm.get_ws().getDsc() + "." + wsm.getDsc()).append("',")
									.append(GenericUtil.fromMapToJsonString2(m2))
									.append(");\nif(q && q.get('success')){q=q.get('").append(parentParam.getDsc())
									.append("');for(var i=0;i<q.size();i++)result.push(_x_(q.get(i)));}");
							scriptEngine.executeQueryAsScript(lookupQueryResult, rc2.toString());
							rc.setLookupQueryResult(lookupQueryResult);
							continue;
						default:
							rc.setLookupQueryResult(lookupQueryResult);
							continue; // burda sadece table icin olur
						}
					if (rc.getValue() != null && rc.getValue().length() > 0
							&& GenericUtil.hasPartInside("7,10,61", Short.toString(c.getControlTip())))
						paramMap.put("pmust_load_id", rc.getValue());
					switch (lookupQueryResult.getQuery().getQueryTip()) {
					case 12:
					case 13:
						lookupQueryResult.prepareTreeQuery(paramMap);
						break; // lookup tree query
					default:
						lookupQueryResult.prepareQuery(paramMap);
					}
					rc.setLookupQueryResult(lookupQueryResult);
					if (c.getControlTip() == 10 || c.getControlTip() == 23 || c.getControlTip() == 7) {
						if (c.getDialogGridId() != 0) {
							if (rc.getExtraValuesMap() == null)
								rc.setExtraValuesMap(new HashMap());
							rc.getExtraValuesMap().put("dialogGrid",
									metadataLoader.getGridResult(scd, c.getDialogGridId(), requestParams, true));
						}

						if (c.getControlTip() == 10 && GenericUtil.isEmpty(rc.getValue()))
							break; // advanced select ise ve degeri yoksa
									// hicbirsey koyma
					}

/*					if (lookupQueryResult.getErrorMap().isEmpty()) {
						runQuery(lookupQueryResult);
						if (tabId != null && lookupQueryResult.getQuery().getMainTableId() != 0
								&& requestParams.get(".w") != null) {
							keys = UserUtil.getTableGridFormCellCachedKeys((String) scd.get("projectId"),
									lookupQueryResult.getQuery().getMainTableId(), (Integer) scd.get("userId"),
									(String) scd.get("sessionId"), requestParams.get(".w"), tabId, -c.getFormCellId(),
									requestParams, true);
							if (keys != null)
								for (Object[] o : lookupQueryResult.getData())
									keys.add(GenericUtil.uInt(o[1]));
						}
					}*/
					// paramMap.clear();
				}
			} catch (Exception e) {
				throw new IWBException("framework", "Load.FormElement", rc.getFormCell().getFormCellId(), null,
						"[41," + rc.getFormCell().getFormCellId() + "]", e);
			}
	}
}

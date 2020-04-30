package iwb.domain.db;

import java.util.List;
import java.util.Map;

import iwb.util.GenericUtil;

public class W5Table implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132324122L;
	private int tableId;
	private int customizationId;
	private String dsc;
	private String summaryRecordSql;

	private short doInsertLogFlag;
	private short doUpdateLogFlag;
	private short doDeleteLogFlag;

	private short vcsFlag;

	private short accessViewTip;
	private String accessViewRoles;
	private String accessViewUsers;
	private String accessViewUserFields;
	
	private short accessInsertTip; //0:kisitlama yok, 1:var
	private String accessInsertRoles;
	private String accessInsertUsers;
	
	private short accessUpdateTip;
	private String accessUpdateRoles;
	private String accessUpdateUsers;
	private String accessUpdateUserFields;
	
	private short accessDeleteTip;
	private String accessDeleteRoles;
	private String accessDeleteUsers;
	private String accessDeleteUserFields;


	private String accessSourceTypes;

	private short makeCommentFlag;
	private int defaultUpdateFormId;
	private int defaultInsertFormId;
	private int defaultViewGridId;
	private int defaultLookupQueryId;
	private short copyTip;
	private short showFeedTip;
	private	short _hasApprovalViewControlFlag;
	private short fileAttachmentFlag;

	private	Map<Short,W5Workflow>	_approvalMap;
	private	List<W5TableField> _tableFieldList;
	private	Map<Integer,W5TableField> _tableFieldMap;
	private	List<W5TableParam> _tableParamList;
	private	List<W5TableChild> _tableChildList;
	private	List<W5TableChild> _tableParentList;

	private	List<W5Conversion> _tableConversionList;
	private short liveSyncFlag;
	private short tableTip;

	
	public W5Table() {
	}

	//@Id
	//@Column(name="table_id")
	public int getTableId() {
		return this.tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	//@Column(name="do_insert_log_flag")
	public short getDoInsertLogFlag() {
		return doInsertLogFlag;
	}

	public void setDoInsertLogFlag(short doInsertLogFlag) {
		this.doInsertLogFlag = doInsertLogFlag;
	}

	//@Column(name="do_update_log_flag")
	public short getDoUpdateLogFlag() {
		return doUpdateLogFlag;
	}

	public void setDoUpdateLogFlag(short doUpdateLogFlag) {
		this.doUpdateLogFlag = doUpdateLogFlag;
	}

	//@Column(name="do_delete_log_flag")
	public short getDoDeleteLogFlag() {
		return doDeleteLogFlag;
	}

	public void setDoDeleteLogFlag(short doDeleteLogFlag) {
		this.doDeleteLogFlag = doDeleteLogFlag;
	}

	//@Transient
	public List<W5TableField> get_tableFieldList() {
		return _tableFieldList;
	}

	public void set_tableFieldList(List<W5TableField> tableFieldList) {
		_tableFieldList = tableFieldList;
	}

	//@Transient
	public List<W5TableParam> get_tableParamList() {
		return _tableParamList;
	}

	public void set_tableParamList(List<W5TableParam> tableParamList) {
		_tableParamList = tableParamList;
	}
	

	//@Column(name="make_comment_flag")
	public short getMakeCommentFlag() {
		return makeCommentFlag;
	}
	public void setMakeCommentFlag(short makeCommentFlag) {
		this.makeCommentFlag = makeCommentFlag;
	}

	//@Transient
	public Map<Integer, W5TableField> get_tableFieldMap() {
		return _tableFieldMap;
	}

	public void set_tableFieldMap(Map<Integer, W5TableField> tableFieldMap) {
		_tableFieldMap = tableFieldMap;
	}
	//@Transient
	public short get_hasApprovalViewControlFlag() {
		return _hasApprovalViewControlFlag;
	}
	public void set_hasApprovalViewControlFlag(short hasApprovalViewControlFlag) {
		_hasApprovalViewControlFlag = hasApprovalViewControlFlag;
	}
	

	//@Column(name="default_update_form_id")
	public int getDefaultUpdateFormId() {
		return defaultUpdateFormId;
	}

	public void setDefaultUpdateFormId(int defaultUpdateFormId) {
		this.defaultUpdateFormId = defaultUpdateFormId;
	}


	//@Column(name="copy_tip")
	public short getCopyTip() {
		return copyTip;
	}

	public void setCopyTip(short copyTip) {
		this.copyTip = copyTip;
	}
	//@Transient
	public List<W5TableChild> get_tableChildList() {
		return _tableChildList;
	}

	public void set_tableChildList(List<W5TableChild> tableChildList) {
		_tableChildList = tableChildList;
	}

	//@Transient
	public List<W5TableChild> get_tableParentList() {
		return _tableParentList;
	}

	public void set_tableParentList(List<W5TableChild> _tableParentList) {
		this._tableParentList = _tableParentList;
	}

	//@Column(name="show_feed_tip")
	public short getShowFeedTip() {
		return showFeedTip;
	}

	public void setShowFeedTip(short showFeedTip) {
		this.showFeedTip = showFeedTip;
	}

	//@Column(name="summary_record_sql")
	public String getSummaryRecordSql() {
		return summaryRecordSql;
	}

	public void setSummaryRecordSql(String summaryRecordSql) {
		this.summaryRecordSql = summaryRecordSql;
	}

/*	//@Transient
	public Map<Integer, W5TableRecordHelper> get_cachedObjectMap() {
		return _cachedObjectMap;
	}

	public void set_cachedObjectMap(
			Map<Integer, W5TableRecordHelper> cachedObjectMap) {
		_cachedObjectMap = cachedObjectMap;
	}
*/	
	//@Column(name="access_view_user_fields")
	public String getAccessViewUserFields() {
		return accessViewUserFields;
	}

	public void setAccessViewUserFields(String accessViewUserFields) {
		this.accessViewUserFields = accessViewUserFields;
	}

	//@Column(name="access_update_user_fields")
	public String getAccessUpdateUserFields() {
		return accessUpdateUserFields;
	}

	public void setAccessUpdateUserFields(String accessUpdateUserFields) {
		this.accessUpdateUserFields = accessUpdateUserFields;
	}

	//@Column(name="access_delete_user_fields")
	public String getAccessDeleteUserFields() {
		return accessDeleteUserFields;
	}

	public void setAccessDeleteUserFields(String accessDeleteUserFields) {
		this.accessDeleteUserFields = accessDeleteUserFields;
	}



	//@Transient
	public List<W5Conversion> get_tableConversionList() {
		return _tableConversionList;
	}

	public void set_tableConversionList(List<W5Conversion> tableConversionList) {
		_tableConversionList = tableConversionList;
	}
	//@Column(name="default_insert_form_id")
	public int getDefaultInsertFormId() {
		return defaultInsertFormId;
	}

	public void setDefaultInsertFormId(int defaultInsertFormId) {
		this.defaultInsertFormId = defaultInsertFormId;
	}
	//@Column(name="default_view_grid_id")
	public int getDefaultViewGridId() {
		return defaultViewGridId;
	}

	public void setDefaultViewGridId(int defaultViewGridId) {
		this.defaultViewGridId = defaultViewGridId;
	}
	//@Column(name="default_lookup_query_id")
	public int getDefaultLookupQueryId() {
		return defaultLookupQueryId;
	}

	public void setDefaultLookupQueryId(int defaultLookupQueryId) {
		this.defaultLookupQueryId = defaultLookupQueryId;
	}

	//@Column(name="file_attachment_flag")
	public short getFileAttachmentFlag() {
		return fileAttachmentFlag;
	}

	public void setFileAttachmentFlag(short fileAttachmentFlag) {
		this.fileAttachmentFlag = fileAttachmentFlag;
	}
	
	//@Transient
	public Map<Short, W5Workflow> get_approvalMap() {
		return _approvalMap;
	}
	public void set_approvalMap(Map<Short, W5Workflow> approvalMap) {
		_approvalMap = approvalMap;
	}
	
	//@Column(name="access_view_tip")
	public short getAccessViewTip() {
		return accessViewTip;
	}

	//@Column(name="access_view_roles")
	public String getAccessViewRoles() {
		return accessViewRoles;
	}

	//@Column(name="access_view_users")
	public String getAccessViewUsers() {
		return accessViewUsers;
	}

	public void setAccessViewTip(short accessViewTip) {
		this.accessViewTip = accessViewTip;
	}

	public void setAccessViewRoles(String accessViewRoles) {
		this.accessViewRoles = accessViewRoles;
    }
	
	public void setAccessViewUsers(String accessViewUsers) {
		this.accessViewUsers = accessViewUsers;
	}
	
	//@Column(name="access_insert_tip")
	public short getAccessInsertTip() {
		return accessInsertTip;
	}
	public void setAccessInsertTip(short accessInsertTip) {
		this.accessInsertTip = accessInsertTip;
	}
	//@Column(name="access_insert_roles")
	public String getAccessInsertRoles() {
		return accessInsertRoles;
	}
	public void setAccessInsertRoles(String accessInsertRoles) {
		this.accessInsertRoles = accessInsertRoles;
	}
	//@Column(name="access_insert_users")
	public String getAccessInsertUsers() {
		return accessInsertUsers;
	}
	public void setAccessInsertUsers(String accessInsertUsers) {
		this.accessInsertUsers = accessInsertUsers;
	}
	//@Column(name="access_update_tip")
	public short getAccessUpdateTip() {
		return accessUpdateTip;
	}
	public void setAccessUpdateTip(short accessUpdateTip) {
		this.accessUpdateTip = accessUpdateTip;
	}
	//@Column(name="access_update_roles")
	public String getAccessUpdateRoles() {
		return accessUpdateRoles;
	}
	public void setAccessUpdateRoles(String accessUpdateRoles) {
		this.accessUpdateRoles = accessUpdateRoles;
	}
	//@Column(name="access_update_users")
	public String getAccessUpdateUsers() {
		return accessUpdateUsers;
	}
	public void setAccessUpdateUsers(String accessUpdateUsers) {
		this.accessUpdateUsers = accessUpdateUsers;
	}
	
	//@Column(name="access_delete_tip")
	public short getAccessDeleteTip() {
		return accessDeleteTip;
	}
	public void setAccessDeleteTip(short accessDeleteTip) {
		this.accessDeleteTip = accessDeleteTip;
	}
	//@Column(name="access_delete_roles")
	public String getAccessDeleteRoles() {
		return accessDeleteRoles;
	}
	public void setAccessDeleteRoles(String accessDeleteRoles) {
		this.accessDeleteRoles = accessDeleteRoles;
	}
	//@Column(name="access_delete_users")
	public String getAccessDeleteUsers() {
		return accessDeleteUsers;
	}
	public void setAccessDeleteUsers(String accessDeleteUsers) {
		this.accessDeleteUsers = accessDeleteUsers;
	}
	
	

	//@Column(name="live_sync_flag")
	public short getLiveSyncFlag() {
		return liveSyncFlag;
	}

	public void setLiveSyncFlag(short liveSyncFlag) {
		this.liveSyncFlag = liveSyncFlag;
	}

	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5Table t = (W5Table)q;
		boolean b = 	this.tableId==t.getTableId() &&
			GenericUtil.safeEquals(this.dsc,t.getDsc()) &&
			GenericUtil.safeEquals(this.summaryRecordSql,t.getSummaryRecordSql()) &&
	
			this.doInsertLogFlag==t.getDoInsertLogFlag() &&
			this.doUpdateLogFlag==t.getDoUpdateLogFlag() &&
			this.doDeleteLogFlag==t.getDoDeleteLogFlag() &&
	
			this.accessViewTip==t.getAccessViewTip() &&
			GenericUtil.safeEquals(this.accessViewRoles,t.getAccessViewRoles()) &&
			GenericUtil.safeEquals(this.accessViewUsers,t.getAccessViewUsers()) &&
			GenericUtil.safeEquals(this.accessViewUserFields,t.getAccessViewUserFields()) &&
			
			this.accessInsertTip==t.getAccessInsertTip() && //0:no restriction
			GenericUtil.safeEquals(this.accessInsertRoles,t.getAccessInsertRoles()) &&
			GenericUtil.safeEquals(this.accessInsertUsers,t.getAccessInsertUsers()) &&
			
			this.accessUpdateTip==t.getAccessUpdateTip() &&
			GenericUtil.safeEquals(this.accessUpdateRoles,t.getAccessUpdateRoles()) &&
			GenericUtil.safeEquals(this.accessUpdateUsers,t.getAccessUpdateUsers()) &&
			GenericUtil.safeEquals(this.accessUpdateUserFields,t.getAccessUpdateUserFields()) &&
			
			this.accessDeleteTip==t.getAccessDeleteTip() &&
			GenericUtil.safeEquals(this.accessDeleteRoles,t.getAccessDeleteRoles()) &&
			GenericUtil.safeEquals(this.accessDeleteUsers,t.getAccessDeleteUsers()) &&
			GenericUtil.safeEquals(this.accessDeleteUserFields,t.getAccessDeleteUserFields()) &&
	
			
	
			this.makeCommentFlag==t.getMakeCommentFlag() &&
			this.defaultUpdateFormId==t.getDefaultUpdateFormId() &&
			this.defaultInsertFormId==t.getDefaultInsertFormId() &&
			this.defaultViewGridId==t.getDefaultViewGridId() &&
			this.defaultLookupQueryId==t.getDefaultLookupQueryId() &&
			this.copyTip==t.getCopyTip() &&
			this.showFeedTip==t.getShowFeedTip() &&
			this.fileAttachmentFlag==t.getFileAttachmentFlag();
		
		if(!b)return false;
		
		if(!GenericUtil.safeEquals(this._tableFieldList, t._tableFieldList))return false;
		/*if(this._tableFieldList!=null && t.get_tableFieldList()!=null){
			if(this._tableFieldList.size()==t.get_tableFieldList().size())return false;
			for(int i=0;i<this._tableFieldList.size();i++){
				W5TableField c1 = this._tableFieldList.get(i); 
				W5TableField c2 = t.get_tableFieldList().get(i);
				if(!c1.equals(c2))return false;
			}			
		} else if(this._tableFieldList!=null ^ t.get_tableFieldList()!=null) return false;*/
		
		
//		if(!GenericUtil.safeEquals(this._tableFilterList, t._tableFilterList))return false;
		/*if(this._tableFilterList!=null && t.get_tableFilterList()!=null){
			if(this._tableFilterList.size()==t.get_tableFilterList().size())return false;
			for(int i=0;i<this._tableFilterList.size();i++){
				W5TableFilter c1 = this._tableFilterList.get(i); 
				W5TableFilter c2 = t.get_tableFilterList().get(i);
				if(!c1.equals(c2))return false;
			}			
		} else if(this._tableFilterList!=null ^ t.get_tableFilterList()!=null) return false;*/
		
		if(!GenericUtil.safeEquals(this._tableConversionList, t._tableConversionList))return false;
		/*if(this._tableConversionList!=null && t.get_tableConversionList()!=null){
			if(this._tableConversionList.size()==t.get_tableConversionList().size())return false;
			for(int i=0;i<this._tableConversionList.size();i++){
				W5Conversion c1 = this._tableConversionList.get(i); 
				W5Conversion c2 = t.get_tableConversionList().get(i);
				if(!c1.equals(c2))return false;
			}			
		} else if(this._tableConversionList!=null ^ t.get_tableConversionList()!=null) return false;*/
		
		
		/*if(this._tableWidgetList!=null && t.get_tableWidgetList()!=null){
			if(this._tableWidgetList.size()==t.get_tableWidgetList().size())return false;
			for(int i=0;i<this._tableWidgetList.size();i++){
				W5Widget c1 = this._tableWidgetList.get(i); 
				W5Widget c2 = t.get_tableWidgetList().get(i);
				if(!c1.equals(c2))return false;
			}			
		} else if(this._tableWidgetList!=null ^ t.get_tableWidgetList()!=null) return false;*/
		
		
		return true;
	}



	//@Column(name="vcs_flag")
	public short getVcsFlag() {
		return vcsFlag;
	}

	public void setVcsFlag(short vcsFlag) {
		this.vcsFlag = vcsFlag;
	}

	
	//@Column(name="table_tip")
	public short getTableTip() {
		return tableTip;
	}

	public void setTableTip(short tableTip) {
		this.tableTip = tableTip;
	}

	//@Column(name="access_source_types")
	public String getAccessSourceTypes() {
		return accessSourceTypes;
	}

	public void setAccessSourceTypes(String accessSourceTypes) {
		this.accessSourceTypes = accessSourceTypes;
	}
	private String projectUuid;
	//@Id
	//@Column(name="project_uuid")
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	//@Column(name="customization_id")
	public int getCustomizationId() {
		return customizationId;
	}

	public void setCustomizationId(int customizationId) {
		this.customizationId = customizationId;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5Table))return false;
		W5Table c = (W5Table)o;
		return c!=null && c.getTableId()==tableId && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*tableId;
	}	
}

package iwb.domain.db;

// Generated Feb 15, 2007 7:51:41 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;

import iwb.util.GenericUtil;

public class W5TableField implements java.io.Serializable, W5Param, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 133333233L;

	private int tableFieldId;

	private int tableId;

	private String dsc;
	
	private short sourceTip;
	
	private short fieldTip;

	private short notNullFlag;

	private Short minLength;

	private Short maxLength;

	private BigDecimal minValue;

	private BigDecimal maxValue;

	private String defaultValue;

	private short tabOrder;
	private short copySourceTip;

	private short canInsertFlag;

	private short canUpdateFlag;
	private short accessViewTip; //0:kisitlama yok, 1:var
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
	
	private short accessMaskTip;
	private String accessMaskRoles;
	private String accessMaskUsers;
	private String accessMaskUserFields;
	
	private int defaultLookupTableId;
	private short defaultControlTip;
	private String relatedSessionField;
	
	private short lkpEncryptionType;

	
	public W5TableField() {
	}
	
	public W5TableField(int tableFieldId) {
		super();
		this.tableFieldId = tableFieldId;
	}

	//@Id
	//@Column(name="table_field_id")
	public int getTableFieldId() {
		return this.tableFieldId;
	}

	public void setTableFieldId(int tableFieldId) {
		this.tableFieldId = tableFieldId;
	}

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

	//@Column(name="field_tip")
	public short getFieldTip() {
		return this.fieldTip;
	}

	public void setFieldTip(short fieldTip) {
		this.fieldTip = fieldTip;
	}

	//@Column(name="min_length")
	public Short getMinLength() {
		return this.minLength;
	}

	public void setMinLength(Short minLength) {
		this.minLength = minLength;
	}

	//@Column(name="not_null_flag")
	public short getNotNullFlag() {
		return notNullFlag;
	}

	public void setNotNullFlag(short notNullFlag) {
		this.notNullFlag = notNullFlag;
	}

	//@Column(name="max_length")
	public Short getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(Short maxLength) {
		this.maxLength = maxLength;
	}

	//@Column(name="min_value")
	public BigDecimal getMinValue() {
		return this.minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	//@Column(name="max_value")
	public BigDecimal getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	//@Column(name="default_value")
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	//@Column(name="tab_order")
	public short getTabOrder() {
		return this.tabOrder;
	}



	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}

	//@Column(name="can_insert_flag")
	public short getCanInsertFlag() {
		return this.canInsertFlag;
	}

	public void setCanInsertFlag(short canInsertFlag) {
		this.canInsertFlag = canInsertFlag;
	}

	//@Column(name="can_update_flag")
	public short getCanUpdateFlag() {
		return this.canUpdateFlag;
	}

	public void setCanUpdateFlag(short canUpdateFlag) {
		this.canUpdateFlag = canUpdateFlag;
	}

	//@Column(name="source_tip")
	public short getSourceTip() {
		return this.sourceTip; //always request
	}
	//@Transient
	public short getParamTip() {
		return this.fieldTip;
	}

	public void setSourceTip(short sourceTip) {
		this.sourceTip = sourceTip;
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


	//@Column(name="copy_source_tip")
	public short getCopySourceTip() {
		return copySourceTip;
	}

	public void setCopySourceTip(short copySourceTip) {
		this.copySourceTip = copySourceTip;
	}

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
	

	//@Column(name="default_lookup_table_id")
	public int getDefaultLookupTableId() {
		return defaultLookupTableId;
	}

	public void setDefaultLookupTableId(int defaultLookupTableId) {
		this.defaultLookupTableId = defaultLookupTableId;
	}
	//@Column(name="default_control_tip")
	public short getDefaultControlTip() {
		return defaultControlTip;
	}

	public void setDefaultControlTip(short defaultControlTip) {
		this.defaultControlTip = defaultControlTip;
	}
	
	


	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5TableField t = (W5TableField)q;
		return
			this.tableFieldId==t.getTableFieldId() &&

			GenericUtil.safeEquals(this.dsc,t.getDsc()) &&
			
			this.sourceTip==t.getSourceTip() &&
			
			this.fieldTip==t.getFieldTip() &&
	
			this.notNullFlag==t.getNotNullFlag() &&
	
			this.minLength==t.getMinLength() &&
	
			this.maxLength==t.getMaxLength() &&
	
	//		private BigDecimal minValue &&
	
	//		private BigDecimal maxValue &&
	
			GenericUtil.safeEquals(this.defaultValue,t.getDefaultValue()) &&
	
			this.tabOrder==t.getTabOrder() &&
			this.copySourceTip==t.getCopySourceTip() &&
	
			this.canInsertFlag==t.getCanInsertFlag() &&
	
			this.canUpdateFlag==t.getCanUpdateFlag() &&
			
			this.accessViewTip==t.getAccessViewTip() &&
			GenericUtil.safeEquals(this.accessViewRoles,t.getAccessViewRoles()) &&
			GenericUtil.safeEquals(this.accessViewUsers,t.getAccessViewUsers()) &&
			GenericUtil.safeEquals(this.accessViewUserFields,t.getAccessViewUserFields()) &&
	
			this.accessInsertTip==t.getAccessInsertTip() && //0:kisitlama yok, 1:var
			GenericUtil.safeEquals(this.accessInsertRoles,t.getAccessInsertRoles()) &&
			GenericUtil.safeEquals(this.accessInsertUsers,t.getAccessInsertUsers()) &&
	
			this.accessUpdateTip==t.getAccessUpdateTip() &&
			GenericUtil.safeEquals(this.accessUpdateRoles,t.getAccessUpdateRoles()) &&
			GenericUtil.safeEquals(this.accessUpdateUsers,t.getAccessUpdateUsers()) &&
			GenericUtil.safeEquals(this.accessUpdateUserFields,t.getAccessUpdateUserFields()) &&
			
		
			this.defaultLookupTableId==t.getDefaultLookupTableId() &&
			this.defaultControlTip==t.getDefaultControlTip();
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

	//@Column(name="related_session_field")
	public String getRelatedSessionField() {
		return relatedSessionField;
	}

	public void setRelatedSessionField(String relatedSessionField) {
		this.relatedSessionField = relatedSessionField;
	}

	//@Column(name="access_mask_tip")
	public short getAccessMaskTip() {
		return accessMaskTip;
	}

	public void setAccessMaskTip(short accessMaskTip) {
		this.accessMaskTip = accessMaskTip;
	}

	//@Column(name="access_mask_roles")
	public String getAccessMaskRoles() {
		return accessMaskRoles;
	}

	public void setAccessMaskRoles(String accessMaskRoles) {
		this.accessMaskRoles = accessMaskRoles;
	}

	//@Column(name="access_mask_users")
	public String getAccessMaskUsers() {
		return accessMaskUsers;
	}

	public void setAccessMaskUsers(String accessMaskUsers) {
		this.accessMaskUsers = accessMaskUsers;
	}

	//@Column(name="access_mask_user_fields")
	public String getAccessMaskUserFields() {
		return accessMaskUserFields;
	}

	public void setAccessMaskUserFields(String accessMaskUserFields) {
		this.accessMaskUserFields = accessMaskUserFields;
	}

	//@Column(name="lkp_encryption_type")
	public short getLkpEncryptionType() {
		return lkpEncryptionType;
	}

	public void setLkpEncryptionType(short lkpEncryptionType) {
		this.lkpEncryptionType = lkpEncryptionType;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5TableField))return false;
		W5TableField c = (W5TableField)o;
		return c!=null && c.getTableFieldId()==tableFieldId && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*tableFieldId;
	}
	
	
	//@Transient
	public int getParentId() {
		return 0;
	}
}

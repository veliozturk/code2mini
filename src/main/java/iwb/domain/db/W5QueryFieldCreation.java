package iwb.domain.db;

public class W5QueryFieldCreation implements java.io.Serializable {

	private int queryFieldId;
		
	private int queryId;
	private int mainTableFieldId;

	private String dsc;

	private short fieldTip;

	private short tabOrder;
	
	private short versionNo;
	private	int insertUserId;
	private	int versionUserId;
	private	java.sql.Timestamp versionDttm;
	
	private short postProcessTip;
	private int customizationId;
	private String projectUuid;
	private String oprojectUuid;
	private int lookupQueryId;
	
	//@Column(name="version_no")
	public short getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(short versionNo) {
		this.versionNo = versionNo;
	}

	//@Column(name="insert_user_id")
	public int getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}

	//@Column(name="version_user_id")
	public int getVersionUserId() {
		return versionUserId;
	}

	public void setVersionUserId(int versionUserId) {
		this.versionUserId = versionUserId;
	}

	//@Column(name="version_dttm")
	public java.sql.Timestamp getVersionDttm() {
		return versionDttm;
	}

	public void setVersionDttm(java.sql.Timestamp versionDttm) {
		this.versionDttm = versionDttm;
	}

	public W5QueryFieldCreation() {
	}
	//@Id
	//@Column(name="query_field_id")
	public int getQueryFieldId() {
		return queryFieldId;
	}

	//@Column(name="query_id")
	public int getQueryId() {
		return queryId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}

	//@Column(name="field_tip")
	public short getFieldTip() {
		return fieldTip;
	}

	//@Column(name="tab_order")
	public short getTabOrder() {
		return tabOrder;
	}


	public void setQueryFieldId(int queryFieldId) {
		this.queryFieldId = queryFieldId;
	}

	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	public void setFieldTip(short fieldTip) {
		this.fieldTip = fieldTip;
	}

	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}
	//@Column(name="post_process_tip")
	public short getPostProcessTip() {
		return postProcessTip;
	}

	public void setPostProcessTip(short postProcessTip) {
		this.postProcessTip = postProcessTip;
	}

	//@Column(name="main_table_field_id")
	public int getMainTableFieldId() {
		return mainTableFieldId;
	}

	public void setMainTableFieldId(int mainTableFieldId) {
		this.mainTableFieldId = mainTableFieldId;
	}
	
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

	//@Column(name="lookup_query_id")
	public int getLookupQueryId() {
		return lookupQueryId;
	}

	public void setLookupQueryId(int lookupQueryId) {
		this.lookupQueryId = lookupQueryId;
	}

	//@Column(name="oproject_uuid")
	public String getOprojectUuid() {
		return oprojectUuid;
	}

	public void setOprojectUuid(String oprojectUuid) {
		this.oprojectUuid = oprojectUuid;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5QueryField))return false;
		W5QueryField c = (W5QueryField)o;
		return c!=null && c.getQueryFieldId()==getQueryFieldId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getQueryFieldId();
	}	
}

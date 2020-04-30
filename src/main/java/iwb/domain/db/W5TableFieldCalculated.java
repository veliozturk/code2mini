package iwb.domain.db;

import iwb.util.GenericUtil;

public class W5TableFieldCalculated implements java.io.Serializable, W5Base {

	private int tableFieldCalculatedId;
	private int tableFieldId;

	private int tableId;
	private int relatedTableId;

	private String dsc;
	private String sqlCode;
	
	private short tabOrder;
	private short fieldTip;
	public W5TableFieldCalculated() {
	}
	
	//@Id
	//@Column(name="table_field_calculated_id")
	public int getTableFieldCalculatedId() {
		return tableFieldCalculatedId;
	}

	public void setTableFieldCalculatedId(int tableFieldCalculatedId) {
		this.tableFieldCalculatedId = tableFieldCalculatedId;
	}

	//@Column(name="related_table_id")
	public int getRelatedTableId() {
		return relatedTableId;
	}

	public void setRelatedTableId(int relatedTableId) {
		this.relatedTableId = relatedTableId;
	}

	//@Column(name="sql_code")
	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}
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


	//@Column(name="tab_order")
	public short getTabOrder() {
		return this.tabOrder;
	}



	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}
	

	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	
	//@Column(name="field_tip")
	public short getFieldTip() {
		return fieldTip;
	}

	public void setFieldTip(short fieldTip) {
		this.fieldTip = fieldTip;
	}

	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5TableFieldCalculated t = (W5TableFieldCalculated)q;
		return 	
			this.tableFieldCalculatedId==t.tableFieldCalculatedId &&
			this.tableFieldId==t.tableFieldId &&
			this.fieldTip==t.fieldTip &&
			this.tableId==t.tableId &&
			this.relatedTableId==t.relatedTableId &&
			GenericUtil.safeEquals(this.dsc, t.dsc) &&
			GenericUtil.safeEquals(this.sqlCode, t.sqlCode) &&
			this.tabOrder==t.tabOrder;
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

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5TableFieldCalculated))return false;
		W5TableFieldCalculated c = (W5TableFieldCalculated)o;
		return c!=null && c.getTableFieldCalculatedId()==tableFieldCalculatedId && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*tableFieldCalculatedId;
	}	
}

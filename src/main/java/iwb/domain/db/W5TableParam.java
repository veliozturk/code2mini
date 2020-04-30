package iwb.domain.db;

import java.math.BigDecimal;

public class W5TableParam  implements java.io.Serializable, W5Param, W5Base {

	private static final long serialVersionUID = 1343434342L;
	private int tableParamId;
	private int tableId;

	private String dsc;

	private String expressionDsc;

//	private int tableFieldId;

	private short tabOrder;
	
	private short paramTip;

	private short operatorTip;

	private short notNullFlag;

	private short sourceTip;

	private String defaultValue;

	private	BigDecimal	minValue;
	private	BigDecimal	maxValue;
	
	private	Short	minLength;
	private	Short	maxLength;
	
	
	public W5TableParam() {
	}
	

	//@Id
	//@Column(name="table_param_id")
	public int getTableParamId() {
		return tableParamId;
	}


	public void setTableParamId(int tableParamId) {
		this.tableParamId = tableParamId;
	}


	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	//@Column(name="expression_dsc")
	public String getExpressionDsc() {
		return expressionDsc;
	}

	public void setExpressionDsc(String expressionDsc) {
		this.expressionDsc = expressionDsc;
	}

	//@Column(name="param_tip")
	public short getParamTip() {
		return paramTip;
	}

	public void setParamTip(short paramTip) {
		this.paramTip = paramTip;
	}

	//@Column(name="operator_tip")
	public short getOperatorTip() {
		return operatorTip;
	}

	public void setOperatorTip(short operatorTip) {
		this.operatorTip = operatorTip;
	}

	//@Column(name="source_tip")
	public short getSourceTip() {
		return sourceTip;
	}

	public void setSourceTip(short sourceTip) {
		this.sourceTip = sourceTip;
	}

	
	//@Column(name="default_value")
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	//@Column(name="min_value")
	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	//@Column(name="max_value")
	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	//@Column(name="min_length")
	public Short getMinLength() {
		return minLength;
	}
	
	public void setMinLength(Short minLength) {
		this.minLength = minLength;
	}

	//@Column(name="max_length")
	public Short getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Short maxLength) {
		this.maxLength = maxLength;
	}

	//@Column(name="tab_order")
	public short getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}



	//@Column(name="table_id")
	public int getTableId() {
		return tableId;
	}


	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	//@Column(name="not_null_flag")
	public short getNotNullFlag() {
		return notNullFlag;
	}


	public void setNotNullFlag(short notNullFlag) {
		this.notNullFlag = notNullFlag;
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
		if(o==null || !(o instanceof W5TableParam))return false;
		W5TableParam c = (W5TableParam)o;
		return c!=null && c.getTableParamId()==tableParamId && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*tableParamId;
	}
	
	
	//@Transient
	public int getParentId() {
		return 0;
	}
	

	//@Transient
	public boolean safeEquals(W5Base q) {

			return false;
	}
}

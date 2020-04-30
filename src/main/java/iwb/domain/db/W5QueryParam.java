package iwb.domain.db;

import java.math.BigDecimal;

public class W5QueryParam implements java.io.Serializable, W5Param, W5Base {

	private static final long serialVersionUID = 12226666333L;


	private int queryParamId;
	
	
	private int queryId;

	private String dsc;

	private String expressionDsc;

	private short tabOrder;
	
	private short paramTip;

	private short operatorTip;

	private short notNullFlag;

	private short sourceTip;

	private String defaultValue;

	private int relatedTableFieldId;
	
	private	BigDecimal	minValue;
	private	BigDecimal	maxValue;
	
	private	Short	minLength;
	private	Short	maxLength;
	public W5QueryParam() {
	}


	//@Id
	//@Column(name="query_param_id")
	public int getQueryParamId() {
		return queryParamId;
	}


	//@Column(name="query_id")
	public int getQueryId() {
		return queryId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}

	//@Column(name="expression_dsc")
	public String getExpressionDsc() {
		return expressionDsc;
	}

	//@Column(name="tab_order")
	public short getTabOrder() {
		return tabOrder;
	}

	//@Column(name="param_tip")
	public short getParamTip() {
		return paramTip;
	}

	//@Column(name="operator_tip")
	public short getOperatorTip() {
		return operatorTip;
	}

	//@Column(name="source_tip")
	public short getSourceTip() {
		return sourceTip;
	}

	//@Column(name="default_value")
	public String getDefaultValue() {
		return defaultValue;
	}

	//@Column(name="min_value")
	public BigDecimal getMinValue() {
		return minValue;
	}

	//@Column(name="max_value")
	public BigDecimal getMaxValue() {
		return maxValue;
	}

	//@Column(name="min_length")
	public Short getMinLength() {
		return minLength;
	}

	//@Column(name="max_length")
	public Short getMaxLength() {
		return maxLength;
	}

	//@Column(name="not_null_flag")
	public short getNotNullFlag() {
		return notNullFlag;
	}

	public void setQueryParamId(int queryParamId) {
		this.queryParamId = queryParamId;
	}


	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	public void setExpressionDsc(String expressionDsc) {
		this.expressionDsc = expressionDsc;
	}

	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}

	public void setParamTip(short paramTip) {
		this.paramTip = paramTip;
	}

	public void setOperatorTip(short operatorTip) {
		this.operatorTip = operatorTip;
	}

	public void setNotNullFlag(short notNullFlag) {
		this.notNullFlag = notNullFlag;
	}

	public void setSourceTip(short sourceTip) {
		this.sourceTip = sourceTip;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinLength(Short minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(Short maxLength) {
		this.maxLength = maxLength;
	}

	//@Column(name="related_table_field_id")
	public int getRelatedTableFieldId() {
		return relatedTableFieldId;
	}

	public void setRelatedTableFieldId(int relatedTableFieldId) {
		this.relatedTableFieldId = relatedTableFieldId;
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
		if(o==null || !(o instanceof W5QueryParam))return false;
		W5QueryParam c = (W5QueryParam)o;
		return c!=null && c.getQueryParamId()==getQueryParamId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getQueryParamId();
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

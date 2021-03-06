package iwb.domain.db;

// Generated Feb 25, 2007 1:41:05 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;



public class W5GlobalFuncParam implements java.io.Serializable, W5Param, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 193784239632947L;

	private int dbFuncParamId;

	private int dbFuncId;

	private String dsc;

	private short paramTip;

	private short tabOrder;

	private short notNullFlag;

	private String defaultValue;

	private Short minLength;

	private Short maxLength;

	private BigDecimal minValue;

	private BigDecimal maxValue;
	
	private short sourceTip;

	private short outFlag;
	
	
	
	//@Column(name="out_flag")
	public short getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(short outFlag) {
		this.outFlag = outFlag;
	}

	public W5GlobalFuncParam() {
	}

	//@Id
	//@Column(name="db_func_param_id")
	public int getDbFuncParamId() {
		return this.dbFuncParamId;
	}

	public void setDbFuncParamId(int dbFuncParamId) {
		this.dbFuncParamId = dbFuncParamId;
	}

	//@Column(name="db_func_id")
	public int getDbFuncId() {
		return this.dbFuncId;
	}

	public void setDbFuncId(int dbFuncId) {
		this.dbFuncId = dbFuncId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	//@Column(name="param_tip")
	public short getParamTip() {
		return this.paramTip;
	}
	

	public void setParamTip(short paramTip) {
		this.paramTip = paramTip;
	}

	//@Column(name="tab_order")
	public short getTabOrder() {
		return this.tabOrder;
	}

	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}

	//@Column(name="default_value")
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	//@Column(name="min_length")
	public Short getMinLength() {
		return this.minLength;
	}

	public void setMinLength(Short minLength) {
		this.minLength = minLength;
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


	//@Column(name="source_tip")
	public short getSourceTip() {
		return sourceTip;
	}

	public void setSourceTip(short sourceTip) {
		this.sourceTip = sourceTip;
	}

	//@Column(name="not_null_flag")
	public short getNotNullFlag() {
		return notNullFlag;
	}

	public void setNotNullFlag(short notNullFlag) {
		this.notNullFlag = notNullFlag;
	}

	public W5GlobalFuncParam(String dsc) {
		super();
		this.dsc = dsc;
		this.outFlag=(short)1;
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
		if(o==null || !(o instanceof W5GlobalFuncParam))return false;
		W5GlobalFuncParam c = (W5GlobalFuncParam)o;
		return c!=null && c.getDbFuncParamId()==getDbFuncParamId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getDbFuncParamId();
	}
	
	
	//@Transient
	public int getParentId() {
		return 0;
	}
	
	public boolean safeEquals(W5Base q) {
		return false;
	}
	
}

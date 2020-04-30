package iwb.domain.db;

import java.math.BigDecimal;



public class W5WsServerMethodParam  implements java.io.Serializable, W5Param, W5Base {

	private int wsServerMethodParamId;
	private int wsServerMethodId;
	private String dsc;
	private short paramTip;
	private short outFlag;
	private short notNullFlag;
	private short tabOrder;
	private short sourceTip;
	private int objectDetailId;

	private String defaultValue;
	private	BigDecimal	minValue;
	private	BigDecimal	maxValue;
	
	private	Short	minLength;
	private	Short	maxLength;
	private int parentWsMethodParamId;

	public W5WsServerMethodParam() {
		super();
	}

	public W5WsServerMethodParam(W5Param p, short outFlag, int parentWsMethodParamId) {
		super();
		this.dsc = p.getDsc();
		this.paramTip = p.getParamTip();
		this.outFlag = outFlag;
		this.sourceTip = p.getSourceTip();
		this.notNullFlag = p.getNotNullFlag();
		this.minValue = p.getMinValue();
		this.maxValue = p.getMaxValue();
		this.defaultValue = p.getDefaultValue();
		this.parentWsMethodParamId = parentWsMethodParamId;
	}
	
	public W5WsServerMethodParam(int wsServerMethodParamId, String dsc, short paramTip) {
		super();
		this.wsServerMethodParamId = wsServerMethodParamId;
		this.dsc = dsc;
		this.paramTip = paramTip;
		this.outFlag = (short)1;
	}
	
	public int getWsServerMethodParamId() {
		return wsServerMethodParamId;
	}
	public void setWsServerMethodParamId(int wsServerMethodParamId) {
		this.wsServerMethodParamId = wsServerMethodParamId;
	}
	public short getParamTip() {
		return paramTip;
	}
	public void setParamTip(short paramTip) {
		this.paramTip = paramTip;
	}
	public int getWsServerMethodId() {
		return wsServerMethodId;
	}
	public void setWsServerMethodId(int wsServerMethodId) {
		this.wsServerMethodId = wsServerMethodId;
	}

	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	
	public short getOutFlag() {
		return outFlag;
	}
	public void setOutFlag(short outFlag) {
		this.outFlag = outFlag;
	}
	
	public short getNotNullFlag() {
		return notNullFlag;
	}
	public void setNotNullFlag(short notNullFlag) {
		this.notNullFlag = notNullFlag;
	}
	
	public short getTabOrder() {
		return tabOrder;
	}
	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public Short getMinLength() {
		return minLength;
	}
	
	public void setMinLength(Short minLength) {
		this.minLength = minLength;
	}

	public Short getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(Short maxLength) {
		this.maxLength = maxLength;
	}
	public short getSourceTip() {
		return sourceTip;
	}

	public void setSourceTip(short sourceTip) {
		this.sourceTip = sourceTip;
	}

	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public int getParentWsMethodParamId() {
		return parentWsMethodParamId;
	}
	public void setParentWsMethodParamId(int parentWsMethodParamId) {
		this.parentWsMethodParamId = parentWsMethodParamId;
	}
	
	public int getObjectDetailId() {
		return objectDetailId;
	}
	public void setObjectDetailId(int objectDetailId) {
		this.objectDetailId = objectDetailId;
	}

	private String projectUuid;
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5WsServerMethodParam))return false;
		W5WsServerMethodParam c = (W5WsServerMethodParam)o;
		return c!=null && c.getWsServerMethodParamId()==getWsServerMethodParamId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getWsServerMethodParamId();
	}
	
	
	public int getParentId() {
		return parentWsMethodParamId;
	}


	public boolean safeEquals(W5Base q) {

			return false;
	}
}

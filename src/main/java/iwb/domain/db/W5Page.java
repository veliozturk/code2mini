package iwb.domain.db;

import java.util.List;

import iwb.util.GenericUtil;


public class W5Page implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 223312314121L;

	private int templateId;
	
	private short templateTip;

	private String dsc;

	private int objectId;

	private short objectTip;

	private short localeMsgFlag;

	private String code;
	private String cssCode;
	private int versionNo;

	private List<W5PageObject> _pageObjectList;
	
	public W5Page() {
	}

	//@Id
	//@Column(name="template_id")
	public int getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	//@Column(name="template_tip")
	public short getTemplateTip() {
		return this.templateTip;
	}

	public void setTemplateTip(short templateTip) {
		this.templateTip = templateTip;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	//@Column(name="object_id")
	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	//@Column(name="object_tip")
	public short getObjectTip() {
		return this.objectTip;
	}

	public void setObjectTip(short objectTip) {
		this.objectTip = objectTip;
	}


	//@Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	//@Transient
	public List<W5PageObject> get_pageObjectList() {
		return _pageObjectList;
	}

	public void set_pageObjectList(List<W5PageObject> templateObjectList) {
		_pageObjectList = templateObjectList;
	}
	
	//@Column(name="locale_msg_flag")
	public short getLocaleMsgFlag() {
		return localeMsgFlag;
	}

	public void setLocaleMsgFlag(short localeMsgFlag) {
		this.localeMsgFlag = localeMsgFlag;
	}


	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5Page t = (W5Page)q;
		boolean b =  
			this.templateId == t.getTemplateId() &&
			this.templateTip == t.getTemplateTip() &&
			GenericUtil.safeEquals(this.dsc, t.getDsc()) &&
			this.objectId == t.getObjectId() &&
			this.objectTip == t.getObjectTip() &&
			this.localeMsgFlag == t.getLocaleMsgFlag() &&
			GenericUtil.safeEquals(this.code, t.getCode());
		
		if(!b)return false;
		if(!GenericUtil.safeEquals(this._pageObjectList, t._pageObjectList))return false;
/*		if(this._pageObjectList!=null && t.get_pageObjectList()!=null){
			if(this._pageObjectList.size()==t.get_pageObjectList().size())return false;
			for(int i=0;i<this._pageObjectList.size();i++){
				W5TemplateObject c1 = this._pageObjectList.get(i); 
				W5TemplateObject c2 = t.get_pageObjectList().get(i);
				if(!c1.equals(c2))return false;
			}			
		} else if(this._pageObjectList!=null ^ t.get_pageObjectList()!=null) return false;*/
		
		return true;
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

	//@Column(name="css_code")
	public String getCssCode() {
		return cssCode;
	}

	public void setCssCode(String cssCode) {
		this.cssCode = cssCode;
	}

	//@Column(name="version_no")
	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5Page))return false;
		W5Page c = (W5Page)o;
		return c!=null && c.getTemplateId()==getTemplateId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getTemplateId();
	}	
}

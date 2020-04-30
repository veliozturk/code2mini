package iwb.domain.db;

// Generated Feb 5, 2007 3:58:07 PM by Hibernate Tools 3.2.0.b9

public class W5ListColumn implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 133823734743L;


	private int listColumnId;


	private int queryFieldId;

	private int listId;
	
	private String localeMsgKey;

	private short tabOrder;

	private short visibleFlag;

	private short width;

	private String template;
	
	private	short alignTip;

	private	short sortableFlag;
	

	private String extraDefinition;

	private W5QueryField _queryField;
	
	public W5ListColumn() {
	}

	public W5ListColumn(int queryFieldId, int listId, String projectUuid) {
		this.queryFieldId = queryFieldId;
		this.listId = listId;
		this.projectUuid = projectUuid;

	}

	public W5ListColumn(int queryFieldId, int listId, String projectUuid,
			String localeMsgKey, short tabOrder, short visibleFlag,
			short width, String renderer, short alignTip,
			 String extraDefinition) {
		this.queryFieldId = queryFieldId;
		this.listId = listId;
		this.projectUuid = projectUuid;
		this.localeMsgKey = localeMsgKey;
		this.tabOrder = tabOrder;
		this.visibleFlag = visibleFlag;
		this.width = width;
		this.template = renderer;
		this.alignTip = alignTip;
		this.extraDefinition = extraDefinition;
	}


	//@Transient
	public W5QueryField get_queryField() {
		return _queryField;
	}

	public void set_queryField(W5QueryField field) {
		_queryField = field;
	}

	//@Id
	//@Column(name="list_column_id")
	public int getListColumnId() {
		return listColumnId;
	}

	public void setListColumnId(int listColumnId) {
		this.listColumnId = listColumnId;
	}
	
	//@Column(name="query_field_id")
	public int getQueryFieldId() {
		return queryFieldId;
	}

	public void setQueryFieldId(int queryFieldId) {
		this.queryFieldId = queryFieldId;
	}

	//@Column(name="list_id")
	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	//@Column(name="locale_msg_key")
	public String getLocaleMsgKey() {
		return this.localeMsgKey;
	}

	public void setLocaleMsgKey(String localeMsgKey) {
		this.localeMsgKey = localeMsgKey;
	}

	//@Column(name="tab_order")
	public short getTabOrder() {
		return this.tabOrder;
	}

	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}

	//@Column(name="width")
	public short getWidth() {
		return this.width;
	}

	public void setWidth(short width) {
		this.width = width;
	}
	
	//@Column(name="template")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String renderer) {
		this.template = renderer;
	}

	//@Column(name="align_tip")
	public short getAlignTip() {
		return alignTip;
	}

	public void setAlignTip(short alignTip) {
		this.alignTip = alignTip;
	}	
/*
	//@Column(name="control_tip")
	public short getControlTip() {
		return controlTip;
	}

	public void setControlTip(short controlTip) {
		this.controlTip = controlTip;
	}

	//@Column(name="lookup_query_id")
	public int getLookupQueryId() {
		return lookupQueryId;
	}

	public void setLookupQueryId(int lookupQueryId) {
		this.lookupQueryId = lookupQueryId;	
	}
*/

	
	//@Column(name="visible_flag")
	public short getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(short visibleFlag) {
		this.visibleFlag = visibleFlag;
	}


	//@Column(name="extra_definition")
	public String getExtraDefinition() {
		return extraDefinition;
	}

	public void setExtraDefinition(String extraDefinition) {
		this.extraDefinition = extraDefinition;
	}

	//@Column(name="sortable_flag")
	public short getSortableFlag() {
		return sortableFlag;
	}

	public void setSortableFlag(short sortableFlag) {
		this.sortableFlag = sortableFlag;
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
		if(o==null || !(o instanceof W5ListColumn))return false;
		W5ListColumn c = (W5ListColumn)o;
		return c!=null && c.getListColumnId()==getListColumnId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getListColumnId();
	}	

	//@Transient
	public boolean safeEquals(W5Base q) {

			return false;
	}
}

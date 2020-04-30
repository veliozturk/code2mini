package iwb.domain.db;

import iwb.util.GenericUtil;

// Generated Feb 5, 2007 3:58:07 PM by Hibernate Tools 3.2.0.b9

public class W5GridColumn implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9876543215671L;

	private int gridColumnId;

	private int queryFieldId;

	private int gridId;
	
	private String localeMsgKey;

	private short tabOrder;

	private short visibleFlag;

	private short width;

	private String renderer;
	
	private	short sortableFlag;
	
	private	short alignTip;
	
	private	short filterFlag;
	
//	private short controlTip;

//	private int lookupQueryId;
	
	private String extraDefinition;

	private W5QueryField _queryField;
	private int gridModuleId;
	private int formCellId;

	private W5FormCell _formCell;
	
	public W5GridColumn() {
	}

	public W5GridColumn(int queryFieldId, int gridId, String projectUuid) {
		this.queryFieldId = queryFieldId;
		this.gridId = gridId;
		this.projectUuid = projectUuid;

	}

	public W5GridColumn(int gridColumnId, int queryFieldId, int gridId, String projectUuid,
			String localeMsgKey, short tabOrder, short visibleFlag,
			short width, String renderer, short sortableFlag, short alignTip,
			short filterFlag, String extraDefinition, int gridModuleId,
			int formCellId) {
		this.gridColumnId = gridColumnId;
		this.queryFieldId = queryFieldId;
		this.gridId = gridId;
		this.projectUuid = projectUuid;
		this.localeMsgKey = localeMsgKey;
		this.tabOrder = tabOrder;
		this.visibleFlag = visibleFlag;
		this.width = width;
		this.renderer = renderer;
		this.sortableFlag = sortableFlag;
		this.alignTip = alignTip;
		this.filterFlag = filterFlag;
		this.extraDefinition = extraDefinition;
		this.gridModuleId = gridModuleId;
		this.formCellId = formCellId;
	}


	//@Transient
	public W5QueryField get_queryField() {
		return _queryField;
	}

	public void set_queryField(W5QueryField field) {
		_queryField = field;
	}

	
	//@Id
	//@Column(name="grid_column_id")
	public int getGridColumnId() {
		return gridColumnId;
	}

	public void setGridColumnId(int gridColumnId) {
		this.gridColumnId = gridColumnId;
	}

	//@Column(name="query_field_id")
	public int getQueryFieldId() {
		return queryFieldId;
	}

	public void setQueryFieldId(int queryFieldId) {
		this.queryFieldId = queryFieldId;
	}

	//@Column(name="grid_id")
	public int getGridId() {
		return gridId;
	}

	public void setGridId(int gridId) {
		this.gridId = gridId;
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
	
	//@Column(name="renderer")
	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
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


	//@Column(name="filter_flag")
	public short getFilterFlag() {
		return filterFlag;
	}
	
	public void setFilterFlag(short filterFlag) {
		this.filterFlag = filterFlag;
	}
	
	//@Column(name="visible_flag")
	public short getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(short visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	//@Column(name="sortable_flag")
	public short getSortableFlag() {
		return sortableFlag;
	}

	public void setSortableFlag(short sortableFlag) {
		this.sortableFlag = sortableFlag;
	}

	//@Column(name="extra_definition")
	public String getExtraDefinition() {
		return extraDefinition;
	}

	public void setExtraDefinition(String extraDefinition) {
		this.extraDefinition = extraDefinition;
	}

	//@Column(name="grid_module_id")
	public int getGridModuleId() {
		return gridModuleId;
	}

	public void setGridModuleId(int gridModuleId) {
		this.gridModuleId = gridModuleId;
	}

	//@Column(name="form_cell_id")
	public int getFormCellId() {
		return formCellId;
	}

	public void setFormCellId(int formCellId) {
		this.formCellId = formCellId;
	}

	//@Transient
	public W5FormCell get_formCell() {
		return _formCell;
	}

	public void set_formCell(W5FormCell formCell) {
		_formCell = formCell;
	}

	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5GridColumn g =(W5GridColumn)q;
		return 
			this.queryFieldId==g.getQueryFieldId() &&

			this.gridId==g.getGridId() &&
			
			GenericUtil.safeEquals(this.localeMsgKey,g.getLocaleMsgKey()) &&
	
			this.tabOrder==g.getTabOrder() &&
	
			this.visibleFlag==g.getVisibleFlag() &&
	
			this.width==g.getWidth() &&
	
			GenericUtil.safeEquals(this.renderer,g.getRenderer()) &&
			
			this.sortableFlag==g.getSortableFlag() &&
			
			this.alignTip==g.getAlignTip() &&
			
			this.filterFlag==g.getFilterFlag() &&
			
	//		this.controlTip &&
	
	//		this.lookupQueryId &&
			
			GenericUtil.safeEquals(this.extraDefinition,g.getRenderer()) &&
	
			this.gridModuleId==g.getGridModuleId() &&
			this.formCellId==g.getFormCellId();
		
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
	
	public W5GridColumn clone() {
		W5GridColumn r = new W5GridColumn();
		
		r.setLocaleMsgKey(localeMsgKey);
		r.setWidth(width);
		r.setAlignTip(alignTip);		
		return r;
	}


	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5GridColumn))return false;
		W5GridColumn c = (W5GridColumn)o;
		return c!=null && c.getGridColumnId()==getGridColumnId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getGridColumnId();
	}		
		
}

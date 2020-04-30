package iwb.domain.db;

public class W5TableChild implements java.io.Serializable, W5Base {


	private static final long serialVersionUID = 8385485198231L;
	private int tableChildId;
	private short relationTip;
	private int tableId;
	private int tableFieldId;
	private int relatedTableId;
	private int relatedTableFieldId;
	private int relatedStaticTableFieldId;
	private int relatedStaticTableFieldVal;
	private short copyStrategyTip;
	private short childViewTip;
	private int childViewObjectId;
	
	//@Id
	//@Column(name="table_child_id")
	public int getTableChildId() {
		return tableChildId;
	}
	public void setTableChildId(int tableChildId) {
		this.tableChildId = tableChildId;
	}


	//@Column(name="relation_tip")
	public short getRelationTip() {
		return relationTip;
	}
	public void setRelationTip(short relationTip) {
		this.relationTip = relationTip;
	}
	//@Column(name="table_id")
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	//@Column(name="table_field_id")
	public int getTableFieldId() {
		return tableFieldId;
	}
	public void setTableFieldId(int tableFieldId) {
		this.tableFieldId = tableFieldId;
	}
	//@Column(name="related_table_id")
	public int getRelatedTableId() {
		return relatedTableId;
	}
	public void setRelatedTableId(int relatedTableId) {
		this.relatedTableId = relatedTableId;
	}
	//@Column(name="related_table_field_id")
	public int getRelatedTableFieldId() {
		return relatedTableFieldId;
	}
	public void setRelatedTableFieldId(int relatedTableFieldId) {
		this.relatedTableFieldId = relatedTableFieldId;
	}
	//@Column(name="related_static_table_field_id")
	public int getRelatedStaticTableFieldId() {
		return relatedStaticTableFieldId;
	}
	public void setRelatedStaticTableFieldId(int relatedStaticTableFieldId) {
		this.relatedStaticTableFieldId = relatedStaticTableFieldId;
	}
	//@Column(name="related_static_table_field_val")
	public int getRelatedStaticTableFieldVal() {
		return relatedStaticTableFieldVal;
	}
	public void setRelatedStaticTableFieldVal(int relatedStaticTableFieldVal) {
		this.relatedStaticTableFieldVal = relatedStaticTableFieldVal;
	}
	//@Column(name="copy_strategy_tip")
	public short getCopyStrategyTip() {
		return copyStrategyTip;
	}
	public void setCopyStrategyTip(short copyStrategyTip) {
		this.copyStrategyTip = copyStrategyTip;
	}
	//@Column(name="child_view_tip")
	public short getChildViewTip() {
		return childViewTip;
	}
	public void setChildViewTip(short childViewTip) {
		this.childViewTip = childViewTip;
	}
	//@Column(name="child_view_object_id")
	public int getChildViewObjectId() {
		return childViewObjectId;
	}
	public void setChildViewObjectId(int childViewObjectId) {
		this.childViewObjectId = childViewObjectId;
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
		if(o==null || !(o instanceof W5TableChild))return false;
		W5TableChild c = (W5TableChild)o;
		return c!=null && c.getTableChildId()==tableChildId && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*tableChildId;
	}
	

	//@Transient
	public boolean safeEquals(W5Base q) {

			return false;
	}
}

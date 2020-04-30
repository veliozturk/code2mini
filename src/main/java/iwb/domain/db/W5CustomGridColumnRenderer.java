package iwb.domain.db;

// Generated Jun 17, 2007 5:12:14 PM by Hibernate Tools 3.2.0.b9



public class W5CustomGridColumnRenderer implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9982282731231231L;
	private int customGridColumnRendererId;
	private int gridId;
	private int queryFieldId;
	private String lookupDetayVal;
	private String cssVal;

	
	//@Id
	//@Column(name="custom_grid_column_renderer_id")
	public int getCustomGridColumnRendererId() {
		return customGridColumnRendererId;
	}
	public void setCustomGridColumnRendererId(int customGridColumnRendererId) {
		this.customGridColumnRendererId = customGridColumnRendererId;
	}

	//@Column(name="grid_id")
	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	//@Column(name="lookup_detay_val")
	public String getLookupDetayVal() {
		return lookupDetayVal;
	}
	public void setLookupDetayVal(String lookupDetayVal) {
		this.lookupDetayVal = lookupDetayVal;
	}
	//@Column(name="css_val")
	public String getCssVal() {
		return cssVal;
	}
	public void setCssVal(String cssVal) {
		this.cssVal = cssVal;
	}
	//@Column(name="query_field_id")
	public int getQueryFieldId() {
		return queryFieldId;
	}
	public void setQueryFieldId(int queryFieldId) {
		this.queryFieldId = queryFieldId;
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
		if(o==null || !(o instanceof W5CustomGridColumnRenderer))return false;
		W5CustomGridColumnRenderer c = (W5CustomGridColumnRenderer)o;
		return c!=null && c.getCustomGridColumnRendererId()==getCustomGridColumnRendererId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getCustomGridColumnRendererId();
	}
	

	public boolean safeEquals(W5Base q) {
		return true;
	}
	
}

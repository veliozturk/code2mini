package iwb.domain.db;

public class W5FormValueCell implements java.io.Serializable {

	private int formCellId;
	private int formValueId;
	private String val;
	private int customizationId;
	
	//@Id
	//@Column(name="form_cell_id")
	public int getFormCellId() {
		return formCellId;
	}

	public void setFormCellId(int formCellId) {
		this.formCellId = formCellId;
	}

	//@Column(name="form_value_id")
	public int getFormValueId() {
		return formValueId;
	}

	public void setFormValueId(int formValueId) {
		this.formValueId = formValueId;
	}
	
	//@Column(name="val")
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	//@Column(name="customization_id")
	public int getCustomizationId() {
		return customizationId;
	}


	public void setCustomizationId(int customizationId) {
		this.customizationId = customizationId;
	}

	
	private String projectUuid;

	//@Column(name="project_uuid")
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}
	


}

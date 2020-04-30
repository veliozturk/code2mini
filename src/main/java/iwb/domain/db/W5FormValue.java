package iwb.domain.db;

public class W5FormValue implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18182038646400L;
	private int formId;
	private int formValueId;
	private int insertUserId;
	private String dsc;
	private int customizationId;
	
	//@Column(name="form_id")
	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

    //@SequenceGenerator(name="sex_form_value",sequenceName="iwb.seq_form_value",allocationSize=1)
	//@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sex_form_value")
	//@Column(name="form_value_id")
	public int getFormValueId() {
		return formValueId;
	}

	public void setFormValueId(int formValueId) {
		this.formValueId = formValueId;
	}

	//@Column(name="insert_user_id")
	public int getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
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

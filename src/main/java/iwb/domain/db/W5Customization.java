package iwb.domain.db;

// Generated Feb 15, 2007 2:10:19 PM by Hibernate Tools 3.2.0.b9

/**
 * WTable generated by hbm2java
 */

public class W5Customization implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 888213213211L;

	private int customizationId;

	private String dsc;
	

	public W5Customization() {
	}

	//@Column(name="dsc")
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	//@Id
	//@Column(name="customization_id")
	public int getCustomizationId() {
		return customizationId;
	}

	public void setCustomizationId(int customizationId) {
		this.customizationId = customizationId;
	}


}

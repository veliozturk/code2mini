package iwb.domain.db;

public class W5ObjectMenuItem implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1918273645132L;
	private int menuItemId;
	private short tabOrder;
	private short objectTip; // hangi tablodan geldigi: 2:table_id, 1:gridId, 3:dbFunc
	private int objectId; //gelen table'in PK'si

	
	private String imgIcon;
	
	private short accessViewTip; //0:kisitlama yok, 1:var
	private String accessViewRoles;
	private String accessViewUsers;
	private short itemTip; // duz icon, menu'lu vs.vs.
	
	private String dsc;

	private String localeMsgKey;

	private String code;
	
	public W5ObjectMenuItem() {
	}


	//@Column(name="dsc")
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	//@Column(name="locale_msg_key")
	public String getLocaleMsgKey() {
		return localeMsgKey;
	}
	public void setLocaleMsgKey(String localeMsgKey) {
		this.localeMsgKey = localeMsgKey;
	}
	//@Id
	//@Column(name="menu_item_id")
	public int getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(int menuId) {
		this.menuItemId = menuId;
	}

	//@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String url) {
		this.code = url;
	}
	//@Column(name="object_tip")
	public short getObjectTip() {
		return objectTip;
	}
	public void setObjectTip(short objectTip) {
		this.objectTip = objectTip;
	}
	//@Column(name="object_id")
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	//@Column(name="img_icon")
	public String getImgIcon() {
		return imgIcon;
	}
	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
	}


	//@Column(name="tab_order")
	public short getTabOrder() {
		return tabOrder;
	}
	public void setTabOrder(short tabOrder) {
		this.tabOrder = tabOrder;
	}
	//@Column(name="access_view_tip")
	public short getAccessViewTip() {
		return accessViewTip;
	}
	public void setAccessViewTip(short accessViewTip) {
		this.accessViewTip = accessViewTip;
	}
	//@Column(name="access_view_roles")
	public String getAccessViewRoles() {
		return accessViewRoles;
	}
	public void setAccessViewRoles(String accessViewRoles) {
		this.accessViewRoles = accessViewRoles;
	}
	//@Column(name="access_view_users")
	public String getAccessViewUsers() {
		return accessViewUsers;
	}
	public void setAccessViewUsers(String accessViewUsers) {
		this.accessViewUsers = accessViewUsers;
	}
	//@Column(name="item_tip")
	public short getItemTip() {
		return itemTip;
	}
	public void setItemTip(short itemTip) {
		this.itemTip = itemTip;
	}
	
	//@Transient
	public boolean safeEquals(W5Base q){
		if(q==null)return false;
		W5ObjectMenuItem l = (W5ObjectMenuItem)q;
		return false;
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
		if(o==null || !(o instanceof W5ObjectMenuItem))return false;
		W5ObjectMenuItem c = (W5ObjectMenuItem)o;
		return c!=null && c.getMenuItemId()==getMenuItemId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getMenuItemId();
	}	
}

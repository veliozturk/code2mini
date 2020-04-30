package iwb.domain.db;

public class M5Menu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 33331924324231L;
	private int menuId;	
	private int parentMenuId;	
	private int userTip;
	private int nodeTip;
	
	private String localeMsgKey;
	private int tabOrder;
	private String imgIcon;
	private String url;
	
	private short accessViewTip;
	private String accessViewRoles;
	private String accessViewUsers;

	
	
	private String projectUuid;
	
	//@Id
	//@Column(name="menu_id")
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	//@Column(name="parent_menu_id")
	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	//@Column(name="user_tip")
	public int getUserTip() {
		return userTip;
	}

	public void setUserTip(int userTip) {
		this.userTip = userTip;
	}

	//@Column(name="node_tip")
	public int getNodeTip() {
		return nodeTip;
	}

	public void setNodeTip(int nodeTip) {
		this.nodeTip = nodeTip;
	}

	//@Column(name="locale_msg_key")
	public String getLocaleMsgKey() {
		return localeMsgKey;
	}

	public void setLocaleMsgKey(String localeMsgKey) {
		this.localeMsgKey = localeMsgKey;
	}

	//@Column(name="tab_order")
	public int getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(int tabOrder) {
		this.tabOrder = tabOrder;
	}

	//@Column(name="img_icon")
	public String getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
	}

	//@Column(name="url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	//@Id
	//@Column(name="project_uuid")
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	public boolean equals(Object o) {
		if(o==null || !(o instanceof M5Menu))return false;
		M5Menu c = (M5Menu)o;
		return c!=null && c.getMenuId()==getMenuId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getMenuId();
	}

	public boolean safeEquals(W5Base q) {
		return false;
	}
	

}

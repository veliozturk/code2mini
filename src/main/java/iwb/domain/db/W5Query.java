package iwb.domain.db;

import java.util.List;

public class W5Query implements java.io.Serializable, W5Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1123543123L;

	private int queryId;

	private int querySourceTip;
	private int mainTableId;

	private String dsc;

	private String sqlSelect;
	
	private String sqlFrom;

	private String sqlWhere;

	private String sqlGroupby;

	private String sqlOrderby;

	private String projectUuid;

	private short queryTip;

	private short logLevelTip;

	private short showParentRecordFlag;
	private short dataFillDirectionTip;

	private	short	_tableIdTabOrder;
	private	short	_tablePkTabOrder;
	
	private List<W5QueryField> _queryFields;
	private	List<W5QueryParam> _queryParams;
	private List<W5QueryField> _aggQueryFields;



	
	public W5Query() {
	}	
	
	public W5Query(int queryId) {
		this.queryId = queryId;
	}


	//@Column(name="log_level_tip")
	public short getLogLevelTip() {
		return logLevelTip;
	}



	//@Id
	//@Column(name="query_id")
	public int getQueryId() {
		return queryId;
	}

	//@Column(name="dsc")
	public String getDsc() {
		return this.dsc;
	}

	//@Column(name="sql_select")
	public String getSqlSelect() {
		return this.sqlSelect;
	}

	//@Column(name="sql_from")
	public String getSqlFrom() {
		return this.sqlFrom;
	}

	//@Column(name="sql_where")
	public String getSqlWhere() {
		return this.sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	//@Column(name="sql_groupby")
	public String getSqlGroupby() {
		return this.sqlGroupby;
	}


	//@Column(name="sql_orderby")
	public String getSqlOrderby() {
		return this.sqlOrderby;
	}


	//@Column(name="main_table_id")
	public int getMainTableId() {
		return mainTableId;
	}

	//@Transient
	public List<W5QueryField> get_queryFields() {
		return _queryFields;
	}

	public void set_queryFields(List<W5QueryField> queryFields) {
		_queryFields = queryFields;
	}

	//@Transient
	public List<W5QueryParam> get_queryParams() {
		return _queryParams;
	}
	




	public void set_queryParams(List<W5QueryParam> queryParams) {
		_queryParams = queryParams;
	}



	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}




	public void setMainTableId(int mainTableId) {
		this.mainTableId = mainTableId;
	}



	public void setDsc(String dsc) {
		this.dsc = dsc;
	}



	public void setSqlSelect(String sqlSelect) {
		this.sqlSelect = sqlSelect;
	}



	public void setSqlFrom(String sqlFrom) {
		this.sqlFrom = sqlFrom;
	}



	public void setSqlGroupby(String sqlGroupby) {
		this.sqlGroupby = sqlGroupby;
	}



	public void setSqlOrderby(String sqlOrderby) {
		this.sqlOrderby = sqlOrderby;
	}





	public void setLogLevelTip(short logLevelTip) {
		this.logLevelTip = logLevelTip;
	}
	



	//@Column(name="query_tip")
	public short getQueryTip() {
		return queryTip;
	}

	public void setQueryTip(short queryTip) {
		this.queryTip = queryTip;
	}


	//@Column(name="show_parent_record_flag")
	public short getShowParentRecordFlag() {
		return showParentRecordFlag;
	}

	public void setShowParentRecordFlag(short showParentRecordFlag) {
		this.showParentRecordFlag = showParentRecordFlag;
	}

	//@Transient
	public short get_tableIdTabOrder() {
		return _tableIdTabOrder;
	}

	public void set_tableIdTabOrder(short tableIdTabOrder) {
		_tableIdTabOrder = tableIdTabOrder;
	}

	//@Transient
	public short get_tablePkTabOrder() {
		return _tablePkTabOrder;
	}

	public void set_tablePkTabOrder(short tablePkTabOrder) {
		_tablePkTabOrder = tablePkTabOrder;
	}

	//@Column(name="data_fill_direction_tip")
	public short getDataFillDirectionTip() {
		return dataFillDirectionTip;
	}
	public void setDataFillDirectionTip(short dataFillDirectionTip) {
		this.dataFillDirectionTip = dataFillDirectionTip;
	}




	//@Column(name="query_source_tip")
	public int getQuerySourceTip() {
		return querySourceTip;
	}

	public void setQuerySourceTip(int querySourceTip) {
		this.querySourceTip = querySourceTip;
	}

	//@Column(name="project_uuid")
	//@Id
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	//@Transient
	public List<W5QueryField> get_aggQueryFields() {
		return _aggQueryFields;
	}

	public void set_aggQueryFields(List<W5QueryField> _aggQueryFields) {
		this._aggQueryFields = _aggQueryFields;
	}

	
	public boolean equals(Object o) {
		if(o==null || !(o instanceof W5Query))return false;
		W5Query c = (W5Query)o;
		return c!=null && c.getQueryId()==getQueryId() && c.getProjectUuid().equals(projectUuid);
	}
	
	public int hashCode() {
		return projectUuid.hashCode() + 100*getQueryId();
	}

	//@Transient
	public boolean safeEquals(W5Base q) {

			return false;
	}
}

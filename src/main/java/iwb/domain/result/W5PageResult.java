package iwb.domain.result;

import java.util.List;
import java.util.Map;

import iwb.domain.db.W5Page;
import iwb.domain.helper.W5TableRecordHelper;



public class W5PageResult implements W5MetaResult{
	
	private	int	templateId;
	private W5Page page;
	private	List<Object> pageObjectList;
	
	private int processTime;
	private Map<String, Object> scd;
	private Map<String,String>	requestParams;
	private List<W5TableRecordHelper> masterRecordList;

	public W5PageResult(int templateId) {
		this.templateId = templateId;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public W5Page getPage() {
		return page;
	}
	public void setPage(W5Page page) {
		this.page = page;
	}
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	public Map<String, Object> getScd() {
		return scd;
	}
	public void setScd(Map<String, Object> scd) {
		this.scd = scd;
	}
	public Map<String, String> getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}
	public List<Object> getPageObjectList() {
		return pageObjectList;
	}
	public void setPageObjectList(List<Object> pageObjectList) {
		this.pageObjectList = pageObjectList;
	}
	public List<W5TableRecordHelper> getMasterRecordList() {
		return masterRecordList;
	}
	public void setMasterRecordList(List<W5TableRecordHelper> masterRecordList) {
		this.masterRecordList = masterRecordList;
	}
}

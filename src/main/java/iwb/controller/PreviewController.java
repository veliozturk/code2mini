/*


 * Created on 07.Nis.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package iwb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import iwb.adapter.ui.ViewAdapter;
import iwb.adapter.ui.ViewMobileAdapter;
import iwb.adapter.ui.extjs.ExtJs3_4;
import iwb.adapter.ui.react.React16;
import iwb.cache.FrameworkCache;
import iwb.cache.FrameworkSetting;
import iwb.domain.db.W5Project;
import iwb.domain.db.W5Query;
import iwb.domain.helper.W5FormCellHelper;
import iwb.domain.helper.W5ReportCellHelper;
import iwb.domain.result.M5ListResult;
import iwb.domain.result.W5FormResult;
import iwb.domain.result.W5GlobalFuncResult;
import iwb.domain.result.W5PageResult;
import iwb.domain.result.W5QueryResult;
import iwb.exception.IWBException;
import iwb.report.RptPdfRenderer;
import iwb.service.FrameworkService;
import iwb.util.GenericUtil;
import iwb.util.UserUtil;

@Controller
@RequestMapping("/preview")
public class PreviewController implements InitializingBean {
	private static Logger logger = Logger.getLogger(PreviewController.class);

	@Autowired
	private FrameworkService service;
	
	

	@Autowired
	private TaskExecutor taskExecutor;

	private ViewAdapter ext3_4;
	private	ViewAdapter	webix3_3;
	private	ViewAdapter	react16;
	private	ViewAdapter	vue2;
	private ViewMobileAdapter f7;

	@Override
	public void afterPropertiesSet() throws Exception {
		ext3_4 = new ExtJs3_4();
		react16 = new React16();
	}


	private ViewAdapter getViewAdapter(Map<String, Object> scd, HttpServletRequest request, ViewAdapter defaultRenderer){
		if(GenericUtil.uInt(scd.get("mobile"))!=0)return ext3_4;
		if(request!=null){
			String renderer = request.getParameter("_renderer");
			if(renderer!=null && renderer.equals("ext3_4"))return ext3_4;
			if(renderer!=null && renderer.startsWith("webix"))return webix3_3;
			if(renderer!=null && renderer.equals("react16"))return react16;
			if(renderer!=null && renderer.equals("vue2"))return vue2;
		}
		if(scd!=null){
			String renderer = (String)scd.get("_renderer");
			if(renderer!=null && renderer.equals("ext3_4"))return ext3_4;
			if(renderer!=null && renderer.startsWith("webix"))return webix3_3;			
			if(renderer!=null && renderer.equals("react16"))return react16;
			if(renderer!=null && renderer.equals("vue2"))return vue2;
		}
		return defaultRenderer;
	}
	
	private ViewAdapter getViewAdapter(Map<String, Object> scd, HttpServletRequest request){
		return getViewAdapter(scd, request, ext3_4);
	}

	@RequestMapping("/*/dyn-res/*")
	public ModelAndView hndDynResource(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndDynResource"); 
    	Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);
    	String uri = request.getRequestURI();
    	if(uri.endsWith(".css")){
    		uri = uri.substring(uri.lastIndexOf('/')+1);
    		uri = uri.substring(0, uri.length()-4);
        	String css = FrameworkCache.getPageCss(scd, GenericUtil.uInt(uri));
        	if(css!=null){
        		response.setContentType("text/css; charset=UTF-8");
        		response.getWriter().write(css);
        	}
    	}
//    	int pageId =  ;

		response.getWriter().close();
    	return null;
		
	}

	@RequestMapping("/*/ajaxChangeChatStatus")
	public void hndAjaxChangeChatStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxChangeChatStatus");
		response.setContentType("application/json");
		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);
		int chatStatusTip = GenericUtil.uInt(request, "chatStatusTip");
		response.getWriter().write("{\"success\":" + UserUtil.updateChatStatus(scd, chatStatusTip) + "}");
		response.getWriter().close();
	}

	@RequestMapping("/*/ajaxQueryData")
	public void hndAjaxQueryData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int queryId = GenericUtil.uInt(request, "_qid");
//		JSONObject jo = null;
		Map<String,String> requestMap = GenericUtil.getParameterMap(request);
/*		if(GenericUtil.safeEquals(request.getContentType(),"application/json")){
			JSONObject jo = HttpUtil.getJson(request);
			if(jo.has("_qid"))queryId = jo.getInt("_qid");
			requestMap.putAll(GenericUtil.fromJSONObjectToMap(jo));
		} */
		logger.info("hndAjaxQueryData(" + queryId + ")");
		Map<String, Object> scd = null;
		HttpSession session = request.getSession(false);
		if ((queryId == 1 || queryId == 824) && (session == null || session.getAttribute("scd-dev") == null
				|| ((HashMap<String, String>) session.getAttribute("scd-dev")).size() == 0)) { // select
																							// role
			if (session == null) {
				response.getWriter().write("{\"success\":false,\"error\":\"no_session\"}");
				return;
			}
			scd = new HashMap<String, Object>();
			scd.put("locale", session.getAttribute("locale"));
			scd.put("userId", session.getAttribute("userId"));
			if (GenericUtil.uInt(session.getAttribute("mobile"))!=0)
				scd.put("mobile", session.getAttribute("mobile"));
			scd.put("customizationId", session.getAttribute("customizationId"));
		} else {
			if (queryId == 142) { // online users
				scd = UserUtil.getScd4Preview(request, "scd-dev", false);
				W5QueryResult qr = new W5QueryResult(142);
				W5Query q = new W5Query();
				q.setQueryTip((short) 0);
				qr.setQuery(q);
				qr.setScd(scd);
				qr.setErrorMap(new HashMap());
				qr.setNewQueryFields(FrameworkCache.cachedOnlineQueryFields);
				List<Object[]> lou = UserUtil.listOnlineUsers(scd);
				if (FrameworkSetting.chatShowAllUsers) {
					Map<Integer, Object[]> slou = new HashMap();
					slou.put((Integer) scd.get("userId"), new Object[] { scd.get("userId") });
					for (Object[] o : lou)
						slou.put(GenericUtil.uInt(o[0]), o);
					W5QueryResult allUsers = service.executeQuery(scd, queryId, requestMap);
					for (Object[] o : allUsers.getData()) {
						String msg = (String) o[6];
						if (msg != null && msg.length() > 18) {
							o[3] = msg.substring(0, 19); // last_msg_date_time
							if (msg.length() > 19)
								o[6] = msg.substring(20);// msg
							else
								o[6] = null;
						} else {
							o[6] = null;
							o[3] = null;
						}

						int u = GenericUtil.uInt(o[0]);

						Object[] o2 = slou.get(u);
						if (o2 == null)
							lou.add(o);
						else if (u != (Integer) scd.get("userId")) {
							if (o2.length > 3)
								o2[3] = o[3];
							if (o2.length > 6)
								o2[6] = o[6];
							if (o2.length > 7)
								o2[7] = o[7];
						}
					}
				}
				qr.setData(lou);
				response.setContentType("application/json");
				response.getWriter().write(getViewAdapter(scd, request).serializeQueryData(qr).toString());
				response.getWriter().close();
				return;
			} else
				scd = UserUtil.getScd4Preview(request, "scd-dev", true);// TODO not auto
		}

		ViewAdapter va = getViewAdapter(scd, request);
		W5QueryResult queryResult = service.executeQuery(scd, queryId, requestMap);

		response.setContentType("application/json");
		response.getWriter().write(va.serializeQueryData(queryResult).toString());
		response.getWriter().close();

	}


	@RequestMapping("/*/ajaxPing")
	public void hndAjaxPing(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxPing");
		
		Map<String, Object> scd = null;
		try {
			scd = UserUtil.getScd4Preview(request, "scd-dev", true);
		} catch(Exception ee) {
			scd = null;
		}
		response.setContentType("application/json");
		String pid = UserUtil.getProjectId(request, "preview");
		W5Project po = FrameworkCache.getProject(pid,"Wrong Project");
// 
		if(GenericUtil.uInt(request, "d")==0)
			response.getWriter().write("{\"success\":true,\"version\":\"v2\",\"session\":" + (scd!=null) + (po!=null ? ", \"name\":\""+GenericUtil.stringToJS(po.getDsc())+"\"":"Default") + "}");
		else {
			response.getWriter().write("{\"success\":true,\"version\":\"v2\",\"session\":" + (scd==null ? "false":GenericUtil.fromMapToJsonString2Recursive(scd)) + (po!=null ? ", \"name\":\""+GenericUtil.stringToJS(po.getDsc())+"\"":"Default") + "}");
		}
		response.getWriter().close();
	}


	@RequestMapping("/*/ajaxExecDbFunc")
	public void hndAjaxExecDbFunc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxExecDbFunc");

		Map<String, Object> scd = null;
		short accessType = (short) 1;
		String pid = UserUtil.getProjectId(request, "preview");
		String newScdKey = "preview-"+pid;
		if(request.getSession(false)!=null && request.getSession(false).getAttribute(newScdKey)!=null)
			scd = UserUtil.getScd4Preview(request, "scd-dev", true);
		else {
			scd = new HashMap();
			W5Project po = FrameworkCache.getProject(pid,"Wrong Project");
			scd.put("customizationId",po.getCustomizationId());scd.put("ocustomizationId",po.getCustomizationId());scd.put("userId",10);scd.put("completeName","XXX");
			scd.put("projectId",po.getProjectUuid());scd.put("projectName", po.getDsc());scd.put("roleId",10);scd.put("roleDsc", "XXX Role");
			scd.put("renderer", po.getUiWebFrontendTip());
			scd.put("_renderer", GenericUtil.getRenderer(po.getUiWebFrontendTip()));
			scd.put("mainTemplateId", po.getUiMainTemplateId());
			scd.put("userName", "Demo User");
			scd.put("email", "demo@icodebetter.com");scd.put("locale", "en");
			scd.put("chat", 1);scd.put("chatStatusTip", 1);
			scd.put("userTip",po.get_defaultUserTip());
			scd.put("path", "../");
			accessType = (short) 6;
		}

		int dbFuncId = GenericUtil.uInt(request, "_did"); // +:dbFuncId,
															// -:formId
		if (dbFuncId == 0) {
			dbFuncId = -GenericUtil.uInt(request, "_fid"); // +:dbFuncId,
															// -:formId
		}
		W5GlobalFuncResult dbFuncResult = GenericUtil.uInt(request, "_notran")==0 ? service.executeFunc(scd, dbFuncId, GenericUtil.getParameterMap(request),
				accessType): 
					service.executeFuncNT(scd, dbFuncId, GenericUtil.getParameterMap(request),
							accessType);

		response.setContentType("application/json");
		response.getWriter().write(getViewAdapter(scd, request).serializeGlobalFunc(dbFuncResult).toString());
		response.getWriter().close();

	}

	

	@RequestMapping("/*/ajaxGetFormSimple")
	public void hndGetFormSimple(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int formId = GenericUtil.uInt(request, "_fid");
		logger.info("hndGetFormSimple(" + formId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		int action = GenericUtil.uInt(request, "a");
		W5FormResult formResult = service.getFormResult(scd, formId, action, GenericUtil.getParameterMap(request));

		response.setContentType("application/json");
		response.getWriter().write(getViewAdapter(scd, request).serializeGetFormSimple(formResult).toString());
		response.getWriter().close();

	}

	@RequestMapping("/*/ajaxReloadFormCell")
	public void hndAjaxReloadFormCell(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxReloadFormCell");
		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);
		int fcId = GenericUtil.uInt(request, "_fcid");
		String webPageId = request.getParameter(".w");
		String tabId = request.getParameter(".t");
		W5FormCellHelper rc = service.reloadFormCell(scd, fcId, webPageId, tabId);
		response.setContentType("application/json");
		response.getWriter()
				.write(ext3_4
						.serializeFormCellStore(rc, (Integer) scd.get("customizationId"), (String) scd.get("locale"))
						.toString());
		response.getWriter().close();
	}

	@RequestMapping("/*/ajaxFeed")
	public void hndAjaxFeed(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxFeed");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		response.setContentType("application/json");

		int platestFeedIndex = request.getParameter("_lfi") == null ? -1 : GenericUtil.uInt(request, "_lfi");
		int pfeedTip = request.getParameter("_ft") == null ? -1 : GenericUtil.uInt(request, "_ft");
		int proleId = request.getParameter("_ri") == null ? -1 : GenericUtil.uInt(request, "_ri");
		int puserId = request.getParameter("_ui") == null ? -1 : GenericUtil.uInt(request, "_ui");
		int pmoduleId = request.getParameter("_mi") == null ? -1 : GenericUtil.uInt(request, "_mi");
		// response.setContentType("application/json");
		response.getWriter()
				.write(getViewAdapter(scd, request).serializeFeeds(scd, platestFeedIndex, pfeedTip, proleId, puserId, pmoduleId).toString());
		response.getWriter().close();
		if (FrameworkSetting.liveSyncRecord) {
			UserUtil.getTableGridFormCellCachedKeys((String) scd.get("projectId"),
					/* mainTable.getTableId() */ 671, (Integer) scd.get("userId"), (String) scd.get("sessionId"),
					request.getParameter(".w"), request.getParameter(".t"), /* grdOrFcId */ 919, null, true);
		}
	}
	


	@RequestMapping("/*/showForm")
	public void hndShowForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int formId = GenericUtil.uInt(request, "_fid");
		logger.info("hndShowForm(" + formId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		int action = GenericUtil.uInt(request, "a");
		W5FormResult formResult = service.getFormResult(scd, formId, action, GenericUtil.getParameterMap(request));

		response.setContentType("application/json");
		response.getWriter().write(getViewAdapter(scd, request).serializeShowForm(formResult).toString());
		response.getWriter().close();

	}
	
	@RequestMapping("/*/showMForm")
	public void hndShowMForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int formId = GenericUtil.uInt(request, "_fid");
		logger.info("hndShowMForm(" + formId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		int action = GenericUtil.uInt(request, "a");
		W5FormResult formResult = service.getFormResult(scd, formId, action, GenericUtil.getParameterMap(request));

		response.getWriter().write(f7.serializeGetForm(formResult).toString());
		response.getWriter().close();

	}


	@RequestMapping("/*/ajaxLogoutUser")
	public void hndAjaxLogoutUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxLogoutUser");
		HttpSession session = request.getSession(false);
		response.setContentType("application/json");
		if (session != null) {
			String projectId = UserUtil.getProjectId(request, "preview/");
			W5Project po = FrameworkCache.getProject(projectId,"Wrong Project");
			Map<String, Object> scd = (Map) session.getAttribute("preview-"+projectId);
			if (scd != null) {
				UserUtil.onlineUserLogout((Integer) scd.get("userId"), scd.containsKey("mobile") ? (String)scd.get("mobileDeviceId") : session.getId());
			}
			session.removeAttribute("preview-"+projectId);
		}
		if(GenericUtil.uInt(request, "d")!=0)throw new IWBException("session","No Session",0,null, "No valid session", null);
		else response.getWriter().write("{\"success\":true}");
	}
	
	
	
	@RequestMapping("/*/login.htm")
	public void hndLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info("hndLoginPage");
		String projectId = UserUtil.getProjectId(request,"preview/");
		W5Project po = FrameworkCache.getProject(projectId,"Wrong Project");
		if(po.getSessionQueryId()==0)
			response.sendRedirect("main.htm");
			
		HttpSession session = request.getSession(false);
		if (session != null) {
			String scdKey = "preview-"+projectId;
			Map<String, Object> scd = (Map<String, Object>) session.getAttribute(scdKey);
			if (scd != null)UserUtil.onlineUserLogout( (Integer) scd.get("userId"), (String) scd.get("sessionId"));
			session.removeAttribute(scdKey);
		}


		Map<String, Object> scd = new HashMap();
		scd.put("userId", 1);
		scd.put("roleId", 1);
		scd.put("customizationId", po.getCustomizationId());
		scd.put("projectId", projectId);scd.put("projectName", po.getDsc());
		String xlocale = "en";
		if(po.getLocaleMsgKeyFlag()!=0 && !GenericUtil.isEmpty(po.getLocales())) {
			xlocale = po.getLocales().split(",")[0];
		}
		scd.put("locale", xlocale);
		scd.put("path", "../");

		W5PageResult pageResult = service.getPageResult(scd, po.getUiLoginTemplateId()==0?1:po.getUiLoginTemplateId(), GenericUtil.getParameterMap(request));
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(getViewAdapter(scd, request).serializeTemplate(pageResult).toString());
		response.getWriter().close();

	}

	@RequestMapping("/*/main.htm")
	public void hndMainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndMainPage");
		
		Map<String, Object> scd = null;
		try{
			scd = UserUtil.getScd4Preview(request, "scd-dev", true);
		} catch(Exception e){scd=null;}
		if(scd==null){
			response.sendRedirect("login.htm");
			return;
		}

		int templateId = GenericUtil.uInt(scd.get("mainTemplateId")); // Login
		
		//if it exists then create new session
		
		/*  how to separate these?   */
		
																		// Page
																		// Template
		W5PageResult pageResult = service.getPageResult(scd, templateId, GenericUtil.getParameterMap(request));
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(getViewAdapter(scd, request).serializeTemplate(pageResult).toString());
		response.getWriter().close();

	}
	
	
	@RequestMapping("/*/showPage")
	public void hndShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int templateId = GenericUtil.uInt(request, "_tid");
		logger.info("hndShowPage(" + templateId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		W5PageResult pageResult = service.getPageResult(scd, templateId, GenericUtil.getParameterMap(request));
		// if(pageResult.getTemplate().getTemplateTip()!=2 && templateId!=218 &&
		// templateId!=611 && templateId!=551 && templateId!=566){ //TODO:cok
		// amele
		// throw new PromisException("security","Template",0,null, "Wrong
		// Template Tip (must be page)", null);
		// }

		if(pageResult.getPage().getTemplateTip()!=0)
			response.setContentType("application/json");

		response.getWriter().write(getViewAdapter(scd, request).serializeTemplate(pageResult).toString());
		response.getWriter().close();

	}
	

	@RequestMapping("/*/showMList")
	public void hndShowMList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int listId = GenericUtil.uInt(request, "_lid");
		logger.info("hndShowMList(" + listId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		M5ListResult listResult = service.getMListResult(scd, listId, GenericUtil.getParameterMap(request));


		response.setContentType("application/json");
		response.getWriter().write(f7.serializeList(listResult).toString());
		response.getWriter().close();

	}

	@RequestMapping("/*/showMPage")
	public void hndShowMPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int templateId = GenericUtil.uInt(request, "_tid");
		logger.info("hndShowMPage(" + templateId + ")");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		W5PageResult pageResult = service.getPageResult(scd, templateId, GenericUtil.getParameterMap(request));


		if(pageResult.getPage().getTemplateTip()!=0)
			response.setContentType("application/json");

		response.getWriter().write(f7.serializePage(pageResult).toString());
		response.getWriter().close();
	}
	
	@RequestMapping("/*/grd/*")
	public ModelAndView hndGridReport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndGridReport");
		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		int gridId = GenericUtil.uInt(request, "_gid");
		String gridColumns = request.getParameter("_columns");

		List<W5ReportCellHelper> list = service.getGridReportResult(scd, gridId, gridColumns,
				GenericUtil.getParameterMap(request));
		if (list != null) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("report", list);
			m.put("scd-dev", scd);
			ModelAndView result = null;
			if (request.getRequestURI().indexOf(".xls") != -1 || "xls".equals(request.getParameter("_fmt")))
				result = null;//new ModelAndView(new RptExcelRenderer(), m);
			else if (request.getRequestURI().indexOf(".pdf") != -1)
				result = new ModelAndView(new RptPdfRenderer(null), m);
			else if (request.getRequestURI().indexOf(".csv") != -1) {
				response.setContentType("application/octet-stream");
				response.getWriter().print(GenericUtil.report2csv(list));
			} else if (request.getRequestURI().indexOf(".txt") != -1) {
				response.setContentType("application/octet-stream");
				response.getWriter().print(GenericUtil.report2text(list));
			}
			return result;

		} else {
			response.getWriter().write("Hata");
			response.getWriter().close();

			return null;
		}

	}



	@RequestMapping("/*/showFormByQuery")
	public void hndShowFormByQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndShowFormByQuery");

		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);

		int formId = GenericUtil.uInt(request, "_fid");
		int queryId = GenericUtil.uInt(request, "_qid");
		W5FormResult formResult = service.getFormResultByQuery(scd, formId, queryId,
				GenericUtil.getParameterMap(request));

		response.setContentType("application/json");
		response.getWriter().write(getViewAdapter(scd, request).serializeShowForm(formResult).toString());
		response.getWriter().close();

	}





	
	@RequestMapping("/*/ajaxCallWs")
	public void hndAjaxCallWs(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("hndAjaxCallWs"); 
	    Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);
	    
		Map m =service.REST(scd, request.getParameter("serviceName"), GenericUtil.getParameterMap(request));
		if(m!=null) {
			if(m.containsKey("_code_") && GenericUtil.uInt(m.get("_code_"))>200) {
				throw new IWBException("framework","REST",0,null, m.containsKey("error") ? m.get("error").toString():m.toString(), null);
			}
			if(m.get("data")!=null && m.get("data") instanceof byte[]) {
				response.setContentType("application/octet-stream");
				byte[] r = (byte[])m.get("data");
				response.getOutputStream().write(r, 0, r.length);
				response.getOutputStream().close();
				return;
			} else if(!m.containsKey("success")) {
				if(m.containsKey("exception")) {
					m.put("success", false);
					m.put("errorType", "rest");
					
				} else 
					m.put("success", true);
			}
		}

		response.getWriter().write(GenericUtil.fromMapToJsonString2Recursive(m));
		response.getWriter().close();		
	}


	

	@RequestMapping("/*/comp/*")
	public void hndComponent(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		logger.info("hndJasperReport"); 
		Map<String, Object> scd = UserUtil.getScd4Preview(request, "scd-dev", true);
    	String uri = request.getRequestURI();
    	if(uri.endsWith(".css")){
    		uri = uri.substring(uri.lastIndexOf('/')+1);
    		uri = uri.substring(0, uri.length()-4);
        	String css = FrameworkCache.getComponentCss(scd, GenericUtil.uInt(uri));
    		response.setContentType("text/css; charset=UTF-8");
        	if(css!=null){
        		response.getWriter().write(css);
        	} else {
        		
        	}
    	} else if(uri.endsWith(".js")){
    		uri = uri.substring(uri.lastIndexOf('/')+1);
    		uri = uri.substring(0, uri.length()-3);
        	String js = FrameworkCache.getComponentJs(scd, GenericUtil.uInt(uri));
    		response.setContentType("text/javascript; charset=UTF-8");
        	if(js!=null){
        		response.getWriter().write(js);
        	} else {
        		
        	}
    	}

		response.getWriter().close();
	}	
	
}

package iwb.timer;

import java.util.Map;
import java.util.TimerTask;

import org.springframework.core.task.TaskExecutor;

import iwb.cache.FrameworkCache;
import iwb.cache.FrameworkSetting;
import iwb.domain.db.W5JobSchedule;
import iwb.domain.db.W5Project;
import iwb.service.FrameworkService;

public class GenericTimer extends TimerTask {
	
	
	public GenericTimer(TaskExecutor taskExecutor, FrameworkService service) {
		super();
		this.taskExecutor = taskExecutor;
		this.service = service;
	}

	private FrameworkService service;
	
	private TaskExecutor taskExecutor;

	private void checkJobs(){
		for(String projectId:FrameworkCache.wJobs.keySet()) {
			W5Project po = FrameworkCache.getProject(projectId);
			if(po!=null && (po.getCustomizationId()==0 || /*po.getCustomizationId()==140 || */!FrameworkSetting.cloud)) {
				Map<Integer, W5JobSchedule> miv = FrameworkCache.wJobs.get(projectId);
				if(miv!=null)for(final W5JobSchedule j:miv.values()) {
					if(j.runCheck()) { //
						taskExecutor.execute(new Runnable() {
				            @Override
				            public void run() {
				            	System.out.println("Start Job: " + j.getDsc());
				            	if(j.getTransactionalFlag()!=0)service.runJob(j);
				            	else service.runJobNT(j);
				            }
				        });
					}
				}
			}
			
		}

	}

	public void run() {
		if(FrameworkSetting.systemStatus!=0)return;
		System.out.println("Timer.Run");
		checkJobs();
	}


}

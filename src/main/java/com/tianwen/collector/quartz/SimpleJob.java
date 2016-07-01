package com.tianwen.collector.quartz;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		System.err.println(new Date().toString() + "-------------------" + map.getString("message"));
	}

}

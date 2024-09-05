package com.example.Cloud.CsvtoDataBase.Config;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetailFactoryBean jobDetail(Job job, JobLauncher jobLauncher) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJobLauncher.class);  // The QuartzJobLauncher class
        factoryBean.setDurability(true); // The job will remain stored even after execution

        // Pass the required parameters to the job (Job and JobLauncher)
        factoryBean.getJobDataMap().put("job", job);
        factoryBean.getJobDataMap().put("jobLauncher", jobLauncher);

        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setRepeatInterval(20000);  // Interval in milliseconds (every 10 seconds)
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);  // Repeat indefinitely
        return factoryBean;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail jobDetail) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(jobDetail);
        schedulerFactoryBean.setTriggers(trigger);
        return schedulerFactoryBean;
    }
}

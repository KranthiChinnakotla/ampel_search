package drugsdb.recent.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import drugsdb.recent.App;
import drugsdb.recent.model.Drug;
import drugsdb.recent.service.DrugService;

@Component
@Configuration
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	private final JdbcTemplate jdbcTemplate;

	

	@Autowired
	DrugService drugService;

	
	  @Autowired 
	  public JobCompletionNotificationListener(JdbcTemplate
	  jdbcTemplate){ this.jdbcTemplate = jdbcTemplate; }
	 

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

			for (Drug d : App.queryList) {
				LOG.info("Drug name " + d.getDrug_name() + ", Target Name " + d.getTarget());
			}
			/*
			try {
				drugService.secondJob();
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		

		}
		 //App.queryList.clear();
	}

//	@Autowired
//	JobCompletionNotificationListener(@Qualifier("databaseToCsvFileJob") Job job, JobLauncher jobLauncher) {
//		this.job = job;
//		this.jobLauncher = jobLauncher;
//	}
	
	

}

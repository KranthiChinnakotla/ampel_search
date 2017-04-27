package edu.medha.service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import edu.medha.fileupload.App;
import edu.medha.model.Drug;

@Service
public class DrugService {
	
	/*@Autowired
	@Qualifier("dbToCsvFileJob")
	Job job2;*/
	
	@Autowired
	JobLauncher jobLauncher;

	public List<Drug> findAllDrugs(){
		List<Drug> drugs= App.queryList;
		return drugs;
	}
	
	/*public void secondJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(job2, jobParameters);
	}*/
	
}

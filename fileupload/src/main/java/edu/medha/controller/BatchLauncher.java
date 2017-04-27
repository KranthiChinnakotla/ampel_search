package edu.medha.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import edu.medha.fileupload.App;
import edu.medha.model.Drug;
import edu.medha.service.DrugService;



@RestController
public class BatchLauncher {

	@Autowired
	@Qualifier("importUserJob")
	Job job;
	
	
	
	@Autowired
	JobLauncher jobLauncher;
	
	
	@Autowired
	DrugService drugService;
	
	@RequestMapping(value="/launcher",method=RequestMethod.GET)
	@ResponseBody
	public String handle() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		App.queryList.clear();
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(job, jobParameters);
		
		return "Go to URL: http://34.196.18.237:9001/download";
		
	}
	
	
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		 response.setHeader("Content-Disposition", "attachment; filename=\"results.csv\"");

	        List<Drug> drugs = App.queryList;
	        String[] header = {"drug_name","target"};
	        String[] csvHeader = {"",""};
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
	                CsvPreference.STANDARD_PREFERENCE);

	        csvWriter.writeHeader(header);

	        for(Drug drug : drugs){
	            csvWriter.write(drug, header);
	        }
	        csvWriter.close();
		
	}
	
	
	
	
	public List<Drug> viewHome(){
		
		return drugService.findAllDrugs();
	}
}

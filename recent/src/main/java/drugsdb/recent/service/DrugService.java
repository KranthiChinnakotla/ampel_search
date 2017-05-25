package drugsdb.recent.service;

import java.util.List;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import drugsdb.recent.App;
import drugsdb.recent.model.Drug;

@Service
public class DrugService {

	/*
	 * @Autowired
	 * 
	 * @Qualifier("dbToCsvFileJob") Job job2;
	 */

	@Autowired
	JobLauncher jobLauncher;

	public List<Drug> findAllDrugs() {
		List<Drug> drugs = App.queryList;
		return drugs;
	}

	/*
	 * public void secondJob() throws JobExecutionAlreadyRunningException,
	 * JobRestartException, JobInstanceAlreadyCompleteException,
	 * JobParametersInvalidException{ JobParameters jobParameters = new
	 * JobParametersBuilder() .addLong("time",System.currentTimeMillis())
	 * .toJobParameters(); jobLauncher.run(job2, jobParameters); }
	 */

}

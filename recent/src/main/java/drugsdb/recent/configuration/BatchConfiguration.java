package drugsdb.recent.configuration;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import drugsdb.recent.model.DrugItemProcessor;
import drugsdb.recent.configuration.JobCompletionNotificationListener;
import drugsdb.recent.controllers.FileUploadController;
import drugsdb.recent.model.Specifications;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(BatchConfiguration.class);

	@Value("${db.driver}")
	private String DB_DRIVER;

	@Value("${db.password}")
	private String DB_PASSWORD;

	@Value("${db.url}")
	private String DB_URL;

	@Value("${db.username}")
	private String DB_USERNAME;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	


	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Specifications> reader() throws MalformedURLException {
		FlatFileItemReader<Specifications> reader = new FlatFileItemReader<Specifications>();
		//reader.setResource(new UrlResource("http://34.196.18.237:9001/files/drug.csv"));
		reader.setResource(new FileSystemResource(FileUploadController.filePath));
		reader.setLineMapper(new DefaultLineMapper<Specifications>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name", "type" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Specifications>() {
					{
						setTargetType(Specifications.class);

					}
				});

			}
		});

		return reader;

	}

	@Bean
	public DrugItemProcessor processor() {
		return new DrugItemProcessor();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) throws MalformedURLException {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1()).end().build();

	}

	@Bean
	public Step step1() throws MalformedURLException {
		return stepBuilderFactory.get("step1").<Specifications, Specifications>chunk(10).reader(reader())
				.processor(processor()).build();
	}

	
	

}

package drugsdb.recent;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import drugsdb.recent.storage.StorageService;
import drugsdb.recent.storage.StorageProperties;
import drugsdb.recent.App;
import drugsdb.recent.controllers.FileUploadController;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@ComponentScan(basePackages="drugsdb.recent")
@EnableConfigurationProperties(StorageProperties.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,JndiConnectionFactoryAutoConfiguration.class})
public class App 
{
    public static void main( String[] args )
    {
    	new File(FileUploadController.uploadingdir).mkdirs();
    	SpringApplication.run(App.class, args);
    }
    
   /* @Bean
   	CommandLineRunner init(StorageService storageService) { 
   		return (args) -> {
               storageService.deleteAll();
               storageService.init();
   		};
   	}*/
}

package drugsdb.recent;

import java.io.File;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import drugsdb.recent.App;
import drugsdb.recent.controllers.FileUploadController;
import drugsdb.recent.model.Drug;



@SpringBootApplication
@ComponentScan(basePackages="drugsdb.recent")
@EnableConfigurationProperties()
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,JndiConnectionFactoryAutoConfiguration.class})
public class App 
{
	public static ArrayList<Drug> queryList = new ArrayList<Drug>();
    public static void main( String[] args )
    {
    	new File(FileUploadController.uploadingdir).mkdirs();
    	SpringApplication.run(App.class, args);
    }
    
  
}

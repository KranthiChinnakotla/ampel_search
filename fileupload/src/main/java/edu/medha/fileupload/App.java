package edu.medha.fileupload;

import java.util.ArrayList;

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

import edu.medha.storage.StorageService;

import edu.medha.model.Drug;

import edu.medha.storage.StorageProperties;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@ComponentScan(basePackages="edu.medha")
@EnableConfigurationProperties(StorageProperties.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,JndiConnectionFactoryAutoConfiguration.class})
public class App 
{
	public static ArrayList<Drug> queryList = new ArrayList<>();
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @Bean
	CommandLineRunner init(StorageService storageService) { 
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
}

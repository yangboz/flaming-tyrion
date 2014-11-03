package hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

//import org.activiti.engine.ProcessEngine;
//import org.activiti.spring.boot.ProcessEngineAutoConfiguration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
//@EnableAutoConfiguration(exclude = ProcessEngineAutoConfiguration.class)
//@ImportResource("classpath:/hello/activiti.cfg.xml")
public class Application {

    public static void main(String[] args) {
    	ApplicationContext applicationContext = 
    			SpringApplication.run(Application.class, args);
//    	ProcessEngine processEngine = applicationContext.getBean(ProcessEngine.class);
    	System.out.println("Init'd the app context with activiti process engine!");
    }
}
package com.cestco.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

//@EnableAutoConfiguration
//@SpringBootApplication
//@ComponentScan
//public class ConsumeApplication   extends SpringBootServletInitializer  implements EmbeddedServletContainerCustomizer {
//
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ConsumeApplication.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ConsumeApplication.class, args);
//    }
//
//    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        //	configurableEmbeddedServletContainer.setPort(9090);
//    }
//}

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class ConsumeApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{

	 @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(ConsumeApplication.class);  
    }  

	 
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(ConsumeApplication.class, args);
	    }

	public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
	//	configurableEmbeddedServletContainer.setPort(9090);
	}
}
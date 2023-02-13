/**
 * copyright to OMF since 01/01/2023
 */
package com.omf.los;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

/**
 * LOS gateway application
 */
@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath:dealertrackervalidationworkflow.bpmn")
public class DmsMessageHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsMessageHandlerApplication.class, args);
	}

}

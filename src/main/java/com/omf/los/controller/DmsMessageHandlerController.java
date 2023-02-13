/**
 * copyright to OMF since 01/01/2023
 */
package com.omf.los.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.lifecycle.ZeebeClientLifecycle;

/**
 * entry point to LOS system to delegate the DMS application to BPM workflow
 */
@RestController
public class DmsMessageHandlerController {

	/** logging messages received from controller */
	private static final Logger LOG = LoggerFactory.getLogger(DmsMessageHandlerController.class);

	/** the bpm notation reference file */
	private static final String DELEAR_MESSAGE_BPM = "dealer_tracker_validation_workflow";

	/** zeebe client to process the business workflow */
	@Autowired
	private ZeebeClientLifecycle client;

	/** push message to camunda */
	@PostMapping("/dealertracker")
	public ResponseEntity<ProcessInstanceEvent> message(@RequestBody final CustomerInfo customerInfo) {
		LOG.info("customer information = {}", customerInfo);
		final ProcessInstanceEvent event = client.newCreateInstanceCommand()
												 .bpmnProcessId(DELEAR_MESSAGE_BPM)
												 .latestVersion()
												 .variables(customerInfo)
												 .send().join();
		LOG.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
				event.getProcessDefinitionKey(), event.getBpmnProcessId(), event.getVersion(), event.getProcessInstanceKey());
		return ResponseEntity.ok(event);
	}

	/** claim the tasks */
	@JobWorker(type = "io.camunda.zeebe:userTask")
	public void handleJob(final JobClient client, final ActivatedJob job) {
		LOG.info("{} {} {}", "-".repeat(30), job.getElementId(), "-".repeat(30));
		Map<String, Object> variables = job.getVariablesAsMap();
		client.newCompleteCommand(job.getKey()).send().join();
		LOG.info("{} {} {}", "-".repeat(30), variables, "-".repeat(30));
	}
}
package com.upl.ugdnsyncsolus.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

@Component
public class PaginatedEventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	
	public static final Logger logger = LoggerFactory.getLogger(PaginatedEventPublisher.class);

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void publish(PaginatedResultsRetrievedEvent appEventA) {
		logger.info("In publisher");
        this.publisher.publishEvent(appEventA);
        logger.info("Completed publisher");
    }
	
}

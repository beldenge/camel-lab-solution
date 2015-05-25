package com.redhat.techtalks.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import com.redhat.techtalks.camel.routes.EnrollmentRouteBuilder;

public class EnrollmentApplication {
	private static final long WAIT_MILLIS = 5000;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		CamelContext camelContext = new DefaultCamelContext();

		camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		camelContext.addRoutes(new EnrollmentRouteBuilder());

		camelContext.start();
		Thread.sleep(WAIT_MILLIS);
		camelContext.stop();
	}
}

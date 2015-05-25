package com.redhat.techtalks.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.redhat.techtalks.camel.routes.EnrollmentRouteBuilder;

public class EnrollmentRouteBuilderTest extends CamelTestSupport {
	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		CamelContext camelContext = super.createCamelContext();

		camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		return camelContext;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EnrollmentRouteBuilder();
	}

	@Test
	public void testEnrolleesConsumer() throws InterruptedException
	{
		MockEndpoint mock = getMockEndpoint("mock:testQueue");
		mock.setAssertPeriod(5000);  // Required in order to expect exactly 4 messages
		mock.expectedMessageCount(4);
		mock.expectedBodiesReceivedInAnyOrder("57", "40", "5", "31");
		mock.assertIsSatisfied();
	}
}

package com.redhat.techtalks.camel.routes;

import java.io.IOException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;

import com.redhat.techtalks.camel.business.AgeCalculator;
import com.redhat.techtalks.camel.model.Enrollment;

public class EnrollmentRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		DataFormat bindy = new BindyCsvDataFormat("com.redhat.techtalks.camel.model");

		from("file:input?noop=true")
			.wireTap("log:com.redhat.techtalks.camel?level=INFO&showAll=true")
			.filter(header("CamelFileNameOnly").startsWith("rewards"))
			.unmarshal(bindy)
			.split(body())
			.setBody(simple("${body[com.redhat.techtalks.camel.model.Enrollment]}"))
			.choice()
				.when(simple("${body.language} == 'ES'"))
					.log("Hola ${body.firstName}!")
					.endChoice()
				.when(simple("${body.language} == 'EN'"))
					.log("Hello ${body.firstName}!")
					.endChoice()
				.otherwise()
					.log(LoggingLevel.ERROR, "OMG!")
					.endChoice()
			.end()
			.marshal().json(JsonLibrary.Jackson)
			.to("stream:out")
			.to("jms:queue:enrollees");

		from("jms:queue:enrollees")
			.onException(IOException.class)
				.maximumRedeliveries(1)
				.redeliveryDelay(500)
			.end()
			.unmarshal().json(JsonLibrary.Jackson, Enrollment.class)
			.bean(new AgeCalculator(), "calculateAge(${body.dateOfBirth})")
			.to("stream:out")
			.to("mock:testQueue");
	}
}

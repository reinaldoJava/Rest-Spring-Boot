package com.rest.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("value")
public class RestExample {

	@Autowired
	private Tracer tracer;
	
	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String rest(@RequestBody String temp ,//@RequestHeader(value="X-Span-Name") String spanName, 
												 @RequestHeader(value="traceId") String traceId, 
												 @RequestHeader(value="spanId") String spanId) 
	{
		long traceIdLong = Span.hexToId(traceId);
		long spanIdLong = Span.hexToId(spanId);
		Span span = Span.builder().traceId(traceIdLong).spanId(spanIdLong).build();
		Span filho = tracer.createSpan("Rest", span);
		filho.tag("Local Component", "Rest primeiro");
		filho.tag("traceId", traceId);
		filho.tag("spanId", spanId);
		//tracer.close(span);
		System.out.println("Sucesso!!");
		tracer.close(filho);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("traceId", filho.traceIdString());
		headers.add("spanId", Span.idToHex(filho.getSpanId()));
		headers.setContentType(MediaType.APPLICATION_JSON);
		tracer.detach(tracer.getCurrentSpan());
		HttpEntity<String> requestRest = new HttpEntity<>("{}", headers);
		restTemplate.exchange("http://127.0.0.1:9092/value/test", HttpMethod.POST, requestRest, String.class);
		
		return "ok";
	}

}

package com.rest.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("value")
public class RestExample {

	@Autowired
	private Tracer tracer;
	@Autowired
	private SpanAccessor spanAccessor;
	
	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String rest(@RequestBody String temp ,//@RequestHeader(value="X-Span-Name") String spanName, 
												 @RequestHeader(value="traceId") String traceId, 
												 @RequestHeader(value="spanId") String spanId) 
	{
		long traceIdLong = Span.hexToId(traceId);
		long spanIdLong = Span.hexToId(spanId);
		Span span = Span.builder().traceId(traceIdLong).spanId(spanIdLong).build();
		Span filho = tracer.createSpan("Rest", span);
		//tracer.continueSpan(span);
		System.out.println("Sucesso!!");
		tracer.close(filho);
		return "ok";
	}
//	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String rest(@RequestBody String temp ,//@RequestHeader(value="X-Span-Name") String spanName, 
//												 @RequestHeader(value="traceId") String traceId, 
//												 @RequestHeader(value="spanId") String spanId)
//	
//	{
//		Span span = this.spanAccessor.getCurrentSpan();
//		tracer.continueSpan(span);
//		//headers.add(Span.SPAN_NAME_NAME, span.getName() );
//		System.out.println("Sucesso!!");
//		return "ok";
//	}
//	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public String rest(@RequestBody String temp) {
//		Span span = this.spanAccessor.getCurrentSpan();
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		//restTemplate.headForHeaders(Span.TRACE_ID_NAME, Span.idToHex(span.getTraceId()));
//		headers.add(Span.SPAN_NAME_NAME, span.getName() );
//		headers.add(Span.SPAN_ID_NAME, Span.idToHex(span.getSpanId()));
//		System.out.println("Sucesso!!");
//		return "ok";
//	}
}

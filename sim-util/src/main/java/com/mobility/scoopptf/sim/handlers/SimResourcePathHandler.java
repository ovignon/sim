package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SimResourcePathHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(SimResourcePathHandler.class);
	private String resourcePath = "/keepAlive.xml";
	private String nationalIdentifier = null;

	public SimResourcePathHandler() {
	}

	public SimResourcePathHandler(String nationalIdentifier) {
		this.nationalIdentifier = nationalIdentifier;
	}

	public SimResourcePathHandler(String nationalIdentifier, String resourcePath) {
		this.resourcePath = resourcePath;
		this.nationalIdentifier = nationalIdentifier;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("==> Handle exchange : {} {}", SimUtil.extractExchangeMethod(exchange),
				SimUtil.extractExchangeUrl(exchange));
		if (SimUtil.exchangeMethodIsPost(exchange)) {
			handlePost(exchange);
		}
		SimUtil.sendResponseWithResource(exchange, resourcePath, nationalIdentifier);
	}

	private void handlePost(HttpExchange exchange) {
		String body = SimUtil.inputStreamToString(exchange.getRequestBody());
		logger.info("----> {}", body);
	}

	public String getNationalIdentifier() {
		return nationalIdentifier;
	}

}

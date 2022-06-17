package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SimGenericHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(SimGenericHandler.class);

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("==> Handle exchange : {} {}", SimUtil.extractExchangeMethod(exchange),
				SimUtil.extractExchangeUrl(exchange));
		if (SimUtil.exchangeMethodIsPost(exchange)) {
			handlePost(exchange);
		}
		SimUtil.sendResponseWithResource(exchange, "/keepAlive.xml");
	}

	private void handlePost(HttpExchange exchange) {
		String body = SimUtil.inputStreamToString(exchange.getRequestBody());
		logger.info("----> {}", body);
	}
}

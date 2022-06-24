package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SimEmptyResponseWithCodeHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(SimEmptyResponseWithCodeHandler.class);
	private int responseCode = 200;

	public SimEmptyResponseWithCodeHandler() {
	}

	public SimEmptyResponseWithCodeHandler(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("==> Handle exchange : {} {}", SimUtil.extractExchangeMethod(exchange),
				SimUtil.extractExchangeUrl(exchange));
		if (SimUtil.exchangeMethodIsPost(exchange)) {
			handlePost(exchange);
		}
		SimUtil.sendEmptyResponseWithCode(exchange, responseCode);
	}

	private void handlePost(HttpExchange exchange) {
		String body = SimUtil.inputStreamToString(exchange.getRequestBody());
		logger.info("----> {}", body);
	}
}

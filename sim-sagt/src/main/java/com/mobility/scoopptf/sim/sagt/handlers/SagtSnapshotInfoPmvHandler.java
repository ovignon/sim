package com.mobility.scoopptf.sim.sagt.handlers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SagtSnapshotInfoPmvHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(SagtSnapshotConfPmvHandler.class);

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("==> Handle exchange : {} {}", SimUtil.extractExchangeMethod(exchange),
				SimUtil.extractExchangeUrl(exchange));
		SimUtil.sendResponseWithResource(exchange, "/snapshotInfoPmv.xml");
	}

}

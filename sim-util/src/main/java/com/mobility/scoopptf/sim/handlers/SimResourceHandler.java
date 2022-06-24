package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;

public class SimResourceHandler extends AbstractSimFileHandler {

	public static final String DEFAULT_PATH = "/keepAlive.xml";

	public SimResourceHandler() {
		super(DEFAULT_PATH);
	}

	public SimResourceHandler(String resourcePath) {
		super(resourcePath);
	}

	public SimResourceHandler(String resourcePath, String valueFilePath) {
		super(resourcePath, valueFilePath);
	}

	@Override
	protected void sendResponse(HttpExchange exchange, String resourceLocation) throws IOException {
		SimUtil.sendResponseWithResource(exchange, resourceLocation);

	}

}

package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;

public class SimResourceHandler extends AbstractSimFileHandler {

	public static final String DEFAULT_PATH = "/keepAlive.xml";

	public SimResourceHandler() {
		super(null, DEFAULT_PATH);
	}

	public SimResourceHandler(String nationalIdentifier, String resourcePath) {
		super(nationalIdentifier, resourcePath);
	}

	public SimResourceHandler(String nationalIdentifier, String resourcePath, String valueFilePath) {
		super(nationalIdentifier, resourcePath, valueFilePath);
	}

	@Override
	protected void sendResponse(HttpExchange exchange, String resourceLocation, String nationalIdentifier)
			throws IOException {
		SimUtil.sendResponseWithResource(exchange, resourceLocation, nationalIdentifier);

	}

}

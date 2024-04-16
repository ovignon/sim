package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class SimFileHandler extends AbstractSimFileHandler {

	public SimFileHandler(String nationalIdentifier, String filePath) {
		super(nationalIdentifier, filePath);
	}

	public SimFileHandler(String nationalIdentifier, String filePath, String valueFilePath) {
		super(nationalIdentifier, filePath, valueFilePath);
	}

	@Override
	protected void sendResponse(HttpExchange exchange, String fileLocation, String nationalIdentifier)
			throws IOException {
//		SimUtil.sendResponseWithFile(exchange, fileLocation);

	}

}

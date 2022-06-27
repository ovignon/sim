package com.mobility.scoopptf.sim.handlers;

import java.io.IOException;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;

public class SimFileHandler extends AbstractSimFileHandler {

	public SimFileHandler(String filePath) {
		super(filePath);
	}

	public SimFileHandler(String filePath, String valueFilePath) {
		super(filePath, valueFilePath);
	}

	@Override
	protected void sendResponse(HttpExchange exchange, String fileLocation) throws IOException {
//		SimUtil.sendResponseWithFile(exchange, fileLocation);

	}

}

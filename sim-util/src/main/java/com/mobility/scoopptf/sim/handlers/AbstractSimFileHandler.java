package com.mobility.scoopptf.sim.handlers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.util.SimUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class AbstractSimFileHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(AbstractSimFileHandler.class);
	protected final String path;
	protected final String valueFilePath;

	protected AbstractSimFileHandler(String path, String valueFilePath) {
		super();
		this.path = path;
		this.valueFilePath = valueFilePath;
	}

	protected AbstractSimFileHandler(String path) {
		this(path, null);
	}

	protected abstract void sendResponse(HttpExchange exchange, String fileLocation) throws IOException;

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("==> Handle exchange : {} {}", SimUtil.extractExchangeMethod(exchange),
				SimUtil.extractExchangeUrl(exchange));
		if (SimUtil.exchangeMethodIsPost(exchange)) {
			handlePost(exchange);
		}
		String responseContentPath = path;
		if (StringUtils.isNotBlank(valueFilePath)) {
			String value = readValue();
			if (value != null) {
				responseContentPath = MessageFormat.format(path, value);
			}

		}
		sendResponse(exchange, responseContentPath);
	}

	private void handlePost(HttpExchange exchange) {
		String body = SimUtil.inputStreamToString(exchange.getRequestBody());
		logger.info("----> {}", body);
	}

	public String readValue() {
		try {
			return IOUtils.toString(new FileInputStream(valueFilePath), StandardCharsets.UTF_8);
		} catch (Exception exc) {
			logger.error("{}", exc);
			return null;
		}
	}

}

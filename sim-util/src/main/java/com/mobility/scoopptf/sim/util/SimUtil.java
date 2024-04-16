package com.mobility.scoopptf.sim.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;

public class SimUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimUtil.class);

	private SimUtil() {
	}

	public static String buildReponseWithResource(String resourcePath) {
		try {
			return IOUtils.resourceToString(resourcePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "#ERROR#";
		}
	}

	public static void sendResponseWithResource(HttpExchange exchange, String resourcePath, String nationalIdentifier)
			throws IOException {
		String response = SimUtil.buildReponseWithResource(resourcePath);
		if (StringUtils.isNotBlank(nationalIdentifier)) {
			response = response.replaceAll("_NATIONAL_IDENTIFIER_", nationalIdentifier);
		}
		int responseCode = 200;
		exchange.sendResponseHeaders(responseCode, response.getBytes().length);
		exchange.getResponseHeaders().set("Content-Type", "text/xml");
		OutputStream os = exchange.getResponseBody();
		LOGGER.info("=====> response.length() = {} / response.getBytes().length = {}", response.length(),
				response.getBytes().length);
		os.write(response.getBytes());
		os.flush();
		os.close();
	}

	public static void sendEmptyResponseWithCode(HttpExchange exchange, int responseCode) throws IOException {
		exchange.sendResponseHeaders(responseCode, 0);
		exchange.getResponseBody().write(new byte[] {});
		exchange.getResponseBody().flush();
		exchange.getResponseBody().close();
	}

	public static boolean checkExchangeMethod(HttpExchange exchange, String methodName) {
		if (StringUtils.isBlank(methodName)) {
			throw new IllegalArgumentException();
		}
		return exchange != null && exchange.getRequestMethod() != null
				&& exchange.getRequestMethod().trim().equalsIgnoreCase(methodName);
	}

	public static String extractExchangeUrl(HttpExchange exchange) {
		if (exchange == null) {
			LOGGER.error("null exchange argument");
			throw new IllegalArgumentException();
		}
		return exchange.getRequestURI() == null ? "/" : exchange.getRequestURI().toString();
	}

	public static String extractExchangeMethod(HttpExchange exchange) {
		if (exchange == null) {
			LOGGER.error("null exchange argument");
			throw new IllegalArgumentException();
		}
		return exchange.getRequestMethod();
	}

	public static boolean exchangeMethodIsGet(HttpExchange exchange) {
		return checkExchangeMethod(exchange, "get");
	}

	public static boolean exchangeMethodIsPost(HttpExchange exchange) {
		return checkExchangeMethod(exchange, "post");
	}

	public static boolean exchangeMethodIsPut(HttpExchange exchange) {
		return checkExchangeMethod(exchange, "put");
	}

	public static boolean exchangeMethodIsDelete(HttpExchange exchange) {
		return checkExchangeMethod(exchange, "delete");
	}

	public static String inputStreamToString(InputStream inputStream) {

		try {
			StringBuilder textBuilder = new StringBuilder();
			try (Reader reader = new BufferedReader(
					new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
				int c = 0;
				while ((c = reader.read()) != -1) {
					textBuilder.append((char) c);
				}
				return textBuilder.toString();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

}

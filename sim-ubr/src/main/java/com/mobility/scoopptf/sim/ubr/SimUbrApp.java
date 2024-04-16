package com.mobility.scoopptf.sim.ubr;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.handlers.SimResourcePathHandler;
import com.sun.net.httpserver.HttpServer;

public class SimUbrApp {

	public static final int DEFAULT_PORT = 9090;

	private static Logger LOGGER = LoggerFactory.getLogger(SimUbrApp.class);

	public static void main(String[] args) {

		int port = DEFAULT_PORT;
		String nationalIdentifier = null;

		if (args != null && args.length > 0 && StringUtils.isNumeric(args[0])) {
			port = Integer.valueOf(args[0]);
		}
		if (args != null && args.length > 1 && StringUtils.isNotBlank(args[1])) {
			nationalIdentifier = args[1];
		}

		try {

			LOGGER.info("===== UBR Simulation on port {} =====", port);

			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/pull", new SimResourcePathHandler(nationalIdentifier));
			server.createContext("/push", new SimResourcePathHandler(nationalIdentifier));
			server.setExecutor(null);
			server.start();

		} catch (Exception exc) {
			LOGGER.error("{}", exc);
			System.exit(1);
		}

	}

}

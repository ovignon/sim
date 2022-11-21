package com.mobility.scoopptf.sim.sagt;

import java.net.InetSocketAddress;

import com.mobility.scoopptf.sim.handlers.SimResourcePathHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimSagtApp {

	public static final int DEFAULT_PORT = 9191;

	private static Logger LOGGER = LoggerFactory.getLogger(SimSagtApp.class);

	public static void main(String[] args) {

		int port = DEFAULT_PORT;

		if (args != null && args.length > 0 && StringUtils.isNumeric(args[0])) {
			port = Integer.valueOf(args[0]);
		}

		try {

			LOGGER.info("===== SAGT Simulation on port {} =====", port);

			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/push", new SimResourcePathHandler());
			server.createContext("/snapshot-evt-info", new SimResourcePathHandler());
//			server.createContext("/snapshot-conf-pmv", new SimResourcePathHandler("/snapshotConfPmv-215.xml"));
//			server.createContext("/snapshot-info-pmv", new SimResourcePathHandler("/snapshotInfoPmv-215.xml"));
			server.createContext("/snapshot-conf-pmv", new SimResourcePathHandler());
			server.createContext("/snapshot-info-pmv", new SimResourcePathHandler());
			server.createContext("/snapshot-info-parking", new SimResourcePathHandler());
			server.setExecutor(null);
			server.start();

		} catch (Exception exc) {
			LOGGER.error("{}", exc);
			System.exit(1);
		}

	}

}

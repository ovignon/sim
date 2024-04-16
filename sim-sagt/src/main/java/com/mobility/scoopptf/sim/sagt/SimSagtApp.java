package com.mobility.scoopptf.sim.sagt;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.handlers.SimResourcePathHandler;
import com.sun.net.httpserver.HttpServer;

public class SimSagtApp {

	public static final int DEFAULT_PORT = 9191;

	private static Logger LOGGER = LoggerFactory.getLogger(SimSagtApp.class);

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

			LOGGER.info("===== SAGT Simulation on port {} =====", port);

			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/push", new SimResourcePathHandler(nationalIdentifier));
			server.createContext("/snapshot", new SimResourcePathHandler(nationalIdentifier));
			server.createContext("/snapshot-evt-info", new SimResourcePathHandler(nationalIdentifier));

			server.createContext("/snapshot-conf-pmv",
					new SimResourcePathHandler(nationalIdentifier, "/snapshotConfPmv-vide.xml"));
			server.createContext("/snapshot-info-pmv",
					new SimResourcePathHandler(nationalIdentifier, "/snapshotInfoPmv-vide.xml"));

//			server.createContext("/snapshot-conf-pmv",
//					new SimResourcePathHandler(nationalIdentifier, "/mantis-665-aprr/snapshot-conf-pmv.xml"));
//			server.createContext("/snapshot-conf-pmv",
//					new SimResourcePathHandler(nationalIdentifier, "/mantis-665-aprr/snapshot-conf-pmv-75070027.xml"));
//			server.createContext("/snapshot-conf-pmv",
//					new SimResourcePathHandler(nationalIdentifier, "/mantis-665-aprr/snapshot-conf-pmv_75030001.xml"));

			server.createContext("/snapshot-info-pmv",
					new SimResourcePathHandler(nationalIdentifier, "/mantis-665-aprr/snapshot-info-pmv.xml"));
//			server.createContext("/snapshot-info-pmv", new SimResourcePathHandler(nationalIdentifier));

			server.createContext("/snapshot-info-parking", new SimResourcePathHandler(nationalIdentifier));
			server.setExecutor(null);
			server.start();

		} catch (Exception exc) {
			LOGGER.error("{}", exc);
			System.exit(1);
		}

	}

}

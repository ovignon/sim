package com.mobility.scoopptf.sim.sagt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobility.scoopptf.sim.handlers.SimFileHandler;
import com.mobility.scoopptf.sim.handlers.SimResourceHandler;
import com.sun.net.httpserver.HttpServer;

public class SimSagtApp {

	public static final int DEFAULT_PORT = 9191;

	private static Logger LOGGER = LoggerFactory.getLogger(SimSagtApp.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int port = DEFAULT_PORT;

		if (args != null && args.length > 0 && StringUtils.isNumeric(args[0])) {
			port = Integer.valueOf(args[0]);
		}

		try {

			LOGGER.info("===== SAGT Simulation on port {} =====", port);

			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/push", new SimResourceHandler());
			server.createContext("/snapshot-evt-info", new SimResourceHandler());
			server.createContext("/snapshot-conf-pmv", new SimResourceHandler("/tests-c3-m260/snapshotConfPmv-{0}.xml",
					"c:/projets/scoop/tmp/number.txt"));
			server.createContext("/snapshot-info-pmv", new SimResourceHandler("/tests-c3-m260/snapshotInfoPmv-{0}.xml",
					"c:/projets/scoop/tmp/number.txt"));
			server.createContext("/snapshot-info-parking", new SimResourceHandler());
			server.createContext("/tmp", new SimFileHandler("C:\\projets\\scoop\\tmp\\tmp-bashrc.txt"));
			server.setExecutor(null);
			server.start();

		} catch (Exception exc) {
			LOGGER.error("{}", exc);
			System.exit(1);
		}

	}

}

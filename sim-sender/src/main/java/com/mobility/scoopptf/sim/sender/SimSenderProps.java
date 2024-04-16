package com.mobility.scoopptf.sim.sender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimSenderProps {

	private static Logger LOGGER = LoggerFactory.getLogger(SimSenderProps.class);
	private static SimSenderProps INSTANCE;

	private static final String PROP_NAME_PLATEFORME_URL_PUSH_SAGT = "plateforme.urlPushSagt";
	private static final String PROP_NAME_PLATEFORME_URL_PUSH_UBR = "plateforme.urlPushUbr";

	private String plateformeUrlPushSagt = null;
	private String plateformeUrlPushUbr = null;

	private SimSenderProps() {
		Properties props = new Properties();
		try (InputStream inputProps = this.getClass().getResourceAsStream("/application.properties")) {
			props.load(inputProps);
			plateformeUrlPushSagt = props.getProperty(PROP_NAME_PLATEFORME_URL_PUSH_SAGT);
			plateformeUrlPushUbr = props.getProperty(PROP_NAME_PLATEFORME_URL_PUSH_UBR);
		} catch (IOException exc) {
			LOGGER.error(exc.getMessage());
		}
	}

	public static SimSenderProps getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimSenderProps();
		}
		return INSTANCE;
	}

	public String getPlateformeUrlPushSagt() {
		return plateformeUrlPushSagt;
	}

	public String getPlateformeUrlPushUbr() {
		return plateformeUrlPushUbr;
	}

}

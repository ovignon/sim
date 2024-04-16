package com.mobility.scoopptf.sim.sender;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SimSenderApp {

	private static Logger LOGGER = LoggerFactory.getLogger(SimSenderApp.class);

	public static void main(String[] args) throws SAXException, IOException, TransformerException {

		LOGGER.info("===== URL push SAGT  " + SimSenderProps.getInstance().getPlateformeUrlPushSagt());
		LOGGER.info("===== URL push UBR  " + SimSenderProps.getInstance().getPlateformeUrlPushUbr());

//		SimSenderService.getInstance().sendMultiMessagesToSagt("/crf_141_b1_001/CRF_141_B1_001_", 100);
		SimSenderService.getInstance().sendMultiMessagesToSagtWithSubstitutor(SimSenderUrlType.SAGT,
				"/crf_141_b1_001/CRF_141_B1_001.xml", 100, 5);

	}

}

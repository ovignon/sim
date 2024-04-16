package com.mobility.scoopptf.sim.sender;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SimSenderService {

	private static Logger LOGGER = LoggerFactory.getLogger(SimSenderService.class);
	private static SimSenderService INSTANCE;

	private DocumentBuilderFactory documentBuilderfactory;
	private DocumentBuilder documentBuilder;

	private TransformerFactory transformerFactory;
	private Transformer transformer;

	private SimSenderService() {
		documentBuilderfactory = DocumentBuilderFactory.newInstance();
//		documentBuilderfactory.setAttribute(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
//		documentBuilderfactory.setAttribute(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
		try {
			documentBuilder = documentBuilderfactory.newDocumentBuilder();
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
		} catch (ParserConfigurationException | TransformerConfigurationException exc) {
			LOGGER.error("{}", exc);
		}

	}

	public static SimSenderService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimSenderService();
		}
		return INSTANCE;
	}

	public String documentToString(Document document) throws TransformerException {
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		DOMSource domSource = new DOMSource(document);
		transformer.transform(domSource, result);
		return writer.toString();
	}

	public void sendMultiMessages(SimSenderUrlType urlType, String resourceBaseLocation, long delayMs) {

		boolean isFirst = true;
		for (int msgNum = 0; msgNum < 1000; ++msgNum) {
			String msgNumStr = StringUtils.leftPad(Integer.toString(msgNum), 3, '0');
			String resourceLocation = resourceBaseLocation + msgNumStr + ".xml";
			String resourceContent = readResource(resourceLocation);
			if (StringUtils.isNotEmpty(resourceContent)) {
				if (!isFirst) {
					try {
						Thread.sleep(delayMs);
					} catch (InterruptedException exc) {
						LOGGER.error("{}", exc);
					}
				}
				isFirst = false;
				postMessageSagt(urlType, resourceContent);
			}
		}

	}

	public void sendMultiMessagesToSagtWithSubstitutor(SimSenderUrlType urlType, String resourceLocation, long delayMs,
			int nbMessages) {

		boolean isFirst = true;
		Map<String, String> parameters = new HashMap<>();
		for (int msgNum = 0; msgNum < nbMessages; ++msgNum) {
			String resourceContent = readResource(resourceLocation);
			if (StringUtils.isNotEmpty(resourceContent)) {
				if (!isFirst) {
					try {
						Thread.sleep(delayMs);
					} catch (InterruptedException exc) {
						LOGGER.error("{}", exc);
					}
				}
				isFirst = false;
				parameters.clear();
				parameters.put("msgNum", Integer.toString(msgNum));
				parameters.put("msgNum3Chars", StringUtils.leftPad(Integer.toString(msgNum), 3, '0'));
				StringSubstitutor substitutor = new StringSubstitutor(parameters);
				resourceContent = substitutor.replace(resourceContent);
				postMessageSagt(urlType, resourceContent);
			}
		}

	}

	public void postMessageSagt(SimSenderUrlType urlType, String messageContent) {
		String url = null;
		if (urlType == SimSenderUrlType.SAGT) {
			url = SimSenderProps.getInstance().getPlateformeUrlPushSagt();
		} else if (urlType == SimSenderUrlType.UBR) {
			url = SimSenderProps.getInstance().getPlateformeUrlPushUbr();
		} else {
			throw new IllegalArgumentException();
		}
		postMessage(url, messageContent);
	}

	public void postMessage(String url, String messageContent) {
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(messageContent);
		httpPost.setEntity(entity);
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			httpclient.execute(httpPost, new HttpClientResponseHandler<String>() {

				@Override
				public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
					LOGGER.info("code : " + response.getCode());
					InputStream input = response.getEntity().getContent();
					String resultContent = IOUtils.toString(input, StandardCharsets.UTF_8);
					LOGGER.info("content : " + resultContent);
					return resultContent;
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String readResource(String resourceLocation) {

		String result = null;

		if (this.getClass().getResource(resourceLocation) == null) {
			return null;
		}

		try (InputStream input = this.getClass().getResourceAsStream(resourceLocation)) {
			result = IOUtils.toString(input, StandardCharsets.UTF_8);
		} catch (Exception exc) {
			LOGGER.error("{}", exc);
		}

		return result;

	}

	public Document readResourceAsDoc(String resourceLocation) throws SAXException, IOException {

		String xmlString = readResource(resourceLocation);
		return documentBuilder.parse(new InputSource(new StringReader(xmlString)));

	}

	public void testGet() {
		HttpGet httpGet = new HttpGet("http://localhost:8088/scoop-ptf/equipements/tous");
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			httpclient.execute(httpGet, new HttpClientResponseHandler<String>() {

				@Override
				public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
					LOGGER.info("code : " + response.getCode());
					InputStream input = response.getEntity().getContent();
					String resultContent = IOUtils.toString(input, StandardCharsets.UTF_8);
					LOGGER.info("content : " + resultContent);
					return resultContent;
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDom() throws SAXException, IOException {
		Document doc = readResourceAsDoc("/CRF_210_DENM_001.xml");
		NodeList children = doc.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node node = children.item(i);
			System.out.println(node.getNodeType());
			System.out.println(node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				element.setAttribute("xmlns:age", "52");
				System.out.println(element.getAttributes().getLength());
				for (int a = 0; a < element.getAttributes().getLength(); ++a) {
					Node attributeNode = element.getAttributes().item(a);
					if (attributeNode.getNodeType() == Node.ATTRIBUTE_NODE) {
						Attr attr = (Attr) attributeNode;
						System.out.println(attr.getName() + " : " + attr.getValue());
					}
				}
			}
		}

	}

}

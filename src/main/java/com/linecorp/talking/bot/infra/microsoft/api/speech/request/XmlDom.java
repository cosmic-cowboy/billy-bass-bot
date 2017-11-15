/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech.request;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlDom {
    public static String createDom(final VoiceFont voiceFont, final String textToSynthesize){
    	Document doc = null;
    	Element speak, voice;
    	try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			doc = builder.newDocument();
			if (doc != null){
				speak = doc.createElement("speak");
				speak.setAttribute("version", "1.0");
				speak.setAttribute("xml:lang", "en-us");
				voice = doc.createElement("voice");
				voice.setAttribute("xml:lang", voiceFont.locale);
				voice.setAttribute("xml:gender", voiceFont.gender);
				voice.setAttribute("name", voiceFont.serviceName);
				voice.appendChild(doc.createTextNode(textToSynthesize));
				speak.appendChild(voice);
				doc.appendChild(speak);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformDom(doc);
    }

    private static String transformDom(Document doc){
	    StringWriter writer = new StringWriter();
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
}

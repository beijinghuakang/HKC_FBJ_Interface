package com.hkc.util;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * @author csc
 * @create 2019-02-13 8:44
 */
public class ReadLocalConfig {
	public  static HashMap<String, String> localXml = new HashMap<String, String>();
	
	@SuppressWarnings("rawtypes")
	public ReadLocalConfig() {
		SAXReader sax = new SAXReader();
		Document document;
		try {
			document = sax.read(ReadLocalConfig.class.getResourceAsStream("/localConfig.xml"));
			Element rootElement = document.getRootElement();
			Iterator it = rootElement.elementIterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();
				localXml.put(element.attributeValue("key"),element.getText());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

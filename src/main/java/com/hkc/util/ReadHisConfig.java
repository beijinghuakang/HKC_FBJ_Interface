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
public class ReadHisConfig {
	public static HashMap<String, String> hisXml = new HashMap<String, String>();
	@SuppressWarnings("rawtypes")
	public ReadHisConfig() {
		SAXReader sax = new SAXReader();
		Document document;
		try {
			document = sax.read(ReadHisConfig.class.getResourceAsStream("/hisConfig.xml"));
			Element rootElement = document.getRootElement();
			Iterator it = rootElement.elementIterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();
				hisXml.put(element.attributeValue("key"),element.getText());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

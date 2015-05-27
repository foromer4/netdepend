package com.picscout.depend.dependency.utils;
import java.util.List;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper fo xml actions
 * @author OSchliefer
 *
 */
public final class XmlUtils {

	
	 static final Logger logger = LogManager.getLogger(XmlUtils.class.getName());
	
	 /**
	  * Read text from xml file
	  * @param file file name
	  * @param xpathExpression xpath to get text from
	  * @return value in xpath
	  */
	public static List<String> readTextFromXML(String file, String xpathExpression)
	{
		List<String> reuslts= new ArrayList<String>();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(file));			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expression = xpath.compile(xpathExpression); 
			NodeList resultNodes = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
			for(int i = 0; i < resultNodes.getLength(); i++)
			{
				reuslts.add(resultNodes.item(i).getTextContent());
			}
		}
			catch(Exception ex)
			{
				logger.warn("Error parsing file: "+ file + " for xpath: " + xpathExpression, ex);
			}
			return reuslts;
	}
	
	/**
	 * Read attribute from xml file
	 * @param file file path
	 * @param xpathExpression xpath 
	 * @param attribute attribute name to get value for
	 * @return value of the requested attribute in the xpath defined
	 */
	public static List<String> readAttributeFromXML(String file, String xpathExpression, String attribute)
	{
		List<String> reuslts= new ArrayList<String>();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(file));			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expression = xpath.compile(xpathExpression); 
			NodeList resultNodes = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
			for(int i = 0; i < resultNodes.getLength(); i++)
			{
				Node attributeNode =resultNodes.item(i).getAttributes().getNamedItem(attribute);
				if(attributeNode!= null){
				reuslts.add(attributeNode.getTextContent());
				}
			}
		}
			catch(Exception ex)
			{
				logger.warn("Error parsing file: "+ file + " for xpath: " + xpathExpression + " attribute:" + attribute, ex);			
			}
			return reuslts;
	}
	
	
	/**
	 * Read element value from xml
	 * @param file file path
	 * @param elementName element name
	 * @return read value from xml by element name
	 */
	public static String readValueFromXML(String file, String elementName)
	{
		String result = null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(file));			
			result = getString(elementName ,document.getDocumentElement());			
			}
			catch(Exception ex)
			{
				logger.warn("Error parsing file: "+ file + " for element: " + elementName, ex);
			}
			return result;
	}
	
	
	
	protected static String getString(String tagName, Element rootElement) {
        NodeList list = rootElement.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();
            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }
}

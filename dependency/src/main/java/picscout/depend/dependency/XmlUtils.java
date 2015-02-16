package picscout.depend.dependency;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XmlUtils {

	
	
	
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
				//TODO - save to log [O.S]
			}
			return reuslts;
	}
	
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
				//TODO - save to log [O.S]				
			}
			return reuslts;
	}
	
	
	
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
				//TODO - save to log [O.S]
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

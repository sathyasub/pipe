package pipe;




import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.Document;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.DOMException;

// For write operation
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;



public class XSLTransformer {
	// Global value so it can be ref'd by the tree-adapter
	static Document document;

	//    public static void main(String[] argv) throws Exception {
	public static String transformToXml(String xslFilePath, String payrollXml, String event,String outputxml ) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		factory.setCoalescing(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);

		try {
			File stylesheet = new File(xslFilePath);
			File datafile = new File(payrollXml);

			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(datafile);

//			XMLUnit.setIgnoreWhitespace(true);

			InputStream agent = new FileInputStream(datafile);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));
			FileOutputStream outputStream = new FileOutputStream(new File(outputxml));
			transformer.transform(new StreamSource(agent), new StreamResult(outputStream));
			
		} catch (TransformerConfigurationException tce) {
			// Error generated by the parser
			System.out.println("\n** Transformer Factory error");
			System.out.println("   " + tce.getMessage());

			// Use the contained exception, if any
			Throwable x = tce;

			if (tce.getException() != null) {
				x = tce.getException();
			}

			x.printStackTrace();
		} catch (TransformerException te) {
			// Error generated by the parser
			System.out.println("\n** Transformation error");
			System.out.println("   " + te.getMessage());

			// Use the contained exception, if any
			Throwable x = te;

			if (te.getException() != null) {
				x = te.getException();
			}

			x.printStackTrace();
		} 
		System.out.println("transform successfull");
		return outputxml;
	} // main
}

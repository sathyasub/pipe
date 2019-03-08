package pipe;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

/**
 *
 * Java program to compare two XML files using XMLUnit example

 * @author Javin Paul
 */
public class XMLComparator {


	//	    public static void main(String args[]) throws FileNotFoundException, 
	//	                                                  SAXException, IOException {
	public static List getCompareResult(String sampleXml, String transformedXml, String event) throws Exception
	{
		//		    	String sampleXml="/Users/in-cbe-sathya/Documents/Transformer/Input/AUS/AUS_PayROLL_ActualOP/HireActualOP.xml";
		//		    	String transformedXml="/Users/in-cbe-sathya/Documents/Transformer/Input/AUS/AUS_PayROLL_ExpectedOP/Hire.xml";

		// reading two xml file to compare in Java program
		FileInputStream fis1 = new FileInputStream(sampleXml);
		FileInputStream fis2 = new FileInputStream(transformedXml);
		// using BufferedReader for improved performance
		BufferedReader  source = new BufferedReader(new InputStreamReader(fis1));
		BufferedReader  target = new BufferedReader(new InputStreamReader(fis2));

		//configuring XMLUnit to ignore white spaces
		XMLUnit.setIgnoreWhitespace(true);


		//comparing two XML using XMLUnit in Java
		List differences = compareXML(source, target);

		
		
		//showing differences found in two xml files
		//        printDifferences(differences);
		return differences;
	}    

	public static List compareXML(Reader source, Reader target) throws
	SAXException, IOException{
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true);

		//creating Diff instance to compare two XML files
		Diff xmlDiff = new Diff(source, target);
		//for getting detailed differences between two xml files
		DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);

		return detailXmlDiff.getAllDifferences();
	}

	public static void printDifferences(Logger logger, List<?> differences, String event, FileHandler fh) throws Exception{
		int totalDifferences = differences.size();
		logger.info("===============================");
		logger.info("Total differences : " + totalDifferences);
		logger.info("================================");
		System.out.println("Total Differences found for : "+event + " is "+differences.size());
		for(Object difference : differences){
			logger.info("Difference found for the "+event+": "+difference);
//				Runtime.getRuntime().exec("Difference found for the "+event+": "+difference);
		}
		
		String s;
		if(differences.size()==0)
			s=event+" is successfully completed";
		else
			s=event+" is failed with "+differences.size()+" failures";
		
		logger.info("Result: "+ s);
		System.out.println("Result: "+ s);
		
	}


}
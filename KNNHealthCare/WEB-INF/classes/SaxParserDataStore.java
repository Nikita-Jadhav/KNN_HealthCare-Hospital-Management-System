
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    DoctorSpeciality doctorspeciality;
	Medicine medicine;
	Accessory accessory;
    
    static HashMap<String,DoctorSpeciality> doctorspecialities;
	static HashMap<String,Medicine> medicines;
	static HashMap<String,Accessory> accessories;
    
    String consoleXmlFileName;
	HashMap<String,String> visitingDetailsHashMap;
    String elementValueRead;
	String currentElement="";
	
    public SaxParserDataStore()
	{
	}
	
	public SaxParserDataStore(String consoleXmlFileName) {
    this.consoleXmlFileName = consoleXmlFileName;
    doctorspecialities = new HashMap<String,DoctorSpeciality>();
	medicines = new HashMap<String,Medicine>();
	accessories=new HashMap<String, Accessory>();
	visitingDetailsHashMap=new HashMap<String,String>();	
	//accessoryHashMap=new HashMap<String,String>();
	parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}
	
////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for voice assistants,phones,laptops,smart watches, fitness watches, headphones, pet trackers, virtual rea;lity, accessories etc.
   @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException
	{
        if (elementName.equalsIgnoreCase("doctor")) 
		{
			currentElement="doctor";
			doctorspeciality = new DoctorSpeciality();
            doctorspeciality.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("Medicine")) 
		{
			currentElement="Medicine";
			medicine = new Medicine();
            medicine.setId(attributes.getValue("id"));
		}
		if (elementName.equals("accessory"))
		{
			currentElement="accessory";
			accessory=new Accessory();
			accessory.setId(attributes.getValue("id"));
	    }
    }
	// when xml end element is parsed store the data into respective hashmap for doctorspeciality respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException
	{ 
        if (element.equals("doctor")) {
			doctorspecialities.put(doctorspeciality.getId(),doctorspeciality);
			return;
        }
		if (element.equals("Medicine")) {
			medicines.put(medicine.getId(),medicine);
			return;
        }
		if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equals("visitingDays") && currentElement.equals("doctor")) 
		{
			visitingDetailsHashMap.put(elementValueRead,elementValueRead);
		}
		if (element.equalsIgnoreCase("visitingDetails") && currentElement.equals("doctor")) {
			doctorspeciality.setVisitingDetails(visitingDetailsHashMap);
			visitingDetailsHashMap=new HashMap<String,String>();
			return;
		}
		
        if (element.equalsIgnoreCase("doctorName")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setName(elementValueRead); 
			return;
        }
		if (element.equalsIgnoreCase("speciality")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setSpeciality(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("doctorImage")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setDoctorImage(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("gender")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setGender(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("languages")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setLanguages(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("emailId")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setEmailId(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("contactNumber_1")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setContactNumber_1(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("contactNumber_2")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setContactNumber_2(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("qualification")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setQualification(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("doctorDescription")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setDoctorDescription(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("yearsInPractice")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setYearsInPractice(elementValueRead);
			return;
        }
		if (element.equalsIgnoreCase("visitingPrice")) {
		    if(currentElement.equals("doctor"))
				doctorspeciality.setVisitingPrice(Double.parseDouble(elementValueRead));
			return;
        }
		/*********************Medicine & Accessory************************/
		if (element.equalsIgnoreCase("name")) {
		    if(currentElement.equals("Medicine"))
				medicine.setProductName(elementValueRead); 
			if(currentElement.equals("accessory"))
				accessory.setProductName(elementValueRead);     
			return;
        }
		if (element.equalsIgnoreCase("productType")) {
		    if(currentElement.equals("Medicine"))
				medicine.setProductType(elementValueRead);
			if(currentElement.equals("accessory"))
				accessory.setProductType(elementValueRead); 
			return;
        }
		if (element.equalsIgnoreCase("price")) {
		    if(currentElement.equals("Medicine"))
				medicine.setProductPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equals("accessory"))
				accessory.setProductPrice(Double.parseDouble(elementValueRead)); 
			return;
        }if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("Medicine"))
				medicine.setProductImage(elementValueRead);
			if(currentElement.equals("accessory"))
				accessory.setProductImage(elementValueRead);
			return;
        }if (element.equalsIgnoreCase("manufacturer")) {
		    if(currentElement.equals("Medicine"))
				medicine.setProductManufacturer(elementValueRead);
			if(currentElement.equals("accessory"))
				accessory.setProductManufacturer(elementValueRead);
			return;
        }if (element.equalsIgnoreCase("manufactureDate")) {
		    if(currentElement.equals("Medicine"))
			{
				try 
				{
					DateFormat df = new SimpleDateFormat("mm/dd/yyyy");				
					//medicine.setManufactureDate(df.parse(elementValueRead));
					medicine.setManufactureDate(elementValueRead);
				}
				catch (Exception e){}
			}
			if(currentElement.equals("accessory"))
			{
				try 
				{
					DateFormat df = new SimpleDateFormat("mm/dd/yyyy");				
					//accessory.setManufactureDate(df.parse(elementValueRead));
					accessory.setManufactureDate(elementValueRead);
				}
				catch (Exception e){}
			}
			return;
        }if (element.equalsIgnoreCase("bestBeforeDate")) {
		    if(currentElement.equals("Medicine"))
			{
				try
				{
					DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
					//medicine.setBestBeforeDate(df.parse(elementValueRead));
					medicine.setBestBeforeDate(elementValueRead);
				}
				catch (Exception e){}
			}
			if(currentElement.equals("accessory"))
			{
				try
				{
					DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
					//accessory.setBestBeforeDate(df.parse(elementValueRead));
					accessory.setBestBeforeDate(elementValueRead);
				}
				catch (Exception e){}
			}			
			return;
        }if (element.equalsIgnoreCase("description")) {
		    if(currentElement.equals("Medicine"))
				medicine.setDescription(elementValueRead);  
			if(currentElement.equals("accessory"))
				accessory.setDescription(elementValueRead);
			return;
        }if (element.equalsIgnoreCase("discount")) {
		    if(currentElement.equals("Medicine"))
				medicine.setDiscount(Double.parseDouble(elementValueRead));
			if(currentElement.equals("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));
			return;
        }if (element.equalsIgnoreCase("originalProductquantity")) {
		    if(currentElement.equals("Medicine"))
				medicine.setOriginalProductquantity(Integer.parseInt(elementValueRead));
			if(currentElement.equals("accessory"))
				accessory.setOriginalProductquantity(Integer.parseInt(elementValueRead)); 
			return;
        }
		if (element.equalsIgnoreCase("manufacturerRebate")) {
		    if(currentElement.equals("Medicine"))
				medicine.setManufacturerRebate(Double.parseDouble(elementValueRead)); 
			if(currentElement.equals("accessory"))
				accessory.setManufacturerRebate(Double.parseDouble(elementValueRead));
			return;
        }
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }

    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\KNNHealthCare\\ProductCatalog.xml");
    }
}

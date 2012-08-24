package edu.gatech.gpslock.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.location.Location;
import android.util.Log;

public class GPSUtil 
{
	private static final String TAG = "GPSUtil";

	public static ArrayList<String> parseKML(String kml)
	{
		ArrayList<String> locations = null;

		try 
		{
			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create input source */
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(kml));

			/* Create a new ContentHandler and apply it to the XML-Reader */
			KMLHandler kmlHandler = new KMLHandler();
			xr.setContentHandler(kmlHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(is);
			/* Parsing has finished. */

			locations = kmlHandler.getLocationList();
		} 
		catch (ParserConfigurationException e) 
		{
			Log.e(TAG, "ParserConfigurationException: " + e.getMessage());
		} 
		catch (SAXException e) 
		{
			Log.e(TAG, "SAXException: " + e.getMessage());
		} 
		catch (IOException e) 
		{
			Log.e(TAG, "IOException: " + e.getMessage());
		}

		return locations;
	}

	public static ArrayList<Location> parseLocation (ArrayList<String> raws)
	{
		ArrayList<Location> locations = new ArrayList<Location>();

		try
		{
			for (String raw : raws)
			{
				/* latitude, longitude, height */
				String[] rawArray = raw.split(",");
				Location location = new Location("");
				location.setLatitude(Double.parseDouble(rawArray[0]));	// unit: degree
				location.setLongitude(Double.parseDouble(rawArray[1]));	// unit: degree
				locations.add(location);
			}
		}
		catch (NumberFormatException e)
		{
			Log.e(TAG, "NumberFormatException: " + e.getMessage());
		}

		return locations;
	}

	private static class KMLHandler extends DefaultHandler
	{
		private ArrayList<String> locations;
		private StringBuffer buff = null;
		private boolean buffering = false; 

		/* <coordinates>-73.964722,40.791523,0.000000</coordinates> 
		 * <coordinates>latitude, longitude, height</coordinates> */

		@Override
		public void startDocument() throws SAXException 
		{
			locations = new ArrayList<String>();
		} 

		@Override
		public void endDocument() throws SAXException 
		{
			// Some sort of finishing up work
		} 

		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
		{
			if (localName.equals("coordinates")) 
			{
				buff = new StringBuffer("");
				buffering = true;
			}   
		} 

		@Override
		public void characters(char ch[], int start, int length) 
		{
			if (buffering) 
			{
				buff.append(ch, start, length);
			}
		} 

		@Override
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
		{
			if (localName.equals("coordinates")) 
			{
				buffering = false; 
				locations.add(buff.toString());
			}
		}

		public ArrayList<String> getLocationList()
		{
			return locations;
		}
	}
}

package com.luke.example.generimapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
	
	class rock {
		private int ID;
		private double X;
		private double Y;

		public rock(int id, double x, double y) {
			ID = id;
			X = x;
			Y = y;
		}
		public int getID(){return ID;}
		public double getX(){return X;}
		public double getY(){return Y;}
	}
	
	private ArrayList<rock> rocks = new ArrayList<rock>();
	private double avgX, avgY;
	private double spanX, spanY;
	
	public XMLReader(){
		
	}
	
	public void parseURL(String URL_) {

		try {
			URL feedURL = new URL(URL_);
			InputStream is = feedURL.openConnection().getInputStream();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("sample");
			/**
			 * NodeList nodeLstX = doc.getElementsByTagName("x"); NodeList
			 * nodeLstY = doc.getElementsByTagName("y"); NodeList nodeLstID =
			 * doc.getElementsByTagName("long");
			 */
			
			double maxX = -360.0, minX = 360.0, maxY = -360.0, minY = 360.0;

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);
				// System.out.println(fstNode.getNodeName());

				// if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList Point = fstElmnt
						.getElementsByTagName("org.postgis.Point");
				Element PointElmnt = (Element) Point.item(0);
				NodeList xChild = PointElmnt.getElementsByTagName("x");
				NodeList yChild = PointElmnt.getElementsByTagName("y");
				Element xElmnt = (Element) xChild.item(0);
				Element yElmnt = (Element) yChild.item(0);
				NodeList x = xElmnt.getChildNodes();
				NodeList y = yElmnt.getChildNodes();

				NodeList fstNodeI = fstElmnt.getElementsByTagName("id");
				Element IDElmnt = (Element) fstNodeI.item(0);
				NodeList fstNodeID = IDElmnt.getElementsByTagName("long");
				Element LongElmnt = (Element) fstNodeID.item(0);
				NodeList lstID = LongElmnt.getChildNodes();

				// System.out.println("ID: " + lstID.item(0).getNodeValue() +
				// " X: " +
				// x.item(0).getNodeValue() + " Y: " +
				// y.item(0).getNodeValue());
				double xent = Double.parseDouble(x.item(0).getNodeValue().trim());
				double yent = Double.parseDouble(y.item(0).getNodeValue().trim());
				rocks.add(new rock(Integer.parseInt(lstID.item(0).getNodeValue().trim()), xent, yent));
				if(xent > maxX) maxX = xent;
				if(xent < minX) minX = xent;
				if(yent > maxY) maxY = yent;
				if(yent < minY) minY = yent;

			}
			spanX = maxX-minX;
			spanY = maxY-minY;
			avgX = (maxX+minX)/2.0;
			avgY = (maxY+minY)/2.0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int size(){
		return rocks.size();
	}
	
	public int getID(int idx){return rocks.get(idx).getID();}
	public double getX(int idx){return rocks.get(idx).getX();}
	public double getY(int idx){return rocks.get(idx).getY();}
	
	public double getAvgX(){
		return avgX;
	}
	public double getAvgY(){
		return avgY;
	}
	public double getSpanX(){
		return spanX;
	}
	public double getSpanY(){
		return spanY;
	}
}

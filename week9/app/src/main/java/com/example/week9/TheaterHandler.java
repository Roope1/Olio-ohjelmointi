package com.example.week9;

import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TheaterHandler {

    //Singleton model
    private static TheaterHandler th = null;
    ArrayList<Theater> theaters = new ArrayList<>();


    public static TheaterHandler getInstance(){
        if(th == null){
            th = new TheaterHandler();
        }
        return th;
    }

    public void fetchTheaters(){
        try {
            // read the XML
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String theatersXML = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(theatersXML);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            // foreach theater in XML create a theater object with the data given

            for(int i = 0; i < nodes.getLength(); i++){
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    // creating the actual object
                    theaters.add(new Theater(Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                            element.getElementsByTagName("Name").item(0).getTextContent()));

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTheaterNames(){
        ArrayList<String> theaterNames = new ArrayList<>();
        for (Theater theater : theaters){
            theaterNames.add(theater.getName());

        }
        return theaterNames;
    }

    public ArrayList<String> getMoviesByTheaterAndDate(int theaterId, Date date){
        ArrayList<String> movies = new ArrayList<>();
        String url = "https://www.finnkino.fi/xml/Schedule/?area="+ theaterId +"&dt=" + new SimpleDateFormat("dd.MM.yyyy").format(date);
        try {
            // read the XML
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("Show");

            // foreach theater in XML create a theater object with the data given

            for(int i = 0; i < nodes.getLength(); i++){
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    // creating the actual object
                    movies.add(element.getElementsByTagName("Title").item(0).getTextContent());

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return movies;
    }

}

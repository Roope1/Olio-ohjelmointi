package com.example.week9;



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
    ArrayList<Movie> movies = new ArrayList<>();

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
        } catch (ParserConfigurationException | IOException | SAXException e) {
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

    public ArrayList<String> getMoviesByTheaterAndDate(int theaterId, String date){
        if(date.equals("")) {
            date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        }
        ArrayList<String> movieNames = new ArrayList<>();
        movies.clear();
        //building the url from the given information
        String url = "https://www.finnkino.fi/xml/Schedule/?area="+ theaterId +"&dt=" + date;
        try {
            // read the XML
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("Show");

            for(int i = 0; i < nodes.getLength(); i++){
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    // creating the actual object
                    movieNames.add(element.getElementsByTagName("Title").item(0).getTextContent());
                    movies.add(new Movie(element.getElementsByTagName("Title").item(0).getTextContent(),
                            Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()),
                            element.getElementsByTagName("dttmShowStart").item(0).getTextContent().toString()));

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return movieNames;
    }

    public ArrayList<String> filterByTime(ArrayList<String> movieNames, String startTime, String endTime){
        if(startTime.isEmpty()){
            return movieNames;
        }
        if(endTime.isEmpty()){
            endTime = "23:59";
        }

        ArrayList<String> filteredMovies = new ArrayList<>();
        String [] startTimeSplitted = startTime.split(":");
        String [] endTimeSplitted = endTime.split(":");


        for(int i = 0; i<movieNames.size(); i++){
            Movie selectedMovie = movies.get(i);
            String StartTimeHour = selectedMovie.getStartTime().split(":")[0];
            String StartTimeMinute = selectedMovie.getStartTime().split(":")[1];

            /* Shit code ahead, please wear your seatbelts and try not to puke */

            if(Integer.parseInt(startTimeSplitted[0]) < Integer.parseInt(StartTimeHour) && Integer.parseInt(endTimeSplitted[0]) > Integer.parseInt(StartTimeHour)){
                filteredMovies.add(selectedMovie.getTitle());
            } else if ((Integer.valueOf(startTimeSplitted[0]) == Integer.valueOf(StartTimeHour) && (Integer.parseInt(startTimeSplitted[1]) < Integer.parseInt(StartTimeMinute)) &&
                    (Integer.valueOf(endTimeSplitted[0]) > Integer.valueOf(StartTimeHour) ))) {
                filteredMovies.add(selectedMovie.getTitle());
            }

            /* Shit code end */

        }


        return filteredMovies;
    }

}

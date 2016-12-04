package com.example.saurav.top_25_songs;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by saura on 04-12-2016.
 */

public class ParseApplication {
    private String data;
    private ArrayList<Application> applications;

    public ParseApplication(String data) {
        this.data = data;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }
    public boolean process(){
        boolean status = true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.data));
            int eventType = xpp.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new Application();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry){
                            if (tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEntry = false;
                            }else if (tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if (tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setArtist(textValue);
                            }else if (tagName.equalsIgnoreCase("price")) {
                                currentRecord.setPrice(textValue);
                            }
                        }
                        break;

                    default:
                        break;


                }
                eventType = xpp.next();

            }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return true;
    }
}

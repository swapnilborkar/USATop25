package com.swapnilborkar.ustop25;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by SWAPNIL on 05-01-2016.
 */
public class ParseSongs {

    private String xmlData;
    private ArrayList<Song> songs;

    public ParseSongs(String xmlData) {
        this.xmlData = xmlData;
        songs = new ArrayList<Song>();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public boolean process() {
        boolean status = true;
        Song currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        //Log.d("ParseSongs", "Starting tag for" + tagName);
                    if(tagName.equalsIgnoreCase("entry"))
                    {
                        inEntry = true;
                        currentRecord = new Song();
                    }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                       // Log.d("ParseSongs", "Ending tag for" + tagName);

                        if(inEntry)
                        {
                            if(tagName.equalsIgnoreCase("entry"))
                            {
                                songs.add(currentRecord);
                                inEntry = false;
                            }

                            else if (tagName.equalsIgnoreCase("name"))
                            {
                                currentRecord.setName(" "+textValue);
                            }

                            else if (tagName.equalsIgnoreCase("artist"))
                            {
                                currentRecord.setArtist(" "+textValue);
                            }

                            else if (tagName.equalsIgnoreCase("releaseDate"))
                            {
                                currentRecord.setRelease(" "+textValue.substring(0,10));
                            }
                        }
                        break;

                    default:

                        // Nothing else to do


                }
                        eventType = xpp.next();
            }

            for (Song song: songs)
            {
                Log.d("parseSongs", "**************");
                Log.d("parseSongs", "Name:" + song.getName());
                Log.d("parseSongs", "Artist:" + song.getArtist());
                Log.d("parseSongs", "Release Date:" + song.getRelease());
            }

            return true;


        } catch (XmlPullParserException e) {
            status = false;
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    }

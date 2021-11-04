package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RSSParser {

    private boolean isReady;

    private ArrayList<String> titles = new ArrayList<>();;
    private ArrayList<String> descs = new ArrayList<>();
    private ArrayList<String> pubs = new ArrayList<>();

    public RSSParser(XmlPullParserFactory factory, String urlStr) {
        isReady = false;
        new Thread(new RSSThread(factory, urlStr)).start();
    }

    public InputStream getInputStream(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTitle(int i) {
        return titles.get(i);
    }

    public String getDescription(int i) {
        return descs.get(i);
    }

    public boolean isReady() {
        return isReady;
    }

    public class RSSThread implements Runnable {

        private XmlPullParserFactory factory;
        private String urlStr;

        public RSSThread(XmlPullParserFactory factory, String urlStr){
            this.factory = factory;
            this.factory.setNamespaceAware(false);

            this.urlStr = urlStr;
        }

        @Override
        public void run() {

            try {
                InputStream in = getInputStream(urlStr);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF_8");

                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    Log.d("RSSParser", "Looping through Document");
                    if(eventType == XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")){
                            if(insideItem) {
                                Log.d("RSSParser", "Inside Title");
                                String title = xpp.nextText();
                                titles.add(title);
                                Log.d("RSSParser", "Title: " + title);
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if(insideItem) {
                                Log.d("RSSParser", "Inside Description");
                                String desc = xpp.nextText();
                                descs.add(desc);
                                Log.d("RSSParser", "Desc: " + desc);
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubdate")) {
                            if(insideItem) {
                                String pub = xpp.nextText();
                                pubs.add(pub);
                                Log.d("RSSParser", "PubDate: " + pub);
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    eventType = xpp.next();

                }
                Log.d("RSSParser", "End of Document");
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

            for(String t: titles)
                Log.d("RSSParser", t);
            for(String t: descs)
                Log.d("RSSParser", t);
            for(String t: pubs)
                Log.d("RSSParser", t);

            isReady = true;
        }
    }

}

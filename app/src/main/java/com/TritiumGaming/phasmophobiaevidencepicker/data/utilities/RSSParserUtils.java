package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessageData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RSSParserUtils {

    private final NewsletterViewModel newsLetterViewModel;

    private NewsletterViewModel.InboxType inboxType;

    public RSSParserUtils(XmlPullParserFactory factory, String urlStr, NewsletterViewModel.InboxType type, NewsletterViewModel newsLetterViewModel) {
        this.newsLetterViewModel = newsLetterViewModel;
        this.inboxType = type;

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
                if(in == null)
                    throw new IOException("RSSParser: InputStream is null! Cannot retrieve database info. Is the internet connected?");

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF_8");

                boolean insideItem = false;
                int eventType = xpp.getEventType();

                NewsletterMessagesData messageList = new NewsletterMessagesData();
                String title = null, date = null, description = null;

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    //Log.d("RSSParser", "Looping through Document");
                    if(eventType == XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")){
                            if(insideItem) {
                                title = xpp.nextText();
                                Log.d("RSSParser", "Title: " + title.substring(0,5));
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if(insideItem) {
                                description = xpp.nextText();
                                Log.d("RSSParser", "Desc: " + description.substring(0,5));
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubdate")) {
                            if(insideItem) {
                                date = xpp.nextText();
                                Log.d("RSSParser", "PubDate: " + date.substring(0,5));
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        NewsletterMessageData message = new NewsletterMessageData(title, description, date);
                        if(message.hasContent())
                            messageList.add(message);
                        title = null;
                        date = null;
                        description = null;
                    }
                    eventType = xpp.next();
                }
                in.close();

                newsLetterViewModel.addInbox(messageList, inboxType);
                Log.d("Saving InboxMessageList", "Count: " + messageList.getMessageCount());
                Log.d("RSSParser", "End of Document");

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

        }
    }

}

import CONFIG.CONFIG;
import Models.Channel;
import Models.Event;
import TVHApi.ChannelsApi;
import TVHApi.EventsApi;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;


public class Main {

    public static void main(String[] args) {

        String userPassword = CONFIG.Username + ":" + CONFIG.Password;
        String encode = Base64.getEncoder().encodeToString(userPassword.getBytes());

        ChannelsApi channels = new ChannelsApi();
        EventsApi events = new EventsApi();
        JSONArray eventJSON = null;
        JSONArray channelJSON = null;
        JSONArray jijbentzachtJSON = null;


        try {
            eventJSON = events.getAllEvensFromServer(encode);
            channelJSON = channels.getAllChannelsFromTVH(encode);
            jijbentzachtJSON = channels.getChannelImgs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Channel> channelArrayList = new ArrayList<>();

        for (int i = 0; i < channelJSON.length(); i++) {

            JSONObject jsonChannel = channelJSON.getJSONObject(i);

            String channelImg = null;
            String uuid = jsonChannel.getString("uuid");
            String channelName = jsonChannel.getString("name");
            int channelNumber = jsonChannel.getInt("number");

            ArrayList<Event> channelEvents = new ArrayList<>();

            for (int j = 0; j < jijbentzachtJSON.length(); j++) {

                //JSONObject jijbentzachtObject = jijbentzachtJSON.getJSONObject(i);
                String name = jijbentzachtJSON.getJSONObject(j).getString("channel");
                String img = jijbentzachtJSON.getJSONObject(j).getString("img");

                System.out.println(j + " " + name + "-" + channelName);
                if (name.equals(channelName) &&
                        img.contains("http")) {

                    channelImg = img;
                }
            }

            Channel channel = new Channel(uuid, channelName, channelNumber, channelImg, channelEvents);
            channelArrayList.add(channel);
        }

        for (int i = 0; i < eventJSON.length(); i++) {

            JSONObject event = eventJSON.getJSONObject(i);
            String channelUuid;
            int eventID;
            int startTime;
            int endTime;


            try {
                channelUuid = event.getString("channelUuid");
                eventID = event.getInt("eventId");
                startTime = event.getInt("start");
                endTime = event.getInt("stop");
            } catch (JSONException e) {
                System.out.println("necessary content not found: " + event.toString());
                continue;
            }

            String title;
            try {
                title = event.getString("title");
            } catch (JSONException e) {
                title = "No tile";
            }

            int episodeId;
            try {
                episodeId = event.getInt("episodeId");
            } catch (JSONException e) {
                episodeId = 0;
            }


            int nextEventId;
            try {
                nextEventId = event.getInt("nextEventId");
            } catch (JSONException e) {
                nextEventId = 0;
            }

            String description;
            try {
                description = event.getString("description");
            } catch (JSONException e) {
                description = "No description";
            }

            Event event1 = new Event(channelUuid, title, description, eventID, startTime, endTime, nextEventId, episodeId);

            for (Channel ch : channelArrayList)
            {
                if(ch.getUuid().equals(channelUuid))
                {
                    ch.addEvent(event1);
                }
            }
        }

        // sorting arraylist to get the channelnumbers in correct order in plex
        //channelArrayList.sort(Channel::compareTo);

        // make xml file
        xmlParcer(channelArrayList);
    }

    public static void xmlParcer(ArrayList<Channel> channels)
    {
        Document root = DocumentHelper.createDocument();

        Element document = root.addElement("tv");

        for (Channel c : channels) {

            Element channel = document.addElement("channel");
            channel.addAttribute("id", "" + c.getChannelNumber());

            channel.addElement("display-name")
                    .addText(c.getChannelName());

            channel.addElement("icon")
                    .addAttribute("src", c.getChannelImg());
        }

        for (Channel c : channels) {

            for (Event e: c.getEvents()) {

                Element program = document.addElement("programme");
                program.addAttribute("start", e.getStartTime() + " +0200");
                program.addAttribute("stop", e.getEndTime() + " +0200");
                program.addAttribute("channel", c.getChannelNumber() + "");

                program.addElement("title")
                        .addAttribute("lang","dut")
                        .addText(e.getTitle());

                program.addElement("desc")
                        .addAttribute("lang", "dut")
                        .addText(e.getDescription());
            }

        }

        try {
            OutputStream outputStream = new FileOutputStream(CONFIG.outputPath);

            String header = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                    "<!DOCTYPE tv SYSTEM \"xmltv.dtd\">";

            outputStream.write(header.getBytes());

            OutputFormat format = OutputFormat.createPrettyPrint();

            XMLWriter writer = new XMLWriter(outputStream, format);

            writer.write(document);

            outputStream.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
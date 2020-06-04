package parsers;

import Models.Channel;
import Models.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class ChannelParser {

    public static ArrayList<Channel> channelsParser(JSONArray channelsJSON, JSONArray eventsJSON, JSONArray imgUrlJSON) {
        ArrayList<Channel> channels = new ArrayList<>();

        for (int i = 0; i < channelsJSON.length(); i++) {

            JSONObject jsonChannel = channelsJSON.getJSONObject(i);

            String uuid = jsonChannel.getString("uuid");
            String channelName = jsonChannel.getString("name");
            int channelNumber = jsonChannel.getInt("number");
            String channelImg = null;

            for (int j = 0; j < imgUrlJSON.length(); j++)
            {
                JSONObject url = imgUrlJSON.getJSONObject(j);
                if (channelName.equals(url.getString("channel")) && url.getString("img").contains("http"))
                {
                    channelImg = url.getString("img");
                    break;
                }
            }

            Channel channel = new Channel(uuid, channelName, channelNumber, channelImg);
            channels.add(channel);
        }

        addEventsToChannels(eventsJSON, channels);

        return channels;
    }

    public static void addEventsToChannels(JSONArray eventsJSON, ArrayList<Channel> channels) {

        for (int i = 0; i < eventsJSON.length(); i++) {

            JSONObject JSONevent = eventsJSON.getJSONObject(i);
            String title, channelUuid;
            int eventID, startTime, endTime, episodeId, nextEventId;

            try {
                channelUuid = JSONevent.getString("channelUuid");
                startTime = JSONevent.getInt("start");
                endTime = JSONevent.getInt("stop");
            } catch (JSONException e) {
                System.out.println("necessary content not found: " + JSONevent.toString());
                continue;
            }

            try {
                eventID = JSONevent.getInt("eventId");
            } catch (JSONException e) {
                eventID = 0;
            }

            try {
                title = JSONevent.getString("title");
            } catch (JSONException e) {
                title = "No tile";
            }

            try {
                episodeId = JSONevent.getInt("episodeId");
            } catch (JSONException e) {
                episodeId = 0;
            }

            try {
                nextEventId = JSONevent.getInt("nextEventId");
            } catch (JSONException e) {
                nextEventId = 0;
            }

            String description;
            try {
                description = JSONevent.getString("description");
            } catch (JSONException e) {
                description = "No description";
            }

            Event event = new Event(channelUuid, title, description, eventID, startTime, endTime, nextEventId, episodeId);

            for (Channel ch : channels) {
                if (ch.getUuid().equals(channelUuid)) {
                    ch.addEvent(event);
                }
            }
        }
    }
}

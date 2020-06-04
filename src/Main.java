import CONFIG.CONFIG;
import Models.Channel;
import TVHApi.ChannelsApi;
import TVHApi.EventsApi;
import org.json.JSONArray;
import parsers.ChannelParser;
import parsers.TVXMLParser;

import java.util.ArrayList;
import java.util.Base64;

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

        ArrayList<Channel> channelArrayList = ChannelParser.channelsParser(channelJSON, eventJSON, jijbentzachtJSON);
        // sorting arraylist to get the channelnumbers in correct order in plex
        //channelArrayList.sort(Channel::compareTo);

        // make xml file
        TVXMLParser.xmlParser(channelArrayList);
    }
}
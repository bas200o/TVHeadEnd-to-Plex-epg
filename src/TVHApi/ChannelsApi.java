package TVHApi;

import CONFIG.CONFIG;
import Models.Channel;
import Models.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChannelsApi {

    public JSONArray getChannelsFromTVH(String authBasic, int limit) throws IOException {

        URL tvhurl = new URL( CONFIG.tvh + "/api/channel/grid?limit=" + limit);

        HttpURLConnection connection = (HttpURLConnection) tvhurl.openConnection();

        connection.setRequestMethod("GET");
        String basicauth = "Basic " + authBasic;

        connection.setRequestProperty("Authorization", basicauth);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray entries = jsonObject.getJSONArray("entries");

        return entries;
    }

    public int getChannelTotalTVH(String authBasic) throws IOException {
        URL tvhurl = new URL( CONFIG.tvh + "/api/channel/grid?limit=0");

        HttpURLConnection connection = (HttpURLConnection) tvhurl.openConnection();

        connection.setRequestMethod("GET");
        String basicauth = "Basic " + authBasic;

        connection.setRequestProperty("Authorization", basicauth);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        int total = jsonObject.getInt("total");

        return total;
    }

    public JSONArray getAllChannelsFromTVH(String authBasic) throws Exception
    {
        int channels = getChannelTotalTVH(authBasic);
        return getChannelsFromTVH(authBasic, channels);
    }

    public JSONArray getChannelImgs() throws IOException {
        URL tvhurl = new URL( CONFIG.channelURLIMG);

        HttpURLConnection connection = (HttpURLConnection) tvhurl.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray entries = jsonObject.getJSONArray("channels");

        return entries;
    }
}

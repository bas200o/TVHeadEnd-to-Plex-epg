package TVHApi;

import CONFIG.CONFIG;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChannelsApi {

    public JSONArray getChannelsFromTVH(String authBasic, int limit) throws Exception {

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

    public int getChannelTotalTVH(String authBasic)throws Exception
    {
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

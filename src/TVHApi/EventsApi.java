package TVHApi;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import CONFIG.*;

public class EventsApi {

    public JSONObject getEventFromServer(String authBasic, int limit) throws Exception {

        URL tvhurl = new URL( CONFIG.tvh + "/api/epg/events/grid?limit=" + limit);

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

        return jsonObject;
    }

    public int getCountFromServer(String authBasic) throws Exception {
        JSONObject jsonObject = getEventFromServer(authBasic, 0);
        return jsonObject.getInt("totalCount");
    }

    public JSONArray getAllEvensFromServer(String authBasic) throws Exception {
        int indexlenth = getCountFromServer(authBasic);
        JSONObject jsonObject = getEventFromServer(authBasic, indexlenth);
        return jsonObject.getJSONArray("entries");
    }



}

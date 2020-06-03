package CONFIG;

public class CONFIG
{

    public static final String Username = "tvadmin";
    public static final String Password = "abadpassword";

    public static final String IP = "192.168.178.11";
    public static final int tvhPort = 9981;

    public static final String tvh = "http://" + IP + ":" + tvhPort;

    /**
     * filepath to output xml file
     */
    public static final String outputPath = "d:/kek/channels.xml";

    /**
     *  URL to get a json array with objects of the channel name and a url to a icon.
     *  Plex will not accept svg images so png or jpeg only.
     *  link below is for ziggo
     */
    public static final String channelURLIMG = "https://jijbentzacht.nl/channelsimg.json";

}

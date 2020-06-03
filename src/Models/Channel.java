package Models;

import java.util.ArrayList;
import java.util.Comparator;

public class Channel implements Comparable<Channel> {
    private String uuid;
    private String channelName;
    private int channelNumber;
    private String channelImg;
    private ArrayList<Event> events;

    public Channel(String uuid, String channelName, int channelNumber, String channelImg) {
        this.uuid = uuid;
        this.channelName = channelName;
        this.channelNumber = channelNumber;
        this.channelImg = channelImg;
        events = new ArrayList<>();
    }

    public Channel(String uuid, String channelName, int channelNumber, String channelImg, ArrayList<Event> events) {
        this.uuid = uuid;
        this.channelName = channelName;
        this.channelNumber = channelNumber;
        this.channelImg = channelImg;
        this.events = events;
    }

    @Override
    public int compareTo(Channel o) {
        return this.getChannelNumber() > o.getChannelNumber() ? 1 : -1;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getChannelImg() {
        return channelImg;
    }

    public void setChannelImg(String channelImg) {
        this.channelImg = channelImg;
    }

    public void addEvent(Event event)
    {
        this.events.add(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "uuid='" + uuid + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelNumber=" + channelNumber +
                ", channelImg='" + channelImg + '\'' +
                ", events=" + events +
                '}';
    }
}

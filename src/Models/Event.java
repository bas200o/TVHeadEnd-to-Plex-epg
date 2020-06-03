package Models;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class Event {
    private String channelUuid;
    private String title;
    private String description;
    private int eventID;
    private int startTime;
    private int endTime;
    private int nextEventId;
    private int episodeId;

    public Event(String channelUuid, String title, String description, int eventID, int startTime, int endTime, int nextEventId, int episodeId) {
        this.channelUuid = channelUuid;
        this.title = title;
        this.description = description;
        this.eventID = eventID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nextEventId = nextEventId;
        this.episodeId = episodeId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public String getChannelUuid() {
        return channelUuid;
    }

    public void setChannelUuid(String channelUuid) {
        this.channelUuid = channelUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getStartTimeUnix() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTimeUnix() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getNextEventId() {
        return nextEventId;
    }

    public void setNextEventId(int nextEventId) {
        this.nextEventId = nextEventId;
    }

    public String getStartTime()
    {
        long unixSeconds = this.startTime;
        // convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(ZoneId.of("Europe/Amsterdam")));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getEndTime()
    {
        long unixSeconds = this.endTime;
        // convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(ZoneId.of("Europe/Amsterdam")));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    @Override
    public String toString() {
        return "Event{" +
                "channelUuid='" + channelUuid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventID=" + eventID +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", episodeId=" + episodeId +
                '}';
    }
}

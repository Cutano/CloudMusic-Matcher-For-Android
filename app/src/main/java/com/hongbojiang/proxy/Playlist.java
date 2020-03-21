package com.hongbojiang.proxy;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Playlist {
    private List<Object> subscribers;
    private boolean subscribed;
    private Creator creator;
    private Object artists;
    private Object tracks;
    private Object updateFrequency;
    private long backgroundCoverID;
    private Object backgroundCoverURL;
    private long titleImage;
    private Object titleImageURL;
    private Object englishTitle;
    private boolean opRecommend;
    private Object recommendInfo;
    private List<String> tags;
    private long trackUpdateTime;
    private String description;
    private boolean ordered;
    private long status;
    private long adType;
    private long trackNumberUpdateTime;
    private long specialType;
    private long updateTime;
    private String commentThreadID;
    private long privacy;
    private String coverImgURL;
    private boolean newImported;
    private boolean anonimous;
    private long trackCount;
    private long totalDuration;
    private long playCount;
    private double coverImgID;
    private long createTime;
    private boolean highQuality;
    private long userID;
    private long subscribedCount;
    private long cloudTrackCount;
    private String name;
    private long id;
    private String coverImgIDStr;

    @JsonProperty("subscribers")
    public List<Object> getSubscribers() { return subscribers; }
    @JsonProperty("subscribers")
    public void setSubscribers(List<Object> value) { this.subscribers = value; }

    @JsonProperty("subscribed")
    public boolean getSubscribed() { return subscribed; }
    @JsonProperty("subscribed")
    public void setSubscribed(boolean value) { this.subscribed = value; }

    @JsonProperty("creator")
    public Creator getCreator() { return creator; }
    @JsonProperty("creator")
    public void setCreator(Creator value) { this.creator = value; }

    @JsonProperty("artists")
    public Object getArtists() { return artists; }
    @JsonProperty("artists")
    public void setArtists(Object value) { this.artists = value; }

    @JsonProperty("tracks")
    public Object getTracks() { return tracks; }
    @JsonProperty("tracks")
    public void setTracks(Object value) { this.tracks = value; }

    @JsonProperty("updateFrequency")
    public Object getUpdateFrequency() { return updateFrequency; }
    @JsonProperty("updateFrequency")
    public void setUpdateFrequency(Object value) { this.updateFrequency = value; }

    @JsonProperty("backgroundCoverId")
    public long getBackgroundCoverID() { return backgroundCoverID; }
    @JsonProperty("backgroundCoverId")
    public void setBackgroundCoverID(long value) { this.backgroundCoverID = value; }

    @JsonProperty("backgroundCoverUrl")
    public Object getBackgroundCoverURL() { return backgroundCoverURL; }
    @JsonProperty("backgroundCoverUrl")
    public void setBackgroundCoverURL(Object value) { this.backgroundCoverURL = value; }

    @JsonProperty("titleImage")
    public long getTitleImage() { return titleImage; }
    @JsonProperty("titleImage")
    public void setTitleImage(long value) { this.titleImage = value; }

    @JsonProperty("titleImageUrl")
    public Object getTitleImageURL() { return titleImageURL; }
    @JsonProperty("titleImageUrl")
    public void setTitleImageURL(Object value) { this.titleImageURL = value; }

    @JsonProperty("englishTitle")
    public Object getEnglishTitle() { return englishTitle; }
    @JsonProperty("englishTitle")
    public void setEnglishTitle(Object value) { this.englishTitle = value; }

    @JsonProperty("opRecommend")
    public boolean getOpRecommend() { return opRecommend; }
    @JsonProperty("opRecommend")
    public void setOpRecommend(boolean value) { this.opRecommend = value; }

    @JsonProperty("recommendInfo")
    public Object getRecommendInfo() { return recommendInfo; }
    @JsonProperty("recommendInfo")
    public void setRecommendInfo(Object value) { this.recommendInfo = value; }

    @JsonProperty("tags")
    public List<String> getTags() { return tags; }
    @JsonProperty("tags")
    public void setTags(List<String> value) { this.tags = value; }

    @JsonProperty("trackUpdateTime")
    public long getTrackUpdateTime() { return trackUpdateTime; }
    @JsonProperty("trackUpdateTime")
    public void setTrackUpdateTime(long value) { this.trackUpdateTime = value; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("ordered")
    public boolean getOrdered() { return ordered; }
    @JsonProperty("ordered")
    public void setOrdered(boolean value) { this.ordered = value; }

    @JsonProperty("status")
    public long getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(long value) { this.status = value; }

    @JsonProperty("adType")
    public long getAdType() { return adType; }
    @JsonProperty("adType")
    public void setAdType(long value) { this.adType = value; }

    @JsonProperty("trackNumberUpdateTime")
    public long getTrackNumberUpdateTime() { return trackNumberUpdateTime; }
    @JsonProperty("trackNumberUpdateTime")
    public void setTrackNumberUpdateTime(long value) { this.trackNumberUpdateTime = value; }

    @JsonProperty("specialType")
    public long getSpecialType() { return specialType; }
    @JsonProperty("specialType")
    public void setSpecialType(long value) { this.specialType = value; }

    @JsonProperty("updateTime")
    public long getUpdateTime() { return updateTime; }
    @JsonProperty("updateTime")
    public void setUpdateTime(long value) { this.updateTime = value; }

    @JsonProperty("commentThreadId")
    public String getCommentThreadID() { return commentThreadID; }
    @JsonProperty("commentThreadId")
    public void setCommentThreadID(String value) { this.commentThreadID = value; }

    @JsonProperty("privacy")
    public long getPrivacy() { return privacy; }
    @JsonProperty("privacy")
    public void setPrivacy(long value) { this.privacy = value; }

    @JsonProperty("coverImgUrl")
    public String getCoverImgURL() { return coverImgURL; }
    @JsonProperty("coverImgUrl")
    public void setCoverImgURL(String value) { this.coverImgURL = value; }

    @JsonProperty("newImported")
    public boolean getNewImported() { return newImported; }
    @JsonProperty("newImported")
    public void setNewImported(boolean value) { this.newImported = value; }

    @JsonProperty("anonimous")
    public boolean getAnonimous() { return anonimous; }
    @JsonProperty("anonimous")
    public void setAnonimous(boolean value) { this.anonimous = value; }

    @JsonProperty("trackCount")
    public long getTrackCount() { return trackCount; }
    @JsonProperty("trackCount")
    public void setTrackCount(long value) { this.trackCount = value; }

    @JsonProperty("totalDuration")
    public long getTotalDuration() { return totalDuration; }
    @JsonProperty("totalDuration")
    public void setTotalDuration(long value) { this.totalDuration = value; }

    @JsonProperty("playCount")
    public long getPlayCount() { return playCount; }
    @JsonProperty("playCount")
    public void setPlayCount(long value) { this.playCount = value; }

    @JsonProperty("coverImgId")
    public double getCoverImgID() { return coverImgID; }
    @JsonProperty("coverImgId")
    public void setCoverImgID(double value) { this.coverImgID = value; }

    @JsonProperty("createTime")
    public long getCreateTime() { return createTime; }
    @JsonProperty("createTime")
    public void setCreateTime(long value) { this.createTime = value; }

    @JsonProperty("highQuality")
    public boolean getHighQuality() { return highQuality; }
    @JsonProperty("highQuality")
    public void setHighQuality(boolean value) { this.highQuality = value; }

    @JsonProperty("userId")
    public long getUserID() { return userID; }
    @JsonProperty("userId")
    public void setUserID(long value) { this.userID = value; }

    @JsonProperty("subscribedCount")
    public long getSubscribedCount() { return subscribedCount; }
    @JsonProperty("subscribedCount")
    public void setSubscribedCount(long value) { this.subscribedCount = value; }

    @JsonProperty("cloudTrackCount")
    public long getCloudTrackCount() { return cloudTrackCount; }
    @JsonProperty("cloudTrackCount")
    public void setCloudTrackCount(long value) { this.cloudTrackCount = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("coverImgId_str")
    public String getCoverImgIDStr() { return coverImgIDStr; }
    @JsonProperty("coverImgId_str")
    public void setCoverImgIDStr(String value) { this.coverImgIDStr = value; }
}

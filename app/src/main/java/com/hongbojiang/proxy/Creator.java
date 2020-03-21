package com.hongbojiang.proxy;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Creator {
    private boolean defaultAvatar;
    private long province;
    private long authStatus;
    private boolean followed;
    private String avatarURL;
    private long accountStatus;
    private long gender;
    private long city;
    private long birthday;
    private long userID;
    private long userType;
    private String nickname;
    private String signature;
    private Description description;
    private Description detailDescription;
    private double avatarImgID;
    private double backgroundImgID;
    private String backgroundURL;
    private long authority;
    private boolean mutual;
    private List<String> expertTags;
    private Object experts;
    private long djStatus;
    private long vipType;
    private Object remarkName;
    private String avatarImgIDStr;
    private String backgroundImgIDStr;
    private String creatorAvatarImgIDStr;

    @JsonProperty("defaultAvatar")
    public boolean getDefaultAvatar() { return defaultAvatar; }
    @JsonProperty("defaultAvatar")
    public void setDefaultAvatar(boolean value) { this.defaultAvatar = value; }

    @JsonProperty("province")
    public long getProvince() { return province; }
    @JsonProperty("province")
    public void setProvince(long value) { this.province = value; }

    @JsonProperty("authStatus")
    public long getAuthStatus() { return authStatus; }
    @JsonProperty("authStatus")
    public void setAuthStatus(long value) { this.authStatus = value; }

    @JsonProperty("followed")
    public boolean getFollowed() { return followed; }
    @JsonProperty("followed")
    public void setFollowed(boolean value) { this.followed = value; }

    @JsonProperty("avatarUrl")
    public String getAvatarURL() { return avatarURL; }
    @JsonProperty("avatarUrl")
    public void setAvatarURL(String value) { this.avatarURL = value; }

    @JsonProperty("accountStatus")
    public long getAccountStatus() { return accountStatus; }
    @JsonProperty("accountStatus")
    public void setAccountStatus(long value) { this.accountStatus = value; }

    @JsonProperty("gender")
    public long getGender() { return gender; }
    @JsonProperty("gender")
    public void setGender(long value) { this.gender = value; }

    @JsonProperty("city")
    public long getCity() { return city; }
    @JsonProperty("city")
    public void setCity(long value) { this.city = value; }

    @JsonProperty("birthday")
    public long getBirthday() { return birthday; }
    @JsonProperty("birthday")
    public void setBirthday(long value) { this.birthday = value; }

    @JsonProperty("userId")
    public long getUserID() { return userID; }
    @JsonProperty("userId")
    public void setUserID(long value) { this.userID = value; }

    @JsonProperty("userType")
    public long getUserType() { return userType; }
    @JsonProperty("userType")
    public void setUserType(long value) { this.userType = value; }

    @JsonProperty("nickname")
    public String getNickname() { return nickname; }
    @JsonProperty("nickname")
    public void setNickname(String value) { this.nickname = value; }

    @JsonProperty("signature")
    public String getSignature() { return signature; }
    @JsonProperty("signature")
    public void setSignature(String value) { this.signature = value; }

    @JsonProperty("description")
    public Description getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(Description value) { this.description = value; }

    @JsonProperty("detailDescription")
    public Description getDetailDescription() { return detailDescription; }
    @JsonProperty("detailDescription")
    public void setDetailDescription(Description value) { this.detailDescription = value; }

    @JsonProperty("avatarImgId")
    public double getAvatarImgID() { return avatarImgID; }
    @JsonProperty("avatarImgId")
    public void setAvatarImgID(double value) { this.avatarImgID = value; }

    @JsonProperty("backgroundImgId")
    public double getBackgroundImgID() { return backgroundImgID; }
    @JsonProperty("backgroundImgId")
    public void setBackgroundImgID(double value) { this.backgroundImgID = value; }

    @JsonProperty("backgroundUrl")
    public String getBackgroundURL() { return backgroundURL; }
    @JsonProperty("backgroundUrl")
    public void setBackgroundURL(String value) { this.backgroundURL = value; }

    @JsonProperty("authority")
    public long getAuthority() { return authority; }
    @JsonProperty("authority")
    public void setAuthority(long value) { this.authority = value; }

    @JsonProperty("mutual")
    public boolean getMutual() { return mutual; }
    @JsonProperty("mutual")
    public void setMutual(boolean value) { this.mutual = value; }

    @JsonProperty("expertTags")
    public List<String> getExpertTags() { return expertTags; }
    @JsonProperty("expertTags")
    public void setExpertTags(List<String> value) { this.expertTags = value; }

    @JsonProperty("experts")
    public Object getExperts() { return experts; }
    @JsonProperty("experts")
    public void setExperts(Object value) { this.experts = value; }

    @JsonProperty("djStatus")
    public long getDjStatus() { return djStatus; }
    @JsonProperty("djStatus")
    public void setDjStatus(long value) { this.djStatus = value; }

    @JsonProperty("vipType")
    public long getVipType() { return vipType; }
    @JsonProperty("vipType")
    public void setVipType(long value) { this.vipType = value; }

    @JsonProperty("remarkName")
    public Object getRemarkName() { return remarkName; }
    @JsonProperty("remarkName")
    public void setRemarkName(Object value) { this.remarkName = value; }

    @JsonProperty("avatarImgIdStr")
    public String getAvatarImgIDStr() { return avatarImgIDStr; }
    @JsonProperty("avatarImgIdStr")
    public void setAvatarImgIDStr(String value) { this.avatarImgIDStr = value; }

    @JsonProperty("backgroundImgIdStr")
    public String getBackgroundImgIDStr() { return backgroundImgIDStr; }
    @JsonProperty("backgroundImgIdStr")
    public void setBackgroundImgIDStr(String value) { this.backgroundImgIDStr = value; }

    @JsonProperty("avatarImgId_str")
    public String getCreatorAvatarImgIDStr() { return creatorAvatarImgIDStr; }
    @JsonProperty("avatarImgId_str")
    public void setCreatorAvatarImgIDStr(String value) { this.creatorAvatarImgIDStr = value; }
}

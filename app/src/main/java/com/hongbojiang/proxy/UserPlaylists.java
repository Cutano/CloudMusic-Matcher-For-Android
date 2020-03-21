package com.hongbojiang.proxy;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class UserPlaylists {
    private boolean more;
    private List<Playlist> playlist;
    private long code;

    @JsonProperty("more")
    public boolean getMore() { return more; }
    @JsonProperty("more")
    public void setMore(boolean value) { this.more = value; }

    @JsonProperty("playlist")
    public List<Playlist> getPlaylist() { return playlist; }
    @JsonProperty("playlist")
    public void setPlaylist(List<Playlist> value) { this.playlist = value; }

    @JsonProperty("code")
    public long getCode() { return code; }
    @JsonProperty("code")
    public void setCode(long value) { this.code = value; }
}

package com.tuan.apmusic.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Song implements Serializable, Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_song")
    @Expose
    private String nameSong;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("album_id")
    @Expose
    private String albumId;
    @SerializedName("playlist_id")
    @Expose
    private String playlistId;
    @SerializedName("artists")
    @Expose
    private String artists;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("like")
    @Expose
    private String like;

    protected Song(Parcel in) {
        id = in.readString();
        nameSong = in.readString();
        title = in.readString();
        albumId = in.readString();
        playlistId = in.readString();
        artists = in.readString();
        thumbnail = in.readString();
        categoryId = in.readString();
        link = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        like = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nameSong);
        parcel.writeString(title);
        parcel.writeString(albumId);
        parcel.writeString(playlistId);
        parcel.writeString(artists);
        parcel.writeString(thumbnail);
        parcel.writeString(categoryId);
        parcel.writeString(link);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        if (v == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(v);
        }
        parcel.writeString(like);
    }
}

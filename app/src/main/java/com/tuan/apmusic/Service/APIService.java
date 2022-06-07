package com.tuan.apmusic.Service;

public class APIService {
    private static final String base_url = "https://api-music-ap.herokuapp.com/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}

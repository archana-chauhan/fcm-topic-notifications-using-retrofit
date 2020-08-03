package com.archana.fcmnotifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIInterface {

    String URL_BASE = "https://fcm.googleapis.com/";

    @Headers({"Content-Type: application/json", "Authorization: key=AAAA0nrSnLA:APA91bFAmKhXHlRtvTVKJqBgY-x9_0MzFnsg9psO8DPW6iXwSviunHeC9C2PhYuMzfWXsbUlC97OmlaFj63S_zy76DZdq-1hsNLHxQT5wzFa8ndUREx-vSyPh_dmz9JJddZt2GTHouDy"})

    @POST("fcm/send")
    Call<Topic> pushNotification(@Body String body);

}
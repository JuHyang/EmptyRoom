package com.juhyang.android.empty_room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kkss2 on 2017-04-16.
 */

public interface ServerInterface {
    @GET("/roomData/{day}/{time}")
    Call<RoomTotalData> getData (@Path("day") int day, @Path("time") int time);
}

package com.juhyang.android.empty_room;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kkss2 on 2017-04-16.
 */

public class RoomModel {

    public  RoomTotalData getRoomTotal (int day, int time) {
        final RoomTotalData roomTotalData = new RoomTotalData();
        roomTotalData.roomTotalData = new ArrayList<>();

        roomTotalData.success = 0;
        roomTotalData.roomTotalData = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://175.201.94.92:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerInterface service = retrofit.create(ServerInterface.class);
        final Call<RoomTotalData> call = service.getData(day, time);
        new Thread  (new Runnable ()
        {

            @Override
            public void run() {
                try {
                    roomTotalData.roomTotalData.addAll(call.execute().body().roomTotalData);
                    roomTotalData.success = 1;
                } catch (IOException e) {
                    roomTotalData.success = 0;
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return roomTotalData;
    }

    public String makeText(int realtime, int status) {
        String result = "";
        if (status == 0 ) {
            result += Integer.toString(realtime) + " : 00 ~ " + Integer.toString(realtime) + " : 30";
        }
        else if (status == 1) {
            result += Integer.toString(realtime) + " : 30 ~ " + Integer.toString(realtime + 1) + " : 00";
        }
        return result;
    }

    public ArrayList<RoomData> makeArray (int buildingNum, int realtime, int status, RoomTotalData totalData) {
        ArrayList<RoomData> roomDatas = new ArrayList<RoomData>();

        int realtime_t = realtime;
        int status_t = status;
        int roop = totalData.roomTotalData.size();
        if (roop == 0) {
            RoomData roomData_temp = new RoomData("", "비어있는 강의실이 없습니다.");
            roomDatas.add(roomData_temp);
            return roomDatas;
        }

        for (int i = 0; i < roop; i++) {
            RoomData roomData_temp = new RoomData();
            roomData_temp.time = makeText(realtime_t, status_t);
            if (status_t == 0) {
                status_t = 1;
            } else if (status_t == 1) {
                status_t = 0;
                realtime_t += 1;
            }
            int roop_2 = totalData.roomTotalData.get(i).size();
            if (roop_2 == 0) {
                roomData_temp.room = "비어있는 강의실이 없습니다.";
                roomDatas.add(roomData_temp);
                continue;
            }
            String temp = "";

            for (int j = 0; j < roop_2; j ++) {
                if (buildingNum == 0) {
                    temp += totalData.roomTotalData.get(i).get(j) + " ";
                } else if (buildingNum == 1) {
                    if (totalData.roomTotalData.get(i).get(j).charAt(0) == '전') {
                        temp += totalData.roomTotalData.get(i).get(j) + " ";
                    } else {
                        continue;
                    }
                } else if (buildingNum == 2) {
                    if (totalData.roomTotalData.get(i).get(j).charAt(0) == '과') {
                        temp += totalData.roomTotalData.get(i).get(j) + " ";
                    } else {
                        continue;
                    }
                } else if (buildingNum == 3) {
                    if (totalData.roomTotalData.get(i).get(j).charAt(0) == '강') {
                        temp += totalData.roomTotalData.get(i).get(j) + " ";
                    } else {
                        continue;
                    }
                }
            }
            roomData_temp.room = temp;
            roomDatas.add(roomData_temp);
        }
        return roomDatas;
    }

}

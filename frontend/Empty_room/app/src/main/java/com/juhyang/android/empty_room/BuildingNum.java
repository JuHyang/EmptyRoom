package com.juhyang.android.empty_room;

import com.orm.SugarRecord;

/**
 * Created by kkss2 on 2017-04-16.
 */

public class BuildingNum extends SugarRecord {
    int buildingnum;

    public BuildingNum (){}

    public BuildingNum (int buildingnum) {
        this.buildingnum = buildingnum;
    }
}

package com.zju.atta.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface GPSService {
    JSONObject getCount();

    JSONArray getTopFive();

    JSONArray getDriverMessageByCode(String code);

    JSONObject getDetail(String code);

    JSONArray getTopProvince();

    JSONObject getTopSpeed();
}

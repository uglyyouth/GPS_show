package com.zju.atta.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zju.atta.service.GPSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by honeycc on 17-1-5.
 */
@Controller
@RequestMapping("/gps")
public class GPSController {
   // private static final Logger LOG = LoggerFactory.getLogger(GPSController.class);

    @Resource
    private GPSService gpsService;


    @RequestMapping(value = "/gpscount.do")
    @ResponseBody
    public JSONObject find(HttpServletRequest req) {

        return  gpsService.getCount();
    }
    @RequestMapping(value = "/distopfive.do")
    @ResponseBody
    public JSONArray topFive(HttpServletRequest req) {

        return  gpsService.getTopFive();
    }

    @RequestMapping(value = "/getmessagebycode.do")
    @ResponseBody
    public JSONArray getDriverMessageByCode(HttpServletRequest req) {
        String code = req.getParameter("code").trim();
        return  gpsService.getDriverMessageByCode(code);
    }
    @RequestMapping(value = "/getdetail.do")
    @ResponseBody
    public JSONObject getDetail(HttpServletRequest req) {
        String code = req.getParameter("code").trim();
        return  gpsService.getDetail(code);
    }

    @RequestMapping(value = "/gettopprovice.do")
    @ResponseBody
    public JSONArray getTopProvice(HttpServletRequest req) {
        return  gpsService.getTopProvince();
    }


    @RequestMapping(value = "/gttopspeed.do")
    @ResponseBody
    public JSONObject getTopSpeed(HttpServletRequest req) {
        return  gpsService.getTopSpeed();
    }
}


package com.zju.atta.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoQuery;
import com.mongodb.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.zju.atta.service.GPSService;
import com.zju.atta.util.MongoDBUtil;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;


@Service
public class GPSServiceImpl implements GPSService {


    /*@Override
    public JSONArray getCount() {
        JSONArray jsonArray = new JSONArray();
        MongoClient client = null;
        try {
            client = MongoDBUtil.init();
            MongoQuery query = new MongoQuery();
            query.use("test").ascending("GPSTIME").find();
            MongoAggregation aggregation=new MongoAggregation(query);

            MongoQuery queryGroupBy = aggregation
                    .group(new Document("CODE","$CODE").append("GPSDATE","$GPSDATE"))
                    .getQuery();

            List<JSONObject> jsonList  = queryGroupBy
                    .projection("GPSDATE","CODE","GPSTIME","country","city","province","district","LNG","LAT")
                    .findAll();
            System.out.println(jsonList.get(0));
            jsonArray.add(jsonList.get(0));
            return jsonArray;
        }finally {
            client.close();
        }
    }*/

    @Override
    public JSONObject getCount() {
        JSONArray jsonArray = new JSONArray();
        MongoClient client = null;
        try {
            client = MongoDBUtil.init();
            MongoQuery query = new MongoQuery();
            List<JSONObject> weakList  = query.use("first").eq("count",1).find();
            JSONArray weakArray = new JSONArray();
            JSONArray weakArrayFirst = new JSONArray();
            weakArrayFirst.add(73.96);
            weakArrayFirst.add(39.701);
            weakArray.add(weakArrayFirst);
            for (JSONObject j :weakList) {
                JSONArray weak = new JSONArray();
                weak.add(j.get("LNG"));
                weak.add(j.get("LAT"));
                weak.add(1);
                weakArray.add(weak);
            }
            jsonArray.add(weakArray);

            //中
            MongoQuery query1 = new MongoQuery();
            List<JSONObject> betterList  = query1.use("first").eq("count",2).find();
            JSONArray betterArray = new JSONArray();
            JSONArray betterArrayFirst = new JSONArray();
            betterArrayFirst.add(75.059);
            betterArrayFirst.add(38.445);
            betterArray.add(betterArrayFirst);
            for (JSONObject j :betterList) {
                JSONArray better = new JSONArray();
                better.add(j.get("LNG"));
                better.add(j.get("LAT"));
                better.add(1);
                betterArray.add(better);
            }
            jsonArray.add(betterArray);

            //强
            MongoQuery query2 = new MongoQuery();
            List<JSONObject> heavyList  = query2.use("first").gte("count",3).find();
            JSONArray heavyArray = new JSONArray();
            JSONArray heavyArrayFirst = new JSONArray();
            heavyArrayFirst.add(75.234);
            heavyArrayFirst.add(37.788);
            heavyArray.add(heavyArrayFirst);
            for (JSONObject j : heavyList) {
                JSONArray heavy = new JSONArray();
                heavy.add(j.get("LNG"));
                heavy.add(j.get("LAT"));
                heavy.add(1);
                heavyArray.add(heavy);
            }
            jsonArray.add(heavyArray);
            System.out.println(jsonArray);

           JSONObject json = new JSONObject();
            json.put("gpsdata",jsonArray);
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            client.close();
        }
        return null;
    }

    @Override
    public JSONArray getTopFive() {
        JSONArray jarray = new JSONArray();
        MongoClient client = null;
        try {
            client = MongoDBUtil.init();
            Map<String, Double> map = new TreeMap<String, Double>();
            MongoQuery query = new MongoQuery();
            List<JSONObject> MaxDisCodeList = query.use("code_dis_max").find();
            for (JSONObject j: MaxDisCodeList) {
                System.out.println(j.get("CODE")+"-----------"+j.get("DIS"));
                MongoQuery query1 = new MongoQuery();
                List<JSONObject>  oneMinDisCode = query1.use("code_dis_min").eq("CODE",j.get("CODE")).find();
                if (!oneMinDisCode.isEmpty()){
                    System.out.println(oneMinDisCode.get(0).get("DIS"));
                    Double dis = ((Double)j.get("DIS"))-((Double)oneMinDisCode.get(0).get("DIS"));
                    map.put(j.get("CODE").toString(), dis);

                }
            }
            Map<String, Double> resultMap = sortMapByValue(map);
            int i = 0;
            for (Map.Entry<String, Double> entry : resultMap.entrySet()) {
                if(i<5){
                    JSONObject json = new JSONObject();
                    json.put("CODE",entry.getKey().replace("？鉈","浙B"));
                    json.put("DIS",entry.getValue());
                    jarray.add(json);
                    i++;
                }else{
                    break;
                }
            }
            return jarray;
        }finally {
            if(client!=null)
                client.close();
        }
    }

    @Override
    public JSONArray getDriverMessageByCode(String code) {
        JSONArray jarray = new JSONArray();
        try {
         /*   DBObject fields = new BasicDBObject("GPSDATE", 1);
            fields.put("CODE", 1);
            fields.put("_id", 0);
            DBObject project = new BasicDBObject("$project", fields );"浙B89B19"
            DBObject match = new BasicDBObject("$match",new BasicDBObject("CODE",code));

            DBObject groupField = new BasicDBObject("_id","$GPSDATE");
            DBObject group = new BasicDBObject("$group",groupField);

            DBObject sort = new BasicDBObject("$sort",new BasicDBObject("GPSDATE",1));
            List<DBObject> pipeline = Arrays.asList(match, group,sort);
            AggregationOutput output = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipeline);
            for (DBObject result : output.results()) {
                System.out.println(result);
                DBObject matchgroup  = new BasicDBObject("CODE",code);
                matchgroup.put("GPSDATE",result.get("_id"));
                DBObject matchdate = new BasicDBObject("$match",matchgroup);
                DBObject sortdate = new BasicDBObject("$sort",new BasicDBObject("GPSTIME",1));
                List<DBObject> pipelinedate = Arrays.asList(matchdate, sortdate);
                AggregationOutput dateoutput = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipelinedate);
                JSONObject json = new JSONObject();
                JSONArray two = new JSONArray();

                for (DBObject resultdate: dateoutput.results()) {
                    System.out.println(resultdate);
                    JSONArray lnglat = new JSONArray();
                    lnglat.add(resultdate.get("LNG"));
                    lnglat.add(resultdate.get("LAT"));
                    two.add(lnglat);
                }
                JSONObject normal = new JSONObject();
                JSONObject color = new JSONObject();
                color.put("color","rgba(223,90,90,1)");
                normal.put("normal",color);
                json.put("coords",two);
                json.put("lineStyle",normal);
                jarray.add(json);
            }"浙B89B19"*/

            DBObject match = new BasicDBObject("$match",new BasicDBObject("CODE",code));

            DBObject groupField = new BasicDBObject("_id","$GPSDATE");
            DBObject group = new BasicDBObject("$group",groupField);

            DBObject sort = new BasicDBObject("$sort",new BasicDBObject("GPSDATE",1));
            List<DBObject> pipeline = Arrays.asList(match, group,sort);
            AggregationOutput output = MongoDBUtil.getCollection("three").aggregate(pipeline);
            for (DBObject result : output.results()) {
                //System.out.println(result);

                DBCollection dbColl = MongoDBUtil.getCollection("three");
                Pattern pattern = Pattern.compile("^" + result.get("_id") + ".*$", Pattern.CASE_INSENSITIVE);
                BasicDBObject query = new BasicDBObject();
                query.put("CODE", code);
                query.put("GPSTIME", pattern);
                BasicDBObject sort1 = new BasicDBObject();
                // 1,表示正序； －1,表示倒序
                sort.put("GPSTIME", 1);
                DBCursor cur = dbColl.find(query).sort(sort1);
                JSONObject json = new JSONObject();
                JSONArray two = new JSONArray();
                while (cur.hasNext()) {
                    DBObject obj = cur.next();
                    JSONArray lnglat = new JSONArray();
                    lnglat.add(obj.get("LNG"));
                    lnglat.add(obj.get("LAT"));
                    two.add(lnglat);
                }
                JSONObject normal = new JSONObject();
                JSONObject color = new JSONObject();
                color.put("color", "rgba(223,"+(int)(Math.random()*255)+",90,1)");
                normal.put("normal", color);
                json.put("coords", two);
                json.put("lineStyle", normal);
                jarray.add(json);
            }
                 return jarray;
        }finally {
            MongoDBUtil.close();
        }
    }

    @Override
    public JSONObject getDetail(String code) {
        MongoClient client = null;
        try {
            client = MongoDBUtil.init();
            MongoQuery query = new MongoQuery();
            JSONObject json  = query.use("gps_data_deal").eq("CODE",code).findOne();
            System.out.println(json);
            return json;
        }finally {
            if(client!=null)
                client.close();
        }
    }

    @Override
    public JSONArray getTopProvince() {
        JSONArray array = new JSONArray();
        try {

            DBObject groupField = new BasicDBObject("_id","$province");
            groupField.put("count",new BasicDBObject("$sum",1));
            DBObject group = new BasicDBObject("$group",groupField);

            DBObject sort = new BasicDBObject("$sort",new BasicDBObject("count",-1));
            List<DBObject> pipeline = Arrays.asList(group,sort);
            AggregationOutput output = MongoDBUtil.getCollection("test").aggregate(pipeline);
            int i = 0 ;
            for (DBObject result : output.results()) {
                if(i<7) {
                    i++;
                    JSONObject json = new JSONObject();
                    json.put("value", result.get("count"));
                    json.put("name", result.get("_id"));
                    array.add(json);
                }else
                    break;
            }
            return array;
        }finally {
                MongoDBUtil.close();
        }
    }

    @Override
    public JSONObject getTopSpeed() {

        JSONObject json = new JSONObject();

        DBObject groupField = new BasicDBObject("CODE","$CODE");
        groupField.put("VEO","$VEO");
        DBObject groupField1 = new BasicDBObject("_id",groupField);
        DBObject group = new BasicDBObject("$group",groupField1);
        DBObject sort = new BasicDBObject("$sort",new BasicDBObject("VEO",-1));
        DBObject limit = new BasicDBObject("$limit",6);
        List<DBObject> pipeline = Arrays.asList(group,sort,limit);
        AggregationOutput output = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipeline);
        JSONArray codearray = new JSONArray();
        JSONArray veoarray = new JSONArray();
        for (DBObject result : output.results()) {
            System.out.print(((BasicDBObject)result.get("_id")).get("CODE")+"---"+((BasicDBObject)result.get("_id")).get("VEO"));
            codearray.add(((BasicDBObject)result.get("_id")).get("CODE"));
            veoarray.add(((BasicDBObject)result.get("_id")).get("VEO"));
        }
        json.put("veodata",veoarray);
        json.put("codedata",codearray);
        return json;
    }


    private   Map<String, Double> sortMapByValue(Map<String, Double> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>(){
            @Override
            public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
                return me2.getValue().compareTo(me1.getValue());
            }
        });
        Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
        Map.Entry<String, Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

}

























/*  HashMap dimMap = new HashMap();
            dimMap.put("CODE",true);
            dimMap.put("GPSDATE",true);
            HashMap forIdxMap = new HashMap();
            forIdxMap.put("min","min(GPSTIME)");
            forIdxMap.put("count","count(GPSTIME)");
            forIdxMap.put("max","max(GPSTIME)");
            HashMap indexMap = new HashMap();
            indexMap.put("GPSTIME","GPSTIME");
            BasicDBList basicDBList = (BasicDBList)MongoDBUtil.getdb().getCollection("test")
                    .group(GroupUtil.generateFormulaKeyObject(dimMap),
                            new BasicDBObject(),
                            GroupUtil.generateFormulaInitObject(indexMap),
                            GroupUtil.generateFormulaReduceObject(indexMap),
                            GroupUtil.generateFormulaFinalizeObject(forIdxMap, indexMap));*//*


    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    // 连接到数据库
    MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = mongoDatabase.getCollection("test");

    */
/*   Map<String,Object> map = new HashMap<String,Object>();
       map.put("format","%Y-%m-%d %H:%M:%s");
       map.put("date","$GPSTIME");*//*

    AggregateIterable<Document> ll = collection.aggregate(Arrays.asList(
            new Document("$group",new Document("_id",new Document("CODE","$CODE").append("GPSDATE","$GPSDATE"))),
            new Document("$sort",new Document("GPSTIME",1))
    ));
    StringBuilder a  = new StringBuilder("");
            for (Document d: ll) {
                    a.append(d.toJson());
                    }

*/
/*

            DB db = MongoDBUtil.getdb();
            BasicDBObject key = new BasicDBObject("CODE",true);
                        key.put("GPSDATE",true);
            BasicDBObject cond = new BasicDBObject();
            BasicDBObject initial = new BasicDBObject("total",0);
            String reduce = "function(curr,result){result.total += 1}";
            BasicDBList basicDBList = (BasicDBList)db.getCollection("test").group(key, cond, initial, reduce);*//*

     */

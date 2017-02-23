package com.zju.atta.util;

/**
 * Created by honeycc on 17-1-4.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.aggregation.MongoAccumulator;
import com.cybermkd.mongo.kit.aggregation.MongoAggregation;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class MongoDBJdbc{

   public static void main(String args[] ) {
      /* DBObject groupField = new BasicDBObject("_id","$province");
       groupField.put("count",new BasicDBObject("$sum",1));
       DBObject group = new BasicDBObject("$group",groupField);

       DBObject sort = new BasicDBObject("$sort",new BasicDBObject("count",-1));
       List<DBObject> pipeline = Arrays.asList(group,sort);
       AggregationOutput output = MongoDBUtil.getdbByDataName("test").getCollection("test").aggregate(pipeline);
       for (DBObject result : output.results()) {
           System.out.println(result.get("_id")+"--"+result.get("count"));
       }*/

      /* JSONObject json = new JSONObject();

       DBObject groupField = new BasicDBObject("CODE","$CODE");
       groupField.put("VEO","$VEO");
       DBObject groupField1 = new BasicDBObject("_id",groupField);
       DBObject group = new BasicDBObject("$group",groupField1);
       DBObject sort = new BasicDBObject("$sort",new BasicDBObject("VEO",-1));
       DBObject limit = new BasicDBObject("$limit",6);
       List<DBObject> pipeline = Arrays.asList(group,sort,limit);
       AggregationOutput output = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipeline);
       for (DBObject result : output.results()) {
           System.out.print(((BasicDBObject)result.get("_id")).get("CODE")+"---"+((BasicDBObject)result.get("_id")).get("VEO"));
       }*/

       JSONArray array = new JSONArray();
       try {

           DBObject groupField = new BasicDBObject("_id", "$province");
           groupField.put("count", new BasicDBObject("$sum", 1));
           DBObject group = new BasicDBObject("$group", groupField);

           DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
           List<DBObject> pipeline = Arrays.asList(group, sort);
           AggregationOutput output = MongoDBUtil.getCollection("test").aggregate(pipeline);
           int i = 0;
           for (DBObject result : output.results()) {
               if (i < 7) {
                   System.out.print(output.results());
                   i++;
                   JSONObject json = new JSONObject();
                   json.put("value", result.get("count"));
                   json.put("name", result.get("_id"));
                   array.add(json);
               } else
                   break;
           }
           System.out.print(array.toJSONString());
       }finally {

       }
   }
    /*public static void main( String args[] ){
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
            System.out.println(json.toJSONString());
        }finally {
            client.close();
        }
*/
    /*public static void main( String args[] ){
        try{
            *//*
               FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while(mongoCursor.hasNext()){
                System.out.println(mongoCursor.next());
            }
            DBObject dbObject = new BasicDBObject("province","浙江省");
            System.out.print(MongoDBUtil.findOne("test",dbObject).toString());

            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println(collection.count());
            BasicDBObject key =  new BasicDBObject("CODE",true);*//*




            *//*
            System.out.println(aggregation1
                    .group(new Document("CODE","$CODE").append("GPSDATE","$GPSDATE"))
                    .aggregate());

            query.use("test").ascending("GPSTIME");
            MongoAggregation aggregation=new MongoAggregation(query);
            System.out.print(JSON.toJSONString(
                    aggregation
                            .include("CODE","GPSDATE","province")
                            .group("$CODE",new MongoAccumulator().max("GPSTIME","$GPSTIME"))
                            .aggregate()));
            System.out.print(JSON.toJSONString(
                    aggregation
                            .include("CODE","GPSDATE","province")
                            .group("$CODE",new MongoAccumulator().min("GPSTIME","$GPSTIME"))
                            .aggregate()));*//*
            *//*JSON.toJSONString(
                    aggregation
                            .group("$CODE",new MongoAccumulator().max("GPSTIME","$GPSTIME"))
                            .aggregate())*//*

            MongoPlugin mongoPlugin=new MongoPlugin();
            mongoPlugin.add("127.0.0.1",27017);
            mongoPlugin.setDatabase("test");
            MongoClient client = mongoPlugin.getMongoClient();
            MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
            MongoQuery query = new MongoQuery();
            query.use("test").*//*eq("CODE","Y115浙B92236").*//*ascending("GPSTIME").find();
            MongoAggregation aggregation=new MongoAggregation(query);
            System.out.print(JSON.toJSONString(aggregation
                    .include("GPSDATE","CODE","GPSTIME","country","city","province","district","LNG","LAT")
                    .projection()
                    //.pipeline(new Document("$match",new Document("provice", "浙江省")))
                    .pipeline(new Document("$group",
                            new Document("_id",new Document("province", "$province")
                                    .append("GPSDATE","$GPSDATE"))
                                    .append("total",new Document("$sum",1))))

                    .out("output")
                    .aggregate()));

           *//* MongoQuery queryGroupBy = aggregation
                    .group(new Document("CODE","$CODE").append("GPSDATE","$GPSDATE"))
                    .getQuery();

            List<JSONObject> jsonList  = queryGroupBy
                    .projection("GPSDATE","CODE","GPSTIME","country","city","province","district","LNG","LAT")
                    .findAll();
            System.out.println(jsonList.get(0));*//*
            client.close();


        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }*/
/*
第二页面，获取形式距离前五的
 */

  /*  public static void main(String args[]){
        JSONArray jarray = new JSONArray();
        Map<String, Double> map = new TreeMap<String, Double>();
        MongoClient client = null;
        client = MongoDBUtil.init();
        MongoQuery query = new MongoQuery();
        List<JSONObject> MaxDisCodeList = query.use("code_dis_max").find();
        for (JSONObject j: MaxDisCodeList) {
            System.out.println(j.get("CODE")+"-----------"+j.get("DIS"));
            MongoQuery query1 = new MongoQuery();
            List<JSONObject>  oneMinDisCode = query1.use("code_dis_min").eq("CODE",j.get("CODE")).find();
            if (!oneMinDisCode.isEmpty()){
                System.out.println(oneMinDisCode.get(0).get("DIS"));
                Double dis = ((Double)j.get("DIS"))-((Double)oneMinDisCode.get(0).get("DIS"));
         *//*       long minJ = (long) oneMinDisCode.get(0).get("DIS");*//*
                map.put(j.get("CODE").toString(), dis);

            }
        }
        Map<String, Double> resultMap = sortMapByValue(map);
        int i = 0;
        for (Map.Entry<String, Double> entry : resultMap.entrySet()) {
           if(i<5){
               JSONObject json = new JSONObject();
               json.put("CODE",entry.getKey());
               json.put("DIS",entry.getValue());
               jarray.add(json);
           }else{
               break;
           }
        }
        System.out.println(jarray.toJSONString());
        client.close();
    }
    /**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
/*
    public  static Map<String, Double> sortMapByValue(Map<String, Double> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(entryList, new MapValueComparator());
        Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
        Map.Entry<String, Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }*/

//第三页面数据
   /* public static void main(String args[]) {
        JSONArray jarray = new JSONArray();
        try {
         *//*   DBObject fields = new BasicDBObject("GPSDATE", 1);
            fields.put("CODE", 1);
            fields.put("_id", 0);
            DBObject project = new BasicDBObject("$project", fields );*//*
            DBObject match = new BasicDBObject("$match",new BasicDBObject("CODE","浙B89B19"));

            DBObject groupField = new BasicDBObject("_id","$GPSDATE");
            DBObject group = new BasicDBObject("$group",groupField);

            DBObject sort = new BasicDBObject("$sort",new BasicDBObject("GPSDATE",1));
            List<DBObject> pipeline = Arrays.asList(match, group,sort);
            AggregationOutput output = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipeline);
            for (DBObject result : output.results()) {
               //System.out.println(result);

                DBCollection dbColl = MongoDBUtil.getCollection("gps_data");
                Pattern pattern = Pattern.compile("^"+result.get("_id")+".*$", Pattern.CASE_INSENSITIVE);
                BasicDBObject query = new BasicDBObject();
                query.put("CODE","浙B89B19");
                query.put("GPSTIME",pattern);
                BasicDBObject sort1 = new BasicDBObject();

                // 1,表示正序； －1,表示倒序
                sort.put("GPSTIME",1);
                DBCursor cur = dbColl.find(query).sort(sort1);
                JSONObject json = new JSONObject();
                JSONArray two = new JSONArray();
                while (cur.hasNext()) {
                    DBObject obj = cur.next();

                   *//* System.out.print("LNG=" + obj.get("LNG"));
                    System.out.print(",LAT=" + obj.get("LAT"));
                    System.out.println(",GPSTIME=" + obj.get("GPSTIME"));*//*
                    JSONArray lnglat = new JSONArray();
                    lnglat.add(obj.get("LNG"));
                    lnglat.add(obj.get("LAT"));
                    two.add(lnglat);
                }
                JSONObject normal = new JSONObject();
                JSONObject color = new JSONObject();
                color.put("color","rgba(223,90,90,1)");
                normal.put("normal",color);
                json.put("coords",two);
                json.put("lineStyle",normal);
                jarray.add(json);

*//*


                DBObject matchgroup  = new BasicDBObject("CODE","浙B89B19");
                matchgroup.put("GPSDATE","/^"+result.get("_id")+"/");
                DBObject matchdate = new BasicDBObject("$match",matchgroup);
                DBObject sortdate = new BasicDBObject("$sort",new BasicDBObject("GPSTIME",1));
                List<DBObject> pipelinedate = Arrays.asList(matchdate, sortdate);
                AggregationOutput dateoutput = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipelinedate);
                JSONObject json = new JSONObject();
                JSONArray two = new JSONArray();
                MongoDBUtil.getCollection("gps_data_deal").find()
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
                jarray.add(json);*//*
            }
            System.out.print( jarray.toJSONString());
        }finally {
            MongoDBUtil.close();
        }
    }*/
     /*   DBObject match = new BasicDBObject("$match", new BasicDBObject("CODE", "浙B52812"));

        // build the $projection operation
        DBObject fields = new BasicDBObject("GPSDATE", 1);
        fields.put("CODE", 1);
        fields.put("_id", 0);
        DBObject project = new BasicDBObject("$project", fields );

        // Now the $group operation
        DBObject groupFields = new BasicDBObject( "_id", "$GPSDATE");
       groupFields.put("average", new BasicDBObject( "$avg", "$amount"));
        DBObject group = new BasicDBObject("$group", groupFields);

        // Finally the $sort operation
        DBObject sort = new BasicDBObject("$sort", new BasicDBObject("amount", -1));*//*

        DBObject match = new BasicDBObject("$match",new BasicDBObject("CODE","浙B52812"));

        DBObject groupField = new BasicDBObject("_id","$GPSDATE");
        DBObject group = new BasicDBObject("$group",groupField);
        //升序
        DBObject sort = new BasicDBObject("$sort",new BasicDBObject("GPSDATE",1));
        List<DBObject> pipeline = Arrays.asList(match, group, sort);
        AggregationOutput output = MongoDBUtil.getCollection("gps_data_deal").aggregate(pipeline);
        for (DBObject result : output.results()) {
            System.out.println(result.get("_id"));
            DBObject matchgroup  = new BasicDBObject("CODE","浙B52812");
            matchgroup.put("GPSDATE",result.get("_id"));
            DBObject matchdate = new BasicDBObject("$match",matchgroup);
            DBObject sortdate = new BasicDBObject("$sort",new BasicDBObject("GPSTIME",1));
            List<DBObject> pipelinedate = Arrays.asList(matchdate, sortdate);
            AggregationOutput dateoutput = MongoDBUtil.getCollection("gps_data").aggregate(pipelinedate);
            for (DBObject resultdate: dateoutput.results()) {
                System.out.println(resultdate);
            }
        }
*/


      /*  JSONArray jarray = new JSONArray();
        MongoClient client = null;
        client = MongoDBUtil.init();
        MongoQuery query = new MongoQuery();
        query.use("gps_data_deal").eq("CODE","浙B52812").find();
        MongoAggregation aggregation=new MongoAggregation(query);
        aggregation.pipeline(new Document("$group",
                        new Document("_id",new Document("province", "$province")
                                .append("GPSDATE","$GPSDATE"))
                                .append("total",new Document("$sum",1)))).getQuery();
        System.out.print(JSON.toJSONString(aggregation.group(new Document("GPSDATE","GPSDATE").append("GPSTIME","$GGPSTIME"))
                .aggregate()));
        List<JSONObject>  dataList =  aggregation.include("CODE","GPSDATE")
                .projection()
                .pipeline(new BasicDBObject("$group",
                new Document("_id",new Document("GPSDATE", "$GPSDATE")
                        .append("GPSTIME","$GPSTIME"))
                        )).aggregate();



        List<JSONObject>  dataList= aggregation.include("CODE","GPSDATE")
                .projection().group(new BasicDBObject("GPSDATE","$GPSDATE")).aggregate();
        for(JSONObject j:dataList){
            System.out.print(j.get("CODE")+"--"+j.get("GPSDATE"));
        }
            client.close();*/
    }
/*    public static void main(String args[]){
        DBObject pipeline =Arrays.asList(
                new Document("$group", new Document( "_id",
                        new Document("MATERIAL_CODE","$MATERIAL_CODE")
                                .append("PROVINCE_NAME","$PROVINCE_NAME")
                                .append("RECORD_DATE_YEAR",new Document("$year","$RECORD_DATE"))
                                .append("RECORD_DATE_MONTH",new Document("$month","$RECORD_DATE"))
                )
                        .append("count", new Document("$sum",1))
                        .append("min_produce_date",new Document("$min","$produce_date"))
                ));

        AggregateIterable<Document> iterable = MongoDBUtil.getdb().getCollection("").aggregate(pipeline).allowDiskUse(true);
        int i=0;
        for (Document d : iterable){
            i++;
            Document _id=(Document)d.get("_id");
            String ITEM_NUMBER=_id.getString("MATERIAL_CODE");
        }
    }

}*/


//比较器类
class MapValueComparator implements Comparator<Map.Entry<String, Double>> {
    @Override
    public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
        return me2.getValue().compareTo(me1.getValue());
    }
}

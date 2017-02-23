package com.zju.atta.util;

import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.*;

import java.util.List;

/**
 * Created by honeycc on 17-1-5.
 */
public class MongoDBUtil {
    private final static ThreadLocal<Mongo> mongos = new ThreadLocal<Mongo>();
    private static final String MONGODB_ADDRESS = "127.0.0.1";
    private static final int MONGODB_PORT = 27017;
    private static final String MONGODB_USERNAME = "root";
    private static final String MONGODB_PASSWORD = "";
    private static final String MONGODB_DBNAME = "gps_db";
    private static final String MONGODB_COLLECTIONNAME = "test";

    public static MongoClient init(){
        MongoPlugin mongoPlugin=new MongoPlugin();
        mongoPlugin.add(MONGODB_ADDRESS,MONGODB_PORT);
        mongoPlugin.setDatabase(MONGODB_DBNAME);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
        return  client;
    }
    public static DB getdbByDataName(String dataname){
        return getMongos().getDB(dataname);
    }

    public static DB getdb(){
        return getMongos().getDB(MONGODB_DBNAME);
    }

    public static Mongo getMongos() {
        Mongo mongo = mongos.get();
        if (mongo == null) {
            try {
                mongo = new Mongo(MONGODB_ADDRESS,MONGODB_PORT);
                mongos.set(mongo);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return mongo;
    }

    public static void close(){
        Mongo mongo = mongos.get();
        if(mongo!=null){
            mongo.close();
            mongos.remove();
        }
    }


    /**
     * 获取集合（表）
     *
     * @param collection
     */
    public static DBCollection getCollection(String collection) {

        return getdb().getCollection(collection);
    }


    /**
     * 插入
     *
     * @param collection
     * @param o 插入
     *
     */
    public static void insert(String collection, DBObject o) {

        getCollection(collection).insert(o);
    }

    /**
     * 批量插入
     *
     * @param collection
     * @param list
     *            插入的列表
     */
    public void insertBatch(String collection, List<DBObject> list) {

        if (list == null || list.isEmpty()) {
            return;
        }

        getCollection(collection).insert(list);

    }


    /**
     * 删除
     *
     * @param collection
     * @param q
     *            查询条件
     */
    public void delete(String collection, DBObject q) {

        getCollection(collection).remove(q);
    }

    /**
     * 批量删除
     *
     * @param collection
     * @param list
     *            删除条件列表
     */
    public void deleteBatch(String collection, List<DBObject> list) {

        if (list == null || list.isEmpty()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            getCollection(collection).remove(list.get(i));
        }
    }


    /**
     * 更新
     *
     * @param collection
     * @param q
     *            查询条件
     * @param setFields
     *            更新对象
     */
    public static void update(String collection, DBObject q, DBObject setFields) {

        getCollection(collection).updateMulti(q,
                new BasicDBObject("$set", setFields));
    }

    /**
     * 查找集合所有对象
     *
     * @param collection
     */
    public static List<DBObject> findAll(String collection) {

        return getCollection(collection).find().toArray();
    }

    /**
     * 按顺序查找集合所有对象
     *
     * @param collection
     *            数据集
     * @param orderBy
     *            排序
     */
    public static List<DBObject> findAll(String collection, DBObject orderBy) {

        return getCollection(collection).find().sort(orderBy)
                .toArray();
    }

    /**
     * 查找（返回一个对象）
     *
     * @param collection
     * @param q
     *            查询条件
     */
    public static DBObject findOne(String collection, DBObject q) {

        return getCollection(collection).findOne(q);
    }

    /**
     * 查找（返回一个对象）
     *
     * @param collection
     * @param q
     *            查询条件
     * @param fileds
     *            返回字段
     */
    public static DBObject findOne(String collection, DBObject q, DBObject fileds) {

        return getCollection(collection).findOne(q, fileds);
    }



    /**
     * 分页查找集合对象，返回特定字段
     *
     * @param collection
     * @param q
     *            查询条件
     * @param fileds
     *            返回字段
     * @pageNo 第n页
     * @perPageCount 每页记录数
     */
    public static List<DBObject> findLess(String collection, DBObject q, DBObject fileds, int pageNo,
                                          int perPageCount) {

        return getCollection(collection).find(q, fileds)
                .skip((pageNo - 1) * perPageCount).limit(perPageCount)
                .toArray();
    }

    public static long getCollectionCount(String collection) {
        return getCollection(collection).getCount();
    }


}

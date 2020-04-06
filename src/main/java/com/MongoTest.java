package com;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MongoTest {

    /**
     * 查找
     */
    @Test
    public void testFind(){
        // 链接Mongo服务器
        MongoClient client = new MongoClient("192.168.79.169");
        // 得到要操作的数据库
        MongoDatabase spitdb = client.getDatabase("spitdb");
        // 得到要操作的集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        // 封装查询条件，只查询用户ID是1013的
        // BasicDBObject bson = new BasicDBObject("userid","1013");
        // 封装条件，查询访问量大于1000的  find({visits:{$gt:1000}})
        BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt" , 1000));
        // 得到集合中所有的文档
        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents) {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("用户ID:" + document.getString("userid"));
            System.out.println("访问量:" + document.getInteger("visits"));
        }
        client.close();
    }

    /**
     * 添加
     */
    @Test
    public void testAdd(){
        // 链接Mongo服务器
        MongoClient client = new MongoClient("192.168.79.169");
        // 得到要操作的数据库
        MongoDatabase spitdb = client.getDatabase("spitdb");
        // 得到要操作的集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //添加一条记录
        Map<String, Object> map = new HashMap<>();
        map.put("content","时间过的好快");
        map.put("userid","1016");
        map.put("visits", 100);
        Document document = new Document(map);
        spit.insertOne(document);
        client.close();
    }
}

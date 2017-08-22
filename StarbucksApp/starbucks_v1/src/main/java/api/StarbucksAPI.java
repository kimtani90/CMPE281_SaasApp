package api ;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue ;
import java.util.concurrent.LinkedBlockingQueue ;
import java.util.concurrent.ConcurrentHashMap ;
import java.util.Collection ;
import com.mongodb.client.MongoCollection;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class StarbucksAPI {


    private static MongoService mongoService = new MongoService();
    private static MongoCollection<Document> collection = mongoService.collection("Order");


    public static void addOrder(String key, Order order) {

        //StarbucksAPI.orders.put( key, order ) ;
        Document itemList = new Document();
     
        Document document = new Document("id", order.id)
                .append("location", order.location)
                .append("qty", order.qty)
                .append("name", order.name)
                .append("milk", order.milk)
                .append("size", order.size)
                .append("status", order.status)
                .append("message", order.message);
        collection.insertOne(document);

    }

    public static void updateOrder(Document existingOrder, Order order) {
    
        Document itemList = new Document();
       
        Document document = new Document("location", order.location)
                .append("qty", order.qty)
                .append("name", order.name)
                .append("milk", order.milk)
                .append("size", order.size)
                .append("status", order.status)
                .append("message", order.message);
        collection.updateOne(existingOrder, new Document("$set", document));
    }

    public static Document getOrder(String key) {


        Document query = new Document("id", key);
        FindIterable<Document> find = collection.find(query);
                
        MongoCursor<Document> cursor = find.iterator();
        Document doc = null;
        try {
            while (cursor.hasNext()) {
               
                doc =  new Document();
                doc = cursor.next();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
      
        return doc ;
    }

   
    public static void removeOrder(Document existing_order) {
       
        collection.deleteOne(existing_order);
    }

    public static void setOrderStatus( Order order, String status ) {
        switch ( status ) {
            case "PLACED":
                order.status = "PLACED" ;
                order.message = "Order has been placed." ;
              break;

            case "PAID":
                order.status = "PAID" ;
                order.message = "Payment Accepted." ;
                break;
        }
    }

    public static FindIterable<Document> getOrders() {

        FindIterable<Document> find = collection.find();
        
        return find;
    }

}



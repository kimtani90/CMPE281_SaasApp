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

    //public enum OrderStatus { PLACED, PAID, PREPARING, SERVED, COLLECTED  }

    private static BlockingQueue<String> orderQueue = new LinkedBlockingQueue<String>();
    private static ConcurrentHashMap<String,Order> orders = new ConcurrentHashMap<String,Order>();
    private static MongoService mongoService = new MongoService();
    private static MongoCollection<Document> collection = mongoService.collection("Order");

    public static void placeOrder(String order_id) {
        try {
            StarbucksAPI.orderQueue.put( order_id ) ;
        } catch (Exception e) {}
       // System.out.println( "Order Placed: " + order_id ) ;
    }

    public static void startOrderProcessor() {
        StarbucksBarista barista = new StarbucksBarista( orderQueue ) ;
        new Thread(barista).start();
    }

    public static void addOrder(String key, Order order) {

        //StarbucksAPI.orders.put( key, order ) ;
        Document itemList = new Document();
      //  ArrayList<Document> itemDocumentList = new ArrayList<>();
        //ArrayList<OrderItem> orderItemsList = order.items;

       /* for  (OrderItem orderItem : orderItemsList) {

            Document doc = new Document("qty", orderItem.qty)
                    .append("name", orderItem.name)
                    .append("milk", orderItem.milk)
                    .append("size", orderItem.size);
            itemDocumentList.add(doc);
        }
        Document document = new Document("id", order.id)
                .append("location", order.location)
                .append("items", itemDocumentList)
                .append("links", order.links)
                .append("status", order.status)
                .append("message", order.message);
*/
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
        //StarbucksAPI.orders.put( key, order ) ;
        Document itemList = new Document();
       /* ArrayList<Document> itemDocumentList = new ArrayList<>();
        ArrayList<OrderItem> orderItemsList = order.items;

        for  (OrderItem orderItem : orderItemsList) {

            Document doc = new Document("qty", orderItem.qty)
                    .append("name", orderItem.name)
                    .append("milk", orderItem.milk)
                    .append("size", orderItem.size);
            itemDocumentList.add(doc);
        }
        Document document = new Document("id", order.id)
                .append("location", order.location)
                .append("items", itemDocumentList)
                .append("links", order.links)
                .append("status", order.status)
                .append("message", order.message);
*/
        Document document = new Document("location", order.location)
                .append("qty", order.qty)
                .append("name", order.name)
                .append("milk", order.milk)
                .append("size", order.size)
             //   .append("links", order.links)
                .append("status", order.status)
                .append("message", order.message);
        collection.updateOne(existingOrder, new Document("$set", document));
    }

    public static Document getOrder(String key) {


        Document query = new Document("id", key);
        FindIterable<Document> find = collection.find(query);
            System.out.println("query"+query+"....key.."+key);
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
        /*StarbucksAPI.orders.remove( key ) ;
        StarbucksAPI.orderQueue.remove( key ) ;*/

        collection.deleteOne(existing_order);
    }

    public static void setOrderStatus( Order order, String URI, String status ) {
        switch ( status ) {
            case "PLACED":
                order.status = "PLACED" ;
                order.message = "Order has been placed." ;
              //  order.links.put ("order", URI+"/"+order.id ) ;
               // order.links.put ("payment",URI+"/"+order.id+"/pay" ) ;
              //  order.location = "Santa Francisco";
                break;

            case "PAID":
                order.status = "PAID" ;
                order.message = "Payment Accepted." ;
                //order.links.remove ( "payment" ) ;
                break;
        }
    }

    public static void setOrderStatus( Order order, String status ) {
        switch ( status ) {
            case "PREPARING":
                order.status = "PREPARING" ;
                order.message = "Order preparations in progress." ;
                break;

            case "SERVED":
                order.status = "SERVED" ;
                order.message = "Order served, wating for Customer pickup." ;
                break;

            case "COLLECTED":
                order.status = "COLLECTED" ;
                order.message = "Order retrived by Customer." ;
                break;
        }
    }


    public static FindIterable<Document> getOrders() {

        FindIterable<Document> find = collection.find();
        /*MongoCursor<Document> cursor = find.iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                OrderItem orderItem = new OrderItem();
                orderItem.setQty(Integer.parseInt((String)doc.get("qty"));
                orderItem.setName((String) doc.get("name"));
                orderItem.setSize((String)doc.get("size"));
                orderItem.setMilk((String)doc.get("milk"));
                StarbucksAPI.orders.put(key, orderItem);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
        return find;
    }

}



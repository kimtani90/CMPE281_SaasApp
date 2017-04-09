package api ;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


class MongoService {
    private static MongoClient mongoClient;
    private static String host = "localhost";   //your host name
    private static int port = 27017;      //your port no.
    private static String databaseName = "starbucks";

    public static MongoClient client() {
        if(mongoClient == null){
            return new MongoClient(host,port);
        }else {
            return mongoClient;
        }
    }

    public MongoCollection<Document> collection(String collectionName) {
        MongoDatabase db = client().getDatabase(databaseName);
        return db.getCollection(collectionName);
    }
}
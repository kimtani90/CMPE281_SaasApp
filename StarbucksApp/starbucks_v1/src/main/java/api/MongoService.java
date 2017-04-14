package api ;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


class MongoService {
    private static MongoClient mongoClient;
    private static String host = "13.56.12.153";   //your host name
    private static int port = 27017;      //your port no.
    private static String databaseName = "starbucks";

    public static MongoClient client() {
        if(mongoClient == null){
            System.out.println("host   "+host +" port "+port);
            return new MongoClient(host,port);
        }else {
            return mongoClient;
        }
    }

    public MongoCollection<Document> collection(String collectionName) {


       /* MongoClientURI uri = new MongoClientURI(
                "mongodb://kimtani90:Dishkim)2@starbuckscluster-shard-00-00-7dr1d.mongodb.net:27017,starbuckscluster-shard-00-01-7dr1d.mongodb.net:27017,starbuckscluster-shard-00-02-7dr1d.mongodb.net:27017/starbucks?ssl=true&replicaSet=starbucksCluster-shard-0&authSource=admin");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase(databaseName);
*/

        MongoDatabase db = client().getDatabase(databaseName);
        System.out.println("connection   "+db );
        return db.getCollection(collectionName);
    }
}
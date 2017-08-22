package api ;

import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.ext.jackson.* ;
import com.mongodb.DBCursor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException ;
import java.util.Collection ;

public class OrdersResource extends ServerResource {

    
    @Get
    public Representation get_action (Representation rep) throws IOException {
        FindIterable<Document> orders = StarbucksAPI.getOrders() ;
        return new JacksonRepresentation<FindIterable<Document>>(orders) ;
    }


}



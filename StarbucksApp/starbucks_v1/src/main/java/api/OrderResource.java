package api ;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.ext.jackson.* ;
import com.mongodb.*;

import java.io.IOException ;
import java.util.ArrayList;

public class OrderResource extends ServerResource {

    /*MongoService mongoService = new MongoService();
    DBCollection collection = mongoService.collection("Order");*/

    @Get
    public Representation get_action() throws JSONException {

        String order_id = getAttribute("order_id") ;
        // Order order = StarbucksAPI.getOrder( order_id ) ;
        Document existing_order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("") ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {
            // Order existing_order = StarbucksAPI.getOrder( order_id ) ;
            if ( order_id == null || order_id.equals("")  || existing_order == null ) {
                setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
                api.Status api = new api.Status() ;
                api.status = "error" ;
                api.message = "Order not found." ;
                return new JacksonRepresentation<api.Status>(api) ;
            }
            else
                return new JacksonRepresentation<Document>(existing_order) ;
        }
    }


    @Post
    public Representation post_action (Representation rep) throws IOException {

        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;

        Order order = orderRep.getObject() ;
        StarbucksAPI.setOrderStatus( order, getReference().toString(), "PLACED" ) ;
        StarbucksAPI.placeOrder( order.id ) ;
        StarbucksAPI.addOrder( order.id, order ) ;

        return new JacksonRepresentation<Order>(order) ;
    }


    @Put
    public Representation put_action (Representation rep) throws IOException {

        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;
        Order order = orderRep.getObject() ;

        String order_id = getAttribute("order_id") ;
        //Order existing_order = StarbucksAPI.getOrder( order_id ) ;
        Document existing_order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("")  || existing_order == null ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;

        }
        else if ( existing_order != null && existing_order.get("status") != "PLACED" ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Update Rejected." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {

            StarbucksAPI.setOrderStatus( order, getReference().toString(), "PLACED" ) ;
            StarbucksAPI.updateOrder( existing_order , order) ;

            return new JacksonRepresentation<Order>(order) ;
        }
    }

    @Delete
    public Representation delete_action (Representation rep) throws IOException {

        String order_id = getAttribute("order_id") ;
        Document existing_order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("")  || existing_order == null ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;

        }
        else if ( existing_order.get("status") !=  "PLACED" ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Cancelling Rejected." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {

            StarbucksAPI.removeOrder(existing_order) ;
            return null ;
        }

    }

}




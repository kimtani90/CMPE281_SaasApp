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

    
    @Get
    public Representation get_action() throws JSONException {

        String order_id = getAttribute("order_id") ;
        
        Document existing_order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("") || existing_order == null) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            Status st = new Status() ;
                st.status = "error" ;
                st.message = "Order not found." ;

            return new JacksonRepresentation<Status>(st) ;
        }
        else {
            
                return new JacksonRepresentation<Document>(existing_order) ;
        }
    }

  
    @Post
    public Representation post_action (Representation rep) throws IOException {

        
        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;

        
        Order order = orderRep.getObject() ;
        StarbucksAPI.setOrderStatus( order, "PLACED" ) ;
        
        StarbucksAPI.addOrder( order.id, order ) ;
        return new JacksonRepresentation<String>(order.id) ;
    }

    @Put
    public Representation put_action (Representation rep) throws IOException {

        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;
        Order order = orderRep.getObject() ;

        String order_id = getAttribute("order_id") ;
       
        Document existing_order = StarbucksAPI.getOrder( order_id ) ;
        
        if ( order_id == null || order_id.equals("")  || existing_order == null ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
                Status st = new Status() ;
                    st.status = "error" ;
                    st.message = "Order not found." ;
                return new JacksonRepresentation<Status>(st) ;

        }
        else if ( existing_order != null && !existing_order.get("status").equals("PLACED") ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            Status st = new Status() ;
                    st.status = "error" ;
                    st.message = "Order update rejected." ;
                return new JacksonRepresentation<Status>(st) ;
        }
        else {
            order.id= order_id;
            StarbucksAPI.setOrderStatus( order, "PLACED" ) ;
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
            Status st = new Status() ;
                    st.status = "error" ;
                    st.message = "Order not found." ;
            return new JacksonRepresentation<Status>(st) ;

        }
        else if ( !((String)existing_order.get("status")).equals("PLACED") ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            Status st = new Status() ;
                    st.status = "error" ;
                    st.message = "Order cancelling rejected." ;
                return new JacksonRepresentation<Status>(st) ;
        }
        else {

            StarbucksAPI.removeOrder(existing_order) ;
            setStatus( org.restlet.data.Status.SUCCESS_OK ) ;
            Status st = new Status() ;
                    st.status = "error" ;
                    st.message = "Order removed successfully." ;
                return new JacksonRepresentation<Status>(st) ;
        }

    }

}




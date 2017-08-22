package api ;

import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import java.util.Arrays;
import java.util.HashSet;
import org.restlet.service.CorsService;

public class StarbucksServer extends Application {

    public static void main(String[] args) throws Exception {
        Component server = new Component() ;
        server.getServers().add(Protocol.HTTP, 8080) ;
        server.getDefaultHost().attach(new StarbucksServer()) ;
        server.start() ;
        
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext()) ;
        router.attach( "/starbucks/sanfrancisco/order/{order_id}", OrderResource.class ) ;        
        router.attach( "/starbucks/sanfrancisco/order/", OrderResource.class ) ;        
        router.attach( "/starbucks/sanfrancisco/orders/", OrdersResource.class ) ;        
        return router;
    }


}


/*

POST    /order
        Create a new order, and upon success, 
        receive a Location header specifying the new order’s URI.

GET     /order/{order_id}
        Request the current state of the order specified by the URI.

PUT     /order/{order_id}
        Update an order at the given URI with new information, 
        providing the full representation.

DELETE  /order/{order_id}
        Logically remove the order identified by the given URI.

POST    /order/{order_id}/pay
        Process payment for the order.

GET     /orders
        Get list of Open Orders        

*/

        
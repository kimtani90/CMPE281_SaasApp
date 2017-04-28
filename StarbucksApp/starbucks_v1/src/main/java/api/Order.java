package api ;

import java.util.ArrayList ;
import java.util.Random ;
import java.util.UUID ;
import java.util.concurrent.ConcurrentHashMap ;

class Order {

	public String id = UUID.randomUUID().toString() ;
	public String location ; 
   // public ArrayList<OrderItem> items = new ArrayList<OrderItem>() ;
	public int qty ;
    public String name ;
    public String milk ;
     public String size ;
   // public ConcurrentHashMap<String,String> links = new ConcurrentHashMap<String,String>();
    public String status ;
    public String message ;
}

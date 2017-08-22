package api ;

import java.util.ArrayList ;
import java.util.Random ;
import java.util.UUID ;
import java.util.concurrent.ConcurrentHashMap ;

class Order {

	public String id = UUID.randomUUID().toString() ;
	public String location ; 
   
	public int qty ;
  public String name ;
  public String milk ;
  public String size ;
  
  public String status ;
  public String message ;
}

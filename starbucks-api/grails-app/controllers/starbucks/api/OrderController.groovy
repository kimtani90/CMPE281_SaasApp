package starbucks.api


import grails.rest.*
import grails.converters.*

class OrderController extends RestfulController {
    static responseFormats = ['json', 'xml']
    OrderController() {
        super(Order)
    }

     @Override 
     def show() {
      def order = new Order(params['order'])
        respond queryForResource(order.orderId)
    }
    /*@Override
     def save() {
  		def order = new Order(params['order'])

  		if(order.save()){
  		render order as json
  }else{
   //handle errors...
  }
 }*/
}

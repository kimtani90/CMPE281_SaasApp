package starbucks.api

import grails.mongodb.*

class Order implements MongoEntity<Order> {

    static constraints = {
    }


     String qty;
     String name;
     String milk;
     String size;
     String location;

}

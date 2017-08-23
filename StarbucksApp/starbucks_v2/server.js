// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
var exp    = require('express');        // call express
var app        = exp();                 // define our app using express
//var bodyParser = require('body-parser');

// configure app to use bodyParser()
// this will let us get the data from a POST
//app.use(bodyParser.urlencoded({ extended: true }));
//app.use(bodyParser.json());

var port = process.env.PORT || 8080;        // set our port

var bodyParser = require('body-parser');
//app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
var mongoose   = require('mongoose');
mongoose.connect('mongodb://localhost:27017/starbucksNodejs'); // connect to our database


var Order     = require('./app/models/Order');
// ROUTES FOR OUR API
// =============================================================================
var router = exp.Router();              // get an instance of the express Router

// test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.route('/order')

.post(function(req, res) {

	var order=new Order();
	
	order.location=req.body.location;
	order.qty=req.body.qty;
	order.name=req.body.name;
	order.milk=req.body.milk;
	order.size=req.body.size;


	console.log(order.name);
	order.save(function(err){
	if(err)
		res.send(err);
    res.status(200).send(order.id);
	});   
});

router.route('/orders')

.get(function(req,res){

	Order.find(function(err, order){

		if(err)
			res.status(404).send("No orders present!");
		res.status(200).json(order);
	});
});


router.route('/order/:order_id')

.get(function(req, res){

	Order.findById(req.params.order_id, function(err, order){

		if(err)
			res.status(404).send({message:'Order not found!'});
		

		res.status(200).json(order);
	});
})

.put(function(req, res){

	Order.findById(req.params.order_id, function(err, order){

		if(err)
			res.send({message:'Order not found!'});
		order.location=req.body.location;
		order.qty=req.body.qty;
		order.name=req.body.name;
		order.milk=req.body.milk;
		order.size=req.body.size;

		order.save(function(err){

			if(err)
				res.send(err);

			res.status(200).json({message:'Order updated!'});
		});
	});
})

.delete(function(req, res){

	Order.remove({
		_id:req.params.order_id
	}, function(err, order){
		if(err)
			res.send({message:'Order not found!'});
		res.status(200).send({message:'Order deleted!'});
		
	});
});




// more routes for our API will happen here

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Magic happens on port ' + port);


package tacos.data;

import tacos.Order;

public interface OrderRepository {
	
	//saving an order requires that you also 
	//save the tacos associated with the order to the Taco_Order_Tacos table
	Order save(Order order);
	
}

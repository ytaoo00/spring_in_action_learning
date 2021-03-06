//Similar to save taco repository,
//for order we need not only save the order data to the Taco_Order table
//but also reference to each taco in the order to the Taco_order_tacos table
//instead of using the cumbersome PreparedStatementCreator
//we use SimpleJdbcInsert, which is an object that wraps JdbcTemplate 
//to make it easier to insert data into a table

package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Taco;
import tacos.Order;

@Repository
public class JdbcOrderRepository implements OrderRepository{

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	//in the constructor, we need to create instance of SimpleJdbcInsert
	//for inserting values into tables
	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		//notes that here we are not assigning JdbcTemplate directly to an instance variable
		
		//this is configured to wrok with the Taco_order table and to 
		//assume that the id property will be provided or generated by the db.
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id"); //id property will be provided or generated by the db
		
		//this is configured to wrok with the Taco_order table and 
		//make no assume on how any IDs will be generated in the table
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		
		// an instance of Jackson's ObjectMapper
		this.objectMapper = new ObjectMapper();
		
	}

	//the flow for saving an order and its associated Taco objects.
	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();
		for(Taco taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}
		
		return order;
	}
	
	//deal with taco_order
	private long saveOrderDetails(Order order) {
		
		//we create a Map<Str,Obj> where the map keys correspond to the column names in the table
		// the data is inserted into.The map values are inserted into those column
		// so Map<to_insert_col_name, to_insert_values>
		@SuppressWarnings("unchecked")
		Map<String,Object> values = 
			objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		// this is a implementation of choice. for now just see it as it creates a map
		// the author implement it this way because that order contains too many properties.
		
		long orderId = 
				orderInserter
					.executeAndReturnKey(values) // save the order info to the Taco_Order table and returns the db generated ID as a number obj
					.longValue(); //convert the ID which is a number obj to long
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
	}
}

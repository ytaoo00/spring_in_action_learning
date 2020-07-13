package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

//this annotation is similar to @Controller or @Component 
//one of the stereotype annotations that Spring defines.
@Repository
public class JdbcIngredientRepository 
	implements IngredientRepository{
	
	private JdbcTemplate jdbc;
	
	//injects the bean with JdbcTemplate via @Autowired. 
	//so because of the @Repository, this class will be automatically discovered by Spring component scanning and
	//instantiated as a bean in the Spring application context.
	//because of the autowired, an instance of JdbcTemplate is injected as an argument to the constructor when 
	//JdbcIngredientRepository is created.
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query(
				"select id, name, type from Ingredient",
				this::mapRowToIngredient);
	}
	
	@Override
	public Ingredient findOne(String id) {
		return jdbc.queryForObject(
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
	}
	
	@Override
	public Ingredient save(Ingredient ingredient) {
		//update() method can be used for any query that writes or updates data in the database
		jdbc.update(
				"insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
	
	//mapping each row in the result set to an object
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException{
		return new Ingredient(
				rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type")));
	}
}

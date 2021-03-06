import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class account {
	
	public String name;
	public String stage;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getStage() { return stage; }
	public void setStage(String stage) { this.stage = stage; }

	public account (String Id) {
		
		get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());
		
		Connection connection = null;
	      Map<String, Object> attributes = new HashMap<>();
	      try {
	        connection = DatabaseUrl.extract().getConnection();

	        Statement stmt = connection.createStatement();
	        String sql = "select * from Salesforce.Account where SFID = '" + Id + "'";
	        //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
	        //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
	        ResultSet rs = stmt.executeQuery(sql);

	        ArrayList<String> output = new ArrayList<String>();
	        while (rs.next()) {
	          name = rs.getString("name");
	          stage = rs.getString("billingcity");
	        }

	        attributes.put("results", output);
	      } catch (Exception e) {
	        attributes.put("message", "There was an error: " + e);
	      } finally {
	        if (connection != null) try{connection.close();} catch(SQLException e){}
	      }
	}
	
	public String toString() {
		
		return "Name : " + name + " Stage : " + stage;
		
	}
	
	
}
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
		
		Connection connection = null;
		
	      try {
	        connection = DatabaseUrl.extract().getConnection();

	        Statement stmt = connection.createStatement();
	        //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
	        //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
	        ResultSet rs = stmt.executeQuery("SELECT Id, Name, billingstate FROM Salesforce.Account where Name = '" + Id + "'");

	        while (rs.next()) {
	          name = rs.getString("Name");
	          stage = rs.getString("billingstate");
	        }

	      } catch (Exception e) {
	    	  
	    	  e.getMessage();
	    	  
	      } finally {
	        if (connection != null) try{connection.close();} catch(SQLException e){}
	      }

	}
	
	public String toString() {
		
		return name + " " + stage;
		
	}
	
	
}
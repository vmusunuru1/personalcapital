package personalcapital;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import org.json.JSONException;
import org.json.JSONObject;

public class CSVToJSON {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//Provide the location of the destination file
				String FILENAME1 = "/Users/vamsimusunuru/Desktop/JSON/output1.csv"; 							
				File file1 = new File(FILENAME1);

				FileWriter writer = new FileWriter(file1, true);

				Class.forName("org.relique.jdbc.csv.CsvDriver");
				
				// Create a connection. The first command line parameter is
				// the directory containing the .csv files.
				// A single connection is thread-safe for use by several threads.
				Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + "/Users/vamsimusunuru/Desktop/CSVFiles/");	//Source CSV file
				
				// Create a Statement object to execute the query with.
				Statement stmt = conn.createStatement();
				String query= "SELECT * FROM file1";					//csvdata is the source file
				
				// Select the ID and NAME columns from csvdata.csv
				ResultSet results = stmt.executeQuery(query);
				ResultSetMetaData metadata = results.getMetaData();
				int numColumns = metadata.getColumnCount();
				int count=1;
				while(results.next())             //iterate rows
				{
					JSONObject obj = new JSONObject();      //extends HashMap
					for (int i = 1; i <= numColumns; ++i)           //iterate columns
					{
					    String column_name = metadata.getColumnName(i);
					    obj.put(column_name, results.getObject(column_name));
					}

					writer.write("{ \"index\" : { \"_index\": \"plans\", \"_type\" : \"listings\", \"_id\" : \""+count+"\" } }\n");
					writer.write(obj.toString());
					writer.write("\n");
					count++;
				}
				// Clean up
				conn.close();
			}
}

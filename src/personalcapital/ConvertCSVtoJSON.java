package personalcapital;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
 
public class ConvertCSVtoJSON {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		
		int rows = getNoOfRowsOfCSVfile("/Users/vamsimusunuru/Desktop/f_5500_2017_latest.csv");
		
		int fileRowLimit = 1000;
		
		int StartingFileRow = 1;
		
		int itertioncount = 1;
		
		while(rows > fileRowLimit)	
		 {
		
		  createJsonFiles("/Users/vamsimusunuru/Desktop/JSON/output"+itertioncount+".json", "/Users/vamsimusunuru/Desktop", "f_5500_2017_latest", fileRowLimit, StartingFileRow);
			
		  fileRowLimit = fileRowLimit + 1000;
		  
		  StartingFileRow = StartingFileRow + 1000;
		  
		  itertioncount++;
		  
		}		
					
		}
	
	
	


	public static void createJsonFiles(String outPutFileName, String directoryoFinputFiles, String inputFileName, int fileRowLimit, int FileStartingRow) throws IOException, ClassNotFoundException, SQLException{
		//Provide the location of the destination file
		String FILENAME1 = outPutFileName; 							
		File file1 = new File(FILENAME1);

		FileWriter writer = new FileWriter(file1, true);

		Class.forName("org.relique.jdbc.csv.CsvDriver");
		
		// Create a connection. The first command line parameter is
		// the directory containing the .csv files.
		// A single connection is thread-safe for use by several threads.
		Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + directoryoFinputFiles);	//Source CSV file
		
		// Create a Statement object to execute the query with.
		Statement stmt = conn.createStatement();
		//inputFile is the source file
		String query= "SELECT * FROM "+inputFileName+"";					
		
		// Select the ID and NAME columns from csvdata.csv
		ResultSet results = stmt.executeQuery(query);
		ResultSetMetaData metadata = results.getMetaData();
		int numColumns = metadata.getColumnCount();
		int count=1;
		while(results.next()) 
			//iterate rows
		{		
			
			if(count > fileRowLimit){
				break;
			}
			
	    	if(count >= FileStartingRow){
				
				
			JSONObject obj = new JSONObject();      //extends HashMap
			for (int i = 1; i <= numColumns; ++i)           //iterate columns
			{
			    String column_name = metadata.getColumnName(i);
			    obj.put(column_name, results.getObject(column_name));
			}

			writer.write("{ \"index\" : { \"_index\": \"plans\", \"_type\" : \"listings\", \"_id\" : \""+count+"\" } }\n");
			writer.write(obj.toString());
			writer.write("\n");
			
			}
	    	count++;
			
			
		}
		// Clean up
		writer.close();
		conn.close();
	  }
	

    public static int getNoOfRowsOfCSVfile(String fileName) throws IOException{
    	BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String input;
        int count = 0;
        while((input = bufferedReader.readLine()) != null)
        {
            count++;
        }

        System.out.println("Count : "+count);
        return count;
    	
    }
}

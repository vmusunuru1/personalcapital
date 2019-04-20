package personalcapital;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;  


public class CSVFileSplit {  
	
	public static void main(String args[]) throws IOException {

	long linesWritten = 0;
    
    int linesPerSplit = 20000;
    
    File inputFile = null;

    try {
        inputFile = new File("/Users/vamsimusunuru/Desktop/f_5500_2017_latest.csv");
        InputStream inputFileStream = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputFileStream));

        String line = reader.readLine();
        
        int count = 1;
        
        String headerLine = null;

        while (line != null) {
            File outFile = new File("/Users/vamsimusunuru/Desktop/CSVFiles/" + "file"+ count +".csv");
            //File outFile = new File(outfileName);
            Writer writer = new OutputStreamWriter(new FileOutputStream(outFile));
            headerLine = line; 
            writer.write(headerLine);
            while (line != null && linesWritten < linesPerSplit) {
                writer.write(line);
                writer.write(System.lineSeparator());
                line = reader.readLine();
                linesWritten++;
            }

            writer.close();
            linesWritten = 0;//next file
            count++;//nect file count
        }

        //reader.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
   
	}
}

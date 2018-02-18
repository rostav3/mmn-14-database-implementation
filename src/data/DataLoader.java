package data;

import java.io.*;
import java.util.ArrayList;

/*************************************************************************************************
 * This class handle the files load and save.
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class DataLoader {

    /**
     * Thid method save the data to csv
     * @param data - The data need to save in file
     * @param csvPath - The csv path for save the data
     */
    public void saveDataToCsv(String data, String csvPath){
        PrintWriter writer;
        try {
            writer = new PrintWriter(csvPath, "UTF-8");
            writer.println(data);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Error while try to save the data to file");
            System.exit(1);
        }
    }

    /**
     * This method load the data from the csv and return him.
     * @param csvPath - The path of the csv file
     * @return the data from the csv as list of Record
     */
    public ArrayList<Record> loadCsvData(String csvPath) {
        BufferedReader br;
        String line;
        ArrayList<Record> list = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(csvPath));
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                list.add(new Record(record, record[0], line.length()));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error while try to load the file");
            System.exit(1);
        }
        return list;
    }
}
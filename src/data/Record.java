package data;

/*************************************************************************************************
 * Pojo class present Record.
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class Record {
    private String sortIndex;
    private String[] record;
    private int recordSize;

    public Record(String [] recordVal, String sortIndexVal, int recordSizeVal){
        sortIndex = sortIndexVal;
        record = recordVal;
        recordSize = recordSizeVal;
    }

    /**
     * @return the index we sort by all the data
     */
    public String getSortIndex() {
        return sortIndex;
    }

    /**
     * @return the record size in bytes
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * @return the record as string line in csv file
     */
    public String toString(){
        String returnVal = "";
        for (int i = 0; i < record.length; i++){
            returnVal += record[i];
            if (i != record.length - 1){
                returnVal += ",";
            }
        }
        return returnVal;
    }
}

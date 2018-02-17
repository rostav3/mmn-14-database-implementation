package data;

/**
 * Created by stav on 2/1/2018.
 */
public class Record {
    private String sortIndex;
    private String[] record;
    private int recordSize;

    public Record(String [] recordVal, String sortIndexVal, int recordSizeVal){
        sortIndex = sortIndexVal;
        record = recordVal;
        recordSize = recordSizeVal;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public String[] getRecord() {
        return record;
    }

    public int getRecordSize() {
        return recordSize;
    }

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

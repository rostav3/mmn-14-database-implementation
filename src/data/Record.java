package data;

/**
 * Created by stav on 2/1/2018.
 */
public class Record {
    private String sortIndex;
    private String[] record;

    public Record(String [] recordVal, String sortIndexVal){
        sortIndex = sortIndexVal;
        record = recordVal;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String[] getRecord() {
        return record;
    }

    public void setRecord(String[] record) {
        this.record = record;
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

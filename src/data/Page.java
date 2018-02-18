package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stav on 2/1/2018.
 */
public class Page {
    private List<Record> dataInPage;
    private int pageSize;

    public Page(){
        dataInPage = new ArrayList<Record>();
        pageSize = 0;
    }

    public void setRecord(Record record){
        dataInPage.add(record);
        pageSize += record.getRecordSize();
    }
    public List<Record> getDataInPage(){
        return dataInPage;
    }
    public String toString(){
        String returnVal = "";
        for (int i = 0; i < dataInPage.size(); i++){
            returnVal += dataInPage.get(i).toString();
            if (i != dataInPage.size() - 1){
                returnVal += "\n";
            }
        }
        return returnVal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

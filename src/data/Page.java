package data;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * Pojo class present Page.
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class Page {
    private List<Record> dataInPage;
    private int pageSize;

    public Page(){
        dataInPage = new ArrayList<>();
        pageSize = 0;
    }

    /**
     * Set the record in the page and add his size to page size parameter.
     * @param record - the record to add.
     */
    public void setRecord(Record record){
        dataInPage.add(record);
        pageSize += record.getRecordSize();
    }

    /**
     * @return the records in the page.
     */
    public List<Record> getDataInPage(){
        return dataInPage;
    }

    /**
     * @return the page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return the page as string of lines in csv file
     */
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
}
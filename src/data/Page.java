package data;

import java.util.ArrayList;

/**
 * Created by stav on 2/1/2018.
 */
public class Page {
    public ArrayList<Record> dataInPage;

    public Page(){
        dataInPage = new ArrayList<Record>();
    }

    public void setRecord(Record record){
        dataInPage.add(record);
    }
    public ArrayList<Record> getDataInPage(){
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
}

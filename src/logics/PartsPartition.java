package logics;

import data.Page;
import data.Record;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * This class handle the partition to parts
 * Created by Stav Rockah on 1/21/2018.
 ************************************************************************************************/
public class PartsPartition {
    private int pageSize;
    private int numOfPages;

    public PartsPartition(int pageSizeVal, int numOfPagesVal){
        pageSize = pageSizeVal;
        numOfPages = numOfPagesVal;
    }

    /**
     * This method separate the data to pages
     * @param data - the list of all the records
     * @return the pages with the data
     */
    public ArrayList<Page> getDataByPages(List<Record> data, boolean isFromDisk) {
        //keep the current line number
        int lineNumberCounter = 0;
        ArrayList<Page> pages = new ArrayList<Page>();

        for (int i = 0; (i < numOfPages) && (lineNumberCounter < data.size()); i++) {
            Page dataPage = new Page();
            for (int j = 0; (j < pageSize) && (lineNumberCounter < data.size()); j++) {
                Record record = data.get(lineNumberCounter);
                dataPage.setRecord(record);
                lineNumberCounter++;
            }
            pages.add(dataPage);
            if (isFromDisk){
                System.out.println("Read total of " + (i+1) + " pages, from disk to memory");
            }else{
                System.out.println("Read total of " + (i+1) + " pages, from memory to disk");
            }
        }
        return pages;
    }
}

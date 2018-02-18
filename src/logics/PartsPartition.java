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

    public PartsPartition(int pageSizeVal){
        pageSize = pageSizeVal;
    }

    /**
     * This method separate the data to pages
     * @param data - the list of all the records
     * @return the pages with the data
     */
    public ArrayList<Page> getDataByPages(List<Record> data, boolean isFromDisk) {
        //keep the current line number
        int lineNumberCounter = 0;
        int pageNumberCounter = 0;
        ArrayList<Page> pages = new ArrayList<Page>();

        while (lineNumberCounter < data.size()) {
            Page dataPage = new Page();
            int currPageSize = 0;
            while ((currPageSize < pageSize) && (lineNumberCounter < data.size())){
                Record record = data.get(lineNumberCounter);
                currPageSize += record.getRecordSize();
                if (!isPageFull(currPageSize)) {
                    dataPage.setRecord(record);
                    lineNumberCounter++;
                    if (isFromDisk){
                        System.out.println("Read total of " + pageSize + " bytes in page no." + pageNumberCounter + " , from disk to memory");
                    }else{
                        System.out.println("Read total of " + pageSize + " bytes in page no." + pageNumberCounter + " , from memory to disk");
                    }
                }
            }

            pages.add(dataPage);
            if (isFromDisk){
                System.out.println("Read total of " + pageNumberCounter + " pages, from disk to memory");
            }else{
                System.out.println("Read total of " + pageNumberCounter + " pages, from memory to disk");
            }
        }
        return pages;
    }

    public boolean isPageFull(int size){
        return size > pageSize;
    }
}

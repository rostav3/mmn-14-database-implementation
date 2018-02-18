package logics;

import data.Page;
import data.Record;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * This class handle the partition to parts
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class PartsPartition {
    private int pageSize;

    public PartsPartition(int pageSizeVal){
        pageSize = pageSizeVal;
    }

    /**
     * This method separate the data to pages
     * @param data - the list of all the records
     * @param needPrint - if need to print the performence to the user
     * @return the pages with the data
     */
    public ArrayList<Page> getDataByPages(List<Record> data, boolean needPrint) {
        //keep the current line number
        int lineNumberCounter = 0;
        int pageNumberCounter = 0;
        ArrayList<Page> pages = new ArrayList<>();

        while (lineNumberCounter < data.size()) {
            Page dataPage = new Page();
            int currPageSize = 0;
            pageNumberCounter++;
            boolean endOfPage = false;
            while ((!endOfPage) && (lineNumberCounter < data.size())){
                Record record = data.get(lineNumberCounter);
                endOfPage = !isRecordCanAdd(currPageSize,record.getRecordSize());
                if (!endOfPage) {
                    currPageSize += record.getRecordSize();
                    dataPage.setRecord(record);
                    lineNumberCounter++;
                    if (needPrint)
                        System.out.println("Read total of " + currPageSize + " bytes in page no." + pageNumberCounter + " , from disk to memory");
                } else if (currPageSize == 0){
                    System.out.println("Sorry, the page size you enter is too small.");
                    System.exit(1);
                }
            }
            pages.add(dataPage);
            if (needPrint)
                System.out.println("Read total of " + pageNumberCounter + " pages, from disk to memory");
        }
        return pages;
    }

    /**
     * check if the record can enter to the page
     * @param size - the size of the page
     * @param recordSize - the record size need to add
     * @return if the record can add to the page
     */
    public boolean isRecordCanAdd(int size, int recordSize){
        return size + recordSize <= pageSize;
    }
}
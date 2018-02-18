package logics;

import data.PageGroupSorted;
import data.Page;
import data.Record;
import utils.GlobalContexts;
import java.util.*;

/*************************************************************************************************
 * This class handle all the sorting
 * In all the algorithm, I decided to save all the data in lists/ groups and not in files.
 * What simulator the RAM memory size is the data I work with.
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class ExternalSort {
    private List<Page> pagesSorted;
    private int levelMerge;
    private int groupMerge;

    /**
     * @param pagesToMerge - the pages need to sort
     * @param pageCount - the count oof pages in the RAM memory
     */
    public ExternalSort(List<Page> pagesToMerge, int pageCount) {
        ArrayList<List<Record>> recordsGroups = new ArrayList<>();
        ArrayList<Record> currRecordsGroup = new ArrayList<>();
        levelMerge = 1;
        // Create groups of pageCount * pages
        // Then, Run merge sort in each group - more effective than run sort for each page
        int i;
        for (i= 0; i < pagesToMerge.size(); i++){
            // If get group of pageCount of pages (as list of record), rum nerge sort and add to the list
            if ((i % pageCount == 0) && (currRecordsGroup.size() != 0)){
                System.out.println("Start merge sort in group number " + Math.floor(i / pageCount));
                recordsGroups.add(MergeSort(currRecordsGroup));
                System.out.println("End merge sort in group number " + Math.floor(i / pageCount));
                currRecordsGroup = new ArrayList<>();
            }
            // Add the records of the page to the current group of the records
            for (Record record:pagesToMerge.get(i).getDataInPage()){
                currRecordsGroup.add(record);
            }
        }
        System.out.println("Start merge sort in group number " + Math.floor((i-1) / pageCount));
        recordsGroups.add(MergeSort(currRecordsGroup));
        System.out.println("End merge sort in group number " + Math.floor((i-1) / pageCount));

        // Return the sorted records to groups of pages
        List<PageGroupSorted> sortedGroups = new ArrayList<>();
        for (List <Record> records : recordsGroups){
            sortedGroups.add(new PageGroupSorted(GlobalContexts.partsPartition.getDataByPages(records, false)));
        }

        // Run over the groups and merge them until get one group of all the data sorted as pages
        sortedGroups =  MergePages(sortedGroups, pageCount-1);
        while (sortedGroups.size() != 1){
            sortedGroups =  MergePages(sortedGroups, pageCount-1);
        }
        pagesSorted = sortedGroups.get(0).getPageGroup();
    }

    /**
     * @return the data sorted in pages
     */
    public List<Page> getPagesSorted(){
        return pagesSorted;
    }

    /**
     * This method run the merge sort algorithm
     * @param records - The data need to sort
     * @return The data sort.
     */
    private List<Record> MergeSort(List<Record> records) {
        // In case it's empty or contain at most 1 record, return the data.
        if (records == null || records.size() < 2){
            return records;
        }

        ArrayList<Record> sorted = new ArrayList<>();
        int middle = (int) Math.floor(records.size() / 2);
        List <Record> left = records.subList(0, middle);
        List <Record> right = records.subList(middle, records.size());

        left = MergeSort(left);
        right = MergeSort(right);

        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < left.size() + right.size(); i++) {
            if (leftIndex == left.size()) {
                sorted.add(right.get(rightIndex));
                rightIndex++;
            } else if (rightIndex == right.size()) {
                sorted.add(left.get(leftIndex));
                leftIndex++;
            } else {
                Record leftPrtSide = left.get(leftIndex);
                Record rightPrtSide = right.get(rightIndex);

                // Get only the part that we need to compare based on the starting index and the length
                String partToCompareLeft = leftPrtSide.getSortIndex();
                String partToCompareRight = rightPrtSide.getSortIndex();

                // add the next smaller record to the sorted list
                if (partToCompareLeft.compareTo(partToCompareRight) < 0) {
                    sorted.add(leftPrtSide);
                    leftIndex++;
                } else {
                    sorted.add(rightPrtSide);
                    rightIndex++;
                }
            }
        }
        return sorted;
    }

    /**
     * This method get the list of all the groups of the sorted data as pages, and merge groups of "countOfPages" into
     * new list of groups
     * @param sortedGroups - The list of all the groups of the sorted data as pages
     * @param pageCount - The count of pages in the RAM memory without the page left for the merged data.
     * @return the new list of groupsPage, after merge "countOfPages" groups.
     */
    private List<PageGroupSorted> MergePages(List <PageGroupSorted> sortedGroups, int pageCount){
        List<List<PageGroupSorted>> groupsForMerge = new ArrayList<>();
        List<PageGroupSorted> group = new ArrayList<>();
        List<PageGroupSorted> newSortedGroups = new ArrayList<>();

        // Create groups of PageGroupSorted in size of "pageCount"
        for (int i = 0; i < sortedGroups.size(); i++){
            if ((i % pageCount == 0) && (group.size() != 0)){
                groupsForMerge.add(group);
                group = new ArrayList<>();
            }
            group.add(sortedGroups.get(i));
        }
        groupsForMerge.add(group);

        // Run each group of pageGroups and merge each group of groupPages to new groupPage sorted as pages
        for (int i=0; i < groupsForMerge.size(); i++){
            System.out.println("Merge level: " + levelMerge + ", merge the group of pages number " + i);
            newSortedGroups.add(MergePagesCount(groupsForMerge.get(i)));
        }
        levelMerge++;
        return newSortedGroups;
    }

    /**
     * This method get list of groupOfPages and merge them to 1 Group of sorted data in pages
     * @param sortedGroups - list of groupOfPages needed to merge
     * @return the list in 1 group, sorted in pages
     */
    private PageGroupSorted MergePagesCount(List<PageGroupSorted> sortedGroups){
        List <Page> group = new ArrayList<>();
        Page currentPage = new Page();

        // create the new group until finish run over all the list that got as parameter.
        while (sortedGroups.size() != 0){
            // Get the minimum record from all the list
            Record record = getMinVal(sortedGroups);

            // If the minimum record can enter to the current page enter him.
            // If not, save the Page in the sorted group, create new Page and enter the the current record to him.
            if (!GlobalContexts.partsPartition.isRecordCanAdd(currentPage.getPageSize(), record.getRecordSize())){
                group.add(currentPage);
                currentPage = new Page();
            }
            currentPage.setRecord(record);
        }
        group.add(currentPage);
        return new PageGroupSorted(group);
    }

    /**
     * run over the first value in the first page in each group and get the minimum value.
     * @param sortedGroups - list of sorted PageGroupSorted
     * @return the minimum record (By the sortedIndex)
     */
    private Record getMinVal(List<PageGroupSorted> sortedGroups){
        Record minRecord = null;
        String minVal = "";
        int index = -1;

        // Run over all the groups and check the first value of each first page in each group
        for (int i= 0; i < sortedGroups.size(); i++){
            Page firstPage = sortedGroups.get(i).getPageGroup().get(0);
            Record record = firstPage.getDataInPage().get(0);

            // If it's smaller from the minVal or it's the first value checked
            if (((minVal.equals("")) || (record.getSortIndex().compareTo(minVal) < 0))){
                minVal = record.getSortIndex();
                minRecord = record;
                index = i;
            }
        }

        Page pageWithMinVal = sortedGroups.get(index).getPageGroup().get(0);

        // Remove the minRecord
        pageWithMinVal.getDataInPage().remove(0);

        // If after the remove the Page is empty, remove him.
        if (pageWithMinVal.getDataInPage().size() == 0){
            sortedGroups.get(index).getPageGroup().remove(0);
        }

        // If after the remove the Group of the pages is empty, remove him.
        if (sortedGroups.get(index).getPageGroup().size() == 0) {
            sortedGroups.remove(index);
        }

        return minRecord;
    }
}
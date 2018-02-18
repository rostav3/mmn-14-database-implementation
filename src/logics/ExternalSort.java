package logics;

import data.GroupOfPages;
import data.Page;
import data.Record;
import utils.GlobalContexts;

import java.util.*;

/*************************************************************************************************
 * This class handle the sorting
 * Created by Stav Rockah on 1/21/2018.
 ************************************************************************************************/
public class ExternalSort {

    private List<Page> pagesSorted;
    /**
     * @param pagesToMerge - the psges need to sort
     */
    public ExternalSort(List<Page> pagesToMerge, int pageCount) {

        ArrayList<List<Record>> groupsSorted = new ArrayList<List<Record>>();
        ArrayList<Record> groupToSort = new ArrayList<Record>();

        // Run merge sort for each group data - P/M groups
        for (int i= 0; i < pagesToMerge.size(); i++){
            if ((i % pageCount == 0) && (groupToSort.size() != 0)){
                groupsSorted.add(MergeSort(groupToSort));
                groupToSort = new ArrayList<>();
            }
            for (Record record:pagesToMerge.get(i).getDataInPage()){
                groupToSort.add(record);
            }
        }
        groupsSorted.add(MergeSort(groupToSort));
        List<GroupOfPages> partsSorted = new ArrayList<GroupOfPages>();
        for (List <Record> records : groupsSorted){
            partsSorted.add(new GroupOfPages(GlobalContexts.partsPartition.getDataByPages(records, true)));
        }
        // Merge in Page size
        // TODO : I need to MARGE M-1 Pages to Pages - like in page 111
        partsSorted =  MergePages(partsSorted, pageCount-1);
        while (partsSorted.size() != 1){
            partsSorted =  MergePages(partsSorted, pageCount-1);
        }
        System.out.println("yayyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        pagesSorted = partsSorted.get(0).getPageGroup();
    }

    public List<Page> getPagesSorted(){
        return pagesSorted;
    }
    /**
     * This method rn the merge sort algorithm
     * @param records - The data need to sort
     * @return The data sort.
     */
    private List<Record> MergeSort(List<Record> records) {
        // In case it's empty or contain at most 1 record, return the data.
        if (records == null || records.size() < 2){
            return records;
        }

        ArrayList<Record> sorted = new ArrayList<Record>();
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


    private List<GroupOfPages> MergePages(List <GroupOfPages> partsSorted, int countOfGroups){
        List<List<GroupOfPages>> partsForMerge = new ArrayList<List<GroupOfPages>>();
        List<GroupOfPages> part = new ArrayList<GroupOfPages>();
        List<GroupOfPages> sortedParts = new ArrayList<GroupOfPages>();

        for (int i = 0; i < partsSorted.size(); i++){
            if ((i % countOfGroups == 0) && (part.size() != 0)){
                partsForMerge.add(part);
                part = new ArrayList<GroupOfPages>();
            }
            part.add(partsSorted.get(i));
        }
        partsForMerge.add(part);

        for (int i=0; i < partsForMerge.size(); i++){
            sortedParts.add(MergePagesCount(partsForMerge.get(i)));
        }
        return sortedParts;
    }

    private GroupOfPages MergePagesCount(List<GroupOfPages> partsSorted){
        List <Page> group = new ArrayList<Page>();
        Page page = new Page();
        while (partsSorted.size() != 0){
            Record record = getMinVal(partsSorted);
            if (GlobalContexts.partsPartition.isPageFull(page.getPageSize() + record.getRecordSize())){
                group.add(page);
                page = new Page();
            }
            page.setRecord(record);
        }
        group.add(page);
        return new GroupOfPages(group);
    }
    private Record getMinVal(List<GroupOfPages> partsSorted){
        try {
            Record minRecord = null;
            String minVal = "";
            int index = -1;
            for (int i= 0; i < partsSorted.size(); i++){
                Record record = partsSorted.get(i).getPageGroup().get(0).getDataInPage().get(0);
                if (((minVal.equals("")) || (record.getSortIndex().compareTo(minVal) < 0))){
                    minVal = record.getSortIndex();
                    minRecord = record;
                    index = i;
                }
            }
            partsSorted.get(index).getPageGroup().get(0).getDataInPage().remove(0);
            if (partsSorted.get(index).getPageGroup().get(0).getDataInPage().size() == 0){
                partsSorted.get(index).getPageGroup().remove(0);
            }
            if (partsSorted.get(index).getPageGroup().size() == 0) {
                partsSorted.remove(index);
            }
            return minRecord;

        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
package logics;

import data.Page;
import data.Record;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************************************
 * This class handle the sorting
 * Created by Stav Rockah on 1/21/2018.
 ************************************************************************************************/
public class ExternalSort {
    private List<Record> records;

    /**
     * @param pagesToMerge - the psges need to sort
     */
    public ExternalSort(ArrayList<Page> pagesToMerge) {
        records = new ArrayList<Record>();

        // Merge all the pages
        System.out.println("Marge the pages to list..");
        for (Page page : pagesToMerge){
            for (Record record : page.getDataInPage()){
                records.add(record);
            }
        }

        // set the records after the sort
        System.out.println("Begin merge sort..");
        records = MergeSort(records);
        System.out.println("Finish merge sort :)");

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

    public List<Record> getRecords() {
        return records;
    }
}
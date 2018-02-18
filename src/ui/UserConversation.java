package ui;

import utils.StringUtils;
import java.util.Scanner;

/*************************************************************************************************
 * This class contain all the user conversion like questions and inputs.
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class UserConversation {
    private Scanner scanner;
    public UserConversation(){
        scanner = new Scanner(System.in);
    }

    /**
     * @return the page size got as input
     */
    public Integer getPageSize(){
        System.out.println("Please enter page size in bytes: ");
        String pageSizeInput = scanner.nextLine();
        while (!StringUtils.isInteger(pageSizeInput)){
            System.out.println("The input need to be a number and '" + pageSizeInput + "' isn't. \n " +
                               "Please try enter page size again: ");
            pageSizeInput = scanner.nextLine();
        }
        return Integer.parseInt(pageSizeInput);
    }

    /**
     * @return the page count got as input
     */
    public Integer getPageNumber(){
        System.out.println("Please enter the page count in the RAM memory: ");
        String pageNumberInput = scanner.nextLine();
        while (!StringUtils.isInteger(pageNumberInput)){
            System.out.println("The input need to be a number and '" + pageNumberInput + "' isn't. \n " +
                    "Please try enter number of pages again: ");
            pageNumberInput = scanner.nextLine();
        }
        return Integer.parseInt(pageNumberInput);
    }

    /**
     * @return the csv path file
     */
    public String getDataPath(){
        System.out.println("Please enter csv file path contain the data you want to sort: ");
        return scanner.nextLine();
    }
}
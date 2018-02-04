package utils;

/*************************************************************************************************
 * This class contain utils for string.
 * Created by Stav Rockah on 1/21/2018.
 ************************************************************************************************/
public class StringUtils {

    /**
     * The method check if the string is integer
     * @param str - the string need to check if integer
     * @return true if the string is integer, else false.
     */
    public static boolean isInteger(String str) {
        if(str.isEmpty()) return false;
        for(int i = 0; i < str.length(); i++) {
            if(i == 0 && str.charAt(i) == '-') {
                if(str.length() == 1) {
                    return false;
                }
            }
            if(Character.digit(str.charAt(i),10) < 0) {
                return false;
            }
        }
        return true;
    }
}

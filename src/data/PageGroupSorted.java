package data;

import java.util.List;

/*************************************************************************************************
 * Pojo class present sorted data as group of pages
 * Created by Stav Rockah, ID.307900878
 ************************************************************************************************/
public class PageGroupSorted {
    private List<Page> pageGroup;

    public PageGroupSorted(List<Page> pages){
        pageGroup = pages;
    }

    /**
     * @return list of pages in the group
     */
    public List<Page> getPageGroup() {
        return pageGroup;
    }
}

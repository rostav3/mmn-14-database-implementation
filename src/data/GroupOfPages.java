package data;

import java.util.List;

/**
 * Created by stav on 2/18/2018.
 */
public class GroupOfPages {
    private List<Page> pageGroup;
    public GroupOfPages(List<Page> pages){
        pageGroup = pages;
    }

    public List<Page> getPageGroup() {
        return pageGroup;
    }
}

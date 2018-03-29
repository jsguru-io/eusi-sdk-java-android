package io.jsguru.eusisdk.models;

/**
 * Created by Petar Suvajac on 3/22/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiPagination {
    private int page;
    private int count;
    private int total;
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

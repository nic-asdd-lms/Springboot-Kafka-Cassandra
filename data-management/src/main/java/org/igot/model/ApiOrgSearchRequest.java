package org.igot.model;

public class ApiOrgSearchRequest {
    private ApiOrgFilterRequest filters = new ApiOrgFilterRequest();
    private ApiSortByRequest sort_by = new ApiSortByRequest();

    public ApiOrgFilterRequest getFilters() {
        return filters;
    }

    public void setFilters(ApiOrgFilterRequest filters) {
        this.filters = filters;
    }

    public ApiSortByRequest getSort_by() {
        return sort_by;
    }

    public void setSort_by(ApiSortByRequest sort_by) {
        this.sort_by = sort_by;
    }
}

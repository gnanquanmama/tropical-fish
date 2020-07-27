package com.mcoding.base.core.common.pattern.filterchain;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 责任链
 *
 * @author wzt on 2020/5/3.
 * @version 1.0
 */
public class FilterContext<Request, Response> {

    private List<Filter<Request>> filterList = Lists.newArrayList();

    private int offSet = 0;

    @Getter
    @Setter
    private Target<Request, Response> target;

    @Getter
    private Response executeResult;


    public void doFilter(Request request) {

        if (offSet < filterList.size()) {
            Filter<Request> filter = filterList.get(offSet++);
            filter.doFilter(request, this);

            return;
        }

        executeResult = target.execute(request);
    }

    public void addFilter(Filter<Request> filter) {
        filterList.add(filter);
    }

}

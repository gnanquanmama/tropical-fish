package com.fastdev;

import java.util.List;
import java.util.Map;

public interface BigDataCenterSearchService {

    void createIndexByBatch(List<Map<String, Object>> dataList, String type);

}

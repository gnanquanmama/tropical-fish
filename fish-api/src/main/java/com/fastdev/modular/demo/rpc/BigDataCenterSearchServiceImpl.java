package com.fastdev.modular.demo.rpc;


import com.alibaba.dubbo.config.annotation.Service;
import com.fastdev.BigDataCenterSearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service(version = "0.0.1-SNAPSHOT")
public class BigDataCenterSearchServiceImpl implements BigDataCenterSearchService {

    private final String index = "big_data_center";


    @Override
    public void createIndexByBatch(List<Map<String, Object>> dataList, String type) {


    }




}

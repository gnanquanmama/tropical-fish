package com.mcoding.modular.search.controller;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.mcoding.base.core.orm.DslParser;
import com.mcoding.base.core.rest.*;

import com.mcoding.modular.search.service.ProductSpuService;
import com.mcoding.modular.search.entity.ProductSpu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品SPU
 * </p>
 *
 * @author wzt
 * @since 2022-06-24
 */
@Slf4j
@Api(tags = "业务-商品服务")
@RestController
public class ProductSpuController {

    @Resource
    private ProductSpuService productSpuService;

    @ApiOperation("创建")
    @PostMapping("/service/goods/create")
    public ResponseResult<String> create(@Valid @RequestBody ProductSpu productSpu) {
        productSpuService.save(productSpu);
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除")
    @PostMapping("/service/goods/delete")
    public ResponseResult<String> delete(@RequestParam Integer id) {
        productSpuService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation(value = "修改")
    @PostMapping("/service/goods/modify")
    public ResponseResult<String> modify(@Valid @RequestBody ProductSpu productSpu) {
        productSpuService.updateById(productSpu);
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询活动详情")
    @GetMapping("/service/goods/detail")
    public ResponseResult<ProductSpu> detail(@RequestParam Integer id) {
        return ResponseResult.success(productSpuService.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/service/goods/queryByPage")
    public ResponseResult<IPage<ProductSpu>> queryByPage(@RequestBody JSONObject queryObject) {

        DslParser<ProductSpu> dslParser = new DslParser<>(queryObject);
        QueryWrapper<ProductSpu> queryWrapper = dslParser.parseToWrapper(ProductSpu.class);

        IPage<ProductSpu> page = dslParser.generatePage();
        productSpuService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private String indexName = "mall_product_spu";

    @ApiOperation("全量同步商品索引")
    @PostMapping("/service/goods/fullIndex")
    public ResponseResult<String> fullIndex() {
        List<ProductSpu> productSpuList = this.productSpuService.list();

        BulkRequest bulkRequest = new BulkRequest();

        for (ProductSpu productSpu : productSpuList) {
            IndexRequest indexRequest = new IndexRequest(indexName, "doc")
                    .id(productSpu.getId().toString())
                    .opType(DocWriteRequest.OpType.CREATE)
                    .source(JSONObject.toJSONString(productSpu), XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("返回状态{}", bulkResponse.status());
            if (bulkResponse.status() == RestStatus.OK) {
                return ResponseResult.success();
            }
        } catch (IOException e) {
            log.error("批量操作失败", e);
        }
        return ResponseResult.success();
    }


    @ApiOperation("修改商品文档数据")
    @PostMapping("/service/goods/esUpdate")
    public ResponseResult<String> esUpdate(@RequestParam String spuId) {

        ProductSpu productSpu = this.productSpuService.getById(spuId);
        productSpu.setName(productSpu.getName() + "_update");

        UpdateRequest updateRequest = new UpdateRequest(indexName, "doc", spuId);
        updateRequest.doc(JSONObject.toJSONString(productSpu), XContentType.JSON);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("返回状态{}", updateResponse.status());
            if (updateResponse.status() == RestStatus.OK) {
                return ResponseResult.success();
            }
        } catch (IOException e) {
            log.error("批量操作失败", e);
        }
        return ResponseResult.success();
    }

    @ApiOperation("删除商品文档数据")
    @PostMapping("/service/goods/esDelete")
    public ResponseResult<String> esDelete(@RequestParam String spuId) {

        DeleteRequest deleteRequest = new DeleteRequest(indexName, "doc", spuId);

        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("返回状态{}", deleteResponse.status());
            if (deleteResponse.status() == RestStatus.OK) {
                return ResponseResult.success();
            }
        } catch (IOException e) {
            log.error("批量操作失败", e);
        }
        return ResponseResult.success();
    }

    @ApiOperation("分页检索商品")
    @PostMapping("/service/goods/search")
    public ResponseResult<String> search(@RequestParam String content, @RequestParam int pageNo, @RequestParam int pageSize) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from((pageNo - 1) * pageSize);
        sourceBuilder.size(pageSize);
        sourceBuilder.sort("createdDate", SortOrder.DESC);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("name", content))
                .must(QueryBuilders.termsQuery("companyId", new int[]{1302, 1600}));
        sourceBuilder.query(boolQuery);

        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(sourceBuilder);

        log.info("searchRequest content = {}", sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("返回状态{}", searchResponse.status());
            if (searchResponse.status() == RestStatus.OK) {
                SearchHits searchHits = searchResponse.getHits();
                log.info("hits={}", searchHits.totalHits);
                return ResponseResult.success();
            }
        } catch (IOException e) {
            log.error("操作失败", e);
        }
        return ResponseResult.success();
    }

}

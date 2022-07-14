package com.mcoding.base.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class EsClientConfig {

    /**
     * 建立连接超时时间
     */
    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    /**
     * 数据传输过程中的超时时间
     */
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    /**
     * 从连接池获取连接的超时时间
     */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;

    /**
     * 路由节点的最大连接数
     */
    public static int MAX_CONN_PER_ROUTE = 10;
    /**
     * client最大连接数量
     */
    public static int MAX_CONN_TOTAL = 30;

    /**
     * es集群节点
     */
    @Value("${elasticsearch.clusterNodes}")
    private String clusterNodes;

    /**
     * es rest client的bean对象
     */
    private RestHighLevelClient restHighLevelClient;

    /**
     * 加载es集群节点，逗号分隔
     *
     * @return 集群
     */
    private HttpHost[] loadHttpHosts() {
        String[] clusterNodesArray = clusterNodes.split(",");
        HttpHost[] httpHosts = new HttpHost[clusterNodesArray.length];
        for (int i = 0; i < clusterNodesArray.length; i++) {
            String clusterNode = clusterNodesArray[i];
            String[] hostAndPort = clusterNode.split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
        }
        return httpHosts;
    }

    /**
     * es client bean
     *
     * @return restHighLevelClient es高级客户端
     */
    @Bean
    public RestHighLevelClient restClient() {
        // 创建restClient的构造器
        RestClientBuilder restClientBuilder = RestClient.builder(loadHttpHosts());
        // 设置连接超时时间等参数
        setConnectTimeOutConfig(restClientBuilder);
        setConnectConfig(restClientBuilder);
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }

    /**
     * 配置连接超时时间等参数
     *
     * @param restClientBuilder 创建restClient的构造器
     */
    private void setConnectTimeOutConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * 使用异步httpclient时设置并发连接数
     *
     * @param restClientBuilder 创建restClient的构造器
     */
    private void setConnectConfig(RestClientBuilder restClientBuilder) {
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    @PreDestroy
    public void close() {
        if (restHighLevelClient != null) {
            try {
                log.info("Closing the ES REST client");
                restHighLevelClient.close();
            } catch (IOException e) {
                log.error("Problem occurred when closing the ES REST client", e);
            }
        }
    }

}
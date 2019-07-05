package com.fastdev.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DaoComponetConfig {

    @Value("${elasticsearch.cluster.addresses}")
    private String elasticsearchClusterAddresses;

    @Bean
    public RestHighLevelClient getRestHighLevelClient(){
        if(StringUtils.isEmpty(elasticsearchClusterAddresses)){
            throw new RuntimeException("ElasticSearch_cluster_hosts is empty");
        }

        String[] addressArray = elasticsearchClusterAddresses.split(";");

        List<HttpHost> httpHostList = Arrays.stream(addressArray).map(address -> {
            String[] ipAndPort = address.split(":");
            String ip = ipAndPort[0];
            int port = Integer.valueOf(ipAndPort[1]);
            return new HttpHost(ipAndPort[0], port, "http");
        }).collect(Collectors.toList());

        HttpHost[] httpHostArray = new HttpHost[httpHostList.size()];
        httpHostList.toArray(httpHostArray);

        RestClientBuilder restClient = RestClient.builder(httpHostArray);

        return new RestHighLevelClient(restClient);
    }

}

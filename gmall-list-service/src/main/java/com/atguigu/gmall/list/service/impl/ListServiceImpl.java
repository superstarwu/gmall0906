package com.atguigu.gmall.list.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.bean.SkuLsParam;
import com.atguigu.gmall.service.ListService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ListServiceImpl implements ListService{

//    @Autowired
//    private SkuService skuService;

    @Autowired
    private JestClient jestClient;

    @Override
    public List<SkuLsInfo> skuLsInfoList(SkuLsParam skuLsParam) {
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();
        String dsl = getMyDsl(skuLsParam);
        //查询
        Search build = new Search.Builder(dsl).addIndex("gmall0906").addType("SkuLsInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long total = execute.getTotal();
        if(total>0){
            List<SearchResult.Hit<SkuLsInfo,Void>> hits = execute.getHits(SkuLsInfo.class);
            for(SearchResult.Hit<SkuLsInfo,Void> hit : hits){
                SkuLsInfo source = hit.source;
                Map<String, List<String>> highlight = hit.highlight;
                List<String> skuName = highlight.get("skuName");

                String s = skuName.get(0);
                source.setSkuName(s);
                skuLsInfos.add(source);
            }
        }
        //聚合值
        List<String> attrValueIdList = new ArrayList<>();
        MetricAggregation aggregations = execute.getAggregations();
        TermsAggregation valueIdAggs = aggregations.getTermsAggregation("valueIdAggs");
        if(valueIdAggs != null){
            List<TermsAggregation.Entry> buckets = valueIdAggs.getBuckets();
            for(TermsAggregation.Entry bucket : buckets){
                attrValueIdList.add(bucket.getKey());
            }
        }
        return skuLsInfos;
    }

    public String getMyDsl(SkuLsParam skuLsParam){
        //查询语句封装
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //联合查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //三级分类Id
        String catalog3Id = skuLsParam.getCatalog3Id();
        if(StringUtils.isNotBlank(catalog3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //分类属性值Id
        String[] valueIds = skuLsParam.getValueId();
        if(valueIds != null && valueIds.length>0){
            for(String valueId : valueIds){
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",valueId);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        //关键字
        String keyWord = skuLsParam.getKeyword();
        if(StringUtils.isNotBlank(keyWord)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName",keyWord);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        //FilteredQueryBuilder filteredQueryBuilder = new FilteredQueryBuilder();

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;font-weight:bolder;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);

        //查询数量
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        //属性值Id的聚合
        TermsBuilder valueIdAggs = AggregationBuilders.terms("valueIdAggs").field("skuAttrValueList.valueId");
        searchSourceBuilder.aggregation(valueIdAggs);
        return searchSourceBuilder.toString();
    }
}

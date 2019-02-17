package com.atguigu.gmall.list.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallListServiceApplicationTests {

	@Autowired
	private JestClient jestClient;

	@Reference
	private SkuService skuService;

	@Test
	public void contextLoads() {
		Search search = new Search.Builder("{\n" +
				"  \"query\": {\n" +
				"    \"bool\": {\n" +
				"      \"filter\": [\n" +
				"     {\n" +
				"       \"terms\":{\n" +
				"         \"actorList.id\": [\"1\",\"2\"]\n" +
				"       }\n" +
				"     },{\n" +
				"       \"term\":{\n" +
				"         \"actorList.id\": \"3\"\n" +
				"       }\n" +
				"     }\n" +
				"        ]\n" +
				"    }\n" +
				"  }\n" +
				"}\n"
				).addIndex("movie_index").addType("movie").build();
		try {
			SearchResult searchResult = jestClient.execute(search);
			List<SearchResult.Hit<Movie,Void>> hitList = searchResult.getHits(Movie.class);
			for(SearchResult.Hit<Movie,Void> hit: hitList){
				Movie movie =hit.source;
				List<Actor> actors = movie.getActorList();
				for(Actor actor : actors){
					System.err.println(movie.getId() + "==>" + movie.getName() + "==>" + movie.getDoubanScore() + "==>" + actor.getId() + "==>" + actor.getName());
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void contextLoad2(){

		String catalog3Id = "1";
		//获取skuInfo的数据
		List<SkuInfo> skuInfoList = skuService.getSkuInfoListByCatalog3Id(catalog3Id);
		//将skuInfo的数据转换成skuLsInfo
		for(SkuInfo skuInfo : skuInfoList){
			SkuLsInfo skuLsInfo = new SkuLsInfo();
			BeanUtils.copyProperties(skuInfo,skuLsInfo);

			//将skuLsInfo的数据封装进elasticsearch中
			Index index = new Index.Builder(skuLsInfo).index("gmall0906").type("SkuLsInfo").id(skuLsInfo.getId()).build();
			try {
				jestClient.execute(index);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getMyDsl(){
		//查询语句封装
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//联合查询
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

//		TermQueryBuilder termQueryBuilder = new TermQueryBuilder(null,null);
//		boolQueryBuilder.filter(termQueryBuilder);
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","hjk");

		boolQueryBuilder.must(matchQueryBuilder);
		searchSourceBuilder.query(boolQueryBuilder);
		//FilteredQueryBuilder filteredQueryBuilder = new FilteredQueryBuilder();

		//高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("skuName");
		searchSourceBuilder.highlight(highlightBuilder);
		return searchSourceBuilder.toString();
	}

	@Test
	public void contextLoad() throws IOException {

		//查询
		String dsl = getMyDsl();
		System.out.println(dsl);

		Search build = new Search.Builder(dsl).addIndex("gmall0906").addType("SkuLsInfo").build();
		SearchResult execute = jestClient.execute(build);
		List<SearchResult.Hit<SkuLsInfo,Void>> hits = execute.getHits(SkuLsInfo.class);
		List<SkuLsInfo> skuLsInfos = new ArrayList<>();
		for(SearchResult.Hit<SkuLsInfo,Void> hit : hits){
			SkuLsInfo skuLsInfo = hit.source;
			skuLsInfos.add(skuLsInfo);
		}
		System.err.println(skuLsInfos.size());
	}

}


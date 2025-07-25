package com.zs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.model.Product;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es")
public class EsController {

    public static final String KEY = "name";
    @Autowired
    private RestHighLevelClient client;

    private final String INDEX_NAME = "product_index";

    /** 创建索引 */
    @GetMapping("/create-index")
    public String createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return "索引创建：" + response.isAcknowledged();
    }

    /** 添加文档 */
    @PostMapping("/add")
    public String add(@RequestBody Product product) throws IOException {
        IndexRequest request = new IndexRequest(INDEX_NAME);
        request.id(product.getId());
        ObjectMapper mapper = new ObjectMapper();
        request.source(mapper.writeValueAsString(product), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return "添加成功：" + response.getResult();
    }

    /** 查询文档 */
    @GetMapping("/get")
    public Map<String, Object> get(@RequestParam String id) throws IOException {
        GetRequest request = new GetRequest(INDEX_NAME, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /** 删除文档 */
    @DeleteMapping("/delete")
    public String delete(@RequestParam String id) throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX_NAME, id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return "删除成功：" + response.getResult();
    }

    /** 关键词查询 */
    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String keyword) throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("name", keyword));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            resultList.add(hit.getSourceAsMap());
        }
        return resultList;
    }

    /** 高亮查询 */
    @GetMapping("/highlight")
    public List<Map<String, Object>> highlightSearch(@RequestParam String q) throws IOException {
        // 构建查询条件
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME); // 索引名
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 匹配查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(KEY, q);
        sourceBuilder.query(matchQuery);

        // 设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(KEY);
        highlightBuilder.preTags("<em style='color:red'>");  // 高亮前缀
        highlightBuilder.postTags("</em>");                  // 高亮后缀
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);

        // 执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceMap = hit.getSourceAsMap();

            // 提取高亮字段替换原字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get(KEY);
            if (highlight != null) {
                Text[] fragments = highlight.fragments();
                StringBuilder highlightedText = new StringBuilder();
                for (Text fragment : fragments) {
                    highlightedText.append(fragment.string());
                }
                sourceMap.put(KEY, highlightedText.toString());
            }

            resultList.add(sourceMap);
        }

        return resultList;
    }
}

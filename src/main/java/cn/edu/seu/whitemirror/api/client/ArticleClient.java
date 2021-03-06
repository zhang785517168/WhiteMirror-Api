package cn.edu.seu.whitemirror.api.client;

import cn.edu.seu.whitemirror.api.dto.ArticleBriefDTO;
import cn.edu.seu.whitemirror.api.dto.ArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snow
 * Date: 13-10-18
 * Time: 下午12:48
 * To change this template use File | Settings | File Templates.
 */

public class ArticleClient {

    private Logger logger = LoggerFactory.getLogger(ArticleClient.class);

    private RestTemplate restTemplate;

    private String restUrl;
    private String apiKey;

    private static final String PAGINATE_ARTICLE_LIST_URL = "sections/%s/articles?pageIndex=%s&pageSize=%s&orderByCreateDate=%s";
    private static final String ARTICLE_URL = "sections/%s/articles/%s";
    private static final String COUNT_ARTICLE_URL = "sections/%s/articles/count";

    public List<ArticleBriefDTO> paginateArticleBriefBySectionId(Long sectionId, Integer pageIndex, Integer pageSize, Boolean orderByCreateDate) {
        String requestUrl = restUrl + String.format(PAGINATE_ARTICLE_LIST_URL, sectionId, pageIndex, pageSize, orderByCreateDate);
        HttpEntity<?> requestEntity = ClientHelper.getRequestEntity(apiKey);
        try {
            ResponseEntity<List<ArticleBriefDTO>> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ArticleBriefDTO>>() {
            });
            return responseEntity.getBody();
        } catch (Exception ex) {
            logger.error("Exception in ArticleClient.paginateArticleBriefBySectionId, ex: ", ex);
            return null;
        }
    }

    public ArticleDTO findArticleBySectionIdAndArticleId(Long sectionId, Long articleId) {
        String requestUrl = restUrl + String.format(ARTICLE_URL, sectionId, articleId);
        HttpEntity<?> requestEntity = ClientHelper.getRequestEntity(apiKey);
        try {
            ResponseEntity<ArticleDTO> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, ArticleDTO.class);
            return responseEntity.getBody();
        } catch (Exception ex) {
            logger.error("Exception in ArticleClient.findArticleBySectionIdAndArticleId, ex: " , ex);
            return null;
        }
    }

    public Integer countArticleBySectionId(Long sectionId) {
        String requestUrl = restUrl + String.format(COUNT_ARTICLE_URL, sectionId);
        HttpEntity<?> requestEntity = ClientHelper.getRequestEntity(apiKey);
        try {
            ResponseEntity<Integer> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, Integer.class);
            return responseEntity.getBody();
        } catch (Exception ex) {
            logger.error("Exception in ArticleClient.countArticleBySectionId, ex: ", ex);
            return null;
        }
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

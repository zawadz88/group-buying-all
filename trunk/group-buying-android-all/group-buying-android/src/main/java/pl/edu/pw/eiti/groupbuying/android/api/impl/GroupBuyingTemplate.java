package pl.edu.pw.eiti.groupbuying.android.api.impl;

import static pl.edu.pw.eiti.groupbuying.android.util.Constants.CONNECTION_TIMEOUT;
import static pl.edu.pw.eiti.groupbuying.android.util.Constants.READ_TIMEOUT;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.api.OfferOperations;

public class GroupBuyingTemplate extends AbstractOAuth2ApiBinding implements GroupBuyingApi {
	
	private final String apiUrlBase;
		
	private OfferOperations offerOperations;

	public GroupBuyingTemplate(String accessToken, String apiUrlBase) {
		super(accessToken);
		this.apiUrlBase = apiUrlBase;
		registerGroupBuyingJsonModule();
		getRestTemplate().setErrorHandler(new GroupBuyingErrorHandler());
		initSubApis();
	}
	
	public GroupBuyingTemplate(String apiUrlBase) {
		super();
		this.apiUrlBase = apiUrlBase;
		registerGroupBuyingJsonModule();
		getRestTemplate().setErrorHandler(new GroupBuyingErrorHandler());
		initSubApis();
	}
	
	public OfferOperations offerOperations() {
		return offerOperations;
	}
	
	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		if (restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(CONNECTION_TIMEOUT);
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(READ_TIMEOUT);
        } else if (restTemplate.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(CONNECTION_TIMEOUT);
            ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(READ_TIMEOUT);
        }
	}
	// private helper 

	private void registerGroupBuyingJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new GroupBuyingModule());
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}
	
	private String getApiUrlBase() {
		return apiUrlBase;
	}
	
	private void initSubApis() {
			this.offerOperations = new OfferTemplate(getRestTemplate(), true, getApiUrlBase());//public api
	}
	
}
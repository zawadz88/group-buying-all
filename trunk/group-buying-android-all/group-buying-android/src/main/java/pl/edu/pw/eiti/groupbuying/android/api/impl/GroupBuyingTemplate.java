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

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.api.OfferOperations;
import android.os.Build;

public class GroupBuyingTemplate extends AbstractOAuth2ApiBinding implements GroupBuyingApi {
	
	private final String apiUrlBase;
		
	private OfferOperations offerOperations;

	public GroupBuyingTemplate(String accessToken, String apiUrlBase) {
		super(accessToken);
		initRequestFactory();		
		this.apiUrlBase = apiUrlBase;
		registerGroupBuyingJsonModule();
		getRestTemplate().setErrorHandler(new GroupBuyingErrorHandler());
		initSubApis();
	}
	
	public GroupBuyingTemplate(String apiUrlBase) {
		super();
		initRequestFactory();
		this.apiUrlBase = apiUrlBase;
		registerGroupBuyingJsonModule();
		getRestTemplate().setErrorHandler(new GroupBuyingErrorHandler());
		initSubApis();
	}

	//FIXME chamski hack, ale dopóki w spring social nic lepszego nie dadza to dupa...
	private void initRequestFactory() {
		if (Build.VERSION.SDK_INT >= 9) {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			requestFactory.setConnectTimeout(CONNECTION_TIMEOUT);
			requestFactory.setReadTimeout(READ_TIMEOUT);
			setRequestFactory(requestFactory);
		} else {
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(CONNECTION_TIMEOUT);
			requestFactory.setReadTimeout(READ_TIMEOUT);			
			setRequestFactory(requestFactory);
		}
	}
	
	public OfferOperations offerOperations() {
		return offerOperations;
	}

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
			this.offerOperations = new OfferTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());//public api
	}
	
}
package pl.edu.pw.eiti.groupbuying.android.task;

import static pl.edu.pw.eiti.groupbuying.android.util.Constants.CONNECTION_TIMEOUT;
import static pl.edu.pw.eiti.groupbuying.android.util.Constants.READ_TIMEOUT;
import static pl.edu.pw.eiti.groupbuying.android.util.Constants.TAG;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.util.HttpsClient;
import android.util.Log;

public class SignInTask extends AbstractGroupBuyingTask<Void> {
	
	private MultiValueMap<String, String> formData;
	private String url;
	private Map<String, Object> responseBody;
	
	public SignInTask(String url, MultiValueMap<String, String> formData, AsyncTaskListener listener) {
		super();
		this.url = url;
		this.formData = formData;
		this.taskListener = listener;
	}

	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		SignInTask task = new SignInTask(url, formData, taskListener);
		return task;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doInBackgroundSafe() throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestHeaders.add("Connection", "close");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(formData, requestHeaders);
/*		RestTemplate restTemplate = new RestTemplate(true);
		if (restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(CONNECTION_TIMEOUT);
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(READ_TIMEOUT);
        } else if (restTemplate.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(CONNECTION_TIMEOUT);
            ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(READ_TIMEOUT);
        }*/
		//FIXME replace with the commented code from above, this causes security risks such as MITM, for development only!!!
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpsClient.getNewHttpClient());
		requestFactory.setConnectTimeout(CONNECTION_TIMEOUT);
		requestFactory.setReadTimeout(READ_TIMEOUT);
		RestTemplate restTemplate = new RestTemplate(true, requestFactory);
		
		responseBody = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class).getBody();
		Log.d(TAG, responseBody.toString());		
	}
	
	public Map<String, Object> getResponseBody() {
		return responseBody;
	}

}

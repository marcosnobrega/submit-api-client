package br.com.submitcms.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static HttpResponsePair get(String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		client.setParams(httpParams);

		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
			return new HttpResponsePair(
					response.getStatusLine().getStatusCode(), 
					EntityUtils.toString(response.getEntity(), "iso-8859-1"));
		} catch (Throwable t1) {
			// Do nothing
 		} finally {
			try {
				if (response != null) {
					response.getEntity().getContent().close();
				}
			} catch (Throwable t2) {
				// Do nothing
			}
		}
		
		return new HttpResponsePair(HttpStatus.SC_INTERNAL_SERVER_ERROR, null);
	}
	
	public static class HttpResponsePair {
		
		int statusCode;
		String response;
		
		public HttpResponsePair(int statusCode, String response) {
			this.statusCode = statusCode;
			this.response = response;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public String getResponse() {
			return response;
		}

	}

}

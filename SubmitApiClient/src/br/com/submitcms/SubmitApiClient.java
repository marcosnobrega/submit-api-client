package br.com.submitcms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.submitcms.model.SubmitApiResponseObject;
import br.com.submitcms.utils.HttpUtils;
import br.com.submitcms.utils.HttpUtils.HttpResponsePair;

public class SubmitApiClient {
	
	private final String API_ENDPOINT = "http://www.submit.10envolve.com.br/api/";
	
	public static final String ACTION_LIST = "list";
	
	public static final String ORDER_DESCENDING = "DESC";
	public static final String ORDER_ASCENDING = "ASC";
	
	private String token;
	private String entity;
	private String action;
	private String orderBy;
	private int quantity;
	private int page;
	private String groupBy;
	private Map<String, String> queryFields;
	
	public SubmitApiClient(String token) {
		this.token = token;
		this.queryFields = new HashMap<String, String>();
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEntity() {
		return entity;
	}
	public SubmitApiClient setEntity(String entity) {
		this.entity = entity;
		return this;
	}
	public String getAction() {
		return action;
	}
	public SubmitApiClient setAction(String action) {
		this.action = action;
		return this;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public SubmitApiClient setOrderBy(String field, String order) {
		this.orderBy = field + "_" + order;
		return this;
	}
	public int getQuantity() {
		return quantity;
	}
	public SubmitApiClient setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}
	public int getPage() {
		return page;
	}
	public SubmitApiClient setPage(int page) {
		this.page = page;
		return this;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public SubmitApiClient setGroupBy(String groupBy) {
		this.groupBy = groupBy;
		return this;
	}
	public Map<String, String> getQueryFields() {
		return queryFields;
	}
	public SubmitApiClient setQueryFields(Map<String, String> queryFields) {
		this.queryFields = queryFields;
		return this;
	}
	
	public SubmitApiClient addQueryField(String fieldName, String value) {
		return addQueryField(fieldName, value, null);
	}
	
	public SubmitApiClient addQueryField(String fieldName, String value, String operator) {
		queryFields.put(fieldName, value);
		if (operator != null) {
			queryFields.put("op_" + fieldName, operator);
		}
		return this;
	}
	
	public SubmitApiResponseObject get() {
		queryFields.put("usarpaginacao", "1");
		
		if(page != 0){
			queryFields.put("pagina", String.valueOf(page));
			queryFields.put("usarpaginacao", "1");
		}
		
		if(quantity != 0){
			queryFields.put("quantidade", String.valueOf(quantity));
		}
		
		if(!orderBy.isEmpty()){
			queryFields.put("orderBy", orderBy);
		}
		
		if(!groupBy.isEmpty()){
			queryFields.put("groupBy", groupBy);
		}
		
		StringBuilder urlBuilder = new StringBuilder(API_ENDPOINT)
			.append(token).append("/")
			.append(entity).append("/")
			.append(action);
		
		if (!queryFields.isEmpty()) {
			String httpQuery = httpBuildQuery(queryFields);
			if (httpQuery != null) {
				urlBuilder.append(httpQuery);
			}
		}
		
		String queryUrl = urlBuilder.toString();
		
		HttpResponsePair httpResponsePair = HttpUtils.get(queryUrl);
		if (httpResponsePair.getStatusCode() != 200) {
			return null;
		} else {
			return new SubmitApiResponseObject(httpResponsePair.getResponse());
		}
	}
	
	public SubmitApiClient prepare(String entity) {
		this.clear();
		this.action = ACTION_LIST;
		this.entity = entity;
		return this;
	}
	
	private void clear() {
		this.entity = "";
		this.action = "";
		this.page = 0;
		this.quantity = 0;
		this.groupBy = "";
		this.orderBy = "";
		this.queryFields.clear();
	}
	
	private String httpBuildQuery(Map<String, String> data) {
		if (data.size() == 0) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		for (Entry<String, String> pair : data.entrySet()) {
			if (counter++ > 0) {
				sb.append("&");
			} else {
				sb.append("/");
			}
			sb.append(pair.getKey() + "=" + pair.getValue());
		}
		
	    return sb.toString();
	}
}

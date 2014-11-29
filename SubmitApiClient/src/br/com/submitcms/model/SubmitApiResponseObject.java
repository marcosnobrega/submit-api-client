package br.com.submitcms.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubmitApiResponseObject {
	
	private int total;
	private int quantity;
	private int page;
	private JSONArray data;
	
	public SubmitApiResponseObject(String response) {
		parseResponse(response);
	}

	private void parseResponse(String response) {
		try {
			JSONObject responseObject = new JSONObject(response);
			this.total = responseObject.optInt("total");
			this.data = responseObject.optJSONArray("resultados");
			this.page = responseObject.optInt("pagina");
			this.quantity = this.data.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}
	
}

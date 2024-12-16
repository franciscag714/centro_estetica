package utils;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import entities.Appointment;

public class QrGenerator {
	String url;
	String authorization;
	
	public QrGenerator() {
		String userId = System.getenv("MPAGO_USER_ID");
		String storeId = System.getenv("MPAGO_EXTERNAL_STORE_ID");
		String posId = System.getenv("MPAGO_EXTERNAL_POS_ID");
		this.authorization = System.getenv("MPAGO_AUTHORIZATION");

		if (userId == null || storeId == null || posId == null || this.authorization == null)
			throw new IllegalArgumentException("Missing Mercado Pago configuration in environment.");
		
		url = "https://api.mercadopago.com/instore/qr/seller/collectors/" + userId + "/stores/" + storeId + "/pos/"
				+ posId + "/orders";
	}

	public Boolean generate(Appointment a) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	        HttpPut httpPut = new HttpPut(url);
	        httpPut.setHeader("Content-Type", "application/json");
	        httpPut.setHeader("Authorization", authorization);

	        String jsonBody = ""
	        		+ "{"
	        		+ "  \"external_reference\": \"appointmentId:"+ a.getId() + "\","
	        		+ "  \"title\": \"Centro de Estética\","
	        		+ "  \"description\": \"Cobro por servicios brindados\","
	        		+ "  \"items\": ["
	        		+ "    {"
	        		+ "      \"title\": \"Centro de Estética\","
	        		+ "      \"unit_price\": " + a.getDoubleTotalIncome() + ","
	        		+ "      \"quantity\": 1,"
	        		+ "      \"unit_measure\": \"unit\","
	        		+ "      \"total_amount\": " + a.getDoubleTotalIncome()
	        		+ "    }"
	        		+ "  ],"
	        		+ "  \"total_amount\": " + a.getDoubleTotalIncome()
	        		+ "}";

	        httpPut.setEntity(new StringEntity(jsonBody));
	        org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPut);

	        if (httpResponse.getStatusLine().getStatusCode() == 204)
	        	return true;

	        return false;
	        
	    } catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

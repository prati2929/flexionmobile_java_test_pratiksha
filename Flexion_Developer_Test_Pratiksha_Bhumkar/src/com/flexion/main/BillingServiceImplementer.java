package com.flexion.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.flexion.utils.Constants;
import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.Purchase;

/*
 * @author: Pratiksha Bhumkar
 * Date: 2nd Nov 2019
 * Place:Pune, India
 */

public class BillingServiceImplementer implements Integration {

	// one instance, reuse
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	static {

		BillingServiceImplementer obj = new BillingServiceImplementer();

		try {
			System.out.println("Testing 1 - Send Http GET request");
			try {
				//	obj.sendGet();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Testing 2 - Send Http POST request");
			try {
				//	obj.sendPost();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				obj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}



	@Override
	public Purchase buy(String itemId) {

		PurchaseDetails detaiils = new PurchaseDetails() ;
		System.out.println("Inside buy method for purchasing new itemId "+ itemId);

		HttpPost post = new HttpPost(Constants.BILLING_API.concat(Constants.DEVELOPER_ID).concat(Constants.CONCAT_OPERATOR).concat(Constants.METHOD_BUY).concat(Constants.CONCAT_OPERATOR.concat(itemId)));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			if (response != null) {
				// return it as a String
				String result = EntityUtils.toString(response.getEntity());
				System.out.println("result received from getPurchases "+result);

				try {
					JSONObject json = new JSONObject(result);

					System.out.println("loop "+json.getString("id") );

					detaiils =  new PurchaseDetails(json.getString("id"), 
							json.getString("itemId"), json.getBoolean("consumed") );

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return detaiils;
	}




	@Override
	public void consume(Purchase paramPurchase) {

		String idOfItemToBeConsumed = paramPurchase.getId();

		System.out.println("Inside consume method for consuming item of id "+ idOfItemToBeConsumed);

		HttpPost post = new HttpPost(Constants.BILLING_API.concat(Constants.DEVELOPER_ID).concat(Constants.CONCAT_OPERATOR).concat(Constants.METHOD_CONSUME).concat(Constants.CONCAT_OPERATOR.concat(idOfItemToBeConsumed)));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			if (response != null) {
				// return it as a String
				String result = EntityUtils.toString(response.getEntity());
				System.out.println("result received from consume "+result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 


	}




	@Override
	public List<Purchase> getPurchases() {
		// Method to get list of all purchases for a developer ID

		List<Purchase> purchases = new ArrayList<Purchase>();

		HttpGet request = new HttpGet(Constants.BILLING_API.concat(Constants.DEVELOPER_ID).concat(Constants.CONCAT_OPERATOR).concat(Constants.METHOD_ALL));


		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println("Headers from response for getPurchases "+ headers);

			if (entity != null) {
				// return it as a String
				String result = EntityUtils.toString(entity);
				System.out.println("result received from getPurchases "+result);

				System.out.println("purchaseDetails "+ purchases.size());
				
				//can be done using deserialization, as response is small, I chose simple conversion 
				try {
					JSONObject json = new JSONObject(result);

					JSONArray obj = (JSONArray) json.get("purchases") ;

					for (int i=0 ; i<obj.length() ; i++) {

						System.out.println("loop "+obj.getJSONObject(i).getString("id") );

						PurchaseDetails detaiils =  new PurchaseDetails(obj.getJSONObject(i).getString("id"), 
								obj.getJSONObject(i).getString("itemId"), obj.getJSONObject(i).getBoolean("consumed") );
						purchases.add(detaiils);

					}


				} catch (JSONException e) {
					e.printStackTrace();
				}



			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if(null!=httpClient) {
				close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return purchases;
	}



	private void close() throws IOException {
		httpClient.close();
	}






}

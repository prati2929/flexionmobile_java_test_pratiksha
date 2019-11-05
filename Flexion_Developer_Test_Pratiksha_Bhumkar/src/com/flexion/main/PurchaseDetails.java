package com.flexion.main;

import com.flexionmobile.codingchallenge.integration.Purchase;



/*
 * @author: Pratiksha Bhumkar
 * Date: 2nd Nov 2019
 * Place:Pune, India
 */


public class PurchaseDetails implements Purchase {

	private  String id="" ;
	private String itemId="" ;
	private boolean consumed=false ;


	PurchaseDetails(){}

	PurchaseDetails(String id, String itemId,boolean consumed ){
		this.id=id;
		this.itemId=itemId;
		this.consumed=consumed;	
	}




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public  String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public boolean getConsumed() {
		return consumed;
	}
	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}



	@Override
	public String toString(){
		return "id: " + id + ", itemId: " + itemId + ", is Consumed: " + consumed  ;
	}



}

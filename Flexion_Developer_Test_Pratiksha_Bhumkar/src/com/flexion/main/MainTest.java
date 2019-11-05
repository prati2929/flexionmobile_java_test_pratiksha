package com.flexion.main;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;

/*
 * @author: Pratiksha Bhumkar
 * Date: 3rd Nov 2019
 * Place:Pune, India
 */

public class MainTest {


	public static void main(String args[]) {

		System.out.println("insidemain method");

		BillingServiceImplementer billingServiceImplementer = new BillingServiceImplementer() ;

		//calling IntegrationTestRunner
		IntegrationTestRunner testRunner = new IntegrationTestRunner();

		testRunner.runTests(billingServiceImplementer);
		System.out.println("All tests are Okay..!");


	}



}

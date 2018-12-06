package com.example.herokupipeexample;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test");
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    protected int port = 0;
    private Strïng path = "/";

    @Autowired
    private CustomerController customerController;

    @Before
    private void initalize(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = path;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testCreateAndGetCustomer(){
        //dumb test. only works safely if this is the only test
        Customer customer = new Customer("firstname", "lastname");
        customerController.newCustomer(customer);

        List<Customer> customers = customerController.findByLastName("lastname");

        assertEquals(1, customers.size());
    }
}

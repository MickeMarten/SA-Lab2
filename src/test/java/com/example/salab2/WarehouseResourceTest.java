package com.example.salab2;

import entities.Product;
import org.junit.jupiter.api.Test;


import service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseResourceTest {
    Dispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        WarehouseService service = new WarehouseService();
        WarehouseResource resource = new WarehouseResource(service);
        service.addProductForTest();

        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(resource);
    }

    @Test
    public void testForGettingALlProductsShouldReturn200() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/warehouse/allproducts");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getProductByIdWithNoValidIDShouldReturn404() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/warehouse/product/66");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void getProductByIdShouldReturn200() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/warehouse/product/1");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getProductByCategoryShouldReturn200() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/warehouse/products/categories/FRUITS");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());


    }


}
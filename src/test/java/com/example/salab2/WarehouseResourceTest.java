package com.example.salab2;

import org.junit.jupiter.api.Test;


import service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
class WarehouseResourceTest {
    Dispatcher dispatcher;

    @BeforeEach
    public void setUp(){
        WarehouseService service = new WarehouseService();
        WarehouseResource resource = new WarehouseResource(service);
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(resource);
    }

    @Test
    public void Test200() throws Exception{
        MockHttpRequest request = MockHttpRequest.get("/warehouse/allproducts");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
    }
}
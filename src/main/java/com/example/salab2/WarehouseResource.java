package com.example.salab2;

import entities.Category;
import entities.Product;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.WarehouseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/warehouse")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarehouseResource {
    private WarehouseService warehouseService;

    public WarehouseResource() {
    }

    @PostConstruct
    public void init() {
        warehouseService.addProductForTest();
    }

    @Inject
    public WarehouseResource(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    @GET
    @Path("/allproducts")
    public Response getAllProducts() {
        List<Product> allProducts = warehouseService.getAllProducts();
        if (allProducts.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No products in the warehouse")
                    .build();
        }
        return Response.ok(allProducts).build();
    }

    @GET
    @Path("/product/{id}")
    public Response getProductById(@PathParam("id") @Valid int id) {
        Optional product = warehouseService.getProductById(id);
        if (product.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found")
                    .build();
        }
        return Response.ok(product.get()).build();
    }

    @GET
    @Path("/products/{category}")
    public Response getProductByCategory(@PathParam("category") @Valid String category) {
        Category categoryEnum = Category.valueOf(category.toUpperCase());

        List<Product> filteredProducts = warehouseService.getAllProducts().stream().filter(product -> product.category().equals(categoryEnum)).collect(Collectors.toUnmodifiableList());
        if (filteredProducts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No products in the warehouse with category " + category)
                    .build();
        }

        return Response.ok(filteredProducts).build();


    }

    @POST
    @Path("/addproduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid Product product) {
        try {
            warehouseService.addNewProduct(product);
            return Response.status(Response.Status.CREATED).entity(product).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
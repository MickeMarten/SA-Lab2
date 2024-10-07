package com.example.salab2;

import entities.Category;
import entities.Product;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validate.ValidId;
import validate.ValidCategory;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("/warehouse")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarehouseResource {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

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
        logger.info("Get all products");
        List<Product> allProducts = warehouseService.getAllProducts();
        if (allProducts.isEmpty()) {
            logger.error("No products in the warehouse");
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No products in the warehouse")
                    .build();
        }
        logger.info("All products returned");
        return Response.ok(allProducts).build();
    }

    @GET
    @Path("/product/{id}")
    public Response getProductById(@PathParam("id") @ValidId int id) {
        logger.info("Get product with id {}", id);
        Optional<Product> product = warehouseService.getProductById(id);
        if (product.isEmpty()) {
            logger.error("Product with id {} not found", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found")
                    .build();
        }
        logger.info("Product with id {} returned", id);
        return Response.ok(product.get()).build();
    }

    @GET
    @Path("/products/{category}")
    public Response getProductByCategory(@PathParam("category") @ValidCategory String category) {
        logger.info("Get products with category {}", category);
        Category categoryEnum = Category.valueOf(category.toUpperCase());

        List<Product> filteredProducts = warehouseService.getAllProducts()
                .stream()
                .filter(product -> product.category().equals(categoryEnum))
                .collect(Collectors.toUnmodifiableList());

        if (filteredProducts.isEmpty()) {
            logger.error("No products in the warehouse with category {}", category);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No products in the warehouse with category " + category)
                    .build();
        }
        logger.info("Products with category {} returned", category);
        return Response.ok(filteredProducts).build();

    }

    @POST
    @Path("/addproduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid Product product) {
        logger.info("Adding product: {}", product);
        warehouseService.addNewProduct(product);
        logger.info("Product added: {}", product);
        return Response.status(Response.Status.CREATED).entity(product).build();

    }
}
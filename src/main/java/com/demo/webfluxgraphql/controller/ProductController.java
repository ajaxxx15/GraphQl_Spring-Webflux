package com.demo.webfluxgraphql.controller;

import com.demo.webfluxgraphql.dto.GetByIdAndNameRequest;
import com.demo.webfluxgraphql.dto.Request;
import com.demo.webfluxgraphql.dto.Response;
import com.demo.webfluxgraphql.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @QueryMapping("getAllProduct")
    public Mono<Response> getAllProduct(){
        return productService.getAllProduct();
    }

//    @PostMapping("/save")
//    public Mono<ResponseEntity<Response>> saveProduct(@RequestBody Mono<Request> request){
//        return productService.saveProduct(request)
//                .map(obj->{
//                    return new ResponseEntity<Response>(obj,HttpStatus.CREATED);
//                });
//    }

    @QueryMapping("getProductByName")
    public Mono<Response> getProductByName(@Argument("name") String name){
        return productService.getProductByName(name);
    }

    @QueryMapping("getByIdAndNameRequest")
    public Mono<Response> getProductByIdAndName(@Argument GetByIdAndNameRequest getByIdAndNameRequest){
        return productService.getProductByIdAndName(getByIdAndNameRequest);
    }

//    @DeleteMapping("/delete/{id}")
//    public Mono<ResponseEntity<Response>> deleteProduct(@PathVariable("id") String id){
//        return productService.deleteProduct(id)
//                .map(obj->{
//                    return new ResponseEntity<Response>(obj,HttpStatus.OK);
//                });
//    }
//
//    @PutMapping("/update")
//    public Mono<ResponseEntity<Response>> updateProduct(@RequestBody Mono<Request> request){
//        return productService.updateProduct(request)
//                .map(obj->{
//                    return new ResponseEntity<Response>(obj,HttpStatus.OK);
//                });
//    }
}

package com.demo.webfluxgraphql.service;

import com.demo.webfluxgraphql.dto.Request;
import com.demo.webfluxgraphql.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface ProductService {

    public Mono<Response> getAllProduct();

    public Mono<Response> saveProduct(Mono<Request> request);

    public Mono<Response> getproduct(String name);

    public Mono<Response> deleteProduct(String id);

    public Mono<Response> updateProduct(Mono<Request> request);
}

package com.demo.webfluxgraphql.repository;

import com.demo.webfluxgraphql.model.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCassandraRepository<Product,String> {
    @AllowFiltering
    Flux<Product> findByName(String name);

}

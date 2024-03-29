package com.demo.webfluxgraphql.service.impl;

import com.demo.webfluxgraphql.dto.GetByIdAndNameRequest;
import com.demo.webfluxgraphql.dto.ProductDto;
import com.demo.webfluxgraphql.dto.Request;
import com.demo.webfluxgraphql.dto.Response;
import com.demo.webfluxgraphql.exception.GenralException;
import com.demo.webfluxgraphql.exception.ProductNotFoundException;
import com.demo.webfluxgraphql.repository.ProductRepository;
import com.demo.webfluxgraphql.service.ProductService;
import com.demo.webfluxgraphql.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<Response> getAllProduct() {
        Response response = new Response();
        return productRepository.findAll().switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .map(AppUtil::entityToDto)
                .log()
                .collectList()
//                .map(AppUtil::dtoToRsponse).
                .map(productDtos -> {
                    response.setProductDto(productDtos);
                    return response;
                })
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();
    }

    @Override
    public Mono<Response> saveProduct(Mono<Request> request) {
        Response response = new Response();
        return request.map(requestObj -> {
                    ProductDto productDto = requestObj.getProductDto().get(0);
                    productDto.setId(UUID.randomUUID().toString());
                    return productDto;
                }).map(AppUtil::dtoToEntity)
                .flatMap(productRepository::save)
                .map(AppUtil::entityToDto)
                .map(productDto -> {
                    response.setProductDto(Arrays.asList(productDto));
                    return response;
                })
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();
    }

    @Override
    public Mono<Response> getProductByName(String name) {
        return productRepository.findByName(name).switchIfEmpty(Mono.error(new ProductNotFoundException(name)))
                .map(AppUtil::entityToDto)
                .collectList()
                .map(AppUtil::dtoToRsponse)
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();
    }

    @Override
    public Mono<Response> getProductByIdAndName(GetByIdAndNameRequest getByIdAndNameRequest) {
        return productRepository.findByIdAndName(getByIdAndNameRequest.getId(), getByIdAndNameRequest.getName())
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Id- " + getByIdAndNameRequest.getId() + ",Name- " + getByIdAndNameRequest.getName())))
                .map(AppUtil::entityToDto)
                .collectList()
                .map(AppUtil::dtoToRsponse)
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();
    }

    @Override
    public Mono<Response> deleteProduct(String id) {

        return productRepository.findById(id).switchIfEmpty(Mono.error(new ProductNotFoundException(id)))
                .flatMap(existingProduct -> productRepository.delete(existingProduct)
                        .then(Mono.just(AppUtil.entityToDto(existingProduct))))
                .map(productDto -> {
                    return AppUtil.dtoToRsponse(Arrays.asList(productDto));
                })
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();
    }


    @Override
    public Mono<Response> updateProduct(Mono<Request> request) {
        return request.map(requestObj -> {
                    return requestObj.getProductDto().get(0);
                })
                .map(productDto -> {
                    return productRepository.findById(productDto.getId()).switchIfEmpty(Mono.error(new ProductNotFoundException(productDto.getId())))
                            .flatMap(existingProduct -> {
                                if (null != productDto.getName())
                                    existingProduct.setName(productDto.getName());
                                if (productDto.getPrice() > 0)
                                    existingProduct.setPrice(productDto.getPrice());

                                if (productDto.getQuantity() >= 0) {
                                    existingProduct.setQuantity(productDto.getQuantity());
                                }
                                return productRepository.save(existingProduct);
                            }).map(AppUtil::entityToDto)
                            .map(updatedProductDto -> {
                                return AppUtil.dtoToRsponse(Arrays.asList(updatedProductDto));
                            })
                            .onErrorMap(ex -> {
                                log.error("Exception Occured :" + ex);
                                throw new GenralException(ex.getMessage());
                            });
                }).flatMap(responseMono -> {
                    return responseMono;
                })
                .onErrorMap(err -> err instanceof ProductNotFoundException ? err : new GenralException(err.getMessage())).log();

    }
}

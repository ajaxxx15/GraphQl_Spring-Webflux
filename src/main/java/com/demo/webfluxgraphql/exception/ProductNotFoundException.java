package com.demo.webfluxgraphql.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ProductNotFoundException extends RuntimeException implements GraphQLError {
    public ProductNotFoundException(String id) {
        super("Product:" + id +" is not found.");
    }
    public ProductNotFoundException() {
        super("No Product found.");
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }


}
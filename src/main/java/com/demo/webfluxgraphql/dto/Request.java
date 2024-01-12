package com.demo.webfluxgraphql.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private List<ProductDto> productDto;
}

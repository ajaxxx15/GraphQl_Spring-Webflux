package com.demo.webfluxgraphql.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private String id;
    private String name;
    private int quantity;
    private double price;
}

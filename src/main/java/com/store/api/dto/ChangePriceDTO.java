package com.store.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePriceDTO {
    private Long productId;
    private double price;
}

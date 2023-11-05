package com.Kalium.model;

import com.Kalium.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductAddBindingModel {
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters!")
    @NotBlank
    private String name;
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters!")
    @NotBlank
    private String description;
    @NotBlank(message = "You must input a price!")
    private BigDecimal price;
    @NotBlank(message = "You must select a category!")
    private CategoryEnum category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}

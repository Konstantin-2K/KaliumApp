package com.Kalium.model.productEntities;

import com.Kalium.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductAddBindingModel {
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters!")
    private String name;
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters!")
    private String description;
    @NotNull(message = "You must input a price!")
    private BigDecimal price;
    @NotNull(message = "You must select a category!")
    private CategoryEnum category;

    @NotNull(message = "You must attach an image!")
    private MultipartFile image;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}

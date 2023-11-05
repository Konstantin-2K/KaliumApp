package com.Kalium.model;

import com.Kalium.model.enums.CategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ProductDTO {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private User addedBy;

    private LocalDate addedDate;

    private CategoryEnum category;

    private byte[] image;

    public ProductDTO() {

    }

    public ProductDTO(UUID id, String name, String description, BigDecimal price, User addedBy, LocalDate addedDate, CategoryEnum category, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.addedBy = addedBy;
        this.addedDate = addedDate;
        this.category = category;
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public static ProductDTO createFromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setAddedBy(product.getAddedBy());
        productDTO.setAddedDate(product.getAddedDate());
        productDTO.setCategory(product.getCategory());
        productDTO.setImage(product.getImage());

        return productDTO;
    }
}

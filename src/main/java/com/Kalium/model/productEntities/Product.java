package com.Kalium.model.productEntities;

import com.Kalium.model.BaseEntity;
import com.Kalium.model.enums.CategoryEnum;
import com.Kalium.model.userEntities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Size(min = 3, max = 20)
    @Column(nullable = false, unique = true)
    private String name;
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne
    private User addedBy;
    @NotNull
    @PastOrPresent
    private LocalDate addedDate;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    @Lob
    @Column(columnDefinition = "BLOB", nullable = false, name = "image")
    private byte[] image;

    private Integer timesBought;

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public Product setAddedBy(User addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public Product setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Product setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public Product setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Integer getTimesBought() {
        return timesBought;
    }

    public void setTimesBought(Integer timesBought) {
        this.timesBought = timesBought;
    }
}

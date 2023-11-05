package com.Kalium.model;

import java.util.List;

public class ProductCategoryDTO {
    private List<ProductDTO> allProducts;

    private List<ProductDTO> individualFlowers;

    private List<ProductDTO> bouquets;

    private List<ProductDTO> presents;

    private List<ProductDTO> specialOffers;

    public ProductCategoryDTO(List<ProductDTO> allProducts, List<ProductDTO> individualFlowers, List<ProductDTO> bouquets, List<ProductDTO> presents, List<ProductDTO> specialOffers) {
        this.allProducts = allProducts;
        this.individualFlowers = individualFlowers;
        this.bouquets = bouquets;
        this.presents = presents;
        this.specialOffers = specialOffers;
    }

    public List<ProductDTO> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<ProductDTO> allProducts) {
        this.allProducts = allProducts;
    }

    public List<ProductDTO> getIndividualFlowers() {
        return individualFlowers;
    }

    public void setIndividualFlowers(List<ProductDTO> individualFlowers) {
        this.individualFlowers = individualFlowers;
    }

    public List<ProductDTO> getBouquets() {
        return bouquets;
    }

    public void setBouquets(List<ProductDTO> bouquets) {
        this.bouquets = bouquets;
    }

    public List<ProductDTO> getPresents() {
        return presents;
    }

    public void setPresents(List<ProductDTO> presents) {
        this.presents = presents;
    }

    public List<ProductDTO> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<ProductDTO> specialOffers) {
        this.specialOffers = specialOffers;
    }
}

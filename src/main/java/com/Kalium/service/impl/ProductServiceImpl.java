package com.Kalium.service.impl;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductCategoryDTO;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import com.Kalium.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public byte[] extractBytes(MultipartFile image) throws IOException {
        return image.getBytes();
    }

    @Override
    public boolean addProduct(ProductAddBindingModel productAddBindingModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Product productByName = productRepository.findByName(productAddBindingModel.getName());

            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());

            if (productByName != null) {
                return false;
            }

            MultipartFile image = productAddBindingModel.getImage();
            byte[] imageBytes = null;

            if (image != null && !image.isEmpty()) {
                try {
                    imageBytes = extractBytes(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Product product = map(productAddBindingModel, user);
            product.setImage(imageBytes);

            productRepository.save(product);

            return true;
        }

        return false;
    }

    private Product map(ProductAddBindingModel productAddBindingModel, User user) {
        return new Product()
                .setName(productAddBindingModel.getName())
                .setDescription(productAddBindingModel.getDescription())
                .setPrice(productAddBindingModel.getPrice())
                .setAddedBy(user)
                .setAddedDate(LocalDate.now())
                .setCategory(productAddBindingModel.getCategory());
    }

    public ProductCategoryDTO getCategoriesViewData() {
        List<ProductDTO> allProducts = productRepository.findAll()
                .stream().map(ProductDTO::createFromProduct)
                .toList();

        List<ProductDTO> individualFlowers = new ArrayList<>();
        List<ProductDTO> bouquets = new ArrayList<>();
        List<ProductDTO> presents = new ArrayList<>();
        List<ProductDTO> specialOffers = new ArrayList<>();

        for (ProductDTO productDTO : allProducts) {
            switch (productDTO.getCategory()) {
                case INDIVIDUAL_FLOWER -> individualFlowers.add(productDTO);
                case BOUQUET -> bouquets.add(productDTO);
                case PRESENT -> presents.add(productDTO);
                case SPECIAL_OFFER -> specialOffers.add(productDTO);
            }
        }
        return new ProductCategoryDTO(allProducts, individualFlowers, bouquets, presents,specialOffers);
    }

    @Override
    public byte[] getProductImageById(UUID productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent() && product.get().getImage() != null) {
            return product.get().getImage();
        }
        return new byte[0];
    }


}

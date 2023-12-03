package com.Kalium.service.impl;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductCategoryDTO;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import com.Kalium.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

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
        MultipartFile image = productAddBindingModel.getImage();

        if (authentication != null && authentication.isAuthenticated() && !image.isEmpty()) {
            Product productByName = productRepository.findByName(productAddBindingModel.getName());

            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());

            if (productByName != null) {
                return false;
            }

            image = productAddBindingModel.getImage();
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
            product.setTimesBought(0);

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

    public List<ProductDTO> getCategoriesViewData(String filterType) {
        List<ProductDTO> allProducts = productRepository.findAll()
                .stream().map(ProductDTO::createFromProduct)
                .toList();

        if (filterType.equals("all")) {
            return allProducts;
        } else {
            List<ProductDTO> requestedFlowers = new ArrayList<>();

            for (ProductDTO productDTO : allProducts) {
                if (productDTO.getCategory().toString().equals(filterType)) {
                    requestedFlowers.add(productDTO);
                }
            }
            return requestedFlowers;
        }
    }

    @Override
    public byte[] getProductImageById(UUID productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent() && product.get().getImage() != null) {
            return product.get().getImage();
        }
        return new byte[0];
    }

    @Override
    public void removeProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductDTO> getMostBought() {
        PageRequest pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "timesBought"));
        List<ProductDTO> mostBought = productRepository.findAll(pageable).getContent().stream().map((product) -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
        return mostBought;
    }
}
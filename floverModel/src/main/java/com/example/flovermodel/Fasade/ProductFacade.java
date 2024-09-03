package com.example.flovermodel.Fasade;

import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductFacade {

    private final ProductService productService;


    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }
    public ProductDTO addProductQuantity(Long productId,int stockQuantity){
       return productService.addProductQuantity(productId,stockQuantity);
    }


}
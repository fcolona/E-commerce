package br.com.ecommerce.api.controller;

import br.com.ecommerce.domain.model.Image;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@AllArgsConstructor
public class ImageController {
    private ImageService imageService;

    @PostMapping(value = "/api/v1/products/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Image> addImagesToProducts(@RequestPart("files") MultipartFile[] imagesFile, @PathVariable long productId) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : imagesFile) {
            Image img = new Image();
            img.setContent(multipartFile.getBytes());

            Product product = new Product();
            img.setProduct(product);
            img.getProduct().setId(productId);
            images.add(img);
        }
       return imageService.addToProduct(images);
    }

    @PutMapping(value = "/api/v1/products/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Image> updateProductImages(@RequestPart("files") MultipartFile[] imagesFile, @PathVariable long productId) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : imagesFile) {
            Image img = new Image();
            img.setContent(multipartFile.getBytes());

            Product product = new Product();
            img.setProduct(product);
            img.getProduct().setId(productId);
            images.add(img);
        }
        return imageService.updateProductImages(productId, images);
    }
}

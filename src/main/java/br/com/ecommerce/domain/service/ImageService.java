package br.com.ecommerce.domain.service;

import br.com.ecommerce.domain.model.Image;
import br.com.ecommerce.domain.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepository imageRepository;

    public List<Image> addToProduct(List<Image> images){
      return imageRepository.saveAll(images);
    }

    public List<Image> updateProductImages(long productId, List<Image> images){
        imageRepository.deleteByProductId(productId);
        return imageRepository.saveAll(images);
    }
}

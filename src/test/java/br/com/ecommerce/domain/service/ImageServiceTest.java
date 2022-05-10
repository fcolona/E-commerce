package br.com.ecommerce.domain.service;


import br.com.ecommerce.domain.model.Image;
import br.com.ecommerce.domain.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    private ImageService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ImageService(imageRepository);
    }

   @Test
   void canAddImagesToProduct(){
       //given
       List<Image> images = new ArrayList<>();
       Image image = new Image();
       byte[] array = new byte[5];
       image.setContent(array);
       images.add(image);

       //when
       underTest.addToProduct(images);

       //then
       verify(imageRepository).saveAll(images);
   }

    @Test
    void canUpdateProductImages(){
        //given
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        byte[] array = new byte[5];
        image.setContent(array);
        images.add(image);

        //when
        underTest.updateProductImages(1L, images);

        //then
        verify(imageRepository).saveAll(images);
    }

}

package com.thoughtworks.product.service;

import com.thoughtworks.product.entity.Product;
import com.thoughtworks.product.exception.ProductNotFoundException;
import com.thoughtworks.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @Before
    public void setUp() throws Exception {
        productService = new ProductService(productRepository);
    }

    @Test
    public void should_get_all_products() {
        //given
        Product product = Product.builder()
                .id(1L)
                .imageUrl("./assets")
                .name("test")
                .price(4D)
                .unit("个")
                .build();

        List<Product> expect = new ArrayList<>();
        expect.add(product);

        given(productRepository.findAll())
                .willReturn(expect);
        //when

        List<Product> actual = productService.getAll();

        //then
        assertThat(actual.get(0).getId()).isEqualTo(1L);
        assertThat(actual.get(0).getName()).isEqualTo("test");
        assertThat(actual.get(0).getImageUrl()).isEqualTo("./assets");
        assertThat(actual.get(0).getPrice()).isEqualTo(4D);
    }

    @Test
    public void should_return_a_product_given_id() {
        //given
        Product product = Product.builder()
                .id(1L)
                .imageUrl("./assets")
                .name("test")
                .price(4D)
                .unit("个")
                .build();

        Optional<Product> optional = Optional.of(product);
        given(productRepository.findById(anyLong()))
                .willReturn(optional);
        //when

        Product actual = productService.get(1L);
        //then
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("test");
        assertThat(actual.getImageUrl()).isEqualTo("./assets");
        assertThat(actual.getPrice()).isEqualTo(4D);
    }

    @Test
    public void should_add_a_product() {
        //given
        Product product = Product.builder()
                .id(1L)
                .imageUrl("./assets")
                .name("test")
                .price(4D)
                .unit("个")
                .build();

        given(productRepository.save(any(Product.class)))
                .willReturn(product);
        //when

        productService.add(product);
        //then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void should_remove_a_product() {
        //given

        //when
        productService.remove(anyLong());
        //then
        verify(productRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void should_update_a_product() {
        //given
        Product product = Product.builder()
                .id(1L)
                .imageUrl("./assets")
                .name("test")
                .price(4D)
                .unit("个")
                .build();

        Optional<Product> op = Optional.of(product);

        Long id = 1L;

        given(productRepository.findById(anyLong()))
                .willReturn(op);

        given(productRepository.save(any(Product.class)))
                .willReturn(product);
        //when
        productService.update(id, product);

        //then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test(expected = ProductNotFoundException.class)
    public void should_throw_product_not_found_exception(){
        //given
        Optional<Product> empty = Optional.empty();

        given(productRepository.findById(anyLong()))
                .willReturn(empty);
        //when
        productService.get(anyLong());

        //then
    }
}
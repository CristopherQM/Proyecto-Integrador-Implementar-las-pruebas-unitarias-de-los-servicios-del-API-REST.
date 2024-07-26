import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.Controllerç.Product;
import org.example.Controllerç.ProductRepository;
import org.example.Controllerç.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById_ProductExists() {
        Product product = new Product(1L, "Laptop");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(1L);
        assertEquals(product, result);
    }

    @Test
    public void testGetProductById_ProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Product result = productService.getProductById(1L);
        assertNull(result);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(null, "Phone");
        Product savedProduct = new Product(1L, "Phone");
        when(productRepository.save(product)).thenReturn(savedProduct);
        Product result = productService.createProduct(product);
        assertEquals(savedProduct, result);
    }

    @Test
    public void testUpdateProduct_ProductExists() {
        Product product = new Product(null, "Phone");
        Product updatedProduct = new Product(1L, "Phone");
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(updatedProduct);
        Product result = productService.updateProduct(1L, product);
        assertEquals(updatedProduct, result);
    }

    @Test
    public void testUpdateProduct_ProductDoesNotExist() {
        Product product = new Product(null, "Phone");
        when(productRepository.existsById(1L)).thenReturn(false);
        Product result = productService.updateProduct(1L, product);
        assertNull(result);
    }

    @Test
    public void testDeleteProduct_ProductExists() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);
        boolean result = productService.deleteProduct(1L);
        assertTrue(result);
    }

    @Test
    public void testDeleteProduct_ProductDoesNotExist() {
        when(productRepository.existsById(1L)).thenReturn(false);
        boolean result = productService.deleteProduct(1L);
        assertFalse(result);
    }

    @Test
    public void testCreateProduct_NullProduct() {
        assertThrows(NullPointerException.class, () -> {
            productService.createProduct(null);
        });
    }

    @Test
    public void testUpdateProduct_InvalidId() {
        Product product = new Product(null, "Phone");
        when(productRepository.existsById(null)).thenReturn(false);
        Product result = productService.updateProduct(null, product);
        assertNull(result);
    }

    @Test
    public void testDeleteProduct_NullId() {
        assertThrows(NullPointerException.class, () -> {
            productService.deleteProduct(null);
        });
    }

    @Test
    public void testGetProductById_Exception() {
        when(productRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> {
            productService.getProductById(1L);
        });
    }
}

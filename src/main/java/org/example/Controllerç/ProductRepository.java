package org.example.Controllerç;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    Product save(Product product);
    boolean existsById(Long id);
    void deleteById(Long id);
}


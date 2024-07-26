package org.example.Controllerç;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);
    Order save(Order order);
    boolean existsById(Long id);
    void deleteById(Long id);
}

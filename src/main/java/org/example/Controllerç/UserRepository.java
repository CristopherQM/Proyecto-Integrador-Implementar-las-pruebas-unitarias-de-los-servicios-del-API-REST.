package org.example.Controllerç;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    User save(User user);
    boolean existsById(Long id);
    void deleteById(Long id);
}


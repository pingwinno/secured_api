package net.streamarchive.secured_db_api.repositories;

import net.streamarchive.secured_db_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String user);
}

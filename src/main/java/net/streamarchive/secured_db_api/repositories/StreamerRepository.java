package net.streamarchive.secured_db_api.repositories;

import net.streamarchive.secured_db_api.models.Streamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamerRepository extends JpaRepository<Streamer, Integer> {
    Streamer findByName(String name);
}

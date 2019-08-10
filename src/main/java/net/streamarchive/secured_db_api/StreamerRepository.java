package net.streamarchive.secured_db_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamerRepository extends JpaRepository<Streamer, String> {
}

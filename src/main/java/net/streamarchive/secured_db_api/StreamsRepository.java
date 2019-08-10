package net.streamarchive.secured_db_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StreamsRepository extends JpaRepository<Stream, UUID> {
    List<Stream> findAllByStreamerNameOrderByDateDesc(String streamer);
}

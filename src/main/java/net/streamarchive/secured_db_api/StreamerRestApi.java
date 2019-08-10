package net.streamarchive.secured_db_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StreamerRestApi {
    @Autowired
    StreamerRepository streamerRepository;

    @RequestMapping("/streamers")
    public List<Streamer> getStreamers() {
        return streamerRepository.findAll();
    }
}

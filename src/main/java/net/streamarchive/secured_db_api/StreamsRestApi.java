package net.streamarchive.secured_db_api;

import net.streamarchive.secured_db_api.models.Stream;
import net.streamarchive.secured_db_api.repositories.StreamerRepository;
import net.streamarchive.secured_db_api.repositories.StreamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/streams")
public class StreamsRestApi {

    @Autowired
    StreamsRepository streamsRepository;
    @Autowired
    StreamerRepository streamerRepository;

    @PreAuthorize("@streamerAuth.authorize(principal,#streamer)")
    @RequestMapping(method = RequestMethod.GET, value = "{streamer}")
    public List<Stream> getStreams(@PathVariable(value = "streamer") String streamer) {
        return streamsRepository.findAllByStreamerNameOrderByDateDesc(streamer);
    }

    @PreAuthorize("@streamerAuth.authorize(principal,#streamer)")
    @RequestMapping(value = "{streamer}/{uuid}")
    public Stream getStream(@PathVariable("uuid") String uuid, @PathVariable(value = "streamer") String streamer) {
        return streamsRepository.findById(UUID.fromString(uuid)).orElseThrow(NotFoundException::new);
    }

    @PreAuthorize("@streamerAuth.authorize(principal,#streamer)")
    @RequestMapping(method = RequestMethod.POST, value = "{streamer}")
    public void addStream(@RequestBody Stream stream, @PathVariable(value = "streamer") String streamer) {
        stream.setStreamer(streamerRepository.findByName(streamer));
        streamsRepository.save(stream);
    }

    @PreAuthorize("@streamerAuth.authorize(principal,#streamer)")
    @RequestMapping(value = "{streamer}/{uuid}", method = RequestMethod.DELETE)
    public void deleteStream(@PathVariable("uuid") String uuid, @PathVariable(value = "streamer") String streamer) {
        streamsRepository.deleteById(UUID.fromString(uuid));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class NotFoundException extends RuntimeException {
    }
}

package net.streamarchive.secured_db_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/streams")
public class StreamsRestApi {

    @Autowired
    StreamsRepository streamsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Stream> getStreams(@RequestParam(value = "streamer") String streamer) {
        return streamsRepository.findAllByStreamerNameOrderByDateDesc(streamer);
    }

    @RequestMapping(value = "{uuid}")
    public Stream getStream(@PathVariable("uuid") String uuid) {
        return streamsRepository.findById(UUID.fromString(uuid)).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addStream(@RequestBody Stream stream) {
        streamsRepository.save(stream);
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    public void deleteStream(@PathVariable("uuid") String uuid) {
        streamsRepository.deleteById(UUID.fromString(uuid));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class NotFoundException extends RuntimeException {
    }
}

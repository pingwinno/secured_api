package net.streamarchive.secured_db_api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.streamarchive.secured_db_api.models.Stream;
import net.streamarchive.secured_db_api.models.Streamer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecuredDbApiApplicationTests {
    @Autowired
    StreamerRepository streamerRepository;
    @Autowired
    StreamsRepository streamsRepository;

    @Test
    public void importStreams() throws IOException {

        Streamer albisha = new Streamer();
        albisha.setName("albisha");
        albisha.setStorageEndpoint("https://94491.s.time4vps.cloud");

        Streamer olyashaa = new Streamer();
        olyashaa.setName("olyashaa");
        olyashaa.setStorageEndpoint("https://storage.streamarchive.net");
        streamerRepository.save(olyashaa);
        streamerRepository.save(albisha);


        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(List.class, Stream.class);
        List<Stream> albishaStreams = objectMapper.readValue(new URL("https://storage.streamarchive.net/db/streams/albisha?limit=1000")
                , type);
        albisha.setStreams(albishaStreams);

        albishaStreams.forEach(stream -> {
            stream.setStreamer(albisha);
        });

        List<Stream> olyashaaStreams = objectMapper.readValue(new URL("https://storage.streamarchive.net/db/streams/olyashaa?limit=1000")
                , type);
        olyashaaStreams.forEach(stream -> {
            stream.setStreamer(olyashaa);
        });
        olyashaa.setStreams(olyashaaStreams);
        streamsRepository.saveAll(albishaStreams);
        streamsRepository.saveAll(olyashaaStreams);
        streamerRepository.save(olyashaa);
        streamerRepository.save(albisha);

    }

}

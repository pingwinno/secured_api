package net.streamarchive.secured_db_api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.streamarchive.secured_db_api.models.Stream;
import net.streamarchive.secured_db_api.models.Streamer;
import net.streamarchive.secured_db_api.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SecuredDbApiApplication {
    Logger logger = LoggerFactory.getLogger(SecuredDbApiApplication.class);
    @Autowired
    UserRepository userRepository;
    @Autowired

    StreamsRepository streamsRepository;
    @Autowired
    StreamerRepository streamerRepository;
    public static void main(String[] args) {
        SpringApplication.run(SecuredDbApiApplication.class, args);
    }

    @PostConstruct
    public void importStreams() throws IOException {

        Streamer albisha = new Streamer();
        albisha.setName("albisha");
        albisha.setStorageEndpoint("https://94491.s.time4vps.cloud");
        streamerRepository.save(albisha);


        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(List.class, Stream.class);
        List<Stream> albishaStreams = objectMapper.readValue(new URL("https://94491.s.time4vps.cloud/db/streams/albisha?limit=1000")
                , type);

        albishaStreams.forEach(stream -> {


            stream.setStreamer(albisha);
            //System.out.println(stream);

        });
        streamsRepository.saveAll(albishaStreams);

        User user = new User();
        user.setName("max");
        user.setPassword(new BCryptPasswordEncoder().encode("siemenss"));
        List<Streamer> streamers = new ArrayList<>();
        streamers.add(albisha);
        user.setStreamers(streamers);


        userRepository.save(user);
        albisha.setUser(user);
        streamerRepository.save(albisha);
        for (Streamer streamer : userRepository.getOne("max").getStreamers()) {
            logger.error(streamer.getName());
        }
    }


}

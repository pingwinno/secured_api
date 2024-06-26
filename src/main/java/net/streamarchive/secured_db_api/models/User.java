package net.streamarchive.secured_db_api.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    String name;
    String password;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Streamer> streamers;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Streamer> getStreamers() {
        return streamers;
    }

    public void setStreamers(List<Streamer> streamers) {
        this.streamers = streamers;
    }
}

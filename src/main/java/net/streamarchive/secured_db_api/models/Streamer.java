package net.streamarchive.secured_db_api.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STREAMERS")
public class Streamer {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String storageEndpoint;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "streamer")
    private List<Stream> streams;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorageEndpoint() {
        return storageEndpoint;
    }

    public void setStorageEndpoint(String storageEndpoint) {
        this.storageEndpoint = storageEndpoint;
    }
}

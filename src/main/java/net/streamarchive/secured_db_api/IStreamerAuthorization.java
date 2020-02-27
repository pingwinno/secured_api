package net.streamarchive.secured_db_api;

import org.springframework.security.core.userdetails.UserDetails;

public interface IStreamerAuthorization {
    boolean authorize(UserDetails principal, String name);
}

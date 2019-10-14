package net.streamarchive.secured_db_api;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("streamerAuth")
public class StreamerAuthorization implements IStreamerAuthorization {
    @Override
    public boolean authorize(UserDetails principal, String streamer) {
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(streamer)) return true;
        }
        ;
        return false;
    }
}

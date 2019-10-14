package net.streamarchive.secured_db_api.configs;

import net.streamarchive.secured_db_api.MySavedRequestAwareAuthenticationSuccessHandler;
import net.streamarchive.secured_db_api.MyUserDetailsService;
import net.streamarchive.secured_db_api.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


@EnableWebSecurity
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().authorizeRequests().antMatchers("/**").authenticated().and()
                .formLogin()
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler).and().csrf().disable()
        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

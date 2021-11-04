package eu.senla.alexbych.bulletinboard.backend.config;

import eu.senla.alexbych.bulletinboard.backend.config.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@ComponentScan("eu.senla.alexbych.bulletinboard.backend")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/setAdmin/**", "/post/boost/*", "/post/delete/*").hasRole("ADMIN")
                .antMatchers("/profile/edit/", "/profile/posts", "/profile/*/setRating",
                        "/post/*/edit", "/post/all", "/post/deleteMy/*", "/post/create", "/post/search",
                        "/post/*/chat/create", "/post/*/comment", "/post/category/*",
                        "/post/{^[0-9]+$}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/*").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package eu.senla.alexbych.bulletinboard.backend.config;

import eu.senla.alexbych.bulletinboard.backend.config.jwt.JwtFilter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.ChatConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.ChatUserConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IChatUserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories("eu.senla.alexbych.bulletinboard.backend.repository")
@PropertySource("classpath:application.properties")
@EntityScan({"eu.senla.alexbych.bulletinboard.backend.model", "eu.senla.alexbych.bulletinboard.chat.model"})
@ComponentScan("eu.senla.alexbych.bulletinboard.backend")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and()
 //               .authorizeRequests()
//                .antMatchers("/setAdmin/**", "/post/boost/*", "/post/delete/*").hasRole("ADMIN")
//                .antMatchers("/profile/edit/", "/profile/posts", "/profile/*/setrating",
//                        "/post/*/edit", "/post/all", "/post/deleteme/*", "/post/create", "/post/search",
//                        "/post/*/chat/create", "/post/*/comment", "/post/category/*",
//                        "/post/{^[0-9]+$}").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/post/all").permitAll()
//                .and()
 //               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ChatConverter chatConverter(){
        return new ChatConverter();
    }

    @Bean
    public ChatUserConverter chatUserConverter(){
        return new ChatUserConverter();
    }
}

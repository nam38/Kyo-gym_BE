package com.capstone.wellnessnavigatorgym.config;

import com.capstone.wellnessnavigatorgym.security.jwt.JwtEntryPoint;
import com.capstone.wellnessnavigatorgym.security.jwt.JwtFilter;
import com.capstone.wellnessnavigatorgym.security.userprinciple.UserDetailService;
import com.capstone.wellnessnavigatorgym.utils.BuildDecisionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public BuildDecisionTree buildDecisionTree() {
        return new BuildDecisionTree();
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/public/**",
                        "/api/v1/course/**",
                        "/api/v1/course-type/**",
                        "/api/v1/comment/**",
                        "/api/v1/day/**",
                        "/api/v1/track-data-ai/**",
                        "/api/v1/payment/**",
                        "/api/v1/course-day/**")
                .permitAll()
                .antMatchers("/api/v1/customer/**",
                        "/api/v1/customer-type/**",
                        "/api/v1/exercise/**").hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

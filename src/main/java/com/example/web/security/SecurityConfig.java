package com.example.web.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService customUserDetailsService;

  /*  @Bean
    public MessageDigestPasswordEncoder messageDigestPasswordEncoder()
    {
        return new MessageDigestPasswordEncoder("sha-256");
    }*/

    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
     // @formatter:off
            http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/admin","/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("USER")
                    .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginProcessingUrl("/login-check")
                    .loginPage("/login")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/login?error").permitAll()
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .deleteCookies("JSESSIONID")
             /*.and()
                 .sessionManagement()
                     .maximumSessions(3)*/;
            /*.and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")*/;
        
     // @formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsService);
    }
    
    /*@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }*/
}

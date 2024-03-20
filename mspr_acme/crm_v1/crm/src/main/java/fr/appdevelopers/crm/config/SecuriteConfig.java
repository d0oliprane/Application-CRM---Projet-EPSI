package fr.appdevelopers.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecuriteConfig {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->{
                auth.requestMatchers("/ajouter-employe").hasAnyRole("ADMIN")
                    .requestMatchers("/archiver-employe/**").hasAnyRole("ADMIN")
                    .requestMatchers("/produits").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("archiver-produit/**").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("desarchiver-produit/**").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("modifier-produit/**").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("produit/").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("produits-archives").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("main_menu").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("/commandes").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("detail_commande/**").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("statistiques").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("ajouter-client").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("clients").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("ajouter-produit").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("/desarchiver-employe/**").hasAnyRole("ADMIN")
                    .requestMatchers("/archiver-client/**").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("/desarchiver-client/**").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("/modifier-client/**").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("/passer-commande").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("/employe/**").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("/employes").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers("/clients-archives").hasAnyRole("ADMIN","RESPONSABLE","EMPLOYE")
                    .requestMatchers("/modifier-employe/**").hasAnyRole("ADMIN")
                    .requestMatchers("/employes-archives").hasAnyRole("ADMIN","RESPONSABLE")
                    .requestMatchers(("/css/**")).permitAll()
                    .requestMatchers(("/font/**")).permitAll()
                    .requestMatchers(("/images/**")).permitAll()
                    .anyRequest().authenticated();
        });

        http.formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/main_menu", true)
                .permitAll()
        );

        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
        );

        return http.build();
    }
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("EMPLOYE")
                .password(passwordEncoder().encode("password"))
                .roles("EMPLOYE").build();

        UserDetails responsable = User.builder()
                .username("RESPONSABLE")
                .password(passwordEncoder().encode("password"))
                .roles("RESPONSABLE").build();

        UserDetails admin = User.builder()
                .username("ADMIN")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user,responsable, admin);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }


}


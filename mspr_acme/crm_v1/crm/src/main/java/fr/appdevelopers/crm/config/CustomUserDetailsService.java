package fr.appdevelopers.crm.config;

import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Role;
import fr.appdevelopers.crm.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeService employeService;

    @Override
    public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException {
        Employe employe = employeService.findByMatricule(matricule);
        if (employe == null) {

           return null;

        } else if (employe.getArchive()) {
            return null;
        }

        List<Role> roles = Collections.singletonList(employe.getRole());
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder()
                .username(employe.getMatricule())
                .password(employe.getMotDePasse())
                .authorities(authorities)
                .build();
    }
}

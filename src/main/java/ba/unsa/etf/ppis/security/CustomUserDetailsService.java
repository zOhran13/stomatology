package ba.unsa.etf.ppis.security;

import ba.unsa.etf.ppis.Model.Role;
import ba.unsa.etf.ppis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with provided username - " + username + " does not exist"));
        return new User(user.getEmail(), user.getPassword(), mapRoleToAuthority(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthority(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
}

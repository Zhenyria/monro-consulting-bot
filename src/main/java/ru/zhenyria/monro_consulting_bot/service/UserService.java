package ru.zhenyria.monro_consulting_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.User;
import ru.zhenyria.monro_consulting_bot.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final String rootUserName;
    private final String rootUserPassword;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       @Value("${application.security.root.name}") String rootUserName,
                       @Value("${application.security.root.password}") String rootUserPassword) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.rootUserName = rootUserName;
        this.rootUserPassword = rootUserPassword;
    }

    @PostConstruct
    private void init() {
        delete(rootUserName);
        register(rootUserName, rootUserPassword);
    }

    public void register(String name, String password) {
        if (isRegistered(name)) {
            throw new IllegalArgumentException("The name %s is used already");
        }

        repository.save(new User(name, passwordEncoder.encode(password)));
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return repository.findById(name)
                         .orElseThrow(() -> new UsernameNotFoundException("User %s is not found".formatted(name)));
    }

    private boolean isRegistered(String name) {
        return repository.existsById(name);
    }

    public void delete(String name) {
        if (repository.existsById(name)) {
            repository.deleteById(name);
        }
    }
}

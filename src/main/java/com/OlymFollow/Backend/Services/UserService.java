package com.OlymFollow.Backend.Services;


import com.OlymFollow.Backend.Dtos.UserDTO;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Repositories.UserRepository;
import com.OlymFollow.Backend.Security.infra.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    protected final UserRepository userRepository;
    protected final CountryService countryService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CountryService countryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryService = countryService;
    }
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserbyId(String encryptedId) throws Exception {
        //Long id = new EncryptedId(encryptedId).getDecrypted();
        Optional<User> user = userRepository.findById(Long.parseLong(encryptedId));
        return user.orElse(null);
    }

    public UserDetails getUserByUsername(String username) {
        return unwrap(userRepository.findByUsername(username));
    }

    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void subscribe(int userID, int countryID){
        var user = userRepository.findById((long) userID);
        if(user.isEmpty()) throw new NotFoundException("User not found");
        var country = countryService.getCountryById(countryID);
        if(country.isEmpty()) throw new NotFoundException("Country not found");
        user.get().subscribe(country.get());
    }

    public void unsubscribe(int userID, int countryID){
        var user = userRepository.findById((long) userID);
        if(user.isEmpty()) throw new NotFoundException("User not found");
        var country = countryService.getCountryById(countryID);
        if(country.isEmpty()) throw new NotFoundException("Country not found");
        user.get().unsubscribe(country.get());
    }

    public void deleteUser(Long id){
        Optional<User> user = getUserById(id);
        user.ifPresentOrElse(userRepository::delete, () -> {throw new NotFoundException("User not found");});
    }
    static User unwrap(Optional<User> user) {
        if(user.isPresent()) return user.get();
        throw new NotFoundException("User not found");
    }
}


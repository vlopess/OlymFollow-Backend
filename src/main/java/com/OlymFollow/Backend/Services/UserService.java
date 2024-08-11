package com.OlymFollow.Backend.Services;


import com.OlymFollow.Backend.Dtos.UserDTO;
import com.OlymFollow.Backend.Dtos.UserRegisterDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Repositories.CountryRepository;
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
    protected final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryRepository = countryRepository;
    }
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public UserDTO getUserById(String encryptedId) throws Exception {
        //Long id = new EncryptedId(encryptedId).getDecrypted();
        Optional<User> user = userRepository.findById(Long.parseLong(encryptedId));
        return new UserDTO(unwrap(user));
    }

    public UserDTO getUserByUsername(String username) {
        return new UserDTO(unwrap(userRepository.findByUsername(username)));
    }

    public User addUser(UserRegisterDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void subscribe(int userID, int countryID){
        var userOp = userRepository.findById((long) userID);
        var user = unwrap(userOp);
        var countryOp = countryRepository.findById((long) countryID);
        var country = CountryService.unwrap(countryOp);
        user.subscribe(country);
    }

    public void unsubscribe(int userID, int countryID){
        var userOp = userRepository.findById((long) userID);
        var user = unwrap(userOp);
        var countryOp = countryRepository.findById((long) countryID);
        var country = CountryService.unwrap(countryOp);
        user.unsubscribe(country);
    }

    public void deleteUser(Long id){
        Optional<User> userOp = getUserById(id);
        var user = unwrap(userOp);
        userRepository.delete(user);
    }
    static User unwrap(Optional<User> user) {
        if(user.isPresent()) return user.get();
        throw new NotFoundException("User not found");
    }
}


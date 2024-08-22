package com.OlymFollow.Backend.Controllers;

import com.OlymFollow.Backend.Dtos.UserDTO;
import com.OlymFollow.Backend.Dtos.UserRegisterDTO;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Services.JWTokenService;
import com.OlymFollow.Backend.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/user")
public class UserController {
    final private UserService userService;
    final private JWTokenService jwtTokenService;


    @Autowired
    public UserController(UserService userService, JWTokenService jwtTokenService) {

        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        }

    @GetMapping(value = "GetAll")
    @Operation(summary = "Busca todos usuários", description = "Retorna uma página com os usuários", security = @SecurityRequirement(name = "bearer-key"))
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) throws Exception {
        var users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Busca o usuário pelo ID", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<UserDTO> getUserById(@RequestParam String id) throws Exception {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(user);

    }

    @GetMapping(value = "/{username}")
    @Secured("ROLE_USER")
    @Operation(summary = "Busca o usuário pelo seu username", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        var user =  userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Transactional
    @PostMapping("/register")
    @Operation(summary = "Cria um usuário")
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserRegisterDTO userDTO, UriComponentsBuilder uriBuilder) throws Exception {
        User user = userService.addUser(userDTO);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        var token = jwtTokenService.generateToken(user.getUsername());
        headers.add("Authorization", "Bearer " + token);
        headers.add("userID", user.getId().toString());
        return ResponseEntity.created(uri).body(("User added!"));
    }

    @Transactional
    @PostMapping(value = "/subscribe/{userID}/{countryID}")
    @Operation(summary = "Para um usuário seguir um país que ele queira receber notificação", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> followCountry(@PathVariable int userID, @PathVariable int countryID) throws Exception {
        userService.subscribe(userID, countryID);
        return ResponseEntity.ok("Inscrição realizada com sucesso!");
    }

    @Transactional
    @DeleteMapping(value = "/unsubscribe/{userID}/{countryID}")
    @Operation(summary = "Para de seguir um país", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> unfollowCountry(@PathVariable int userID, @PathVariable int countryID) throws Exception {
        userService.unsubscribe(userID, countryID);
        return ResponseEntity.ok("Inscrição cancelada com sucesso!");
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga usuário", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

package com.OlymFollow.Backend.Controllers;

import com.OlymFollow.Backend.Dtos.UserDTO;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "GetAll")
    @Operation(summary = "Busca todos usuários", description = "Retorna uma página com os usuários", security = @SecurityRequirement(name = "bearer-key"))
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<User>> findAll(Pageable pageable) throws Exception {
        var users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Busca o usuário pelo ID", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<User> getUserById(@RequestParam String encryptedId) throws Exception {
        var user = userService.getUserbyId(encryptedId);
        return ResponseEntity.ok(user);

    }

    @GetMapping(value = "/{username}")
    @Secured("ROLE_USER")
    @Operation(summary = "Busca o usuário pelo seu username", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        var user =  userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Transactional
    @PostMapping("/register")
    @Operation(summary = "Cria um usuário")
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) throws Exception {
        try {
            User user = userService.addUser(userDTO);
            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(("User added!"));
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
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
    public ResponseEntity<Object> unfollowCountry(@PathVariable int userID, @PathVariable String countryID) throws Exception {
        userService.unsubscribe(userID, Integer.parseInt(countryID));
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

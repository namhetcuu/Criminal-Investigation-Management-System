package com.backend.authservice.controller;

import com.backend.authservice.dto.request.UserCreationRequest;
import com.backend.authservice.dto.response.UserResponse;
import com.backend.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User Query", description = "User API")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    UserService userService;

    /*
     * @method: Get user info
     * @description: Returns the information of the authenticated user.
     */
    @Operation(summary = "Get user info")
    @GetMapping("/my-info")
    public ResponseEntity<UserResponse> myInfo() {
        log.info("Get user info for authenticated user");
        return ResponseEntity.ok(userService.getMyInfo());
    }

    /*
     * @method: Get all Users
     * @description: Returns a list of all users in the system.
     */
    @Operation(summary = "Get all users",
            description = "Returns a list of all users in the system. " +
                    "This endpoint is secured and requires authentication.")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        log.info("Get all users");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("No authenticated user found");
        }
        assert auth != null;
        auth.getAuthorities().forEach(authority ->
                log.info("User {} has authority: {}", auth.getName(), authority.getAuthority())
        );
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /*
     * @method: Create a new User
     * @description: Create a new user with the provided details.
     */
    @Operation(summary = "Create a new user"
    , description = "Create a new user with the provided details.")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreationRequest user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    /*
     * @method: Delete a User
     * @description: Deletes a user by their ID.
     */
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID. ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Delete user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
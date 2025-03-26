package com.bursaefek.bursaefek.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bursaefek.bursaefek.constant.ApiBash;
import com.bursaefek.bursaefek.constant.ResponseExampleSwaggerBash;
import com.bursaefek.bursaefek.model.request.UserRequest;
import com.bursaefek.bursaefek.model.response.CommonResponse;
import com.bursaefek.bursaefek.model.response.LoginResponse;
import com.bursaefek.bursaefek.model.response.LogoutResponse;
import com.bursaefek.bursaefek.model.response.RegisterResponse;
import com.bursaefek.bursaefek.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Auth API", description = "API untuk mengelola authentication (register, login & logout)")
@RequiredArgsConstructor
@RequestMapping(ApiBash.AUTH)
public class UserController {

    private final AppUserService userService;

    @Operation(
        summary = "Registrasi akun", 
        description = "Membuat akun baru.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Product created successfully",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.SIGNUP_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.SIGNUP_FAILED)
                )
            )
        }
    )
    @PostMapping(ApiBash.USER_SIGNUP)
    public ResponseEntity<CommonResponse<RegisterResponse>> createUser(
            @Valid 
            @RequestBody
            @Schema(example = "{ \"email\": \"admin@example.com\", \"password\": \"SecureAdmin123\" }")
            UserRequest userRequest) {
        try {
            RegisterResponse newUser = userService.register(userRequest);
            CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(ApiBash.SIGNUP_SUCCESS)
                .data(newUser)
                .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.SIGNUP_FAILED+ ": " + e.getMessage())
                .data(null)
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
        summary = "Login akun", 
        description = "Melakukan login dengan email dan password.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Product created successfully",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.LOGIN_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.LOGIN_FAILED)
                )
            )
        }
    )
    @PostMapping(ApiBash.LOGIN)
    public ResponseEntity<CommonResponse<LoginResponse>> loginAdminAccount(
        @Schema(example = "{ \"email\": \"admin@example.com\", \"password\": \"SecureAdmin123\" }")
        @RequestBody
        UserRequest userRequest
    ) {
        try {
            LoginResponse userAccount = userService.login(userRequest);
            CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .code(HttpStatus.ACCEPTED.value())
                .message(ApiBash.LOGIN_SUCCESS)
                .data(userAccount)
                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.LOGIN_FAILED+ ": " + e.getMessage())
                .data(null)
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(ApiBash.LOGOUT)
    public ResponseEntity<CommonResponse<LogoutResponse>> logoutAdminAccount(HttpServletRequest logoutRequest) {
        try {
            LogoutResponse logoutResponse = userService.logout(logoutRequest);
            CommonResponse<LogoutResponse> response = CommonResponse.<LogoutResponse>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.LOGOUT_SUCCESS)
                .data(logoutResponse)
                .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CommonResponse<LogoutResponse> response = CommonResponse.<LogoutResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.LOGOUT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
}

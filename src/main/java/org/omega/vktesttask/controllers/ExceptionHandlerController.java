package org.omega.vktesttask.controllers;

import org.omega.vktesttask.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUAEE() {
        return ResponseEntity.badRequest().body("This user already exists");
    }

    @ExceptionHandler(InvalidLoginOrPasswordException.class)
    public ResponseEntity<?> handleILOPE() {
        return ResponseEntity.badRequest().body("Wrong login or password");
    }

    @ExceptionHandler(EmptyPasswordException.class)
    public ResponseEntity<?> handleEPE() {
        return ResponseEntity.badRequest().body("Password can't be empty");
    }

    @ExceptionHandler(EmptyRoleException.class)
    public ResponseEntity<?> handleERE() {
        return ResponseEntity.badRequest().body("Role can't be empty");
    }

    @ExceptionHandler(EmptyLoginException.class)
    public ResponseEntity<?> handleELE() {
        return ResponseEntity.badRequest().body("Login can't be empty");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUNFE() {
        return ResponseEntity.badRequest().body("Wrong login or password");
    }

}

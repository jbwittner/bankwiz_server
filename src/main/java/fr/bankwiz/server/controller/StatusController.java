package fr.bankwiz.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.server.security.AuthenticationFacade;

@RestController
@RequestMapping("status")
public class StatusController {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/public")
    public ResponseEntity<String> getPublicStatus() {
        return new ResponseEntity<>("Public_status_ok", HttpStatus.OK);
    }

    @GetMapping("/private")
    public ResponseEntity<String> getPrivateStatus() {
        return new ResponseEntity<>("Private_status_ok", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminStatus() {
        return new ResponseEntity<>("Admin_status_ok", HttpStatus.OK);
    }
}

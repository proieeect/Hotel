package hotel.controller;

import hotel.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class MailController {

    private final EmailService emailService;

    @GetMapping("/mail/{mail}")
    public ResponseEntity<String> sendMail(@PathVariable String mail) {
        emailService.sendMail(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

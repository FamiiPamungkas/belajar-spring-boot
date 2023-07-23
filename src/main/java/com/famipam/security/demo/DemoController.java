package com.famipam.security.demo;

import com.famipam.security.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello i'm secured yow.");
    }

    @GetMapping("exception")
    public ResponseEntity<?> testException() {
        throw new NotFoundException("Testing exception");
    }
}

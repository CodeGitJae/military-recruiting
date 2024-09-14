package com.gomilitary.api.apiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gomilitary.api.service.teukgiCodeApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final teukgiCodeApiService teukgiCodeApiService;

     @GetMapping("/test")
     public String helloWorld() {
      teukgiCodeApiService.getApiData();
        return "Hello, World! This is a Gomilitary API.";
     }
    
}
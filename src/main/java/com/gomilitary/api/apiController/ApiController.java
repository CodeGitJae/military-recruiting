package com.gomilitary.api.apiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gomilitary.api.service.ArmyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ArmyService armyService;

     @GetMapping("/test")
     public String helloWorld() {
      armyService.getApiData();
        return "Hello, World! This is a Gomilitary API.";
     }
    
}
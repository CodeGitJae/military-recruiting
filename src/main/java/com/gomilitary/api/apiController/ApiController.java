package com.gomilitary.api.apiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomilitary.api.entity.teukgiCodeEntity;
import com.gomilitary.api.service.teukgiCodeApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
  private final teukgiCodeApiService teukgiCodeApiService;

    @GetMapping("/get-data")
    public ResponseEntity<String> helloWorld() {

      List<teukgiCodeEntity> dataArr = teukgiCodeApiService.getSavedData();
    //  System.out.println("dataArr::::::::::::::::::::::::"+dataArr);
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        String jsonString = objectMapper.writeValueAsString(dataArr);
//        System.out.println("jsonString:::::::::::::::::::::::"+jsonString);
          return new ResponseEntity<>(jsonString, HttpStatus.OK);
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

    }
}
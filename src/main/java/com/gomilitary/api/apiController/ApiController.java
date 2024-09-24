package com.gomilitary.api.apiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.gomilitary.api.entity.teukgiCodeEntity;
import com.gomilitary.api.service.teukgiCodeApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
  private final teukgiCodeApiService teukgiCodeApiService;

    @GetMapping("/get-data")
    public ResponseEntity<List<teukgiCodeEntity>> helloWorld() {

      List<teukgiCodeEntity> dataArr = teukgiCodeApiService.getSavedData();
      System.out.println("dataArr::::::::::::::::::::::::"+dataArr);
      
      return new ResponseEntity<List<teukgiCodeEntity>>(dataArr, HttpStatus.OK); 
    }
}

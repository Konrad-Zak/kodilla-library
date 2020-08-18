package project.kodillalibrary.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class ReaderController {

    @RequestMapping(method = RequestMethod.POST, value = "/readers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto){
        return new ReaderDto(1L,"Adam","N",LocalDate.of(1991,10,20));

    }


}

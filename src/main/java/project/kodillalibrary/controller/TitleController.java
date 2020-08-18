package project.kodillalibrary.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.TitleDto;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class TitleController {
    @RequestMapping(method = RequestMethod.POST, value = "/titles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto){
        return titleDto;
    }
}

package project.kodillalibrary.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.RentDto;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class RentController {

    @RequestMapping(method = RequestMethod.POST, value = "/rents", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentDto createRent(@RequestBody RentDto rentDto){
        return rentDto;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rents/{rentId}")
    public String deleteRent(@PathVariable Long rentId){
        return "Its ok";
    }
}

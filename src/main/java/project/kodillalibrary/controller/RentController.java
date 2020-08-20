package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.RentDto;
import project.kodillalibrary.mapper.RentMapper;
import project.kodillalibrary.repository.RentRepository;
import project.kodillalibrary.service.RentDbService;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class RentController {

    @Autowired
    RentMapper rentMapper;

    @Autowired
    RentDbService rentDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/rents", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentDto createRent(@RequestBody RentDto rentDto){
        return rentMapper.mapToRentDto(rentDbService.saveRent(rentMapper.mapToRent(rentDto)));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/rents/{rentId}")
    public void deleteRent(@PathVariable Long rentId){
        rentDbService.deleteRent(rentId);
    }
}

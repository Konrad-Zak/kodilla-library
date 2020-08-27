package project.kodillalibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.facade.RentFacade;

import java.util.List;

@RestController
@RequestMapping("/v1/rents")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RentController {

    private RentFacade rentFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public RentDto createRent(@RequestParam Long readerId , @RequestParam Long bookId){
        return rentFacade.createRent(readerId,bookId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{rentId}")
    public void deleteRent(@PathVariable Long rentId){
        rentFacade.deleteRent(rentId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET)
    public List<RentDto> getRents(){
        return rentFacade.getAllRents();
    }
}

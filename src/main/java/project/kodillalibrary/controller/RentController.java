package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.facade.RentFacade;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class RentController {

    @Autowired
    private RentFacade rentFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/rents")
    public RentDto createRent(@RequestParam Long readerId , @RequestParam Long bookId){
        return rentFacade.createRent(readerId,bookId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/rents/{rentId}")
    public void deleteRent(@PathVariable Long rentId){
        rentFacade.deleteRent(rentId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/rents" )
    public List<RentDto> getRents(){
        return rentFacade.getAllRents();
    }
}

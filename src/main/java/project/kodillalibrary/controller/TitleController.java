package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.TitleDto;
import project.kodillalibrary.exception.DuplicateTitleException;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.service.TitleDbService;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class TitleController {

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private TitleDbService titleDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/titles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto){
        if(!titleDbService.checkDuplicate(titleDto)) {
            return titleMapper.mapToTitleDto(titleDbService.saveTitle(titleMapper.mapToTitle(titleDto)));
        } else {
            throw new DuplicateTitleException();
        }
    }
}

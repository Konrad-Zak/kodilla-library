package project.kodillalibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.TitleDto;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.service.TitleDbService;

import java.util.List;


@RestController
@RequestMapping("/v1/titles")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TitleController {

    private TitleMapper titleMapper;
    private TitleDbService titleDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto){
            return titleMapper.mapToTitleDto(titleDbService.saveTitle(titleMapper.mapToTitle(titleDto)));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET)
    public List<TitleDto> getTitles(){
        return titleMapper.mapToTitleDtoList(titleDbService.getAllTitles());
    }
}

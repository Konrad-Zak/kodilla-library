package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.service.ReaderDbService;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class ReaderController {

    @Autowired
    ReaderMapper readerMapper;

    @Autowired
    ReaderDbService readerDbService;

    @RequestMapping(method = RequestMethod.POST, value = "/readers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto){
        return readerMapper.mapToReaderDto(readerDbService.saveReader(readerMapper.mapToReader(readerDto)));
    }

}

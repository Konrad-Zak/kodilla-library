package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.service.ReaderDbService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class ReaderController {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private ReaderDbService readerDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/readers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto){
        if(readerDto.getRegistrationDate() == null || readerDto.getRegistrationDate().isAfter(LocalDate.now())){
            readerDto.setRegistrationDate(LocalDate.now());
        }
        return readerMapper.mapToReaderDto(readerDbService.saveReader(readerMapper.mapToReader(readerDto)));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value ="/readers" )
    public List<ReaderDto> getBooks(){
        return readerMapper.mapToReaderDtoList(readerDbService.getAllReaders());
    }

}

package project.kodillalibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.service.ReaderDbService;

import java.util.List;


@RestController
@RequestMapping("/v1/readers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReaderController {

    private ReaderMapper readerMapper;
    private ReaderDbService readerDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto){
        return readerMapper.mapToReaderDto(readerDbService.saveReader(readerMapper.mapToReader(readerDto)));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET)
    public List<ReaderDto> getBooks(){
        return readerMapper.mapToReaderDtoList(readerDbService.getAllReaders());
    }

}

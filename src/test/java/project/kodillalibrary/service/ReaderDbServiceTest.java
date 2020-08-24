package project.kodillalibrary.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReaderDbServiceTest {

    @Autowired
    private ReaderDbService readerDbService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderMapper readerMapper;

    @Test
    public void saveReader(){
        //Given
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo",LocalDate.of(2000,10,21));
        //When
        readerDbService.saveReader(readerMapper.mapToReader(readerDto));
        List<Reader> readerList = readerRepository.findAll();
        //Then
        assertEquals(1,readerList.size());
        //CleanUp
        readerRepository.deleteAll();

    }

    @Test
    public void getReader(){
        //Given
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo",LocalDate.of(2000,10,21));
        Reader reader = readerMapper.mapToReader(readerDto);
        readerRepository.save(reader);
        //When
        Optional<Reader> result = readerDbService.getReader(reader.getId());
        //Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Rambo", result.get().getLastName());
        assertEquals(LocalDate.of(2000,10,21), result.get().getRegistrationDate());
        //CleanUp
        readerRepository.deleteAll();
    }

    @Test
    public void getAllReaders(){
        //Given
        ReaderDto readerDto1 = new ReaderDto(
                "John","Rambo",LocalDate.of(2000,10,21));
        ReaderDto readerDto2 = new ReaderDto(
                "Adam","Smith",LocalDate.of(2010,12,20));
        readerRepository.save(readerMapper.mapToReader(readerDto1));
        readerRepository.save(readerMapper.mapToReader(readerDto2));
        //When
        List<Reader> readerList = readerDbService.getAllReaders();
        //Then
        assertEquals(2,readerList.size());
        assertEquals("John", readerList.get(0).getFirstName());
        assertEquals("Adam", readerList.get(1).getFirstName());
        assertEquals("Rambo", readerList.get(0).getLastName());
        assertEquals("Smith", readerList.get(1).getLastName());
        assertEquals(LocalDate.of(2000,10,21), readerList.get(0).getRegistrationDate());
        assertEquals(LocalDate.of(2010,12,20), readerList.get(1).getRegistrationDate());
        //CleanUp
        readerRepository.deleteAll();
    }

}
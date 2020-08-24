package project.kodillalibrary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.domain.TitleDto;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.repository.TitleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TitleDbServiceTest {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private TitleDbService titleDbService;

    @Autowired
    private TitleMapper titleMapper;

    @Test
    public void saveTitle() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 1990);
        //When
        titleDbService.saveTitle(titleMapper.mapToTitle(titleDto));
        List<Title> titles = titleRepository.findAll();
        //Then
        assertEquals(1, titles.size());
        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    public void getTitle() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 1990);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        //When
        Optional<Title> resultTitle = titleDbService.getTitle(title.getId());
        //Then
        assertTrue(resultTitle.isPresent());
        assertEquals("Test" , resultTitle.get().getTitle());
        assertEquals("Daniel", resultTitle.get().getAuthor());
        assertEquals(1990, resultTitle.get().getYear());
        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    public void checkDuplicate() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel",1990);
        TitleDto titleToCheck = new TitleDto("Test", "Daniel", 1990);
        titleRepository.save(titleMapper.mapToTitle(titleDto));
        //When
        Boolean result = titleDbService.checkDuplicate(titleToCheck);
        //Then
        assertTrue(result);
        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    public void getAllTitles() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel",1990);
        TitleDto titleDto1 = new TitleDto("Test 2", "Daniel 2",1980);
        titleRepository.save(titleMapper.mapToTitle(titleDto));
        titleRepository.save(titleMapper.mapToTitle(titleDto1));
        //When
        List<Title> titles = titleDbService.getAllTitles();
        //Then
        assertEquals(2,titles.size());
        assertEquals("Test", titles.get(0).getTitle());
        assertEquals("Test 2",titles.get(1).getTitle());
        assertEquals("Daniel", titles.get(0).getAuthor());
        assertEquals("Daniel 2", titles.get(1).getAuthor());
        assertEquals(1990, titles.get(0).getYear());
        assertEquals(1980, titles.get(1).getYear());
        //CleanUp
        titleRepository.deleteAll();
    }
}
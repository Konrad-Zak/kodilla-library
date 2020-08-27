package project.kodillalibrary.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.domain.TitleDto;
import project.kodillalibrary.exception.DuplicateTitleException;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.service.TitleDbService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TitleController.class)
public class TitleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TitleDbService titleDbService;

    @MockBean
    private TitleMapper titleMapper;

    @Test
    public void createTitle() throws Exception {
        //Given
        Title title = new Title(1L, "Test", "Daniel", 2000);
        TitleDto titleDto =  new TitleDto(1L,"Test","Daniel",2000);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(titleDto);

        when(titleMapper.mapToTitle(ArgumentMatchers.any(TitleDto.class))).thenReturn(title);
        when(titleDbService.saveTitle(title)).thenReturn(title);
        when(titleMapper.mapToTitleDto(ArgumentMatchers.any(Title.class))).thenReturn(titleDto);

        //When & Then
        mockMvc.perform(post("/v1/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("Test")))
                .andExpect(jsonPath("$.author",is("Daniel")))
                .andExpect(jsonPath("$.year",is(2000)));
    }

    @Test
    public void createTitleThrowException() throws Exception {
        //Given
        Title title = new Title(1L, "Test", "Daniel", 2000);
        TitleDto titleDto =  new TitleDto(1L,"Test","Daniel",2000);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(titleDto);

        when(titleMapper.mapToTitle(ArgumentMatchers.any(TitleDto.class))).thenReturn(title);
        when(titleDbService.saveTitle(title)).thenReturn(title);
        when(titleMapper.mapToTitleDto(ArgumentMatchers.any(Title.class))).thenThrow(new DuplicateTitleException());

        //When & Then
        mockMvc.perform(post("/v1/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test
    public void getTitles() throws Exception {
        //Given
        List<Title> titleList = Arrays.asList(new Title(1L, "Test", "Daniel", 2000),
                new Title(2L, "Test 2", "Daniel 2", 2001));
        List<TitleDto> titleDtoList = Arrays.asList(new TitleDto(1L, "Test", "Daniel", 2000),
                new TitleDto(2L, "Test 2", "Daniel 2", 2001));

        when(titleDbService.getAllTitles()).thenReturn(titleList);
        when(titleMapper.mapToTitleDtoList(ArgumentMatchers.anyList())).thenReturn(titleDtoList);

        //When & Then
        mockMvc.perform(get("/v1/titles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test")))
                .andExpect(jsonPath("$[0].author", is("Daniel")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test 2")))
                .andExpect(jsonPath("$[1].author", is("Daniel 2")))
                .andExpect(jsonPath("$[1].year", is(2001)));
    }
}
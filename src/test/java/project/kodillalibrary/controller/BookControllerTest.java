package project.kodillalibrary.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.facade.BookFacade;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookFacade bookFacade;

    @Test
    public void createBook() throws Exception {
        //Given
        BookDto bookDto = new BookDto(
                1L,new Title(1L,"Test","Daniel",2000), Status.AVAILABLE);
        when(bookFacade.createBook(1L)).thenReturn(bookDto);

        //When & Then
        mockMvc.perform(post("/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title.id", is(1)))
                .andExpect(jsonPath("$.title.title", is("Test")))
                .andExpect(jsonPath("$.title.author", is("Daniel")))
                .andExpect(jsonPath("$.title.year",is(2000)))
                .andExpect(jsonPath("$.status", is(Status.AVAILABLE.toString())));

    }

    @Test
    public void updateBookStatus() throws Exception {
        //Given
        BookDto bookDto = new BookDto(
                1L,new Title(1L,"Test","Daniel",2000), Status.RESERVED);
        when(bookFacade.updateBook(1L,"RESERVED")).thenReturn(bookDto);

        //When & Then
        mockMvc.perform(put("/v1/books/1/RESERVED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title.id", is(1)))
                .andExpect(jsonPath("$.title.title", is("Test")))
                .andExpect(jsonPath("$.title.author", is("Daniel")))
                .andExpect(jsonPath("$.title.year",is(2000)))
                .andExpect(jsonPath("$.status", is(Status.RESERVED.toString())));
    }

    @Test
    public void getQuantityOfBooksAvailableStatusByTitle() throws Exception {
        //Given
        when(bookFacade.getQuantityOfBooksStatusAvailableByTitle("Java")).thenReturn(3);

        //When & Then
        mockMvc.perform(get("/v1/books/Java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().string("3"));
    }

    @Test
    public void getBooks() throws Exception {
        //Given
        List<BookDto> books = Arrays.asList(
                new BookDto(1L,new Title(1L,"Test","Daniel",2000), Status.RESERVED),
                new BookDto(2L, new Title(2L,"Test 2","Daniel 2",2001), Status.AVAILABLE));

        when(bookFacade.getAllBooks()).thenReturn(books);

        //When & Then
        mockMvc.perform(get("/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].title.id", is(1)))
                .andExpect(jsonPath("$[0].title.title", is("Test")))
                .andExpect(jsonPath("$[0].title.author", is("Daniel")))
                .andExpect(jsonPath("$[0].title.year",is(2000)))
                .andExpect(jsonPath("$[0].status", is(Status.RESERVED.toString())))
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].title.id", is(2)))
                .andExpect(jsonPath("$[1].title.title", is("Test 2")))
                .andExpect(jsonPath("$[1].title.author", is("Daniel 2")))
                .andExpect(jsonPath("$[1].title.year",is(2001)))
                .andExpect(jsonPath("$[1].status", is(Status.AVAILABLE.toString())));
    }

    @Test
    public void getStatusList() throws Exception {
        //Given
        List<Status> statusList = Arrays.asList(Status.values());
        when(bookFacade.getAllStatus()).thenReturn(statusList);

        //When & Then
        String statusResponse = "[\"AVAILABLE\",\"INACCESSIBLE\",\"RESERVED\",\"BLOCKED\"]";
        mockMvc.perform(get("/v1/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().string(statusResponse));
    }
}
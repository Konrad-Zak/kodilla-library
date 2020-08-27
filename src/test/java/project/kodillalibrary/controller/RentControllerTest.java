package project.kodillalibrary.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.facade.RentFacade;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RentController.class)
public class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentFacade rentFacade;

    @Test
    public void createRent() throws Exception {
        //Given
        Title title = new Title(1L,"Test","Daniel",2000);
        Book book = new Book(1L, title, Status.AVAILABLE);
        Reader reader = new Reader(1L, "John", "Rambo", LocalDate.now());
        RentDto rentDto = new RentDto(1L, book, reader, LocalDate.now(), LocalDate.now().plusDays(14));

        when(rentFacade.createRent(1L,1L)).thenReturn(rentDto);

        //When & Then
        mockMvc.perform(post("/v1/rents?readerId=1&bookId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.book.id", is(1)))
                .andExpect(jsonPath("$.book.title.id",is(1)))
                .andExpect(jsonPath("$.book.title.title",is("Test")))
                .andExpect(jsonPath("$.book.title.author",is("Daniel")))
                .andExpect(jsonPath("$.book.title.year",is(2000)))
                .andExpect(jsonPath("$.book.status",is(Status.AVAILABLE.toString())))
                .andExpect(jsonPath("$.reader.id",is(1)))
                .andExpect(jsonPath("$.reader.firstName",is("John")))
                .andExpect(jsonPath("$.reader.lastName",is("Rambo")))
                .andExpect(jsonPath("$.reader.registrationDate",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.rent",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.deliver",is(LocalDate.now().plusDays(14).toString())));
    }

    @Test
    public void deleteRent() throws Exception {
        //When & Then
        mockMvc.perform(delete("/v1/rents/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRents() throws Exception {
        //Given
        Title title = new Title(1L,"Test","Daniel",2000);
        Title title1 = new Title(2L,"Test 2","Daniel 2",2001);
        Book book = new Book(1L, title, Status.AVAILABLE);
        Book book1 = new Book(2L, title1, Status.AVAILABLE);
        Reader reader = new Reader(1L, "John", "Rambo", LocalDate.now());
        RentDto rentDto = new RentDto(1L, book, reader, LocalDate.now(), LocalDate.now().plusDays(14));
        RentDto rentDto1 = new RentDto(2L, book1, reader, LocalDate.now(), LocalDate.now().plusDays(14));
        List<RentDto> rentDtoList = Arrays.asList(rentDto,rentDto1);

        when(rentFacade.getAllRents()).thenReturn(rentDtoList);

        //When & Then
        mockMvc.perform(get("/v1/rents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].book.id", is(1)))
                .andExpect(jsonPath("$[0].book.title.id",is(1)))
                .andExpect(jsonPath("$[0].book.title.title",is("Test")))
                .andExpect(jsonPath("$[0].book.title.author",is("Daniel")))
                .andExpect(jsonPath("$[0].book.title.year",is(2000)))
                .andExpect(jsonPath("$[0].book.status",is(Status.AVAILABLE.toString())))
                .andExpect(jsonPath("$[0].reader.id",is(1)))
                .andExpect(jsonPath("$[0].reader.firstName",is("John")))
                .andExpect(jsonPath("$[0].reader.lastName",is("Rambo")))
                .andExpect(jsonPath("$[0].reader.registrationDate",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].rent",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].deliver",is(LocalDate.now().plusDays(14).toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].book.id", is(2)))
                .andExpect(jsonPath("$[1].book.title.id",is(2)))
                .andExpect(jsonPath("$[1].book.title.title",is("Test 2")))
                .andExpect(jsonPath("$[1].book.title.author",is("Daniel 2")))
                .andExpect(jsonPath("$[1].book.title.year",is(2001)))
                .andExpect(jsonPath("$[1].book.status",is(Status.AVAILABLE.toString())))
                .andExpect(jsonPath("$[1].reader.id",is(1)))
                .andExpect(jsonPath("$[1].reader.firstName",is("John")))
                .andExpect(jsonPath("$[1].reader.lastName",is("Rambo")))
                .andExpect(jsonPath("$[1].reader.registrationDate",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].rent",is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].deliver",is(LocalDate.now().plusDays(14).toString())));

    }
}
package project.kodillalibrary.controller;

import com.google.gson.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.service.ReaderDbService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReaderController.class)
public class ReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private ReaderDbService readerDbService;

    @Test
    public void createReader() throws Exception {
        //Given
        Reader reader = new Reader(1L,"John","Rambo", LocalDate.now());
        ReaderDto readerDto = new ReaderDto(1L,"John","Rambo", LocalDate.now());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.toString());
                    }
                })
                .create();
        String jsonContent = gson.toJson(readerDto);
        
        when(readerMapper.mapToReader(ArgumentMatchers.any(ReaderDto.class))).thenReturn(reader);
        when(readerDbService.saveReader(reader)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(ArgumentMatchers.any(Reader.class))).thenReturn(readerDto);

        //When & Then
        mockMvc.perform(post("/v1/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Rambo")))
                .andExpect(jsonPath("$.registrationDate", is(LocalDate.now().toString())));
    }

    @Test
    public void getBooks() throws Exception {
        //Given
        List<Reader> readerList = Arrays.asList(new Reader(1L,"John","Rambo",LocalDate.now()),
                new Reader(2L,"John 2","Rambo 2",LocalDate.now()));
        List<ReaderDto> readerListDto = Arrays.asList(new ReaderDto(1L,"John","Rambo",LocalDate.now()),
                new ReaderDto(2L,"John 2","Rambo 2",LocalDate.now()));

        when(readerDbService.getAllReaders()).thenReturn(readerList);
        when(readerMapper.mapToReaderDtoList(anyList())).thenReturn(readerListDto);

        //When & Then
        mockMvc.perform(get("/v1/readers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Rambo")))
                .andExpect(jsonPath("$[0].registrationDate", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("John 2")))
                .andExpect(jsonPath("$[1].lastName", is("Rambo 2")))
                .andExpect(jsonPath("$[1].registrationDate", is(LocalDate.now().toString())));
    }
}
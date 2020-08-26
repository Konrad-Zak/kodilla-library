package project.kodillalibrary.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReaderDbService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderDbService.class);
    private ReaderRepository readerRepository;

    public Reader saveReader(final Reader reader){
        if(reader.getRegistrationDate().isBefore(LocalDate.now().plusDays(1))){
            LOGGER.info("Create new reader of id: " + reader.getId());
            return readerRepository.save(reader);
        } else {
            LOGGER.error("Not correct date is after: " + LocalDate.now());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Reader> getReader(Long readerId){
        LOGGER.debug("Request: get reader: " + readerId);
        return readerRepository.findById(readerId);
    }

    public List<Reader> getAllReaders(){
        LOGGER.debug("Request: get all readers");
        return readerRepository.findAll();
    }
}

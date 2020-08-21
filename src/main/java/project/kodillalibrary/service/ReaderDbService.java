package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.repository.ReaderRepository;

import java.util.Optional;

@Service
public class ReaderDbService {

    @Autowired
    private ReaderRepository readerRepository;

    public Reader saveReader(final Reader reader){
        return readerRepository.save(reader);
    }

    public Optional<Reader> getReader(Long id){
        return readerRepository.findById(id);
    }
}

package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;

import java.time.LocalDate;

@Component
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto){
        if(readerDto.getRegistrationDate() == null || readerDto.getRegistrationDate().isAfter(LocalDate.now())){
            readerDto.setRegistrationDate(LocalDate.now());
        }
        return new Reader(
                readerDto.getId(),readerDto.getFirstName(),readerDto.getLastName(),readerDto.getRegistrationDate()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader){
        return new ReaderDto(
                reader.getId(),reader.getFirstName(),reader.getLastName(),reader.getRegistrationDate()
        );
    }
}

package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Reader;
import project.kodillalibrary.domain.ReaderDto;
import project.kodillalibrary.domain.RentDto;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto){
        return new Reader(
                readerDto.getId(),readerDto.getFirstName(),readerDto.getLastName(),readerDto.getRegistrationDate()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader){
        return new ReaderDto(
                reader.getId(),reader.getFirstName(),reader.getLastName(),reader.getRegistrationDate()
        );
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readers){
        return readers.stream()
                .map(reader -> new ReaderDto(
                        reader.getId(),reader.getFirstName(),reader.getLastName(),reader.getRegistrationDate()))
                .collect(Collectors.toList());
    }
}

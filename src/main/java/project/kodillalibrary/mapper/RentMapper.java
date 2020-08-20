package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Rent;
import project.kodillalibrary.domain.RentDto;

import java.time.LocalDate;

@Component
public class RentMapper {

    private final static LocalDate DATE = LocalDate.now();
    private final static int DURATION_OF_RENT = 7;

    public Rent mapToRent(final RentDto rentDto){
        if(rentDto.getRent() == null || rentDto.getRent().isBefore(DATE)){
            rentDto.setRent(LocalDate.now());
        }
        if(rentDto.getDeliver() == null || rentDto.getDeliver().isBefore(DATE.plusDays(DURATION_OF_RENT))){
            rentDto.setDeliver(DATE.plusDays(DURATION_OF_RENT));
        }
        return new Rent(
                rentDto.getId(),rentDto.getBook(),rentDto.getReader(),rentDto.getRent(),rentDto.getDeliver()
        );
    }

    public RentDto mapToRentDto(final Rent rent){
        return new RentDto(
                rent.getId(),rent.getBook(),rent.getReader(),rent.getRent(),rent.getDeliver()
        );
    }
}

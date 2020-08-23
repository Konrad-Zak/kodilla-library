package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Rent;
import project.kodillalibrary.domain.RentDto;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class RentMapper {

    public Rent mapToRent(final RentDto rentDto){
        return new Rent(
                rentDto.getId(),rentDto.getBook(),rentDto.getReader(),rentDto.getRent(),rentDto.getDeliver()
        );
    }

    public RentDto mapToRentDto(final Rent rent){
        return new RentDto(
                rent.getId(),rent.getBook(),rent.getReader(),rent.getRent(),rent.getDeliver()
        );
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rents){
        return rents.stream()
                .map(rent -> new RentDto(rent.getId(),rent.getBook(),rent.getReader(),rent.getRent(),rent.getDeliver()))
                .collect(Collectors.toList());
    }
}

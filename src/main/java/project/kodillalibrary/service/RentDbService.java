package project.kodillalibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Rent;
import project.kodillalibrary.repository.RentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentDbService {

    private RentRepository rentRepository;

    public Rent saveRent(final Rent rent){
        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(final Long id){
        return rentRepository.findById(id);
    }

    public void deleteRent(final Long id){
        rentRepository.deleteById(id);
    }

    public List<Rent> getAllRents(){
        return rentRepository.findAll();
    }
}

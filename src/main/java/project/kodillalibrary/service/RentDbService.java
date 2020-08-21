package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Rent;
import project.kodillalibrary.repository.RentRepository;

import java.util.Optional;

@Service
public class RentDbService {

    @Autowired
    RentRepository rentRepository;

    public Rent saveRent(final Rent rent){
        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(final Long id){
        return rentRepository.findById(id);
    }

    public void deleteRent(final Long id){
        rentRepository.deleteById(id);
    }

}

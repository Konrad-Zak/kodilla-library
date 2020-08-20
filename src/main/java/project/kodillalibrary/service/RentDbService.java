package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Rent;
import project.kodillalibrary.repository.RentRepository;

@Service
public class RentDbService {

    @Autowired
    RentRepository rentRepository;

    public Rent saveRent(Rent rent){
        return rentRepository.save(rent);
    }

    public void deleteRent(Long id){
        rentRepository.deleteById(id);
    }

}

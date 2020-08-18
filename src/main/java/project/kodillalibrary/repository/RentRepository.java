package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Rent;

public interface RentRepository extends CrudRepository<Rent,Long> {
    @Override
    Rent save (Rent rent);
}

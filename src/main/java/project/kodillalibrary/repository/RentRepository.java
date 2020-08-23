package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Rent;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends CrudRepository<Rent,Long> {
    @Override
    Rent save (Rent rent);

    @Override
    Optional<Rent> findById(Long id);

    @Override
    List<Rent> findAll();
}

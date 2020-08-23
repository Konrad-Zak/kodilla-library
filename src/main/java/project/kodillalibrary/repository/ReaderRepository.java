package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Reader;

import java.util.List;
import java.util.Optional;

public interface ReaderRepository extends CrudRepository<Reader,Long> {
    @Override
    Reader save(Reader reader);

    @Override
    Optional<Reader> findById(Long id);

    @Override
    List<Reader> findAll();
}

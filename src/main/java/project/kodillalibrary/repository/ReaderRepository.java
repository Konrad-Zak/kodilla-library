package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Reader;

public interface ReaderRepository extends CrudRepository<Reader,Long> {
    @Override
    Reader save(Reader reader);
}

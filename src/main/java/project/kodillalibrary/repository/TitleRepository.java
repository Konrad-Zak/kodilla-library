package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Title;

import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title,Long> {
    @Override
    Title save(Title title);

    @Override
    Optional<Title> findById(Long id);

    Boolean existsByAuthorAndTitleAndYear(String author, String title, Integer year);
}

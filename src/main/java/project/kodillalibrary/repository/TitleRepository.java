package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Title;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title,Long> {
    @Override
    Title save(Title title);

    @Override
    Optional<Title> findById(Long id);

    @Override
    List<Title> findAll();

    Boolean existsByAuthorAndTitleAndYear(String author, String title, Integer year);
}

package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Title;

public interface TitleRepository extends CrudRepository<Title,Long> {
    @Override
    Title save(Title title);

    Boolean existsByAuthorAndTitleAndYear(String author, String title, Integer year);
}

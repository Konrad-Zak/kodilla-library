package project.kodillalibrary.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.exception.DuplicateTitleException;
import project.kodillalibrary.repository.TitleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TitleDbService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TitleDbService.class);
    private TitleRepository titleRepository;

    public Title saveTitle(final Title title){
        if(!checkDuplicate(title)){
            LOGGER.info("Create new title of id: " + title.getId());
            return titleRepository.save(title);
        } else {
            LOGGER.error("Title is available on data bases: " + title.getId());
            throw new DuplicateTitleException();
        }
    }

    public Optional<Title> getTitle(final Long titleId){
        LOGGER.debug("Request: get title: " + titleId);
        return titleRepository.findById(titleId);
    }

    public List<Title> getAllTitles(){
        LOGGER.debug("Request: get all titles");
        return titleRepository.findAll();
    }

    public Boolean checkDuplicate(final Title title){
        return titleRepository.existsByAuthorAndTitleAndYear(
                title.getAuthor(),title.getTitle(),title.getYear());
    }
}

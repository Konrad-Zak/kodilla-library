package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.domain.TitleDto;
import project.kodillalibrary.repository.TitleRepository;

@Service
public class TitleDbService {

    @Autowired
    private TitleRepository titleRepository;

    public Title saveTitle(final Title title){
        return titleRepository.save(title);
    }

    public Boolean checkDuplicate(final TitleDto titleDto){
        return titleRepository.existsByAuthorAndTitleAndYear(
                titleDto.getAuthor(),titleDto.getTitle(),titleDto.getYear());
    }
}
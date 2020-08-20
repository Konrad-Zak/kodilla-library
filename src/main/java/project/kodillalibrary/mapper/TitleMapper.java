package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.domain.TitleDto;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto){
        return new Title(titleDto.getId(),titleDto.getTitle(),titleDto.getAuthor(),titleDto.getYear());
    }

    public TitleDto mapToTitleDto(final Title title){
        return new TitleDto(title.getId(),title.getTitle(),title.getAuthor(),title.getYear());
    }
}

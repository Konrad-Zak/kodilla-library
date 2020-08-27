package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.domain.TitleDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto){
        return new Title(titleDto.getId(),titleDto.getTitle(),titleDto.getAuthor(),titleDto.getYear());
    }

    public TitleDto mapToTitleDto(final Title title){
        return new TitleDto(title.getId(),title.getTitle(),title.getAuthor(),title.getYear());
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titles ){
        return titles.stream()
                .map(title -> new TitleDto(title.getId(),title.getTitle(),title.getAuthor(),title.getYear()))
                .collect(Collectors.toList());
    }
}

package project.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private Long id;
    private String title;
    private String author;
    private int year;

    public TitleDto(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}

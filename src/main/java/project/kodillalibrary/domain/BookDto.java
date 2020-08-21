package project.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDto {
    private Long id;
    private Title title;
    private Status status;

    public BookDto(Title title, Status status) {
        this.title = title;
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

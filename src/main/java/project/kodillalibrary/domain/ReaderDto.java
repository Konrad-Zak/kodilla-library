package project.kodillalibrary.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    public ReaderDto(String firstName, String lastName, LocalDate registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
    }

}

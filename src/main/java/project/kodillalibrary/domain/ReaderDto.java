package project.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class ReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate registrationDate;

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ReaderDto(String firstName, String lastName, LocalDate registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
    }
}

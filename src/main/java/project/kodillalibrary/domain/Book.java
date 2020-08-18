package project.kodillalibrary.domain;

import javax.persistence.*;

@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn (name = "TITLE_ID")
    private Title title;

    @Column(name = "STATUS")
    private String status;
}

package project.kodillalibrary.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TITLE")
    private Title titleList ;

    @Column(name = "STATUS")
    private String status;
}

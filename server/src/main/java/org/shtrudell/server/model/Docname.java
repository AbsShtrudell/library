package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "docname")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Docname {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Title", length = 50)
    private String title;

    @Column(name = "Edition")
    private Integer edition;

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AuthorID", nullable = false)
    private Author author;

    @Column(name = "ISBN", nullable = false)
    private Integer isbn;
}
package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

import java.time.LocalDate;

@Entity
@Table(name = "docname")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Docname implements Identifiable {
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "AuthorID", nullable = false)
    @ToString.Exclude
    private Author author;

    @Column(name = "ISBN", nullable = false)
    private Integer isbn;
}
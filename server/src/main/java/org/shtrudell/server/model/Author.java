package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Author implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "Surname", length = 50)
    private String surname;

    @Column(name = "Patronymic", length = 50)
    private String patronymic;
}
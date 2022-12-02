package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Document implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NameID", nullable = false)
    @ToString.Exclude
    private Docname name;
}
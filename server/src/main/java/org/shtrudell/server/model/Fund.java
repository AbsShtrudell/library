package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fund")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Fund implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 50)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(targetEntity = Document.class, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "document_fund",
            joinColumns = { @JoinColumn(name = "FundID", referencedColumnName = "ID")},
            inverseJoinColumns = { @JoinColumn(name = "DocumentID", referencedColumnName = "ID") })
    private Set<Document> documents = new HashSet<>();
}
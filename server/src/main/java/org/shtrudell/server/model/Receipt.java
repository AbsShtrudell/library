package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "receipt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Receipt implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FundID", nullable = false)
    @ToString.Exclude
    private Fund fund;

    @OneToMany(targetEntity=Document.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ReceiptID", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Document> documents;
}
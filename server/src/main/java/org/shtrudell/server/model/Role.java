package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Role implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 50)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(targetEntity = Fund.class, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_fund",
            joinColumns = { @JoinColumn(name = "RoleID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "FundID", referencedColumnName = "ID")})
    private List<Fund> funds = new ArrayList<>();
}
package org.shtrudell.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.shtrudell.server.integration.Identifiable;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "Surname", length = 50)
    private String surname;

    @Column(name = "Pass", nullable = false)
    private byte[] pass;

    @Column(name = "Login", nullable = false, length = 50)
    private String login;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;
}
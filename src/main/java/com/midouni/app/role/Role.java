package com.midouni.app.role;

import com.midouni.app.common.BaseEntity;
import com.midouni.app.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "ROLES")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue
    private String id;

    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

package com.midouni.app.role;

import com.midouni.app.common.BaseEntity;
import com.midouni.app.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ROLES")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue
    private String id;

    @Column(name = "ROLE_NAME", nullable = false, unique = true, length = 50)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

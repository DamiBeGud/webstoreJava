package com.webstore.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "users")

@Data
@NoArgsConstructor

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

    private List<Roles> roles = new ArrayList<>();

}

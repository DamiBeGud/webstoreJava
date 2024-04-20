package com.webstore.models;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "roles")

@Data
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Name;
}

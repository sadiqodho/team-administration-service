package com.team.administration.models;

import com.team.administration.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Position position;
    @URL
    @Column(name = "github_profile_url", unique = true)
    private String gitHubProfileURL;
}
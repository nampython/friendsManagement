package org.FriendsManagement.model.friends;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "friendsmanagement", catalog = "")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "email")
    private String email;
}

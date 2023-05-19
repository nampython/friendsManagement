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
@Table(name = "friendship", schema = "friendmanagement", catalog = "")
public class FriendshipEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "friendship_id")
    private int friendshipId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "friend_id")
    private int friendId;
    @Basic
    @Column(name = "status")
    private String status;

}

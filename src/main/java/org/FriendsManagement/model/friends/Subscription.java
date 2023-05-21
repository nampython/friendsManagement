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
@Table(name = "subscription", schema = "friendsmanagement", catalog = "")
public class Subscription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subscription_id")
    private int subscriptionId;
    @Basic
    @Column(name = "subscriber_id")
    private int subscriberId;
    @Basic
    @Column(name = "target_id")
    private int targetId;
}

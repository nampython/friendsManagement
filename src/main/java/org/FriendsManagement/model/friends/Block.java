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
@Table(name = "block", schema = "friendsmanagement", catalog = "")
public class Block {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "block_id")
    private int blockId;
    @Basic
    @Column(name = "blocker_id")
    private int blockerId;
    @Basic
    @Column(name = "blocked_id")
    private int blockedId;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getBlockerId() {
        return blockerId;
    }

    public void setBlockerId(int blockerId) {
        this.blockerId = blockerId;
    }

    public int getBlockedId() {
        return blockedId;
    }

    public void setBlockedId(int blockedId) {
        this.blockedId = blockedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block that = (Block) o;
        return blockId == that.blockId && blockerId == that.blockerId && blockedId == that.blockedId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, blockerId, blockedId);
    }
}

package com.example.imcommunity.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String content;
    private Date gmt_create;
    private Date gmt_modified;
    @ManyToOne(targetEntity = Comment.class, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reply reply = (Reply) o;

        return Objects.equals(id, reply.id);
    }

    @Override
    public int hashCode() {
        return 2026185064;
    }
}

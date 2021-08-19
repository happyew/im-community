package com.example.imcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmtCreated;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmtModified;
    @ManyToOne(targetEntity = Comment.class, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;
    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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

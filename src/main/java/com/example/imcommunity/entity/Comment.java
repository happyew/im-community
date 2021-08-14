package com.example.imcommunity.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String content;
    private Long likeCount;
    private Date gmt_create;
    private Date gmt_modified;
    @ManyToOne(targetEntity = Question.class, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reply> replies = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;

        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return 860659860;
    }
}

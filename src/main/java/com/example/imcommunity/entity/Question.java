package com.example.imcommunity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 问题实体类
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String title;
    private String description;
    private String tag;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmtCreate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmtModified;
    private Long viewCount = 0L;
    private Long commentCount = 0L;
    private Long likeCount = 0L;
    @ManyToOne(targetEntity = GiteeUser.class, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "gitee_user_id", referencedColumnName = "id")
    private GiteeUser giteeUser;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;

        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return 1344421622;
    }
}

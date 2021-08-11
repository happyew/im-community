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
import java.util.UUID;

/**
 * Gitee用户信息实体类
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GiteeUser {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String accountId;
    private String name;
    @Column(unique = true)
    private String token = UUID.randomUUID().toString();
    private String avatarUrl;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmt_create;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date gmt_modified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiteeUser giteeUser = (GiteeUser) o;

        return Objects.equals(id, giteeUser.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}

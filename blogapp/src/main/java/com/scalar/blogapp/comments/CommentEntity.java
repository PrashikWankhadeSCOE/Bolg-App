package com.scalar.blogapp.comments;


import com.scalar.blogapp.article.ArticleEntity;
import com.scalar.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.lang.Nullable;
import java.util.Date;

@Entity(name = "comments")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private long id;

    @Nullable
    private String title;

    @NonNull
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="articleId",nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name="authorId",nullable = false)
    private UserEntity author;
}

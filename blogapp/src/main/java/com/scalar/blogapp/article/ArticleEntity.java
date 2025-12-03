package com.scalar.blogapp.article;


import com.scalar.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import java.util.Date;

@Entity(name = "article")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String slug;

    @Nullable
    private String subtitle;

    @NonNull
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="authorId",nullable = false)
    private UserEntity author;

    //Todo: Add Tags
}

package com.scalar.blogapp.article;

import com.scalar.blogapp.article.dtos.CreateArticleRequest;
import com.scalar.blogapp.article.dtos.UpdateArticleRequest;
import com.scalar.blogapp.users.UsersRepository;
import com.scalar.blogapp.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private UsersRepository usersRepository;

    public ArticleService(ArticleRepository articleRepository,UsersRepository usersRepository) {
        this.articleRepository = articleRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles(){
        return articleRepository.findAll();
    }


    public ArticleEntity getArticleBySlug(String slug){
        var article = articleRepository.findBySlug(slug);
        if(article == null){
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a,Long authorId){

        var author = this.usersRepository.findById(authorId).orElseThrow(()-> new UsersService.UserNotFoundException(authorId));

        return articleRepository.save(ArticleEntity.builder()
                .title(a.getTitle())
                        .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-")) //Todo : Create a proper sluggification function
                .body(a.getBody())
                        .subtitle(a.getSubtitle())
                        .author(author)
                .build());
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a){
        var article = articleRepository.findById(articleId).orElseThrow(()-> new ArticleNotFoundException(articleId));

        if(a.getTitle() != null){
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }
        if(a.getSubtitle() != null){
            article.setSubtitle(a.getSubtitle());
        }
        if(a.getBody() != null){
            article.setBody(a.getBody());
        }

        return articleRepository.save(article);
    }

    public static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article" + slug + "not found");
        }

        public ArticleNotFoundException(Long articleId){
            super("Article wit article Id : " + articleId + " not found ");
        }
    }
}

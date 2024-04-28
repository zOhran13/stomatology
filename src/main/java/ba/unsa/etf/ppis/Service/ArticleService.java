package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.Exception.ArticleNotFoundException;
import ba.unsa.etf.ppis.Model.*;
import ba.unsa.etf.ppis.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article getArticleById(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticleById(String id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        articleRepository.deleteById(id);
    }

    public boolean existsArticleById(String id) {
        return articleRepository.existsById(id);
    }

    public Article updateArticle(Article updatedArticle) {
        return articleRepository.save(updatedArticle);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> findArticlesByAuthor(String authorName) {
        return articleRepository.findByAuthor(authorName);
    }

    public List<Article> findArticlesByKeyword(String keyword) {
        return articleRepository.findByKeywordInTitle(keyword);
    }
}
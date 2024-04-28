package ba.unsa.etf.ppis.Controller;

//import io.swagger.annotations.Api;
import ba.unsa.etf.ppis.Exception.*;
import ba.unsa.etf.ppis.Model.*;
import ba.unsa.etf.ppis.Service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/articles")
@RefreshScope
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getArticleById(@PathVariable String id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        } catch (ArticleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", ex.getMessage()));
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ErrorResponse> deleteArticleById(@PathVariable String id) {
        try {
            articleService.deleteArticleById(id);
            return ResponseEntity.ok(new ErrorResponse("No", "Article with ID " + id + " has been deleted successfully"));
        } catch (ArticleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", ex.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable String id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
        try {
            if (!articleService.existsArticleById(id)) {
                throw new ArticleNotFoundException(id);
            }

            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                bindingResult.getAllErrors().forEach(error -> {
                    errorMessage.append(error.getDefaultMessage()).append("");
                });
                return ResponseEntity.badRequest().body(new ErrorResponse("validation", errorMessage.toString()));
            }

            updatedArticle.setId(id);
            Article savedArticle = articleService.updateArticle(updatedArticle);
            return ResponseEntity.ok(savedArticle);
        } catch (ArticleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("validation", ex.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createArticle(@RequestBody @Valid Article article, BindingResult bindingResult) {
        try {

                if (bindingResult.hasErrors()) {
                    StringBuilder errorMessage = new StringBuilder();
                    bindingResult.getAllErrors().forEach(error -> {
                        errorMessage.append(error.getDefaultMessage()).append("");
                    });
                    return ResponseEntity.badRequest().body(new ErrorResponse("validation", errorMessage.toString()));
                }

                Article savedArticle = articleService.createArticle(article);
                return ResponseEntity.ok(new ErrorResponse("No", "Article added successfully!"));

        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Not found", "Psychologist (author) not found: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<Map<String, Object>> articlesWithPsychologist = new ArrayList<>();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articlesWithPsychologist);
    }

    @GetMapping("/byKeyword/{keyword}")
    public ResponseEntity<?> getArticlesByKeyword(@PathVariable String keyword) {
        List<Article> articles = articleService.findArticlesByKeyword(keyword);
        Map<String, Object> response = new HashMap<>();
        if (articles.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("No data", "No articles found containing keyword: " + keyword));
        }
        response.put("articles", articles);
        return ResponseEntity.ok(response);
    }

    // ----------- Stare metode zbog testiranja -----------
    @GetMapping("/allArticles")
    public ResponseEntity<List<Article>> getAllArticlesWithoutAuthors() {
        List<Article> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articles);
        }
    }

    @PostMapping("/addNew")
    public ResponseEntity<?> createArticleOld(@RequestBody @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError titleError = bindingResult.getFieldError("title");
            if (titleError != null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "validation");
                errorResponse.put("message", titleError.getDefaultMessage());
                return ResponseEntity.badRequest().body(errorResponse);
            }
            return ResponseEntity.badRequest().body("Invalid data: " + bindingResult.getAllErrors());
        }

        Article savedArticle = articleService.createArticle(article);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Article added successfully");
        return ResponseEntity.ok(response);
    }
}


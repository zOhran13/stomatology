package ba.unsa.etf.ppis;

import ba.unsa.etf.ppis.Model.*;
import ba.unsa.etf.ppis.Repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
// @EnableEurekaClient
public class StomatologyApplication implements CommandLineRunner {

	@Autowired
	private ArticleRepository articleRepo;

	@Autowired
	private TextRepository textRepo;

	@Autowired
	private VideoRepository videoRepo;

	@Autowired
	private ImageRepository imageRepo;

	@Override
	public void run(String... args) throws Exception {
		// prvi clanak
		/*Article article = new Article();
		article.setId("aaaaaaaa");
		article.setAuthor("9d0d4a57-8623-4686-9d2f-06c2640c78c8");
		article.setTitle("Ovo je PPIS projekat.");

		Text textSection = new Text();
		textSection.setContent("Ovo je tekst.");

		Video videoSection = new Video();
		videoSection.setVideoUrl("Ovo je video.");

		Image imageSection = new Image();
		imageSection.setImageUrl("Ovo je slika.");

		article.setText(textSection);
		article.setImage(imageSection);
		article.setVideo(videoSection);

		articleRepo.save(article);

		textSection.setArticle(article);
		textRepo.save(textSection);
		videoSection.setArticle(article);
		videoRepo.save(videoSection);
		imageSection.setArticle(article);
		imageRepo.save(imageSection);
*/
		// drugi clanak
		Article article2 = new Article();
		article2.setAuthor("0dbac29d-a95c-42cd-bc0d-6809cad9e4fb");
		article2.setTitle("Drugi clanak.");

		Text textSection2 = new Text();
		textSection2.setContent("Jos jedan tekst.");

		Video videoSection2 = new Video();
		videoSection2.setVideoUrl("Jos jedan video.");

		Image imageSection2 = new Image();
		imageSection2.setImageUrl("Jos jedna slika.");

		article2.setText(textSection2);
		article2.setImage(imageSection2);
		article2.setVideo(videoSection2);

		articleRepo.save(article2);

		textSection2.setArticle(article2);
		textRepo.save(textSection2);
		videoSection2.setArticle(article2);
		videoRepo.save(videoSection2);
		imageSection2.setArticle(article2);
		imageRepo.save(imageSection2);

	}

	public static void main(String[] args) {
		SpringApplication.run(StomatologyApplication.class, args);
	}

}

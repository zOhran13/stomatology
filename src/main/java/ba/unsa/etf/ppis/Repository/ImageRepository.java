package ba.unsa.etf.ppis.Repository;


import ba.unsa.etf.ppis.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}

package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, String> {
}

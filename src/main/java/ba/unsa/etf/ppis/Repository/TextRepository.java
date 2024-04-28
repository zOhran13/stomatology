package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, String> {
}

package at.ac.tuwien.ase.repository;

import at.ac.tuwien.ase.domain.ViennaLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Michael on 16.06.2017.
 */
public interface ViennaLineRepository extends JpaRepository<ViennaLine, String> {

    ViennaLine findByName(String name);
}

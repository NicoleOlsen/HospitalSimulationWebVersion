package hospital;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * JpaRepository supports:
 * Creating new instances
 * Updating existing ones
 * Deleting
 * Finding (one, all, by simple or complex properties)
 */

public interface SimulationRepository extends JpaRepository<Simulation, Long> {

}
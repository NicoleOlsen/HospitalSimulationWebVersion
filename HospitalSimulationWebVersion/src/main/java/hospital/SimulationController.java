package hospital; 

import lombok.Data;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * RUN
 * curl -v localhost:8080/simulations
 * curl -v localhost:8080/simulations/1
 * curl -X POST localhost:8080/simulations -H 'Content-type:application/json' -d '{"patients": "X,H,T,X", "medications": "AN,I"}'
 * curl -X PUT localhost:8080/simulations/4 -H 'Content-type:application/json' -d '{"patients": "X,H,T,X", "medications": "AN,I"}'
 * curl -X DELETE localhost:8080/simulations/1
 */

@RestController
class SimulationController {

	private final SimulationRepository repository;

	SimulationController(SimulationRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/simulations")
	List<Simulation> all() {
		return repository.findAll();
	}

	@PostMapping("/simulations")
	Simulation newSimulation(@RequestBody Simulation newSimulation) {
		return repository.save(newSimulation);
	}
	
	@GetMapping("/simulations/{id}")
	Simulation one(@PathVariable Long id) {

		return repository.findById(id)
			.orElseThrow(() -> new SimulationNotFoundException(id));
	}
	
	/*
	@GetMapping("/simulations/{id}")
	Resource<Simulation> one(@PathVariable Long id) {

		Simulation simulation = repository.findById(id)
			.orElseThrow(() -> new SimulationNotFoundException(id));

		return new Resource<>(simulation,
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SimulationController.class).one(id)).withSelfRel(),
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SimulationController.class).all()).withRel("simulations"));
	}*/

	@PutMapping("/simulations/{id}")
	Simulation replaceSimulation(@RequestBody Simulation newSimulation, @PathVariable Long id) {

		return repository.findById(id)
			.map(simulation -> {
				simulation.setPatients(newSimulation.getPatients());
				simulation.setMedications(newSimulation.getMedications());
				simulation.setOutput(newSimulation.getOutput());
				return repository.save(simulation);
			})
			.orElseGet(() -> {
				newSimulation.setId(id);
				return repository.save(newSimulation);
			});
	}

	@DeleteMapping("/simulations/{id}")
	void deleteSimulation(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
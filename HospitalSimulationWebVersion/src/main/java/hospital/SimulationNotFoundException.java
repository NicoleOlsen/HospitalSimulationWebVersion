package hospital;

class SimulationNotFoundException extends RuntimeException {

	SimulationNotFoundException(Long id) {
		super("Could not find simulation with ID " + id);
	}
}
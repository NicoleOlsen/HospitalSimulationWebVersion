package hospital;

import lombok.extern.slf4j.Slf4j;

//import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(SimulationRepository repository) {
		return args -> {
			repository.save(new Simulation("X,X,X,H,T,F", "AN,I,AS", "F:0,X:3,T:0,D:0,H:3"));
			repository.save(new Simulation("H,H,F,D", "I", "F:1,X:0,T:0,D:1,H:2"));
		};
	}
}

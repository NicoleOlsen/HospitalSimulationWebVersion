package hospital;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicationController {
	@GetMapping("/medications")
	public String medications() {
		return "medication";
	}
}

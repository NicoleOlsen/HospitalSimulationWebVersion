package hospital;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import core.HospitalSimulation;

@Controller
public class HospitalSimulationController {

    @GetMapping("/hospitalSimulation")
    public String hospitalSimulationForm(Model model) {
    	HospitalSimulation hs = new HospitalSimulation();
    	hs.setPatients("");
    	hs.setMedications("");
        model.addAttribute("hospitalSimulation", hs);
        return "hospitalSimulation";
    }

    @PostMapping("/hospitalSimulation")
    public String hospitalSimulationSubmit(@ModelAttribute HospitalSimulation hospitalSimulation) {
    	hospitalSimulation.generateOutput();
        return "result";
    }

}

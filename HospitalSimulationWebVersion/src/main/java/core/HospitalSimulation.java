package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Hospital.Medication;
import core.Patient.HealthState;

/**
 * Class combines objects of Patient and Hospital. Here we interact
 * with user, process input data and print string representation of patients
 * health states to strout.
 * 
 * @author sabinaorazem
 * @since 23.10.2018
 * @version 1.1
 */
public class HospitalSimulation implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	private static Hospital hospital = new Hospital();
	
	private static HashMap<HealthState, Integer> mapPatMed = new HashMap<>();
	
	public String patients; // TODO private?
	public String medications;
	public String output; // TODO rename
	
	/**
	 * Method converts strin into objects Patient. Data is processed
	 * and data about healed patient is received. Method returns the health state of
	 * the patient after a visit to the hospital.
	 * 
	 * @param String patients
	 * @param String medications
	 * @return String mapToStr(mapPatMed)
	 */
	
	public void generateOutput() {
		ArrayList<Medication> medsGivenToPatients = new ArrayList<>();
		ArrayList<Patient> healedPatients = new ArrayList<>();
		
		initialize();
				
		String[] listOfPatients = patients.toUpperCase().split(","); 
		String[] listOfMedications = medications.toUpperCase().split(",");
		
		if (listOfPatients.length == 0 || "".equals(listOfPatients[0])) { // ",H,H,T"
			output = mapToStr(mapPatMed);
			return;
		}

		for (int j = 0; j < listOfMedications.length; j++) {
			try {
				if("".equals(listOfMedications[j])) 
					medsGivenToPatients.add(Medication.N);
				else
					medsGivenToPatients.add(Medication.valueOf(listOfMedications[j])); 
			} catch (IllegalArgumentException e) {
				output = listOfMedications[j] + " is not valid medication." + Medication.displayValidMedications();
				return;
			}
		}

		for (int i = 0; i < listOfPatients.length; i++) {
			HealthState healthState = null;
			try {
				healthState = HealthState.valueOf(listOfPatients[i]);
			} catch (IllegalArgumentException e) {
				output = listOfPatients[i] + " is not a valid health state. " + HealthState.displayValidHealthStates();
				return;
			}

			Patient patient = new Patient(healthState, medsGivenToPatients);
			Patient healedPatient = hospital.healPatient(patient);
			healedPatients.add(healedPatient);
			mapPatMed.computeIfPresent(healedPatient.getCurrentState(), (k,v) -> v+1);
		}
		output = mapToStr(mapPatMed);
		return;
	}

	/**
	 * Initialize map with state of having no patients.
	 */
	private static void initialize() {
		mapPatMed.put(HealthState.F, 0);
		mapPatMed.put(HealthState.H, 0);
		mapPatMed.put(HealthState.D, 0);
		mapPatMed.put(HealthState.T, 0);
		mapPatMed.put(HealthState.X, 0);
	}
	
	@Override
	public String toString() {
		return "HospitalSimulation [patients=" + patients + ", medications=" + medications + ", output=" + output + "]";
	}

	/**
	 * Convert map representation of patients and health states to string that can
	 * be printed to strout.
	 * 
	 * @param map
	 * @return String
	 */
	private static String mapToStr(HashMap<HealthState, Integer> map) {
		String out = "";
		for (Map.Entry<HealthState, Integer> entry : map.entrySet()) {
			if (out != "") 
				out += ",";
			out += entry.getKey().toString() + ":";
			out += entry.getValue();
		}
		return out;
	}
	
	/**
	 * @return Hospital
	 */
	public static Hospital getHospital() {
		return hospital;
	}

	/**
	 * @param hospital
	 */
	public static void setHospital(Hospital hospital) {
		HospitalSimulation.hospital = hospital;
	}

	/**
	 * @return HashMap<HealthState, Integer>
	 */
	public static HashMap<HealthState, Integer> getMapPatMed() {
		return mapPatMed;
	}

	/**
	 * @param mapPatMed
	 */
	public static void setMapPatMed(HashMap<HealthState, Integer> mapPatMed) {
		HospitalSimulation.mapPatMed = mapPatMed;
	}

	public String getPatients() {
		return patients;
	}

	public void setPatients(String patients) {
		this.patients = patients;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public static void main(String[] args) {
		HospitalSimulation hs = new HospitalSimulation();
		hs.setPatients("");
		hs.setMedications("");
		if (args.length >= 1) {
			hs.setPatients(args[0]);
		}
		if (args.length == 2) {
			hs.setMedications(args[1]);
		}
		if (args.length > 2) {
			System.out.println(
					"Too many arguments. Please enter two arguments; list of patients and list of medications.");
		}
		hs.setPatients("X,H,X,D");
		hs.setMedications("I");
		
		// X:2,D:1,H:1,T:0,F:0
		hs.setPatients("X");
		hs.setMedications("AN");
		System.out.println(hs.getMedications().toString());
		//X:4,D:0,H:0,T:0,F:0
		hs.generateOutput();
		System.out.println(hs.getOutput());
		hs.setPatients("H");
		hs.setMedications("I");
		hs.generateOutput();
		System.out.println(hs.getMedications().toString());
		System.out.println(hs.getOutput());
		return;
	}
}
	
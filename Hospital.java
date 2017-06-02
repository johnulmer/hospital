package hospital;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Hospital {
	public static Care[] employees;
	public static Patient[] patients;
	
	private static ArrayList deSerializePersons(String fileName) {
		ArrayList persons = new ArrayList();
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while (true) {
				try {
					Person p = (Person) in.readObject();
					persons.add(p);
					
				} catch (EOFException e) {
					break;
				}
			}
			in.close();
			fileIn.close();
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		return persons;
	}
	
	private static void SerializePersons(Person[] persons, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i=0; i<persons.length; i++) {
                out.writeObject(persons[i]);
            }

            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileName);
        } catch (IOException ex) {
            System.out.println(ex);
        }
	}

	public static void main(String[] args) {
		employees = new Care[10];
		
		for (int i= 0; i < 5; i++) {
			Doctor d = new Doctor();
			employees[i] = d;
		}
		for (int i= 0; i < 5; i++) {
			Nurse n = new Nurse();
			employees[i+5] = n;
		}
		//patients = deSerializePersons("patient.data").toArray();

		patients = new Patient[10];
		for (int i= 0; i < 10; i++) {
			Patient p = new Patient();
			p.firstName = "test" + i;
			p.lastName = "last test" + i;
			patients[i] = p;
		} 
		for (int i=0; i < 10; i++) {
			Random r = new Random();
			int n = r.nextInt(10);
			System.out.println(employees[n].readChart(patients[i]));
		}
		SerializePersons(patients, "patient.data");
		System.out.println("behold, i am renewed!" + deSerializePersons("patient.data").toString());
	}
}
	
	class Person implements Serializable {
		public String firstName;
		public String lastName;
		public String formattedName() {
			return this.lastName + ", " + firstName;
		}
		
	}
	
	class Doctor extends Person implements Care {
		public String specialty;
		public Date hireDate;

		public String readChart(Patient p) {
			// TODO Auto-generated method stub
			return "I am above this";
		}

		public BloodPressure readBloodPressure(Patient p) {
			// TODO Auto-generated method stub
			return null;
		}

		public double takeTemperature(Patient p) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	class Patient extends Person {
		private int diastolic;
		private int systolic;
		private String chart;
		
		public BloodPressure takeBloodPressure() {
			BloodPressure b = new BloodPressure();
			b.diastolic = this.diastolic;
			b.systolic = this.systolic;
			return b;
		}
		public String readChart() {
			return this.chart;
		}
		
	}
	class Nurse extends Person implements Care {

		public double takeTemperature(Patient p) {
			// TODO Auto-generated method stub
			return 0;
		}

		public String readChart(Patient p) {
			// TODO Auto-generated method stub
			return "not dead yet, he or she feels happy!";
		}

		public BloodPressure readBloodPressure(Patient p) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	class BloodPressure {
		public int systolic;
		public int diastolic;
	}
	interface Care {
		double takeTemperature(Patient p);
		String readChart(Patient p);
		BloodPressure readBloodPressure(Patient p);
	}
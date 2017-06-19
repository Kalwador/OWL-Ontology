package AirportOntology;

import AirportOntology.model.AirportModel;
import AirportOntology.model.FlightModel;
import AirportOntology.model.PlaneModel;
import AirportOntology.model.SuperModel;
import javafx.stage.FileChooser;

import java.io.*;

public class TxtDataLoader {
    public static SuperModel loadData() {
        SuperModel superModel = new SuperModel();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT(*.txt)", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (FileReader file = new FileReader(selectedFile)) {
                BufferedReader bufor = new BufferedReader(file);
                String linia;


                while ((linia = bufor.readLine()) != null) {
                    String[] pola = linia.split(",");
                    if (pola[0].equals("Airport")) {
                        AirportModel airportModel = new AirportModel();
                        airportModel.setType(pola[1]);
                        airportModel.setName(pola[2]);
                        airportModel.setId(Integer.parseInt(pola[3]));
                        airportModel.setArea(Integer.parseInt(pola[4]));
                        superModel.addAirport(airportModel);
                    }
                    if (pola[0].equals("Plane")) {
                        PlaneModel planeModel = new PlaneModel();
                        planeModel.setType(pola[1]);
                        planeModel.setName(pola[2]);
                        planeModel.setId(Integer.parseInt(pola[3]));
                        planeModel.setEngins(Integer.parseInt(pola[4]));
                        planeModel.setSeats(Integer.parseInt(pola[5]));
                        planeModel.setYear(Integer.parseInt(pola[6]));
                        superModel.addPlane(planeModel);
                    }
                    if (pola[0].equals("Flight")) {
                        FlightModel flightModel = new FlightModel();
                        flightModel.setType(pola[1]);
                        flightModel.setName(pola[2]);
                        flightModel.setId(Integer.parseInt(pola[3]));
                        flightModel.setStart(pola[4]);
                        flightModel.setEnd(pola[5]);
                        flightModel.setPlane(pola[6]);
                        superModel.addFlight(flightModel);
                    }
                }
            } catch (FileNotFoundException w1) {
                System.out.println("SAVE FILE NOT FOUND");
            } catch (IOException w2) {
                System.out.println("DATA LOAD FILURE - IO EXCEPTION");
            } catch (NumberFormatException w3) {
                System.out.println("WRONG NUMBER FORMAT");
            }
        }
        return superModel;
    }
}

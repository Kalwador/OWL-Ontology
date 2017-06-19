package AirportOntology.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SuperModel {
    public List<AirportModel> airportList;
    public List<FlightModel> flightList;
    public List<PlaneModel> planeList;

    public SuperModel() {
        this.airportList = new ArrayList<>();
        this.flightList = new ArrayList<>();
        this.planeList = new ArrayList<>();
    }
    public void addAirport(AirportModel model){
        this.airportList.add(model);
    }
    public void addFlight(FlightModel model){
        this.flightList.add(model);
    }
    public void addPlane(PlaneModel model){
        this.planeList.add(model);
    }
}

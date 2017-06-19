package AirportOntology.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlightModel {
    private int id;
    private String name;
    private String type;
    private String plane;
    private String start;
    private String end;
}

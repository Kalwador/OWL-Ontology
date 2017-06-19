package AirportOntology.model;

import lombok.*;

/**
 * Created by Kalvador on 04/05/2017.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AirportModel {
    private int id;
    private String name;
    private String type;
    private int area;
}

package AirportOntology;

import AirportOntology.model.AirportModel;
import AirportOntology.model.FlightModel;
import AirportOntology.model.PlaneModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Kalvador on 07/05/2017.
 */

public class TableColumns {

    /**
     * KOLUMNY DO TABEL
     */
    public static void getAirportColumns(TableView<AirportModel> aTable) {
        TableColumn<AirportModel, Integer> aTabId = new TableColumn<>("Id");
        aTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AirportModel, String> aTabName = new TableColumn<>("Name");
        aTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<AirportModel, String> aTabType = new TableColumn<>("Type");
        aTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<AirportModel, Integer> aTabArea = new TableColumn<>("Area [Ha]");
        aTabArea.setCellValueFactory(new PropertyValueFactory<>("area"));

        aTable.getColumns().add(aTabId);
        aTable.getColumns().add(aTabName);
        aTable.getColumns().add(aTabType);
        aTable.getColumns().add(aTabArea);
    }

    public static void getFlightColumns(TableView<FlightModel> fTable) {
        TableColumn<FlightModel, Integer> fTabId = new TableColumn<>("Id");
        fTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<FlightModel, String> fTabName = new TableColumn<>("Name");
        fTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FlightModel, String> fTabType = new TableColumn<>("Type");
        fTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<FlightModel, String> fTabPlane = new TableColumn<>("Plane");
        fTabPlane.setCellValueFactory(new PropertyValueFactory<>("plane"));

        TableColumn<FlightModel, String> fTabStart = new TableColumn<>("Start");
        fTabStart.setCellValueFactory(new PropertyValueFactory<>("start"));

        TableColumn<FlightModel, String> fTabEnd = new TableColumn<>("End");
        fTabEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        fTable.getColumns().add(fTabId);
        fTable.getColumns().add(fTabName);
        fTable.getColumns().add(fTabType);
        fTable.getColumns().add(fTabPlane);
        fTable.getColumns().add(fTabStart);
        fTable.getColumns().add(fTabEnd);
    }

    public static void getPlaneColumns(TableView<PlaneModel> pTable) {
        TableColumn<PlaneModel, Integer> pTabId = new TableColumn<>("Id");
        pTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PlaneModel, String> pTabName = new TableColumn<>("Name");
        pTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PlaneModel, String> pTabType = new TableColumn<>("Type");
        pTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<PlaneModel, Integer> pTabEngins = new TableColumn<>("Engins");
        pTabEngins.setCellValueFactory(new PropertyValueFactory<>("engins"));

        TableColumn<PlaneModel, Integer> aTabSeats = new TableColumn<>("Seats");
        aTabSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));

        TableColumn<PlaneModel, Integer> aTabYear = new TableColumn<>("Year of Production");
        aTabYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        pTable.getColumns().add(pTabId);
        pTable.getColumns().add(pTabName);
        pTable.getColumns().add(pTabType);
        pTable.getColumns().add(pTabEngins);
        pTable.getColumns().add(aTabSeats);
        pTable.getColumns().add(aTabYear);
    }
}

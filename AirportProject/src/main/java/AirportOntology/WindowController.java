package AirportOntology;

import AirportOntology.model.AirportModel;
import AirportOntology.model.FlightModel;
import AirportOntology.model.PlaneModel;
import AirportOntology.model.SuperModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private ComboBox<String> aType;
    @FXML
    private ComboBox<String> fType;
    @FXML
    private Button pUpdate;
    @FXML
    private MenuItem mExit;
    @FXML
    private ComboBox<String> fStart;
    @FXML
    private TableView<PlaneModel> pTable;
    @FXML
    private TableView<AirportModel> aTable;
    @FXML
    private TableView<FlightModel> fTable;
    @FXML
    private TextField pName;
    @FXML
    private TextField pEngins;
    @FXML
    private ComboBox<String> pType;
    @FXML
    private TextField pYear;
    @FXML
    private TextField aId;
    @FXML
    private TextField fId;
    @FXML
    private TextField pSeats;
    @FXML
    private MenuItem mAbout;
    @FXML
    private MenuItem mLoadData;
    @FXML
    private Button pAdd;
    @FXML
    private TextField pId;
    @FXML
    private Button aUpdate;
    @FXML
    private MenuItem mSave;
    @FXML
    private Button aAdd;
    @FXML
    private Button fUpdate;
    @FXML
    private MenuItem mOpen;
    @FXML
    private Button fAdd;
    @FXML
    private TextField aName;
    @FXML
    private TextField fName;
    @FXML
    private TextField aArea;
    @FXML
    private ComboBox<String> fEnd;
    @FXML
    private ComboBox<String> fPlane;
    @FXML
    private Button fDelete;
    @FXML
    private Button pDelete;
    @FXML
    private Button aDelete;

    private DAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DAO();
        dao.open();

        mSave.setOnAction(e -> dao.saveOntology());

        mOpen.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OWL(*.owl)", "*.owl"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                if (dao.open(selectedFile)) {
                    updateTables();
                } else {
                    showAlert(Alert.AlertType.ERROR, "ERROR", "Error during parse file.");
                }
            }
        });
        mLoadData.setOnAction(e -> {
            SuperModel superModel = TxtDataLoader.loadData();
            for (PlaneModel model : superModel.getPlaneList()) {
                dao.addPlane(model);
            }
            for (AirportModel model : superModel.getAirportList()) {
                dao.addAirport(model);
            }
            for (FlightModel model : superModel.getFlightList()) {
                dao.addFlight(model);
            }
            updateTables();
        });
        mAbout.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, "INFORMATION", "Projekt na zaliczenie z przedmiotu \r\n" +
                    "'Projekt Inżynierski'\r\n" +
                    "Autor: Piotr Szpila");
        });
        mExit.setOnAction(e -> {
            Optional<File> optional = Optional.ofNullable(DAO.tempData);
            optional.ifPresent(file -> DAO.tempData.delete());
            System.exit(0);
        });

        String comboBoxType1[] = {"Domestic", "Internacional"};
        aType.getItems().addAll(comboBoxType1);
        fType.getItems().addAll(comboBoxType1);

        String comboBoxType2[] = {"Narrow", "Wide"};
        pType.getItems().addAll(comboBoxType2);

        fType.setOnAction(n -> updateComboBox());

        aAdd.setOnAction(e -> {
            AirportModel temp = new AirportModel();
            try {
                temp.setId(Integer.parseInt(aId.getText()));
                temp.setType(aType.getValue());
                temp.setName(aName.getText());
                temp.setArea(Integer.parseInt(aArea.getText()));
                if (isAirportNameAvailable(temp)) {
                    dao.addAirport(temp);
                    updateAirportTable();
                }
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });
        pAdd.setOnAction(e -> {
            PlaneModel temp = new PlaneModel();
            try {
                temp.setId(Integer.parseInt(pId.getText()));
                temp.setType(pType.getValue());
                temp.setName(pName.getText());
                temp.setSeats(Integer.parseInt(pSeats.getText()));
                temp.setEngins(Integer.parseInt(pEngins.getText()));
                temp.setYear(Integer.parseInt(pYear.getText()));
                if (isPlaneNameAvailable(temp)) {
                    dao.addPlane(temp);
                    updatePlaneTable();
                }
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });
        fAdd.setOnAction(e -> {
            FlightModel temp = new FlightModel();
            try {
                temp.setId(Integer.parseInt(fId.getText()));
                temp.setType(fType.getValue());
                temp.setName(fName.getText());
                temp.setStart(fStart.getValue());
                temp.setEnd(fEnd.getValue());
                System.out.println(temp.getStart() + "  " + temp.getEnd());
                temp.setPlane(fPlane.getValue());
                if (isFlightNameAvailable(temp)) {
                    dao.addFlight(temp);
                    updateFlightTable();
                }
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });


        aTable.setOnMouseClicked(e -> {
            AirportModel temp = dao.aList.get(aTable.getSelectionModel().getFocusedIndex());
            aId.setText(String.valueOf(temp.getId()));
            aName.setText(String.valueOf(temp.getName()));
            aArea.setText(String.valueOf(temp.getArea()));
            if (temp.getType().equals("Domestic")) {
                aType.setValue(aType.getItems().get(0));
            } else {
                aType.setValue(aType.getItems().get(1));
            }
        });
        pTable.setOnMouseClicked(e -> {
            PlaneModel temp = dao.pList.get(pTable.getSelectionModel().getFocusedIndex());
            pId.setText(String.valueOf(temp.getId()));
            pName.setText(String.valueOf(temp.getName()));
            pSeats.setText(String.valueOf(temp.getSeats()));
            pEngins.setText(String.valueOf(temp.getEngins()));
            pYear.setText(String.valueOf(temp.getYear()));
            if (temp.getType().equals("Narrow Body")) {
                pType.setValue(pType.getItems().get(0));

            } else {
                pType.setValue(pType.getItems().get(1));
            }
        });
        fTable.setOnMouseClicked(e -> {
            FlightModel temp = dao.fList.get(fTable.getSelectionModel().getFocusedIndex());
            fId.setText(String.valueOf(temp.getId()));
            fName.setText(String.valueOf(temp.getName()));
            if (temp.getType().equals("Domestic")) {

                updateDomesticComboBox();
                fType.setValue(fType.getItems().get(0));
            } else {
                updateInternationalComboBox();
                fType.setValue(fType.getItems().get(1));
            }
        });

        aUpdate.setOnAction(e -> {
            AirportModel temp = new AirportModel();
            try {
                temp.setId(Integer.parseInt(aId.getText()));
                temp.setType(aType.getValue());
                temp.setName(aName.getText());
                temp.setArea(Integer.parseInt(aArea.getText()));
                dao.deleteEntity(dao.aList.get(aTable.getSelectionModel().getFocusedIndex()).getName());
                dao.addAirport(temp);
                updateAirportTable();
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });
        pUpdate.setOnAction(e -> {
            PlaneModel temp = new PlaneModel();
            try {
                temp.setId(Integer.parseInt(pId.getText()));
                temp.setType(pType.getValue());
                temp.setName(pName.getText());
                temp.setSeats(Integer.parseInt(pSeats.getText()));
                temp.setEngins(Integer.parseInt(pEngins.getText()));
                temp.setYear(Integer.parseInt(pYear.getText()));
                dao.deleteEntity(dao.pList.get(pTable.getSelectionModel().getFocusedIndex()).getName());
                dao.addPlane(temp);
                updatePlaneTable();
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });
        fUpdate.setOnAction(e -> {
            FlightModel temp = new FlightModel();
            try {
                temp.setId(Integer.parseInt(fId.getText()));
                temp.setType(fType.getValue());
                temp.setName(fName.getText());
                temp.setStart(fStart.getValue());
                temp.setEnd(fEnd.getValue());
                System.out.println(temp.getStart() + "  " + temp.getEnd());
                temp.setPlane(fPlane.getValue());
                dao.deleteEntity(dao.fList.get(fTable.getSelectionModel().getFocusedIndex()).getName());
                dao.addFlight(temp);
                updateFlightTable();
            } catch (NumberFormatException ex1) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "WRONG TYPE OF INPUTED DATA");
            }
        });


        fDelete.setOnAction(e -> {
            if (dao.fList.size() > 0) {
                dao.deleteEntity(dao.fList.get(fTable.getSelectionModel().getFocusedIndex()).getName());
                updateFlightTable();
            }
        });
        aDelete.setOnAction(e -> {
            if (dao.aList.size() > 0) {
                String name = dao.aList.get(aTable.getSelectionModel().getFocusedIndex()).getName();
                if (!isAirportUsed(name)) {
                    dao.deleteEntity(name);
                    updateAirportTable();
                    updateFlightTable();
                } else {
                    showAlert(Alert.AlertType.WARNING, "WARRNING", "THIS AIRPORT IS STILL IN USE");
                }
            }
        });
        pDelete.setOnAction(e -> {
            if (dao.pList.size() > 0) {
                String name = dao.pList.get(pTable.getSelectionModel().getFocusedIndex()).getName();
                if (!isPlaneUsed(name)) {
                    dao.deleteEntity(name);
                    updatePlaneTable();
                    updateFlightTable();
                } else {
                    showAlert(Alert.AlertType.WARNING, "WARRNING", "THIS PLANE IS STILL IN USE");
                }
            }
        });

        TableColumns.getAirportColumns(aTable);
        TableColumns.getPlaneColumns(pTable);
        TableColumns.getFlightColumns(fTable);

        updateTables();
    }

    private void updateAirportTable() {
        updateComboBox();
        dao.aList.clear();
        dao.readAirportList();
        aTable.setItems(dao.aList);
    }

    private void updateFlightTable() {
        updateComboBox();
        dao.fList.clear();
        dao.readFlightList();
        fTable.setItems(dao.fList);
    }

    private void updatePlaneTable() {
        updateComboBox();
        dao.pList.clear();
        dao.readPlaneList();
        pTable.setItems(dao.pList);
    }

    private void updateTables() {
        updateAirportTable();
        updatePlaneTable();
        updateFlightTable();
    }

    private void updateComboBox() {
        Optional<String> optional = Optional.ofNullable(fType.getValue());
        if (optional.isPresent()) {
            if (fType.getValue().equals("Domestic")) {
                updateDomesticComboBox();
            } else {
                updateInternationalComboBox();
            }
        }
    }

    private void updateDomesticComboBox() {
        String[] pointList = dao.getDomesticAirports();
        fPlane.getItems().setAll(dao.getNarrowBodyPlanes());

        fStart.getItems().setAll(pointList);

        fEnd.getItems().setAll(pointList);
    }

    private void updateInternationalComboBox() {
        String[] pointList = dao.getInternacionalAirports();
        fPlane.getItems().setAll(dao.getWideBodyPlanes());

        fStart.getItems().setAll(pointList);

        fEnd.getItems().setAll(pointList);
    }

    private boolean isAirportNameAvailable(AirportModel a) {
        for (AirportModel airport : dao.aList) {
            if (airport.getName().equals(a.getName())) {
                showAlert(Alert.AlertType.WARNING, "WRONG NAME", "Individual with this NAME already exist");
                return false;
            }
            if (airport.getId() == a.getId()) {
                showAlert(Alert.AlertType.WARNING, "WRONG ID", "Individual with this ID already exist");
                return false;
            }
        }
        return true;
    }

    private boolean isPlaneNameAvailable(PlaneModel p) {
        for (PlaneModel plane : dao.pList) {
            if (plane.getName().equals(p.getName())) {
                showAlert(Alert.AlertType.WARNING, "WRONG NAME", "Individual with this NAME already exist");
                return false;
            }
            if (plane.getId() == p.getId()) {
                showAlert(Alert.AlertType.WARNING, "WRONG ID", "Individual with this ID already exist");
                return false;
            }
        }
        return true;
    }

    private boolean isFlightNameAvailable(FlightModel f) {
        for (FlightModel flight : dao.fList) {
            if (flight.getName().equals(f.getName())) {
                showAlert(Alert.AlertType.WARNING, "WRONG NAME", "Individual with this NAME already exist");

                return false;
            }
            if (flight.getId() == f.getId()) {
                showAlert(Alert.AlertType.WARNING, "WRONG ID", "Individual with this ID already exist");
                return false;
            }
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

    private boolean isAirportUsed(String name) {
        for (FlightModel flight : dao.fList) {
            if (flight.getStart().equals(name)) return true;
            if (flight.getEnd().equals(name)) return true;
        }
        return false;
    }

    private boolean isPlaneUsed(String name) {
        for (FlightModel flight : dao.fList) {
            if (flight.getPlane().equals(name)) return true;
        }
        return false;
    }
}

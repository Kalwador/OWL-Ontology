package AirportOntology;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import AirportOntology.model.AirportModel;
import AirportOntology.model.FlightModel;
import AirportOntology.model.PlaneModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import org.semanticweb.owlapi.io.OWLObjectRenderer;

class DAO {

    ObservableList<AirportModel> aList = FXCollections.observableArrayList();
    ObservableList<PlaneModel> pList = FXCollections.observableArrayList();
    ObservableList<FlightModel> fList = FXCollections.observableArrayList();

    private static final String BASE_URL = "http://www.semanticweb.org/kalvador/AirportOntology";
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    private File file = new File("res/AirportOntology.owl");
    static File tempData;

    private OWLOntologyManager manager = null;
    private OWLOntology ontology = null;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private OWLDataFactory factory;
    private PrefixOWLOntologyFormat pm;

    /**
     * Prepare ontology and reasoner
     *
     * @return True - if connection succes, oterwise false
     */
    boolean open() {
        manager = OWLManager.createOWLOntologyManager();
        try {
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(this.file));
        } catch (OWLOntologyCreationException e) {
            return false;
        }
        reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ontology);

        factory = manager.getOWLDataFactory();
        pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
        pm.setDefaultPrefix(BASE_URL + "#");
        return true;
    }

    boolean open(File file) {
        manager = OWLManager.createOWLOntologyManager();

        try {
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(file));
        } catch (OWLOntologyCreationException e) {
            return false;
        }

        reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ontology);

        factory = manager.getOWLDataFactory();
        pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
        pm.setDefaultPrefix(BASE_URL + "#");
        return true;
    }

    boolean saveOntology() {
        IRI documentIRI2 = IRI.create(this.file);
        try {
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), documentIRI2);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    boolean saveOntology(File file) {
        IRI documentIRI2 = IRI.create(file);
        try {
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), documentIRI2);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void readAirportList() {

        OWLClass internacional_airport = factory.getOWLClass(":International_Airport", pm);
        OWLClass domestic_airport = factory.getOWLClass(":Domestic_Airport", pm);

        OWLDataProperty idAirportProperty = factory.getOWLDataProperty(":Id_Airport", pm);
        OWLDataProperty areaAirportProperty = factory.getOWLDataProperty(":Area[Ha]", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(internacional_airport, false).getFlattened()) {

            AirportModel model = new AirportModel();

            OWLNamedIndividual airport = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("International");
            model.setName(renderer.render(a));
            reasoner.getDataPropertyValues(airport, idAirportProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(airport, areaAirportProperty).iterator().forEachRemaining(n -> {
                model.setArea(Integer.parseInt(n.getLiteral()));
            });
            aList.add(model);
        }

        for (OWLNamedIndividual a : reasoner.getInstances(domestic_airport, false).getFlattened()) {

            AirportModel model = new AirportModel();

            OWLNamedIndividual airport = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("Domestic");
            model.setName(renderer.render(a));
            reasoner.getDataPropertyValues(airport, idAirportProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(airport, areaAirportProperty).iterator().forEachRemaining(n -> {
                model.setArea(Integer.parseInt(n.getLiteral()));
            });
            aList.add(model);
        }
    }

    void readPlaneList() {

        OWLClass wideBody = factory.getOWLClass(":Wide_Body", pm);
        OWLClass narrowBody = factory.getOWLClass(":Narrow_Body", pm);

        OWLDataProperty idPlaneProperty = factory.getOWLDataProperty(":Id_Plane", pm);
        OWLDataProperty seatsPlaneProperty = factory.getOWLDataProperty(":Max_Seats", pm);
        OWLDataProperty enginsPlaneProperty = factory.getOWLDataProperty(":Engins", pm);
        OWLDataProperty datePlaneProperty = factory.getOWLDataProperty(":Date_Of_Production", pm);


        for (OWLNamedIndividual a : reasoner.getInstances(wideBody, false).getFlattened()) {

            PlaneModel model = new PlaneModel();

            OWLNamedIndividual plane = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("Wide Body");
            model.setName(renderer.render(a));
            reasoner.getDataPropertyValues(plane, idPlaneProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, seatsPlaneProperty).iterator().forEachRemaining(n -> {
                model.setSeats(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, enginsPlaneProperty).iterator().forEachRemaining(n -> {
                model.setEngins(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, datePlaneProperty).iterator().forEachRemaining(n -> {
                model.setYear(Integer.parseInt(n.getLiteral()));
            });

            pList.add(model);
        }
        for (OWLNamedIndividual a : reasoner.getInstances(narrowBody, false).getFlattened()) {

            PlaneModel model = new PlaneModel();

            OWLNamedIndividual plane = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("Narrow Body");
            model.setName(renderer.render(a));
            reasoner.getDataPropertyValues(plane, idPlaneProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, seatsPlaneProperty).iterator().forEachRemaining(n -> {
                model.setSeats(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, enginsPlaneProperty).iterator().forEachRemaining(n -> {
                model.setEngins(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getDataPropertyValues(plane, datePlaneProperty).iterator().forEachRemaining(n -> {
                model.setYear(Integer.parseInt(n.getLiteral()));
            });

            pList.add(model);
        }
    }

    void readFlightList() {

        OWLClass internationalFlight = factory.getOWLClass(":International_Flight", pm);
        OWLClass domesticFlight = factory.getOWLClass(":Domestic_Flight", pm);

        OWLDataProperty idFlightProperty = factory.getOWLDataProperty(":Id_Flight", pm);
        OWLObjectProperty startFlightProperty = factory.getOWLObjectProperty(":Start_Point", pm);
        OWLObjectProperty endFlightProperty = factory.getOWLObjectProperty(":End_Point", pm);
        OWLObjectProperty planeFlightProperty = factory.getOWLObjectProperty(":Plane", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(internationalFlight, false).getFlattened()) {

            FlightModel model = new FlightModel();

            OWLNamedIndividual plane = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("International");
            model.setName(renderer.render(a));

            reasoner.getDataPropertyValues(plane, idFlightProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getObjectPropertyValues(plane, planeFlightProperty).getFlattened().forEach(n -> {
                model.setPlane(renderer.render(n));
            });

            reasoner.getObjectPropertyValues(plane, startFlightProperty).getFlattened().forEach(n -> {
                model.setStart(renderer.render(n));
            });
            reasoner.getObjectPropertyValues(plane, endFlightProperty).getFlattened().forEach(n -> {
                model.setEnd(renderer.render(n));
            });

            fList.add(model);
        }

        for (OWLNamedIndividual a : reasoner.getInstances(domesticFlight, false).getFlattened()) {

            FlightModel model = new FlightModel();

            OWLNamedIndividual plane = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);
            model.setType("Domestic");
            model.setName(renderer.render(a));

            reasoner.getDataPropertyValues(plane, idFlightProperty).iterator().forEachRemaining(n -> {
                model.setId(Integer.parseInt(n.getLiteral()));
            });
            reasoner.getObjectPropertyValues(plane, planeFlightProperty).getFlattened().forEach(n -> {
                model.setPlane(renderer.render(n));
            });

            reasoner.getObjectPropertyValues(plane, startFlightProperty).getFlattened().forEach(n -> {
                model.setStart(renderer.render(n));
            });
            reasoner.getObjectPropertyValues(plane, endFlightProperty).getFlattened().forEach(n -> {
                model.setEnd(renderer.render(n));
            });

            fList.add(model);
        }
    }

    String[] getDomesticAirports() {

        List<String> list = new ArrayList<>();

        OWLClass domestic_airport = factory.getOWLClass(":Domestic_Airport", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(domestic_airport, false).getFlattened()) {
            list.add(renderer.render(a));
        }
        return list.toArray(new String[0]);
    }

    String[] getInternacionalAirports() {

        List<String> list = new ArrayList<>();

        OWLClass internacional_airport = factory.getOWLClass(":International_Airport", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(internacional_airport, false).getFlattened()) {
            list.add(renderer.render(a));
        }
        return list.toArray(new String[0]);
    }

    String[] getNarrowBodyPlanes() {

        ArrayList<String> list = new ArrayList<>();

        OWLClass narrow_body = factory.getOWLClass(":Narrow_Body", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(narrow_body, false).getFlattened()) {
            list.add(renderer.render(a));
        }
        return list.toArray(new String[0]);
    }

    String[] getWideBodyPlanes() {

        ArrayList<String> list = new ArrayList<>();

        OWLClass wide_body = factory.getOWLClass(":Wide_Body", pm);

        for (OWLNamedIndividual a : reasoner.getInstances(wide_body, false).getFlattened()) {
            list.add(renderer.render(a));
        }
        return list.toArray(new String[0]);
    }

    void addAirport(AirportModel model) {
        OWLClass airportClass;
        if (model.getType().equals("Internacional")) {
            airportClass = factory.getOWLClass(":International_Airport", pm);
        } else {
            airportClass = factory.getOWLClass(":Domestic_Airport", pm);
        }
        OWLNamedIndividual airport = new OWLNamedIndividualImpl(IRI.create("#" + model.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(airportClass, airport);
        manager.addAxiom(ontology, ax0);


        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER
                .getIRI());

        OWLDataPropertyExpression idProperty = factory.getOWLDataProperty(":Id_Airport", pm);
        OWLDataPropertyExpression areaProperty = factory.getOWLDataProperty(":Area[Ha]", pm);

        OWLLiteral idLitereal = factory.getOWLLiteral(String.valueOf(model.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(idProperty, airport, idLitereal);
        manager.addAxiom(ontology, ax1);

        OWLLiteral areaLiteral = factory.getOWLLiteral(String.valueOf(model.getArea()), integerDatatype);
        OWLAxiom ax2 = factory.getOWLDataPropertyAssertionAxiom(areaProperty, airport, areaLiteral);
        manager.addAxiom(ontology, ax2);

        applyChanges();
    }

    void addPlane(PlaneModel model) {
        OWLClass planeClass;
        if (model.getType().equals("Narrow")) {
            planeClass = factory.getOWLClass(":Narrow_Body", pm);
        } else {
            planeClass = factory.getOWLClass(":Wide_Body", pm);
        }
        OWLNamedIndividual plane = new OWLNamedIndividualImpl(IRI.create("#" + model.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(planeClass, plane);
        manager.addAxiom(ontology, ax0);

        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER
                .getIRI());

        OWLDataPropertyExpression idProperty = factory.getOWLDataProperty(":Id_Plane", pm);
        OWLDataPropertyExpression enginsProperty = factory.getOWLDataProperty(":Engins", pm);
        OWLDataPropertyExpression seatsProperty = factory.getOWLDataProperty(":Max_Seats", pm);
        OWLDataPropertyExpression dateProperty = factory.getOWLDataProperty(":Date_Of_Production", pm);


        OWLLiteral idLitereal = factory.getOWLLiteral(String.valueOf(model.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(idProperty, plane, idLitereal);
        manager.addAxiom(ontology, ax1);

        OWLLiteral enginsLiteral = factory.getOWLLiteral(String.valueOf(model.getEngins()), integerDatatype);
        OWLAxiom ax2 = factory.getOWLDataPropertyAssertionAxiom(enginsProperty, plane, enginsLiteral);
        manager.addAxiom(ontology, ax2);

        OWLLiteral seatsLiteral = factory.getOWLLiteral(String.valueOf(model.getSeats()), integerDatatype);
        OWLAxiom ax3 = factory.getOWLDataPropertyAssertionAxiom(seatsProperty, plane, seatsLiteral);
        manager.addAxiom(ontology, ax3);

        OWLLiteral yearLiteral = factory.getOWLLiteral(String.valueOf(model.getYear()), integerDatatype);
        OWLAxiom ax4 = factory.getOWLDataPropertyAssertionAxiom(dateProperty, plane, yearLiteral);
        manager.addAxiom(ontology, ax4);

        applyChanges();
    }

    void addFlight(FlightModel model) {
        OWLClass flightClass;
        if (model.getType().equals("Domestic")) {
            flightClass = factory.getOWLClass(":Domestic_Flight", pm);
        } else {
            flightClass = factory.getOWLClass(":International_Flight", pm);
        }
        OWLNamedIndividual flight = new OWLNamedIndividualImpl(IRI.create("#" + model.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(flightClass, flight);
        manager.addAxiom(ontology, ax0);

        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER
                .getIRI());

        OWLDataPropertyExpression idFlightPropertyExpresion = factory.getOWLDataProperty(":Id_Flight", pm);
        OWLObjectProperty startFlightProperty = factory.getOWLObjectProperty(":Start_Point", pm);
        OWLObjectProperty endFlightProperty = factory.getOWLObjectProperty(":End_Point", pm);
        OWLObjectProperty planeFlightProperty = factory.getOWLObjectProperty(":Plane", pm);

        OWLLiteral idLitereal = factory.getOWLLiteral(String.valueOf(model.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(idFlightPropertyExpresion, flight, idLitereal);
        manager.addAxiom(ontology, ax1);

        OWLNamedIndividual start = factory.getOWLNamedIndividual(":" + model.getStart(), pm);
        OWLNamedIndividual end = factory.getOWLNamedIndividual(":" + model.getEnd(), pm);
        OWLNamedIndividual plane = factory.getOWLNamedIndividual(":" + model.getPlane(), pm);


        OWLObjectPropertyAssertionAxiom ax2 = factory
                .getOWLObjectPropertyAssertionAxiom(startFlightProperty, flight, start);
        manager.addAxiom(ontology, ax2);

        OWLObjectPropertyAssertionAxiom ax3 = factory
                .getOWLObjectPropertyAssertionAxiom(endFlightProperty, flight, end);
        manager.addAxiom(ontology, ax3);

        OWLObjectPropertyAssertionAxiom ax4 = factory
                .getOWLObjectPropertyAssertionAxiom(planeFlightProperty, flight, plane);
        manager.addAxiom(ontology, ax4);

        applyChanges();
    }

    void deleteEntity(String name) {
        OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(ontology));
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":" + name, pm);
        individual.accept(remover);
        manager.applyChanges(remover.getChanges());
        applyChanges();
    }

    void applyChanges() {
        tempData = new File("res/tempData.owl");
        saveOntology(tempData);
        open(tempData);
    }
}

package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.MyConstants;
import com.prog3.ipt.Model.TravelDocumentClasses.SingleTicket;
import com.prog3.ipt.Model.TravelDocumentClasses.SingleTicketConcreteFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * AddSingleTicketsViewController is a class that extends TravelDocumentsManagementViewController
 * This class handles AddSingleTickets view
 */
public class AddSingleTicketsViewController extends TravelDocumentsManagementViewController {
    private SingleTicket mySingleTicket;

    // Navigation Bar
    @FXML
    private Button backButton;

    // VBox
    @FXML
    private TextField ID_RideTextField;
    @FXML
    private TextField ID_LineTextField;
    @FXML
    private Button decreaseMonoTicketQuantityButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Button increaseMonoTicketQuantityButton;
    @FXML
    private Label priceResultLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Button addMonoTicketsToCart;



    // SingleTicket setter
    private void setMySingleTicket(SingleTicket mySingleTicket) { this.mySingleTicket = mySingleTicket; }

    // SingleTicket getter
    private SingleTicket getMySingleTicket() { return mySingleTicket; }


    /**
     * Back to previous view
     * @param event Button clicked
     */
    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }

    /**
     * Adds a single ticket to cart
     * @param event Button clicked
     */
    @FXML
    private void onAddSingleTicketsToCartButtonClick(ActionEvent event) {
        String ID_Ride = ID_RideTextField.getText(), ID_Line = ID_LineTextField.getText();
        LocalDate ticketIssueDate = LocalDate.now();
        if (!super.checkTextFieldsContent(ID_LineTextField, ID_RideTextField) || Integer.valueOf(quantityTextField.getText()) <= 0) { super.raiseErrorAlert("Hai lasciato uno o pi?? campi vuoti"); return; }
        // check line id, ride id, ticket issue date validity
        if (!FacadeSingleton.validateRide(ID_Line, ID_Ride)) { super.raiseErrorAlert("Biglietto/i singolo/i selezionato/i non valido/i!"); return; }
        if (!FacadeSingleton.checkTicketIssueDateValidity(ID_Line, ticketIssueDate)) { super.raiseErrorAlert("Biglietto/i singolo/i selezionato/i non valido/i! Non ?? possibile acquistare alcuno/i biglietto/i per una linea la cui data di attivazione ?? successiva alla data di emissione del/i biglietto/i!"); return; }

        // create single ticket factory
        int quantity = Integer.valueOf(quantityTextField.getText());
        super.myTravelDocumentFactory = new SingleTicketConcreteFactory();

        // for each ticket in order
        for (int i = 0; i < quantity; i++) {

            // create ticket
            setMySingleTicket((SingleTicket) super.myTravelDocumentFactory.createTravelDocument(MyConstants.singleTicketPrice, ticketIssueDate, null, null, ID_Line, ID_Ride, null, null));

            // add ticket to order
            super.getOrder().addTravelDocument(getMySingleTicket());

            // set order to observer
            super.setOrder(new Order(null, super.getOrder().getPurchaseDate(), super.getOrder().getPurchasePrice(), super.getOrder().getCitizenID(), super.getOrder().getPaymentMethodStrategy(), super.getOrder().getPurchaseList(), super.getOrder().getPurchaseObservableList()));
        }
        super.raiseConfirmationAlert("Biglietto/i singolo/i aggiunto/i correttamente al carrello!");
        initializeViewComponents();
    }

    /**
     * Increases ticket quantity
     * @param event Button clicked
     */
    @FXML
    private void onIncreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
        priceResultLabel.setText("???  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.singleTicketPrice));
    }

    /**
     * Decreases membership quantity
     * @param event Button clicked
     */
    @FXML
    private void onDecreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
        priceResultLabel.setText("???  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.singleTicketPrice));
    }

    /**
     * Shows help information
     * @param event
     */
    @FXML
    private void onHelpButtonClick(ActionEvent event) { super.raiseInformationAlert("?? possibile aggiungere uno o pi?? biglietti singoli al carrello. ?? possibile utilizzare il biglietto singolo per la corsa e per la linea da te specificata. La validit?? del titolo di viaggio ?? a partire dalla data di timbratura del biglietto fino al giorno seguente allo stesso orario. Se selezioni pi?? biglietti singoli, questi saranno validi tutti per la stessa corsa e per la stessa linea. Se ?? necessario che siano diverse, aggiungi separatamente i biglietti singoli al carrello."); }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see com.prog3.ipt.Controller.ViewController#initializeViewComponents()
     */
    @Override
    protected void initializeViewComponents() {
        super.clearTextFieldsContent(ID_RideTextField, ID_LineTextField, quantityTextField);
        quantityTextField.setText(Integer.toString(0));
        priceResultLabel.setText("???  " + String.valueOf(0.0));
    }
 }
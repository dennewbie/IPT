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
import java.util.UUID;

public class AddSingleTicketsViewController extends TravelDocumentsManagementViewController {
    SingleTicket mySingleTicket;



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



    private void setMySingleTicket(SingleTicket mySingleTicket) { this.mySingleTicket = mySingleTicket; }
    private SingleTicket getMySingleTicket() { return mySingleTicket; }
    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }
    @FXML
    void onAddSingleTicketsToCartButtonClick(ActionEvent event) {

        String ID_Ride = ID_RideTextField.getText(), ID_Line = ID_LineTextField.getText();
        LocalDate ticketIssueDate = LocalDate.now();

        if (!super.checkTextFieldsContent(ID_LineTextField, ID_RideTextField) || Integer.valueOf(quantityTextField.getText()) <= 0) { super.raiseErrorAlert("Hai lasciato uno o più campi vuoti"); return; }

        // check line id, ride id, ticket issue date validity
        if (!FacadeSingleton.validateRide(ID_Line, ID_Ride)) { super.raiseErrorAlert("Biglietto/i singolo/i selezionato/i non valido/i!"); return; }
        if (!FacadeSingleton.checkTicketIssueDateValidity(ID_Line, ticketIssueDate)) { super.raiseErrorAlert("Biglietto/i singolo/i selezionato/i non valido/i! Non è possibile acquistare alcuno/i biglietto/i per una linea la cui data di attivazione è successiva alla data di emissione del/i biglietto/i!"); return; }

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

    @FXML
    void onIncreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
        priceResultLabel.setText("€  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.singleTicketPrice));
    }

    @FXML
    void onDecreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
        priceResultLabel.setText("€  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.singleTicketPrice));
    }

    @FXML
    void onHelpButtonClick(ActionEvent event) { super.raiseInformationAlert("È possibile aggiungere uno o più biglietti singoli al carrello. È possibile utilizzare il biglietto singolo per la corsa e per la linea da te specificata. La validità del titolo di viaggio è a partire dalla data di timbratura del biglietto fino al giorno seguente allo stesso orario. Se selezioni più biglietti singoli, questi saranno validi tutti per la stessa corsa e per la stessa linea. Se è necessario che siano diverse, aggiungi separatamente i biglietti singoli al carrello."); }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        super.clearTextFieldsContent(ID_RideTextField, ID_LineTextField, quantityTextField);
        quantityTextField.setText(Integer.toString(0));
        priceResultLabel.setText("€  " + String.valueOf(0.0));
    }
 }
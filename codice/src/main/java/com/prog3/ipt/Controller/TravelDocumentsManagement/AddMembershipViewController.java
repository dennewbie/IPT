package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.MyConstants;
import com.prog3.ipt.Model.TravelDocumentClasses.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * AddMembershipViewController is a class that extends TravelDocumentsManagementViewController and
 * represents view of adding membership
 */
public class AddMembershipViewController extends TravelDocumentsManagementViewController {
    Membership myMembership;

    // NavigationBar
    @FXML
    private Button backButton;

    // VBox
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Button decreaseMembershipQuantityButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Button increaseMembershipQuantityButton;
    @FXML
    private Label priceResultLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Button addMembershipToCart;

    //Membership setter
    private void setMyMembership(Membership myMembership) {this.myMembership = myMembership;}

    //Membership getter
    private Membership getMyMembership() {return myMembership;}

    /**
     * Button to go back to previous view
     * @param event Button clicked
     */
    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }

    /**
     * Button to add a membership to cart
     * @param event Button clicked
     */
    @FXML
    void onAddMembershipToCartButtonClick(ActionEvent event) {
        LocalDate startDate = startDatePicker.getValue();
        if (!super.checkDatePickersContent(startDatePicker) || Integer.valueOf(quantityTextField.getText()) <= 0) { super.raiseErrorAlert("Hai lasciato uno o più campi vuoti"); return; }
        if (startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())) { super.raiseErrorAlert("Non puoi comprare un abbonamento che ha una data di inizio validità nel passato."); return; }

        // create membership factory
        int quantity = Integer.valueOf(quantityTextField.getText());
        super.myTravelDocumentFactory = new MembershipConcreteFactory();

        // for each membership in citizen order
        for (int i = 0; i < quantity; i++) {

            // create membership
            setMyMembership((Membership) super.myTravelDocumentFactory.createTravelDocument(MyConstants.membershipPrice, LocalDate.now(), startDate.plusYears(1), null, null, null, null, startDate));

            // add membership to order
            super.getOrder().addTravelDocument(getMyMembership());

            // set order to observer
            super.setOrder(new Order(null, super.getOrder().getPurchaseDate(), super.getOrder().getPurchasePrice(), super.getOrder().getCitizenID(), super.getOrder().getPaymentMethodStrategy(), super.getOrder().getPurchaseList(), super.getOrder().getPurchaseObservableList()));
        }
        super.raiseConfirmationAlert("Abbonamento/i aggiunto/i correttamente al carrello!");
        initializeViewComponents();
    }

    /**
     * Button to increase membership quantity
     * @param event Button clicked
     */
    @FXML
    void onIncreaseMembershipQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
        priceResultLabel.setText("€  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.membershipPrice));
    }

    /**
     * Button to decrease membership quantity
     * @param event Button clicked
     */
    @FXML
    void onDecreaseMembershipQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
        priceResultLabel.setText("€  " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.membershipPrice));
    }

    /**
     * Button to show help view
     * @param event
     */
    @FXML
    void onHelpButtonClick(ActionEvent event) { super.raiseInformationAlert("È possibile aggiungere uno o più abbonamenti al carrello. L'abbonamneto avrà la data di inizio validità pari a quella da te inserita e sarà valido per un anno. Se selezioni più abbonamenti avranno tutti la stessa data di inizio validità. Se è necessario che siano diverse, aggiungi separatamente gli abbonamenti al carrello."); }

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
        super.clearDatePickersContent(startDatePicker);
        super.clearTextFieldsContent(quantityTextField);
        quantityTextField.setText(Integer.toString(0));
        priceResultLabel.setText("€  " + String.valueOf(0.0));
    }
}
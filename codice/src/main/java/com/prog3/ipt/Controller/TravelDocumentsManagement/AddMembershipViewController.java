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



    private void setMyMembership(Membership myMembership) {
        this.myMembership = myMembership;
    }
    private Membership getMyMembership() {
        return myMembership;
    }

    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }

    @FXML
    void onAddMembershipToCartButtonClick(ActionEvent event) {
        LocalDate startDate = startDatePicker.getValue();
        if (!super.checkDatePickersContent(startDatePicker) || Integer.valueOf(quantityTextField.getText()) <= 0)  return;

        if (startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi comprare un abbonamento che ha una data di inizio validità nel passato.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // aggiunta al carrello
        int quantity = Integer.valueOf(quantityTextField.getText());
        super.myTravelDocumentFactory = new MembershipConcreteFactory();
        for (int i = 0; i < quantity; i++) {
            setMyMembership((Membership) super.myTravelDocumentFactory.createTravelDocument(MyConstants.membershipPrice, LocalDate.now(), startDate.plusYears(1), null, null, null, null, startDate));
            super.getOrder().addTravelDocument(getMyMembership());
            super.setOrder(new Order(super.getOrder().getPurchaseDate(), super.getOrder().getPurchasePrice(), super.getOrder().getCitizenID(), super.getOrder().getPaymentMethodStrategy(), super.getOrder().getPurchaseList(), super.getOrder().getPurchaseObservableList()));
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Abbonamento/i aggiunto/i correttamente al carrello!", ButtonType.OK);
        alert.showAndWait();
        initializeViewComponents();
    }

    @FXML
    void onIncreaseMembershipQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
        priceResultLabel.setText("€ " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.membershipPrice));
    }

    @FXML
    void onDecreaseMembershipQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
        priceResultLabel.setText("€ " + String.valueOf(Integer.parseInt(quantityTextField.getText()) * MyConstants.membershipPrice));
    }

    @FXML
    void onHelpButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "È possibile aggiungere uno o più abbonamenti al carrello. " +
                "L'abbonamneto avrà la data di inizio validità pari a quella da te inserita e sarà valido per un anno. " +
                "Se selezioni più abbonamenti avranno tutti la stessa data di inizio validità. Se è necessario che siano diverse, aggiungi" +
                "separatamente gli abbonamenti al carrello.", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        super.clearDatePickersContent(startDatePicker);
        super.clearTextFieldsContent(quantityTextField);
        quantityTextField.setText(Integer.toString(0));
        priceResultLabel.setText("€ " + String.valueOf(0.0));
    }
}
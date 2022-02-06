package com.prog3.ipt.Controller.TravelDocumentsManagement;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * ActionDeleteButtonTableCell is a class that extends TableCell class, it represents a TableCell
 * dedicated with a specific cancellation button
 * @param <S> Generic parameter
 */
public class ActionDeleteButtonTableCell<S> extends TableCell<S, Button> {
    private final Button actionButton;



    /**
     * ActionDeleteButtonTableCell constructor
     * @param label Label written on the button
     * @param function Function performed after clicking the button
     */
    private ActionDeleteButtonTableCell(String label, Function< S, S> function) {
        this.getStyleClass().add("deleteButton");
        this.actionButton = new Button(label);
        this.actionButton.setOnAction((ActionEvent e) -> function.apply(getCurrentItem()));
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }

    // Getter
    private S getCurrentItem() { return getTableView().getItems().get(getIndex()); }

    /**
     * Returns to the screen to return to after pressing the button
     * @param label Label written on the button
     * @param function Function performed after clicking the button
     * @param <S> Generic parameter
     * @return a reference to Callback<TableColumn<S, Button>, TableCell<S, Button>> object
     */
    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function< S, S> function) {
        return param -> new ActionDeleteButtonTableCell<>(label, function);
    }

    // Updater
    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}
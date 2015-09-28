package com.ui;

import com.dto.Client;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ClientForm extends FormLayout {

    public int currentClient = 0;
    public Button save = new Button("Apply");
    public Button cancel = new Button("Cancel");
    public Button viewCars = new Button("Client's cars");
    public Button viewOrders = new Button("Related orders");
    public TextField firstName = new TextField("First name");
    public TextField lastName = new TextField("Last name");
    public TextField phone = new TextField("Phone");
    public TextField address = new TextField("Address");
    public TextField email = new TextField("Email");
    public DateField birthDate = new DateField("Date of birth");

    Client client;
    BeanFieldGroup<Client> formFieldBindings;

    public ClientForm() {
        buildLayout();
        configureComponents();
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);
        addComponents(firstName, lastName, birthDate, address, phone, email, actions, viewCars);
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        birthDate.setDateFormat("dd.MM.yyyy");

    }

    public void edit(Client client) {
        try {
            currentClient = client.getClientId();
            this.client = client;
            if(client != null) {
                // Bind the properties of the client POJO to fiels in this form
                formFieldBindings = BeanFieldGroup.bindFieldsBuffered(client, this);
                birthDate.setValue(client.getDateOfBirth());
                firstName.focus();
            }
            setVisible(client != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validate() {

        boolean validation = false;

        String firstName = "^[a-zA-Z]{2,15}$";
        String lastName = "^[a-zA-Z]{2,15}$";
        String date = "^[0-9]{2}.[0-9]{2}.[0-9]{2}";
        String email = "^([a-z0-9_-]+.)*[a-z0-9_-]+@[a-z0-9_-]+(.[a-z0-9_-]+)*.[a-z]{2,6}$";
        String phoneNumber = "^([0-9+]{0,3})s?([0-9]{1,6})s?([0-9-]{1,9})$";


        if (this.firstName.getValue().matches(firstName) && this.lastName.getValue().matches(lastName) &&
            this.email.getValue().matches(email) && this.phone.getValue().matches(phoneNumber) &&
            this.address.getValue().length() < 100 && this.address.getValue().length() > 0 &&  this.birthDate.getValue() != null) {

                validation = true;
        }
        return validation;
    }
}

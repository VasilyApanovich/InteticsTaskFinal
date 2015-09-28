package com.ui;

import com.db.DatabaseConnection;
import com.dto.Client;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

import java.util.ArrayList;

public class ClientsGrid extends Grid {
	public ClientsGrid() {
		setSizeFull();
		setEditorEnabled(false);
        setContainerDataSource(new BeanItemContainer<>(Client.class));
        setColumnOrder("firstName", "lastName", "phone");
        removeColumn("address");
        removeColumn("email");
        removeColumn("dateOfBirth");
        removeColumn("clientId");
        setSelectionMode(SelectionMode.SINGLE);
        updateClients();
	}
    public void updateClients() {
        ArrayList<Client> list = DatabaseConnection.getListFromDb();
        for (Client client : list) {
            getContainerDataSource().addItem(client);
        }
    }
}

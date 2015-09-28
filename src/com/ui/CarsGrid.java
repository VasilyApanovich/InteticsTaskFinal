package com.ui;

import com.dto.Car;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

import java.util.ArrayList;

public class CarsGrid extends Grid {
    public CarsGrid() {
        setWidth("100%");
        setEditorEnabled(false);
        setSelectionMode(SelectionMode.SINGLE);

        setContainerDataSource(new BeanItemContainer<>(Car.class));
        setColumnOrder("make", "model", "year", "VIN");
        removeColumn("clientId");
        removeColumn("carId");
    }

    @Override
    public void saveEditor() throws FieldGroup.CommitException {
        super.saveEditor();

    }

    public void fillCarGrid(ArrayList<Car> list) {
        for (Car car : list) {
            getContainerDataSource().addItem(car);
        }
    }

    public void cleanData() {
        getContainerDataSource().removeAllItems();
    }
}

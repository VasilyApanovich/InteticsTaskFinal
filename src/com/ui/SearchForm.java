package com.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SearchForm extends HorizontalLayout {

    public final TextField firstNameField = new TextField();
    public final TextField lastNameField = new TextField();
    public Button search = new Button("Search");
    public Button add = new Button("New client");

    public SearchForm() {
        buildLayout();
        configureComponents();
    }

    private void buildLayout() {
        addComponent(firstNameField);
        addComponent(lastNameField);
        addComponent(search);
        addComponent(add);
    }

    private void configureComponents() {
        search.setStyleName(ValoTheme.BUTTON_PRIMARY);
        add.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        firstNameField.setInputPrompt("First name");
        lastNameField.setInputPrompt("Last name");

        setSizeFull();
        add.setSizeFull();
        search.setSizeFull();
        firstNameField.setSizeFull();
        lastNameField.setSizeFull();

        setSpacing(true);
    }

    public void clean() {
        firstNameField.setValue("");
        lastNameField.setValue("");
    }
}

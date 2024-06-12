package com.example.demo.view;

import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.util.StringUtils;

@PermitAll
@Route(value="")
@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
public class MainView extends VerticalLayout {

    private final FactorRepo factorRepo;

    private final FactorEditor editor;

    final Grid<Factor> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView( FactorRepo repo, FactorEditor editor) {
        this.factorRepo = repo;
        this.editor = editor;
        this.grid = new Grid<>( Factor.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, this.editor);

        grid.setHeight( "300px");
        grid.setColumns( "id", "owner", "products", "sumTotal");
        grid.getColumnByKey("id").setWidth( "50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by Owner");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editFactor(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editFactor(new Factor("")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers( filter.getValue());
        });

        // Initialize listing
        listCustomers(null);
    }

    // tag::listCustomers[]
    void listCustomers(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(factorRepo.findByOwner( filterText));
        } else {
            grid.setItems(factorRepo.findAll());
        }
    }
    // end::listCustomers[]

}

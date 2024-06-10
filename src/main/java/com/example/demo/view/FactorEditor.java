package com.example.demo.view;

import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class FactorEditor extends VerticalLayout implements KeyNotifier {

    private FactorRepo factorRepo;

    /**
     * The currently edited customer
     */
    private Factor factor;

    /* Fields to edit properties in Customer entity */
    DatePicker datePicker = new DatePicker("Date");
    TextField owner = new TextField("Owner");
    TextField products = new TextField("Products");
    TextField sumTotal = new TextField("SumTotal");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Factor> binder = new Binder<>(Factor.class);
    private ChangeHandler changeHandler;

    @Autowired
    public FactorEditor(FactorRepo repository) {
        this.factorRepo = repository;

        add( owner, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editFactor(factor));
        setVisible(false);
    }

    void delete() {
        factorRepo.delete(factor);
        changeHandler.onChange();
    }

    void save() {
        factorRepo.save(factor);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editFactor(Factor f) {
        if (f == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = (f.getId() != null);
        if (persisted) {
            // Find fresh entity for editing
            // In a more complex app, you might want to load
            // the entity/DTO with lazy loaded relations for editing
            factor = factorRepo.findById( f.getId()).get();
        }
        else {
            factor = f;
        }

        cancel.setVisible( persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean( factor);

        setVisible(true);

        // Focus first name initially
        owner.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
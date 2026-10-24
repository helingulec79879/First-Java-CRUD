package com.example.moviewatchlist;

import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * Form used to add, edit and remove a single {@link Movie}.
 */
@SpringComponent
@UIScope
public class MovieEditor extends VerticalLayout implements KeyNotifier {

    static final List<String> GENRES = List.of("Action", "Animation", "Comedy", "Crime",
            "Drama", "Horror", "Romance", "Sci-Fi", "Thriller", "Other");

    private final MovieRepository repository;

    /** The movie currently being edited. */
    private Movie movie;

    /* Fields bound to the properties of the Movie entity */
    TextField title = new TextField("Title");
    TextField director = new TextField("Director");
    ComboBox<String> genre = new ComboBox<>("Genre");
    IntegerField releaseYear = new IntegerField("Release year");
    IntegerField rating = new IntegerField("Rating (1-10)");
    Checkbox watched = new Checkbox("Already watched");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Remove", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Movie> binder = new Binder<>(Movie.class);
    private ChangeHandler changeHandler;

    public MovieEditor(MovieRepository repository) {
        this.repository = repository;

        genre.setItems(GENRES);
        releaseYear.setMin(1888);
        releaseYear.setMax(2100);
        rating.setMin(1);
        rating.setMax(10);

        H3 formTitle = new H3("Movie details");
        formTitle.getStyle().set("margin", "0").set("color", "#6d28d9");
        FormLayout fields = new FormLayout(title, director, genre, releaseYear, rating, watched);
        add(formTitle, fields, actions);

        // card style for the form
        getStyle()
            .set("background", "#faf5ff")
            .set("border", "1px solid #e9d5ff")
            .set("border-radius", "16px")
            .set("padding", "20px");

        // Title and director are required, the other fields are bound by naming convention
        binder.forField(title).asRequired("Title is required").bind(Movie::getTitle, Movie::setTitle);
        binder.forField(director).asRequired("Director is required").bind(Movie::getDirector, Movie::setDirector);
        binder.bindInstanceFields(this);

        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editMovie(null));
        setVisible(false);
    }

    void delete() {
        repository.delete(movie);
        changeHandler.onChange();
    }

    void save() {
        if (!binder.validate().isOk()) {
            return;
        }
        repository.save(movie);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editMovie(Movie m) {
        if (m == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = m.getId() != null;
        if (persisted) {
            // Load a fresh copy of the entity from the database
            movie = repository.findById(m.getId()).get();
        }
        else {
            movie = m;
        }
        // Only already saved movies can be removed
        delete.setVisible(persisted);

        binder.setBean(movie);

        setVisible(true);
        title.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}

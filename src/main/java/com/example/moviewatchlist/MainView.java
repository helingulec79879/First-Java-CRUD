package com.example.moviewatchlist;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

/**
 * Main page of the application: a searchable movie list and an editor form.
 */
@Route
@PageTitle("Movie Watchlist")
public class MainView extends VerticalLayout {

    private final MovieRepository repo;

    private final MovieEditor editor;

    final Grid<Movie> grid;

    final TextField filter;

    final Span stats;

    private final Button addNewBtn;

    public MainView(MovieRepository repo, MovieEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Movie.class);
        this.filter = new TextField();
        this.stats = new Span();
        this.addNewBtn = new Button("Add movie", VaadinIcon.PLUS.create());

        // page layout: centered content with a maximum width
        setMaxWidth("1150px");
        getStyle().set("margin", "0 auto").set("padding", "24px");

        // header banner
        Icon movieIcon = VaadinIcon.FILM.create();
        movieIcon.setSize("36px");
        movieIcon.setColor("white");
        H2 title = new H2("Movie Watchlist");
        title.getStyle().set("color", "white").set("margin", "0");
        Span subtitle = new Span("Keep track of the movies you want to watch and rate the ones you have seen.");
        subtitle.getStyle().set("color", "rgba(255, 255, 255, 0.85)");
        VerticalLayout titles = new VerticalLayout(title, subtitle);
        titles.setPadding(false);
        titles.setSpacing(false);
        HorizontalLayout header = new HorizontalLayout(movieIcon, titles);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.getStyle()
            .set("background", "linear-gradient(135deg, #6d28d9, #db2777)")
            .set("border-radius", "16px")
            .set("padding", "20px 24px")
            .set("box-shadow", "0 4px 12px rgba(0, 0, 0, 0.15)");

        // toolbar: search, add button and statistics
        filter.setPlaceholder("Search by title or director");
        filter.setPrefixComponent(VaadinIcon.SEARCH.create());
        filter.setClearButtonVisible(true);
        filter.setWidth("320px");
        stats.getStyle()
            .set("background", "#f3e8ff")
            .set("color", "#6d28d9")
            .set("padding", "4px 12px")
            .set("border-radius", "999px")
            .set("font-weight", "600")
            .set("font-size", "14px");
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, stats);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // table
        grid.setHeight("340px");
        grid.setColumns("id", "title", "director", "genre", "releaseYear", "rating");
        grid.getColumnByKey("id").setWidth("60px").setFlexGrow(0);
        grid.getColumnByKey("releaseYear").setHeader("Year").setWidth("90px").setFlexGrow(0);
        grid.getColumnByKey("rating").setHeader("Rating").setWidth("90px").setFlexGrow(0);
        grid.addColumn(m -> Boolean.TRUE.equals(m.getWatched()) ? "Yes" : "No")
            .setHeader("Watched").setKey("watched").setWidth("100px").setFlexGrow(0);
        grid.getStyle()
            .set("border-radius", "16px")
            .set("box-shadow", "0 2px 8px rgba(0, 0, 0, 0.08)");

        // footer
        Div footer = new Div(new Span("First Java CRUD project - Helin Gulec (79879)"));
        footer.setWidthFull();
        footer.getStyle()
            .set("text-align", "center")
            .set("color", "#6b7280")
            .set("font-size", "13px")
            .set("margin-top", "16px");

        add(header, actions, grid, editor, footer);

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listMovies(e.getValue()));

        // Connect selected Movie to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> editor.editMovie(e.getValue()));

        // Create and edit a new Movie when the button is clicked
        addNewBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            editor.editMovie(new Movie("", "", null, null, null, false));
        });

        // Listen to changes made by the editor and refresh data from the database
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listMovies(filter.getValue());
        });

        // Initialize listing
        listMovies(null);
    }

    void listMovies(String filterText) {
        List<Movie> movies = StringUtils.hasText(filterText) ? repo.search(filterText) : repo.findAll();
        grid.setItems(movies);
        stats.setText("Movies: " + repo.count() + "  |  Watched: " + repo.countByWatchedTrue());
    }

}

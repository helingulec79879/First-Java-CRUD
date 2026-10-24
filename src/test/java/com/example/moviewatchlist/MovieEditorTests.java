package com.example.moviewatchlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieEditorTests {

    @Mock MovieRepository movieRepository;
    @InjectMocks MovieEditor editor;
    @Mock MovieEditor.ChangeHandler changeHandler;

    @BeforeEach
    public void init() {
        editor.setChangeHandler(changeHandler);
    }

    @Test
    public void shouldSaveMovieWhenEditorSaveClicked() {
        editor.editMovie(new Movie("", "", null, null, null, false));
        editor.title.setValue("Inception");
        editor.director.setValue("Christopher Nolan");

        editor.save();

        then(movieRepository).should().save(argThat(m ->
                "Inception".equals(m.getTitle()) && "Christopher Nolan".equals(m.getDirector())));
    }

    @Test
    public void shouldNotSaveMovieWithoutTitle() {
        editor.editMovie(new Movie("", "", null, null, null, false));
        editor.director.setValue("Christopher Nolan");

        editor.save();

        then(movieRepository).should(never()).save(any());
    }

    @Test
    public void shouldRemoveMovieWhenEditorDeleteClicked() {
        editor.editMovie(new Movie("Parasite", "Bong Joon-ho", "Thriller", 2019, 9, true));

        editor.delete();

        then(movieRepository).should().delete(argThat(m -> "Parasite".equals(m.getTitle())));
    }

}

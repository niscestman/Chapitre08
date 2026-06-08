package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookShelf")
class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() {
        shelf = new BookShelf();

        effectiveJava = new Book(
                "Effective Java",
                "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8)
        );

        codeComplete = new Book(
                "Code Complete",
                "Steve McConnel",
                LocalDate.of(2004, Month.JUNE, 9)
        );

        mythicalManMonth = new Book(
                "The Mythical Man-Month",
                "Frederick Phillips Brooks",
                LocalDate.of(1975, Month.JANUARY, 1)
        );

        cleanCode = new Book(
                "Clean Code",
                "Robert C. Martin",
                LocalDate.of(2008, Month.AUGUST, 1)
        );
    }

    @Nested
    @DisplayName("Quand la bibliothèque est vide")
    class EmptyShelf {

        @Test
        @DisplayName("aucun livre n'a été ajouté")
        void shelfEmptyWhenNoBookAdded() {
            assertTrue(
                    shelf.books().isEmpty(),
                    "BookShelf devrait être vide."
            );
        }

        @Test
        @DisplayName("add est appelé sans livre")
        void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();

            assertTrue(
                    shelf.books().isEmpty(),
                    "BookShelf devrait être vide."
            );
        }
    }

    @Nested
    @DisplayName("Après ajout de livres")
    class BooksAdded {

        @Test
        @DisplayName("contient deux livres lorsque deux livres sont ajoutés")
        void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            shelf.add(codeComplete, effectiveJava);

            assertEquals(
                    2,
                    shelf.books().size(),
                    "BookShelf devrait contenir deux livres."
            );
        }

        @Test
        @DisplayName("la collection retournée est immuable")
        void booksReturnedFromBookShelfIsImmutableForClient() {
            shelf.add(effectiveJava, codeComplete);

            List<Book> books = shelf.books();

            assertThrows(
                    UnsupportedOperationException.class,
                    () -> books.add(mythicalManMonth)
            );
        }
    }

    @Nested
    @DisplayName("Arrangement des livres")
    class Arrangement {

        @Test
        @DisplayName("par titre")
        void bookshelfArrangedByBookTitle() {

            shelf.add(
                    effectiveJava,
                    codeComplete,
                    mythicalManMonth
            );

            List<Book> books = shelf.arrange();

            assertEquals(
                    asList(
                            codeComplete,
                            effectiveJava,
                            mythicalManMonth
                    ),
                    books
            );
        }

        @Test
        @DisplayName("ne modifie pas l'ordre d'insertion")
        void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {

            shelf.add(
                    effectiveJava,
                    codeComplete,
                    mythicalManMonth
            );

            shelf.arrange();

            assertEquals(
                    asList(
                            effectiveJava,
                            codeComplete,
                            mythicalManMonth
                    ),
                    shelf.books()
            );
        }

        @Test
        @DisplayName("selon un comparateur fourni")
        void bookshelfArrangedByUserProvidedCriteria() {

            shelf.add(
                    effectiveJava,
                    codeComplete,
                    mythicalManMonth
            );

            List<Book> books =
                    shelf.arrange(
                            Comparator.<Book>naturalOrder().reversed()
                    );

            assertEquals(
                    asList(
                            mythicalManMonth,
                            effectiveJava,
                            codeComplete
                    ),
                    books
            );
        }
    }

    @Nested
    @DisplayName("Regroupement des livres")
    class Grouping {

        @Test
        @DisplayName("par année de publication")
        void groupBooksInsideBookShelfByPublicationYear() {

            shelf.add(
                    effectiveJava,
                    codeComplete,
                    mythicalManMonth,
                    cleanCode
            );

            Map<Year, List<Book>> booksByPublicationYear =
                    shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsEntry(
                            Year.of(2008),
                            Arrays.asList(
                                    effectiveJava,
                                    cleanCode
                            )
                    );

            assertThat(booksByPublicationYear)
                    .containsEntry(
                            Year.of(2004),
                            Collections.singletonList(codeComplete)
                    );

            assertThat(booksByPublicationYear)
                    .containsEntry(
                            Year.of(1975),
                            Collections.singletonList(mythicalManMonth)
                    );
        }

        @Test
        @DisplayName("selon le critère fourni par l'utilisateur (auteur)")
        void groupBooksByUserProvidedCriteria() {

            shelf.add(
                    effectiveJava,
                    codeComplete,
                    mythicalManMonth,
                    cleanCode
            );

            Map<String, List<Book>> booksByAuthor =
                    shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsEntry(
                            "Joshua Bloch",
                            Collections.singletonList(effectiveJava)
                    );

            assertThat(booksByAuthor)
                    .containsEntry(
                            "Steve McConnel",
                            Collections.singletonList(codeComplete)
                    );

            assertThat(booksByAuthor)
                    .containsEntry(
                            "Frederick Phillips Brooks",
                            Collections.singletonList(mythicalManMonth)
                    );

            assertThat(booksByAuthor)
                    .containsEntry(
                            "Robert C. Martin",
                            Collections.singletonList(cleanCode)
                    );
        }
    }
}
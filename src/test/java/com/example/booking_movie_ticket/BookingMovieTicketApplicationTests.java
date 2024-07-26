package com.example.booking_movie_ticket;

import com.example.booking_movie_ticket.entity.*;
import com.example.booking_movie_ticket.model.enums.Role;
import com.example.booking_movie_ticket.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class BookingMovieTicketApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void save_users(){
        Role userRole=Role.USER;
        Role adminRole=Role.ADMIN;

        User user1= User.builder()
                .name("khue")
                .email("khue1@gmail.com")
                .password(passwordEncoder.encode("123"))
                .enable(true)
                .role(adminRole)
                .build();
        userRepository.save(user1);

        User user2= User.builder()
                .name("khue")
                .email("khue2@gmail.com")
                .password(passwordEncoder.encode("123"))
                .enable(true)
                .role(userRole)
                .build();
        userRepository.save(user2);
    }
    @Test
    void saved_Movie() {
        Faker faker=new Faker();
        Slugify slugify= Slugify.builder().build();
        Boolean status=faker.bool().bool();
        Random rd=new Random();

        List<Country>countries=countryRepository.findAll();
        List<Genre>genres=genreRepository.findAll();
        List<Actor>actors=actorRepository.findAll();
        List<Director>directors=directorRepository.findAll();

        for (int i=0;i<100;i++){

            Country rdCountry=countries.get(rd.nextInt(countries.size()));

            List<Genre> rdGenres=new ArrayList<>();
            for (int j=0;j<rd.nextInt(2)+1;j++){
                Genre rdGenre=genres.get(rd.nextInt(genres.size()));
                if (!rdGenres.contains((rdGenre))){
                    rdGenres.add(rdGenre);
                }
            }
            List<Actor> rdActors=new ArrayList<>();
            for (int j=0;j<rd.nextInt(3)+5;j++){
                Actor rdActor=actors.get(rd.nextInt(actors.size()));
                if (!rdActors.contains((rdActor))){
                    rdActors.add(rdActor);
                }
            }
            List<Director> rdDirectors=new ArrayList<>();
            for (int j=0;j<rd.nextInt(2)+1;j++){
                Director rdDirector=directors.get(rd.nextInt(directors.size()));
                if (!rdDirectors.contains((rdDirector))){
                    rdDirectors.add(rdDirector);
                }
            }

            String name=faker.book().title();
            Movie movie= Movie.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .description(faker.lorem().paragraph())
                    .poster("https://placehold.co/600x400?text="+name.substring(0,1).toLowerCase())
                    .releaseYear(faker.number().numberBetween(2000,2024))
                    .rating(faker.number().randomDouble(1,1,5))
                    .trailerURL("src=\"https://www.youtube.com/embed/hY1nE5vAriQ?si=vKKw9LOj0MFiBsQT\"")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .publishedAt(status?LocalDateTime.now():null)
                    .country(rdCountry)
                    .genres(rdGenres)
                    .actors(rdActors)
                    .directors(rdDirectors)
                    .build();
            movieRepository.save(movie);
        }
    }
    @Test
    void save_countries() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 20; i++) {
            String name = faker.country().name();
            Country country = Country.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            countryRepository.save(country);
        }
    }

    @Test
    void save_genres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 20; i++) {
            String name = faker.book().genre();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_directors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        for (int i = 0; i < 30; i++) {
            String name = faker.name().fullName();
            Director director = Director.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name)
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            directorRepository.save(director);
        }
    }

    @Test
    void save_actors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Actor actor = Actor.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name)
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            actorRepository.save(actor);
        }
    }

}

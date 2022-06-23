package ru.siaw.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Database {
    @Getter private static final List<User> users = new ArrayList<>();
    private static final File file = new File("database.json");

    private Database() {
        throw new IllegalStateException("Utility class");
    }

    public static void read() {
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                User[] data = mapper.readValue(file, User[].class);

                if (data != null && data.length > 0)
                    users.addAll(Arrays.asList(data));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void write() {
        try {
            FileWriter writer = new FileWriter(file, false);
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(writer, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User findUser(long id) {
        Optional<User> search = new ArrayList<>(users).stream()
                .filter(user -> user.getId() == id)
                .findFirst();

        if (!search.isPresent()) {
            User user = new User(id);
            users.add(user);

            return user;
        }

        return search.get();
    }
}

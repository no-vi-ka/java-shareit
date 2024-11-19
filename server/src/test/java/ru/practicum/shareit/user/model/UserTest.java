package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    void usersShouldBeEqualWhenDataIsEqual() {
        User user1 = User.builder()
                .id(1L)
                .name("Username")
                .email("mail@mail.com").build();
        User user2 = User.builder()
                .id(1L)
                .name("Username")
                .email("mail@mail.com").build();
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getEmail(), user2.getEmail());
    }
}

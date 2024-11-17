package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotOwnerException extends RuntimeException {
    public NotOwnerException(String message) {
        super(message);
        log.error(message);
    }
}
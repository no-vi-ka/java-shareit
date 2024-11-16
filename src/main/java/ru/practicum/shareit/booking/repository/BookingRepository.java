package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.status.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBookerId(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :bookerId " +
            "AND current_timestamp < b.start")
    List<Booking> findByBookerAndStateCurrent(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :bookerId " +
            "AND current_timestamp > b.end")
    List<Booking> findByBookerAndStatePast(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :bookerId " +
            "AND current_timestamp < b.start")
    List<Booking> findByBookerAndStateFuture(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :bookerId " +
            "AND b.status = :status")
    List<Booking> findAllByBookerIdAndStatus(Integer bookerId, Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = :ownerId")
    List<Booking> findAllByOwnerId(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = :ownerId " +
            "AND current_timestamp < b.start")
    List<Booking> findByOwnerAndStateCurrent(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = :ownerId " +
            "AND current_timestamp > b.end")
    List<Booking> findByOwnerAndStatePast(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = :ownerId " +
            "AND current_timestamp < b.start")
    List<Booking> findByOwnerAndStateFuture(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = :ownerId " +
            "AND b.status = :status")
    List<Booking> findAllByOwnerIdAndStatus(Integer ownerId, Status status);

    List<Booking> findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(Integer userId, Integer itemId, Status status, LocalDateTime now);
}
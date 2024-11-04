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
            "WHERE b.booker.id = ?1 " +
            "AND current_timestamp < b.start")
    List<Booking> findByBookerAndStateCurrent(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1 " +
            "AND current_timestamp > b.end")
    List<Booking> findByBookerAndStatePast(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1 " +
            "AND current_timestamp < b.start")
    List<Booking> findByBookerAndStateFuture(Integer bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1 " +
            "AND b.status = ?2")
    List<Booking> findAllByBookerIdAndStatus(Integer bookerId, Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = ?1")
    List<Booking> findAllByOwnerId(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = ?1 " +
            "AND current_timestamp < b.start")
    List<Booking> findByOwnerAndStateCurrent(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = ?1 " +
            "AND current_timestamp > b.end")
    List<Booking> findByOwnerAndStatePast(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = ?1 " +
            "AND current_timestamp < b.start")
    List<Booking> findByOwnerAndStateFuture(Integer ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.owner.id = ?1 " +
            "AND b.status = ?2")
    List<Booking> findAllByOwnerIdAndStatus(Integer ownerId, Status status);

    Boolean existsByBookerIdAndItemIdAndEndBefore(Integer bookerId, Integer itemId, LocalDateTime localDateTime);

    List<Booking> findAllByBookerIdAndItemIdAndStatusAndEndBefore(Integer userId, Integer itemId, Status status, LocalDateTime now);
}
package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query(value = "select r " +
            "from ItemRequest r " +
            "where r.requestor.id <> :requestorId " +
            "order by r.created desc")
    List<ItemRequest> findByOtherUsers(@Param("requestorId") Long requestorId);

    List<ItemRequest> findByRequestorIdOrderByCreatedDesc(Long requestorId);
}
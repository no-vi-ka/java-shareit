package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByOwnerId(Integer userId);

    @Query("select i " +
            "from Item as i " +
            "where i.available = true " +
            "and (lower(i.name) like (lower (concat ('%',:text, '%')))" +
            "or lower(i.description) like (lower (concat ('%',:text, '%'))))")
    List<Item> search(String text);
}
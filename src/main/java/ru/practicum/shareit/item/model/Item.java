package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;


@Getter
@Setter
@Entity
@Builder
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", nullable = false, length = 1024)
    private String description;
    @Column(name = "is_available", nullable = false)
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ItemRequest request;
}
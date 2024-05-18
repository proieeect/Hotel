package hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hotel.controller.request.RoomRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Room {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_room_id")
    private Hotel hotel;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_room_reservation_id")
    private Book book;
    private Long price;
    private Boolean isAvailable;
    private Integer roomNumber;

    public static Room map(RoomRequestDTO roomRequestDTO){
        return Room.builder().
                type(roomRequestDTO.getType())
                .roomNumber(roomRequestDTO.getRoomNumber())
                .price(roomRequestDTO.getPrice())
                .isAvailable(roomRequestDTO.getIsAvailable())
                .build();
    }
}

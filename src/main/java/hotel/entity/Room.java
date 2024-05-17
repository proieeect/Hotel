package hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
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
}

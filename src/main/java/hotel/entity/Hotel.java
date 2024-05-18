package hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long latitude;
    private Long longitude;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    public Hotel(String name, Long latitude, Long longitude, List<Room> rooms) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rooms = rooms;
    }
}

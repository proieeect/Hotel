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
    private int id;
    private String name;
    private Long price;
    private long latitude;
    private long longitude;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}

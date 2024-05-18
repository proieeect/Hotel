package hotel.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDTO {
    private String name;
    private Long latitude;
    private Long longitude;
    private List<RoomRequestDTO> rooms;
}

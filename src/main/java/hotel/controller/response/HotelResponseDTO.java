package hotel.controller.response;

import hotel.entity.Hotel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponseDTO {
    private Long id;
    private String hotelName;
    public static HotelResponseDTO map(Hotel hotel){
        return HotelResponseDTO.builder()
                .id(hotel.getId())
                .hotelName(builder().hotelName)
                .build();
    }
}

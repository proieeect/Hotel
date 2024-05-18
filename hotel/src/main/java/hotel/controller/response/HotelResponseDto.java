package hotel.controller.response;

import hotel.entity.Hotel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponseDto {
    private Long id;
    private String hotelName;
    public static HotelResponseDto map(Hotel hotel){
        return HotelResponseDto.builder()
                .id(hotel.getId())
                .hotelName(builder().hotelName)
                .build();
    }
}

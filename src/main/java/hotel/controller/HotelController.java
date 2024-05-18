package hotel.controller;

import hotel.controller.request.HotelRequestDTO;
import hotel.controller.request.RoomRequestDTO;
import hotel.controller.request.UserPositionRequestDTO;
import hotel.controller.response.HotelResponseDTO;
import hotel.entity.Hotel;
import hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping(value = "/hotels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HotelController {
    @Autowired
    private HotelService hotelService;

    //dropdown
    @PostMapping(value = "/position")
    public ResponseEntity<List<HotelResponseDTO>> getHotelsByUserPosition(@RequestBody UserPositionRequestDTO userPositionRequestDTO) {
        return new ResponseEntity<>(hotelService.getHotelsByCoordinates(userPositionRequestDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelResponseDTO> getHotelsById(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getHotelByID(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody HotelRequestDTO hotelRequestDTO) {
        return new ResponseEntity<>(hotelService.createHotel(hotelRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable("id") Long id) {
        if (hotelService.cancelBooking(id) != true) {
            return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.OK);
    }
}

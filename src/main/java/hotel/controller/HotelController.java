package hotel.controller;

import hotel.controller.request.HotelRequestDTO;

import hotel.controller.request.UserPositionRequest;
import hotel.controller.response.HotelResponseDto;
import hotel.entity.Hotel;
import hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/hotels")
@RestController
public class HotelController {
    @Autowired
    private HotelService hotelService;

    //dropdown
    @PostMapping(value = "/position")
    public ResponseEntity<List<HotelResponseDto>> getHotelsByUserPosition(@RequestBody UserPositionRequest userPositionRequest) {
        return new ResponseEntity<>(hotelService.getHotelsByCoordinates(userPositionRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelResponseDto> getHotelsById(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getHotelByID(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/type-rooms/{id}")
    public ResponseEntity<String[]> getRoomsTypeForHotel(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getRoomsTypeForHotel(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody HotelRequestDTO hotel) {
        return new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable("id") Long id) {
        if (hotelService.cancelBooking(id) != true) {
            return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.OK);
    }
}

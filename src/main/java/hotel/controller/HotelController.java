package hotel.controller;

import hotel.controller.request.UserPositionRequest;
import hotel.controller.response.HotelResponseDto;
import hotel.entity.Hotel;
import hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("Hotel")
@RestController("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    //dropdown
    @PostMapping(value = "/position")
    public ResponseEntity<List<HotelResponseDto>> getHotelsByUserPosition(@RequestBody UserPositionRequest userPositionRequest, @PathVariable Long userRadiusPreference) {
        return new ResponseEntity<>(hotelService.getHotelsByCoordinates(userPositionRequest, userRadiusPreference), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelResponseDto> getHotelsById(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getHotelByID(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> cancelBooking(@PathVariable("id") Long id) {
        if (hotelService.cancelBooking(id)!=true) {
            return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(hotelService.cancelBooking(id), HttpStatus.OK);
    }
}

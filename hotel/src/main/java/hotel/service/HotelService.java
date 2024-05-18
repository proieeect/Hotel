package hotel.service;

import hotel.controller.request.HotelRequestDTO;
import hotel.controller.request.UserPositionRequest;
import hotel.controller.response.HotelResponseDto;
import hotel.entity.Book;
import hotel.entity.Hotel;
import hotel.entity.Room;
import hotel.repository.BookRepository;
import hotel.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<HotelResponseDto> getHotelsByCoordinates(UserPositionRequest userPositionRequest) {
        Long userLatitude = userPositionRequest.getLatitude();
        Long userLongitude = userPositionRequest.getLongitude();

        List<Hotel> filteredHotels = hotelRepository.findAll().stream().toList();

        List<HotelResponseDto> newListFiltered = new ArrayList<>();

        for (Hotel oneHotel : filteredHotels) {
            Long distanceBetweenUserAndHotel = convertPositionsBetweenUserAndHotel(userLatitude, userLongitude, oneHotel.getLatitude(), oneHotel.getLongitude());
            if (distanceBetweenUserAndHotel <= userPositionRequest.getRadius()) {
                HotelResponseDto hotelResponseDto = new HotelResponseDto(oneHotel.getId(), oneHotel.getName());
                newListFiltered.add(hotelResponseDto);
            }
        }

        return newListFiltered;
    }

    public HotelResponseDto getHotelByID(Long id) {
        return HotelResponseDto.map(hotelRepository.findById(id).get());
    }

    public String[] getRoomsTypeForHotel(Long id) {
        return (String[]) hotelRepository.findById(id).get().getRooms().stream().map(Room::getType).toArray();
    }

    @Transactional
    public Hotel createHotel(HotelRequestDTO hotelRequestDTO) {
        List<Room> rooms = new ArrayList<>();

        hotelRequestDTO.getRooms().stream().forEach(
                e -> {
                    Room room = Room.map(e);
                    rooms.add(room);
                }
        );

        Hotel hotel = new Hotel(hotelRequestDTO.getName(), hotelRequestDTO.getLatitude(), hotelRequestDTO.getLongitude(), rooms);
        return hotelRepository.save(hotel);
    }

    public boolean cancelBooking(Long id) {
        Book oneBooking = bookRepository.findById(id).get();
        if (oneBooking.getCodeReservation().equals(id)) {
            bookRepository.delete(oneBooking);
        }
        return true;
    }

    private static final Long EARTH_RADIUS = Long.valueOf(6371000); // Raza medie a Pământului în metri

    public static Long convertPositionsBetweenUserAndHotel(Long userLatitude, Long userLongitude, Long hotelLatitude, Long hotelLongitude) {

        Long totalLatitudeDistance = Math.round(toRadians(hotelLatitude - userLatitude));
        Long totalLongitudeDistance = Math.round(toRadians(hotelLongitude - userLongitude));
        Long a = Math.round(sin(totalLatitudeDistance / 2) * sin(totalLatitudeDistance / 2)
                + cos(toRadians(userLatitude)) * cos(toRadians(hotelLatitude))
                * sin(totalLongitudeDistance / 2) * sin(totalLongitudeDistance / 2));
        Long c = Math.round(2 * atan2(sqrt(a), sqrt(1 - a)));
        return Long.valueOf(EARTH_RADIUS * c);


    }

}

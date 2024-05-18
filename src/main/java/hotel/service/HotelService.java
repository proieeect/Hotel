package hotel.service;

import hotel.controller.request.HotelRequestDTO;
import hotel.controller.request.UserPositionRequestDTO;
import hotel.controller.response.HotelResponseDTO;
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
import java.util.stream.Collectors;

import static java.lang.Math.*;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<HotelResponseDTO> getHotelsByCoordinates(UserPositionRequestDTO userPositionRequestDTO) {
        Long userLatitude = userPositionRequestDTO.getLatitude();
        Long userLongitude = userPositionRequestDTO.getLongitude();

        List<Hotel> filteredHotels = hotelRepository.findAll().stream().toList();

        List<HotelResponseDTO> newListFiltered = new ArrayList<>();
        for (Hotel oneHotel : filteredHotels) {
            Long distanceBetweenUserAndHotel = convertPositionsBetweenUserAndHotel(userLatitude, userLongitude, oneHotel.getLatitude(), oneHotel.getLongitude());
            if (distanceBetweenUserAndHotel <= (long) (userPositionRequestDTO.getRadius()*0.001)){
                HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(oneHotel.getId(), oneHotel.getName());
                newListFiltered.add(hotelResponseDTO);
            }
        }
        return newListFiltered;
    }

    public HotelResponseDTO getHotelByID(Long id) {
        return HotelResponseDTO.map(hotelRepository.findById(id).get());
    }

    @Transactional
    public Hotel createHotel(HotelRequestDTO hotelRequestDTO) {
        //daca nu trebuie sa crrezi repository room si sa le salvezi mai intai rooms
        //hotel.getLiSTArOOM().forEach(rommRepository.save(room))
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
        Hotel hotel = hotelRepository.findById(id).get();
        if (oneBooking.equals(hotel)) {
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

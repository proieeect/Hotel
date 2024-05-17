package hotel.service;

import hotel.controller.request.UserPositionRequest;
import hotel.controller.response.HotelResponseDto;
import hotel.entity.Hotel;
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

    public List<HotelResponseDto> getHotelsByCoordinates(UserPositionRequest userPositionRequest) {
        // convert in m user position
        double userM = 10;
        // convert in m hotel position
//        Map<String, Long[]> meters = new HashMap<>();
//        hotelRepository.findAll().forEach(
//                e -> {
//                    meters.put(e.getName(), generateHotelPositionInMeter(e.getLatitude(), e.getLongitude()));
//                }
//        );

//         logica lu narcis parcurgi hotelM +
        List<HotelResponseDto> list = new ArrayList<>();
//        lista.add(new HotelResponseDto(hotelRepository.findByName(), hotelRepository.findByName(name)) )
        return list;
//        lista.stream().anyMatch(e->e.getId()==meters.keySet().)
        if (meters.keySet().contains(list.stream().findAny())
//               &&(userPositionRequest.getRadius()<=meters.keySet().iterator())

    }

    public HotelResponseDto getHotelByID(int id) {
        return HotelResponseDto.map(hotelRepository.findById(id).get());
    }

    @Transactional
    public Hotel createHotel(Hotel hotel) {
        //daca nu trebuie sa crrezi repository room si sa le salvezi mai intai rooms
        //hotel.getLiSTArOOM().forEach(rommRepository.save(room))
        return hotelRepository.save(hotel); // rooms se salveaza?
    }

//    private Long[] generateHotelPositionInMeter(Long latitude, Long longitude) {
//        Long[] meters = new Long[2];
//
//        // Raza ecuatorială a elipsoidului WGS84 (m)
//        Long a = Math.round(6378137.0);
//        // Aplatisarea elipsoidului WGS84
//        Long f = Math.round(1 / 298.257223563);
//
//        Long phi = Math.round(Math.toRadians(latitude));
//        Long lambda = Math.round(Math.toRadians(longitude));
//
//        Long sinPhi = Math.round(Math.sin(phi));
//        Long e2 = f * (2 - f);
//        Long N = Math.round(a / Math.sqrt(1 - e2 * sinPhi * sinPhi));
//
//        Long x = Math.round(N * Math.cos(phi) * Math.cos(lambda));
//        Long y = Math.round(N * Math.cos(phi) * Math.sin(lambda));
//
//        meters[0] = x;
//        meters[1] = y;
//
//        return meters;
//    }

//    private Long[] generateUserPositionInMeter(long latitude, long longitude) {
//        Long[] meters = new Long[2];
//
//        // Raza ecuatorială a elipsoidului WGS84 (m)
//        Long a = Math.round(6378137.0);
//        // Aplatisarea elipsoidului WGS84
//        Long f = Math.round(1 / 298.257223563);
//
//        Long phi = Math.round(Math.toRadians(latitude));
//        Long lambda = Math.round(Math.toRadians(longitude));
//
//        Long sinPhi = Math.round(Math.sin(phi));
//        Long e2 = f * (2 - f);
//        Long N = Math.round(a / Math.sqrt(1 - e2 * sinPhi * sinPhi));
//
//        Long x = Math.round(N * Math.cos(phi) * Math.cos(lambda));
//        Long y = Math.round(N * Math.cos(phi) * Math.sin(lambda));
//
//        meters[0] = x;
//        meters[1] = y;
//
//        return meters;
//    }

    private static final double EARTH_RADIUS = 6371000; // Raza medie a Pământului în metri

    public static double convertPositionsBetweenUserAndHotel(double userLatitude, double userLongitude, double hotelLatitude, double hotelLongitude) {

        double totalLatitudeDistance = toRadians(hotelLatitude - userLatitude);
        double totalLongitudeDistance = toRadians(hotelLongitude - userLongitude);
        double a = sin(totalLatitudeDistance / 2) * sin(totalLatitudeDistance / 2)
                + cos(toRadians(userLatitude)) * cos(toRadians(hotelLatitude))
                * sin(totalLongitudeDistance / 2) * sin(totalLongitudeDistance / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return EARTH_RADIUS * c;


    }

}

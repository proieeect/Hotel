//package hotel.util;
//
//import hotel.controller.request.HotelRequestDTO;
//import hotel.controller.response.HotelResponseDto;
//import hotel.entity.Hotel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "Hotel App Spring Model")
//public interface MapperUtilClass {
//
//    MapperUtilClass MAPPER = Mappers.getMapper(MapperUtilClass.class);
//
//    //    @Mapping(target = "employeeId", source = "entity.id")
//    //    @Mapping(target = "employeeName", source = "entity.name")
//
//    HotelResponseDto hotelToHotelResponseDTO(Hotel entity);
//
//    @Mapping(target = "HotelResponseDto", expression = "java(Hotel)")
//    HotelResponseDto hotelToHotelResponseDTO(Hotel hotel);
//
//    HotelRequestDTO hotelToHotelRequestDTO(Hotel entity);
//
//    @Mapping(target = "HotelRequestDTO", expression = "java(Hotel)")
//    Hotel hotelRequestDTOtoHotel(HotelRequestDTO hotelRequestDTO);
//
//
//    default List<Hotel> listHotelRequestDTOToHotel(List<HotelRequestDTO> hotelRequestDTO) {
//        return hotelRequestDTO.stream()
//                .map(e -> hotelRequestDTOtoHotel(e))
//                .collect(Collectors.toList());
//    }
//
//    default List<HotelResponseDto> listHotelToHotelResponseDTOList(List<Hotel> hotelList) {
//        return hotelList.stream()
//                .map(e -> hotelToHotelResponseDTO(e))
//                .collect(Collectors.toList());
//    }
//
//}

//package ru.practicum.explore.event.dto;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//@Mapper
//public interface Test {
//    // will map all other fields that you specify
//    @Mapping( target = "lat", ignore = true )
//    @Mapping( target = "lon", ignore = true )
//    Location map(Float s1, Float s2);
//
//    default Location map(Float s1, Float s2, @MappingTarget Location t) {
//        System.out.println(s1 + "@@@@@@@#######################");
//        return new Location(s1, s2);
//        // do whatever you like with city_names and technologies
//    }
//
//    @Mapping(target = "location", ignore = true)
//    String lat(Location location)
//}

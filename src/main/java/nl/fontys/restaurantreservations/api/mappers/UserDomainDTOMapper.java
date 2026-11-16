package nl.fontys.restaurantreservations.api.mappers;

import nl.fontys.restaurantreservations.api.dtos.UserDTO;
import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserDomainDTOMapper {
    public UserDomain toDomain(UserDTO dto) {
        return new UserDomain(
                dto.email(),
                dto.name(),
                dto.password(),
                dto.role()
        );
    }

    public List<UserDomain> listToDomain(List<UserDTO> dtos) {
        List<UserDomain> domains = new java.util.ArrayList<>(Collections.emptyList());
        for (UserDTO dto : dtos)
        {
            domains.add(toDomain(dto));
        }
        return domains;
    }

    public UserDTO toDTO(UserDomain domain) {
        return new UserDTO(
                domain.getEmail(),
                domain.getName(),
                domain.getPassword(),
                domain.getRole()
        );
    }

    public List<UserDTO> listToDTO(List<UserDomain> domains) {
        List<UserDTO> dtos = new java.util.ArrayList<>(Collections.emptyList());
        for (UserDomain domain : domains)
        {
            dtos.add(toDTO(domain));
        }
        return dtos;
    }
}

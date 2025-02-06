package com.susanafigueroa.userservice.service.handler;

import com.susanafigueroa.user.UserInformation;
import com.susanafigueroa.user.UserInformationRequest;
import com.susanafigueroa.userservice.entity.PortfolioItem;
import com.susanafigueroa.userservice.entity.User;
import com.susanafigueroa.userservice.exceptions.UnknownUserException;
import com.susanafigueroa.userservice.repository.PortfolioItemRepository;
import com.susanafigueroa.userservice.repository.UserRepository;
import com.susanafigueroa.userservice.util.EntityMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/// for handling the user information and give us the response
@Service
public class UserInformationRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public UserInformationRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    // obtengo la información desde la request gRPC (UserInformationRequest)
    // envío la información en DTO gRPC (UserInformation)
    public UserInformation getUserInformation(UserInformationRequest request) {
        // busco el user en la base de datos
        // si no existe el user en la base de datos lanza excepción
        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknownUserException(request.getUserId()));

        // obtengo el listado de PortfolioItems del user
        List<PortfolioItem> listPortfolioItems = this.portfolioItemRepository.findAllByUserId(request.getUserId());

        // transformo las entidades User y PortfolioItem de la db JPA en un DTO gRPC (UserInformation) usando el método
        // toUserInformation para enviarlo como respuesta al cliente
        return EntityMessageMapper.toUserInformation(user, listPortfolioItems);
    }

}

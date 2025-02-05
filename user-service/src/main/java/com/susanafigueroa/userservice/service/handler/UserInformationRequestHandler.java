package com.susanafigueroa.userservice.service.handler;

import com.susanafigueroa.user.UserInformation;
import com.susanafigueroa.user.UserInformationRequest;
import com.susanafigueroa.userservice.entity.PortfolioItem;
import com.susanafigueroa.userservice.entity.User;
import com.susanafigueroa.userservice.exceptions.UnknownUserException;
import com.susanafigueroa.userservice.repository.PortfolioItemRepository;
import com.susanafigueroa.userservice.repository.UserRepository;
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

    public UserInformation getUserInformation(UserInformationRequest request) {
        // obtain user
        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknownUserException(request.getUserId()));

        // obtain the Portfolioitems list of the user
        List<PortfolioItem> listPortfolioItems = this.portfolioItemRepository.findAllByUserId(request.getUserId());

    }

}

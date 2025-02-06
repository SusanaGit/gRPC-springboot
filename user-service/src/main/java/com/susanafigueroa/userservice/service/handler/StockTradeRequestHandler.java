package com.susanafigueroa.userservice.service.handler;

import com.susanafigueroa.common.Ticker;
import com.susanafigueroa.user.StockTradeRequest;
import com.susanafigueroa.user.StockTradeResponse;
import com.susanafigueroa.userservice.exceptions.UnknownTickerException;
import com.susanafigueroa.userservice.exceptions.UnknownUserException;
import com.susanafigueroa.userservice.repository.PortfolioItemRepository;
import com.susanafigueroa.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StockTradeRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public StockTradeRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    // the user can buy stock
    public StockTradeResponse buyStock(StockTradeRequest request) {
        // validate ticker
        this.validateTicker(request.getTicker());

        // validate user
        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknownUserException(request.getUserId()));


    }

    // validate the ticker
    private void validateTicker(Ticker ticker) {
        if(Ticker.UNKNOWN.equals(ticker)) {
            throw new UnknownTickerException();
        }
    }

    // the user can sell stock

}

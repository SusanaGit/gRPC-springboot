package com.susanafigueroa.userservice.service.handler;

import com.susanafigueroa.common.Ticker;
import com.susanafigueroa.user.StockTradeRequest;
import com.susanafigueroa.user.StockTradeResponse;
import com.susanafigueroa.userservice.exceptions.InsufficientBalanceException;
import com.susanafigueroa.userservice.exceptions.UnknownTickerException;
import com.susanafigueroa.userservice.exceptions.UnknownUserException;
import com.susanafigueroa.userservice.repository.PortfolioItemRepository;
import com.susanafigueroa.userservice.repository.UserRepository;
import com.susanafigueroa.userservice.util.EntityMessageMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StockTradeRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public StockTradeRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Transactional
    // the user can buy stock
    public StockTradeResponse buyStock(StockTradeRequest request) {
        // validate ticker
        this.validateTicker(request.getTicker());

        // validate user
        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknownUserException(request.getUserId()));

        // validate the user has enough balance to purchase the stock(s)
        var totalPrice = request.getQuantity() * request.getPrice();
        this.validateUserbalance(user.getId(), user.getBalance(), totalPrice);

        // valid request: update the user balance after buy
        user.setBalance(user.getBalance() - totalPrice);

        // increase the quantity of the stock
        this.portfolioItemRepository.findByUserIdAndTicker(user.getId(), request.getTicker())
                .ifPresentOrElse(
                        // si el user ya tiene este tipo de acción, aumenta la cantidad
                        item -> item.setQuantity(item.getQuantity() + request.getQuantity()),
                        // si el user aún no tiene este tipo de acción, se crea un nuevo registro en la db
                        // con la información de la compra de la/s nuevas acciones
                        () -> this.portfolioItemRepository.save(EntityMessageMapper.toPortfolioItem(request))
                );

        return EntityMessageMapper.toStockTradeResponse(request, user.getBalance());
    }

    // to validate the ticker
    private void validateTicker(Ticker ticker) {
        if(Ticker.UNKNOWN.equals(ticker)) {
            throw new UnknownTickerException();
        }
    }

    // to validate the user has enough balance to purchase the stock(s)
    private void validateUserbalance(Integer userId, Integer userBalance, Integer totalPrice) {
        if(totalPrice > userBalance) {
            throw new InsufficientBalanceException(userId);
        }
    }

    // the user can sell stock

}

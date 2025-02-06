package com.susanafigueroa.userservice.util;

import com.susanafigueroa.user.Holding;
import com.susanafigueroa.user.StockTradeRequest;
import com.susanafigueroa.user.StockTradeResponse;
import com.susanafigueroa.user.UserInformation;
import com.susanafigueroa.userservice.entity.PortfolioItem;
import com.susanafigueroa.userservice.entity.User;

import java.util.List;

public class EntityMessageMapper {

    // transforma las entidades User y PortfolioItem de JPA en un DTO gRPC (UserInformation) para enviarlo al cliente
    public static UserInformation toUserInformation(User user, List<PortfolioItem> listPortfolioItems) {
        var holdings = listPortfolioItems.stream()
                .map(i -> Holding.newBuilder().setTicker(i.getTicker()).setQuantity(i.getQuantity()).build())
                .toList();

        return UserInformation.newBuilder()
                .setUserId(user.getId())
                .setName(user.getName())
                .setBalance(user.getBalance())
                .addAllHoldings(holdings)
                .build();
    }

    // toma un DTO gRPC (StockTradeRequest) que viene del cliente y lo convierte en una entidad JPA (PortfolioItem)
    // para añadirlo a la base de datos
    public static PortfolioItem toPortfolioItem(StockTradeRequest request) {
        var item = new PortfolioItem();
        item.setUserId(request.getUserId());
        item.setTicker(request.getTicker());
        item.setQuantity(request.getQuantity());
        return item;
    }

    // toma un objeto DTO gRPC (StockTradeRequest) y el balance actualizado del usuario, y genera
    // otro DTO gRPC (StockTradeResponse) para enviarlo al cliente con el nuevo balance
    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request, int balance) {
        return StockTradeResponse.newBuilder()
                .setUserId(request.getUserId())
                .setPrice(request.getPrice())
                .setTicker(request.getTicker())
                .setQuantity(request.getQuantity())
                .setAction(request.getAction())
                .setTotalPrice(request.getPrice() * request.getQuantity())
                .setBalance(balance)
                .build();
    }
}

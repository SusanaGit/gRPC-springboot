package com.susanafigueroa.userservice.util;

import com.susanafigueroa.user.Holding;
import com.susanafigueroa.user.UserInformation;
import com.susanafigueroa.userservice.entity.PortfolioItem;
import com.susanafigueroa.userservice.entity.User;

import java.util.List;

// to convert User and PortfolioItem entities of the db JPA to a DTO gRPC (UserInformation)
// UserInformation es un objeto gRPC generado desde .proto , diseñado para transportar datos
public class EntityMessageMapper {

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
}

package com.susanafigueroa.userservice.service;

import com.susanafigueroa.user.*;
import com.susanafigueroa.userservice.service.handler.StockTradeRequestHandler;
import com.susanafigueroa.userservice.service.handler.UserInformationRequestHandler;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// permite que Spring detecte e inyecte el servicio automáticamente en el servidor gRPC
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserInformationRequestHandler userInformationRequestHandler;
    private final StockTradeRequestHandler stockTradeRequestHandler;

    public UserService(UserInformationRequestHandler userInformationRequestHandler, StockTradeRequestHandler stockTradeRequestHandler) {
        this.userInformationRequestHandler = userInformationRequestHandler;
        this.stockTradeRequestHandler = stockTradeRequestHandler;
    }

    // cuando el cliente envía la request, aquí es donde se recibe
    @Override
    public void getUserInformation(UserInformationRequest request, StreamObserver<UserInformation> responseObserver) {
        // busca la información del usuario en la base de datos
        // convierte la información en un objeto UserInformation (DTO gRPC)
        var userInformation = this.userInformationRequestHandler.getUserInformation(request);

        // envía la respuesta al cliente
        responseObserver.onNext(userInformation);

        // respuesta completada y finaliza la comunicación
        responseObserver.onCompleted();
    }

    @Override
    public void tradeStock(StockTradeRequest request, StreamObserver<StockTradeResponse> responseObserver) {
        log.info("{}", request);
        var response = TradeAction.SELL.equals(request.getAction()) ?
                this.stockTradeRequestHandler.buyStock(request) :
                this.stockTradeRequestHandler.sellStock(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

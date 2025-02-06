package com.susanafigueroa.userservice.service;

import com.susanafigueroa.user.*;
import com.susanafigueroa.userservice.service.handler.UserInformationRequestHandler;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

// permite que Spring detecte e inyecte el servicio automáticamente en el servidor gRPC
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserInformationRequestHandler userInformationRequestHandler;

    public UserService(UserInformationRequestHandler userInformationRequestHandler) {
        this.userInformationRequestHandler = userInformationRequestHandler;
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
    }
}

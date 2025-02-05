package com.susanafigueroa.userservice.service;

import com.susanafigueroa.user.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

// permite que Spring detecte e inyecte el servicio automáticamente en el servidor gRPC
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    // cuando el cliente envía la request, aquí es donde se recibe
    @Override
    public void getUserInformation(UserInformationRequest request, StreamObserver<UserInformation> responseObserver) {
    }

    @Override
    public void tradeStock(StockTradeRequest request, StreamObserver<StockTradeResponse> responseObserver) {
    }
}

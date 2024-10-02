package com.example.apigateway.configuration;

import com.example.apigateway.exception.CustomException;
import com.example.apigateway.exception.define.ErrorCode;
import com.example.apigateway.exception.define.ErrorMessage;
import com.example.apigateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

     IdentityService identityService;

    @NonFinal
    private String[] publicEndpoints = {
            "/auth/.*",
            "/api/user/registration"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        //Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader)) {
            throw new CustomException(ErrorMessage.UNAUTHENTICATED, ErrorCode.unauthorized);
        }

        String token = authHeader.get(0).replace("Bearer ", "");

        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getResult().isValid())
                return chain.filter(exchange);
            else
                return Mono.error((Throwable) new CustomException(ErrorMessage.UNAUTHENTICATED, ErrorCode.unauthorized));
        });

    }

    private boolean isPublicEndpoint(ServerHttpRequest request){
        return Arrays.stream(publicEndpoints)
                .anyMatch(s -> request.getURI().getPath().matches(s));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

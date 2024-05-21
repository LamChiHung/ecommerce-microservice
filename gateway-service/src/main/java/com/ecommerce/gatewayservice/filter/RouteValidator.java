package com.ecommerce.gatewayservice.filter;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

  public static final List<String> openApiEndpoints = List.of(
      "/api/auth/register",
      "/api/auth/login",
      "/api/products",
      "/eureka"
  );
  public static final List<String> sellerApiEndpoints = List.of(
      "/api/products/sellers",
      "/api/orders/sellers"
  );


  public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
      .noneMatch(uri -> request.getURI().getPath().contains(uri));
  public Predicate<ServerHttpRequest> isSellerSecured = request -> sellerApiEndpoints.stream()
      .anyMatch(uri -> request.getURI().getPath().contains(uri));

}

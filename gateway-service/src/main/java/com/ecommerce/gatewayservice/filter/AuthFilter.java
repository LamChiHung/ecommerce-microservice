package com.ecommerce.gatewayservice.filter;

import com.ecommerce.gatewayservice.client.dto.ResponseDto;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  public AuthFilter() {
    super(Config.class);
  }

  @Autowired
  private RouteValidator routeValidator;
  @Autowired
  private RestTemplate restTemplate;
  @Value("${application.config.auth-url}")
  private String authUrl;

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      if (routeValidator.isSecured.test(exchange.getRequest())
          || routeValidator.isSellerSecured.test(exchange.getRequest())) {
//               header contains token or not
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)
            .get(0);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          authHeader = authHeader.substring(7);
        }
        try {
//                    REST call to AUTH service
          ResponseDto<?> response;
          if (routeValidator.isSellerSecured.test(exchange.getRequest())) {
            String url = authUrl + "/validate/sellers?token=" + authHeader;
            response = restTemplate.getForObject(url, ResponseDto.class);
          } else {
            String url = authUrl + "/validate/customers?token=" + authHeader;
            response = restTemplate.getForObject(url, ResponseDto.class);
          }
          if (response != null && response.getStatusCode().equals("00")) {
            Map<String, String> information = (Map) response.getData();
            HttpHeaders newHeaders = new HttpHeaders();
            newHeaders.addAll(exchange.getRequest().getHeaders());
            newHeaders.add("username", information.get("username"));
            newHeaders.add("userId", information.get("id"));
            newHeaders.add("email", information.get("email"));
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .headers(h -> h.putAll(newHeaders)).build();
            exchange = exchange.mutate().request(modifiedRequest).build();
          }
        } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
      }
      return chain.filter(exchange);
    });
  }

  public static class Config {

  }
}

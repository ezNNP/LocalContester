package ru.stray27.simplecontester.backend.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import ru.stray27.simplecontester.backend.apigateway.dto.UserInfoResponse;
import ru.stray27.simplecontester.backend.apigateway.dto.UserLoginRequest;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class FilterConfiguration {

    private final String authServiceLoginString = "http://AUTH-SERVICE/api/auth/login";
    private final String authServiceUserinfoString = "http://AUTH-SERVICE/api/auth/userinfo/";

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final List<String> allAccessible = new ArrayList<String>(){{
        add("/api/auth/**");
        add("/api/task/get/*");
    }};

    private final List<String> userAccessible = new ArrayList<String>(){{
        addAll(allAccessible);
        add("/api/runner/**");
        add("/api/checker/**");
        add("/api/task/get/*");
    }};

    private final List<String> adminAccessible = new ArrayList<String>(){{
        addAll(userAccessible);
        add("/api/task/**");
    }};



    @Bean
    public GlobalFilter globalFilter(RestTemplate restTemplate) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            log.info("Incoming request. Address: {}", path);
            if (accessResourcesCheck(exchange.getRequest(), restTemplate) == HttpStatus.OK) {
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    log.info("third post filter");
                }));
            } else {
                return Mono.fromRunnable(() -> {
                    log.info("Forbidden request. Address: {}", path);
                });
            }
        });
    }

    private HttpStatus accessResourcesCheck(ServerHttpRequest request, RestTemplate restTemplate) {
        if (isAllAccessible(request.getPath().toString())) {
            return HttpStatus.OK;
        }

        String username = request.getHeaders().getFirst("auth-login");
        String password = request.getHeaders().getFirst("auth-password");

        HttpStatus userLoginStatus = restTemplate.postForEntity(authServiceLoginString, UserLoginRequest.builder().username(username).password(password).build(), Object.class).getStatusCode();
        UserInfoResponse userInfo = restTemplate.getForObject(authServiceUserinfoString + username, UserInfoResponse.class);

        if (userLoginStatus.is2xxSuccessful()) {
            switch (userInfo.getRole().toUpperCase()) {
                case "USER":
                    if (isUserAccessible(request.getPath().toString())) return HttpStatus.OK;
                case "ADMIN":
                    if (isAdminAccessible(request.getPath().toString())) return HttpStatus.OK;
                case "SUPER_ADMIN":
                    return HttpStatus.OK;
                default:
                    return HttpStatus.FORBIDDEN;
            }
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    private boolean isAllAccessible(String path) {
        for (String element: allAccessible) {
            if (antPathMatcher.match(element, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUserAccessible(String path) {
        for (String element: userAccessible) {
            if (antPathMatcher.match(element, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdminAccessible(String path) {
        for (String element: adminAccessible) {
            if (antPathMatcher.match(element, path)) {
                return true;
            }
        }
        return false;
    }
}

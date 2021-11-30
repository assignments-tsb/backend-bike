package com.bike.app.api;

import com.bike.app.core.Bike;
import com.bike.app.core.usecase.ListRentalBikes;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller("/bikes")
public class BikeAPI {

    private final ListRentalBikes useCase;

    @Value @Builder
    @Schema(name = "Bike", description = "The json data for holding a single bike detail")
    static class BikeJson {

        String bikeId;
        String customerName;
        LocalDateTime checkoutTime;
        LocalDateTime checkinTime;
        Long totalTimeSpentInSeconds;
        LocalDateTime dateModified;

        static BikeJson from(Bike entity) {
            return BikeJson.builder()
                    .bikeId(entity.getId())
                    .customerName(entity.getCustomerName())
                    .build();
        }
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "STAFF"})
    @Operation(operationId = "bike-list-all", summary = "List of all the bikes available for rental",
            description = "Allows administrators to create a new User for the system")
    @ApiResponse(responseCode = "401", description = "Authentication is required")
    @ApiResponse(responseCode = "403", description = "User is not allowed to create a new user")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @Tag(name = "Bikes")
    @TransactionalAdvice
    public List<BikeJson> listBikes() {
        return useCase.perform().stream()
                .map(BikeJson::from)
                .collect(Collectors.toList());
    }
}

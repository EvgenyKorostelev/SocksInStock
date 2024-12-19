package ru.korostelev.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateSockPayload(

        @NotNull(message = "{catalogue.products.create.errors.title_size_is_null}")
        @Size(min = 3, max = 50, message = "{catalogue.products.create.errors.title_size_is_invalid}")
        String color,

        @NotNull(message = "{catalogue.products.create.errors.title_size_is_null}")
        @Size(min = 1, max = 3, message = "{catalogue.products.create.errors.description_size_is_invalid}")
        Integer percentageCotton,

        @NotNull(message = "{catalogue.products.create.errors.title_size_is_null}")
        @Size(min = 1, message = "{catalogue.products.create.errors.description_size_is_invalid}")
        Integer pieces) {
}

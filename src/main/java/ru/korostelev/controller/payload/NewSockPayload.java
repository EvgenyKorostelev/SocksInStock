package ru.korostelev.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewSockPayload(

        @NotNull(message = "{catalogue.socks.create.errors.color_size_is_null}")
        @Size(min = 3, max = 50, message = "{catalogue.sock.create.errors.color_size_is_invalid}")
        String color,

        @NotNull(message = "{catalogue.socks.create.errors.percentageCotton_size_is_null}")
        @Min(0)
        @Max(100)
        Integer percentageCotton,

        @NotNull(message = "{catalogue.socks.create.errors.pieces_size_is_null}")
        @Min(1)
        Integer pieces) {
}

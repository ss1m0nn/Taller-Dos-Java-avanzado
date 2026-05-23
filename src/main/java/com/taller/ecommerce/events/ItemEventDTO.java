package com.taller.ecommerce.events;

public record ItemEventDTO(
        Long productoId,
        Integer cantidad,
        Double precioUnitario
) {}

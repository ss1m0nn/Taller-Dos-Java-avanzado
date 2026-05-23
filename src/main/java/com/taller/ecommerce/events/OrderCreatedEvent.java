package com.taller.ecommerce.events;

import java.util.List;

public record OrderCreatedEvent(
        Long ordenId,
        Long usuarioId,
        String correoUsuario,
        Double total,
        List<ItemEventDTO> items) {}


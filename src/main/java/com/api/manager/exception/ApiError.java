package com.api.manager.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        int status,
        String message,
        Instant timestamp,
        Map<String, String> errors
) {
}

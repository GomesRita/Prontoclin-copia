package com.application.SpringProntoClin.infra.audit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
public class AuditLogFilter extends OncePerRequestFilter {

    private final AuditLogRepository auditLogRepository;

    public AuditLogFilter(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        try {
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            AuditLog log = new AuditLog();
            log.setTimestamp(Instant.now());
            log.setUsername(resolveUsername());
            log.setMethod(request.getMethod());
            log.setPath(request.getRequestURI());
            log.setQuery(request.getQueryString());
            log.setStatus(response.getStatus());
            log.setDurationMs(duration);
            log.setRequestBody(extractBody(wrappedRequest));

            auditLogRepository.save(log);
        }
    }

    private String resolveUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal != null) {
                return principal.toString();
            }
        }
        return "anonymous";
    }

    private String extractBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        if (buf == null || buf.length == 0) {
            return null;
        }
        String body = new String(buf, StandardCharsets.UTF_8);
        if (body.length() > 4000) {
            return body.substring(0, 4000);
        }
        return body;
    }
}

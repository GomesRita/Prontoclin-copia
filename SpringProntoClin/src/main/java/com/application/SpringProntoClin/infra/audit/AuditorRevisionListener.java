package com.application.SpringProntoClin.infra.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditableRevisionEntity rev = (AuditableRevisionEntity) revisionEntity;
        String username = "system";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                username = userDetails.getUsername();
            } else if (principal != null) {
                username = principal.toString();
            }
        }

        rev.setUsername(username);
    }
}

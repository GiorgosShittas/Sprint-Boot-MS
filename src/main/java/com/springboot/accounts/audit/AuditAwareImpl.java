package com.springboot.accounts.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("AuditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO Auto-generated method stub
        return Optional.of("ACCOUNT_MS");
    }
    
}

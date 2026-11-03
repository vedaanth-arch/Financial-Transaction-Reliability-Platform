package com.financialplatform.financial_transaction_reliability_platform.api.transactions;

import com.financialplatform.financial_transaction_reliability_platform.demo.DemoStore;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/transactions")
public class TransactionsController {
    private final DemoStore store;
    public TransactionsController(DemoStore store) { this.store=store; }
    public record TransactionView(UUID id,UUID sourceAccountId,UUID destinationAccountId,java.math.BigDecimal amount,String currency,String status,Date createdAt) {}
    @GetMapping
    public List<TransactionView> history(@RequestHeader(value="Authorization",required=false) String authorization,@RequestParam UUID accountId) {
        return store.history(store.authenticate(authorization),accountId).stream().map(t->new TransactionView(t.id(),t.sourceAccountId(),t.destinationAccountId(),t.amount(),t.currency(),t.status(),t.createdAt())).toList();
    }
}

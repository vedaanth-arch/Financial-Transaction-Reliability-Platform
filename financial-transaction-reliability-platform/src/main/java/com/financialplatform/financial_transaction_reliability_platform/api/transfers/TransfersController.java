package com.financialplatform.financial_transaction_reliability_platform.api.transfers;

import com.financialplatform.financial_transaction_reliability_platform.demo.DemoStore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/transfers")
public class TransfersController {
    private final DemoStore store;
    public TransfersController(DemoStore store) { this.store=store; }
    public record CreateTransfer(@NotNull UUID sourceAccountId,@NotNull UUID destinationAccountId,
            @NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal amount) {}
    public record TransferView(UUID id,UUID sourceAccountId,UUID destinationAccountId,BigDecimal amount,String currency,String status,Date createdAt) {}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public TransferView create(@RequestHeader(value="Authorization",required=false) String authorization,@RequestHeader(value="Idempotency-Key",required=false) String key,@Valid @RequestBody CreateTransfer request) {
        var transfer=store.transfer(store.authenticate(authorization),request.sourceAccountId(),request.destinationAccountId(),request.amount(),key); return view(transfer);
    }
    @GetMapping("/{transferId}")
    public TransferView get(@RequestHeader(value="Authorization",required=false) String authorization,@PathVariable UUID transferId) {
        return view(store.transfer(store.authenticate(authorization),transferId));
    }
    private TransferView view(DemoStore.Transfer t) { return new TransferView(t.id(),t.sourceAccountId(),t.destinationAccountId(),t.amount(),t.currency(),t.status(),t.createdAt()); }
}

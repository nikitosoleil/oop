package com.knu.lab3.controller;

import com.knu.lab3.dto.BillDTO;
import com.knu.lab3.service.BillControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BillController {
    private final BillControllerService billService;

    @GetMapping(value = "/bill/{email}")
    public List<BillDTO> findAllBillsByUser(@PathVariable String email) {
        return billService.findAllBills(email);
    }

    @PostMapping(value = "/bill/{email}")
    public BillDTO addBillForUser(@PathVariable String email) {
        return billService.createBillByUser(email);
    }

    @PostMapping(value = "/bill")
    public BillDTO addBill(@Valid @RequestBody BillDTO bill) {
        return billService.save(bill);
    }

    @PatchMapping(value = "/bill/{id}/pay")
    public BillDTO payBill(@PathVariable Long id) {
        return billService.payBill(id);
    }
}

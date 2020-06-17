package com.knu.lab3.service.data;

import com.knu.lab3.entity.Bill;
import com.knu.lab3.exception.BillAlreadyPaid;
import com.knu.lab3.exception.BillNotFound;
import com.knu.lab3.exception.UserIsBlocked;
import com.knu.lab3.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    public List<Bill> findAllBills(String email) {
        return billRepository.findByUserEmail(email);
    }

    @Transactional
    public Bill payBill(Long id, Boolean paid) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (!optionalBill.isPresent()) {
            throw new BillNotFound("Bill with id " + id + " not found");
        }
        Bill bill = optionalBill.get();
        if (bill.getPaid()) {
            throw new BillAlreadyPaid("Bill with id " + id + " is already paid");
        }
        System.out.println(bill.getUser().getBlocked());
        if (bill.getUser() == null || bill.getUser().getBlocked()) {
            throw new UserIsBlocked("User with is blocked");
        }
        bill.setPaid(paid);
        return bill;
    }

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }
}

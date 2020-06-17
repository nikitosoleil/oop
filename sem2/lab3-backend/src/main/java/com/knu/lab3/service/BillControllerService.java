package com.knu.lab3.service;

import com.knu.lab3.converter.BillConverter;
import com.knu.lab3.dto.BillDTO;
import com.knu.lab3.entity.Bill;
import com.knu.lab3.entity.User;
import com.knu.lab3.exception.UserNotFound;
import com.knu.lab3.service.data.BillService;
import com.knu.lab3.service.data.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillControllerService {
    private final BillService billService;
    private final UserService userService;

    private final BillConverter billConverter;

    public List<BillDTO> findAllBills(String email) {
        return billConverter.convertToListDTO(billService.findAllBills(email));
    }

    public BillDTO createBillByUser(String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFound("User with email " + email + " not found");
        }

        Bill bill = Bill.builder()
                .paid(false)
                .date(new Date(System.currentTimeMillis()))
                .price(user.get().getServices().stream().mapToDouble(com.knu.lab3.entity.Service::getPrice).sum())
                .user(user.get())
                .build();

        return billConverter.convertToDTO(billService.save(bill));
    }

    @Transactional
    public BillDTO payBill(Long id) {
        return billConverter.convertToDTO(billService.payBill(id, true));
    }

    @Transactional
    public BillDTO save(BillDTO bill) {
        Optional<User> user = userService.findUserByEmail(bill.getEmail());
        if (!user.isPresent()) {
            throw new UserNotFound("User with email " + bill.getEmail() + " not found");
        }
        return billConverter.convertToDTO(billService.save(billConverter.convertToEntity(bill, user.get())));
    }
}

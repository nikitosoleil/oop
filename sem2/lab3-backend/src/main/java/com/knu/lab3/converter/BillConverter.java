package com.knu.lab3.converter;

import com.knu.lab3.dto.BillDTO;
import com.knu.lab3.entity.Bill;
import com.knu.lab3.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillConverter {
    public Bill convertToEntity(BillDTO billDTO, User user) {
        return Bill.builder()
                .id(billDTO.getId())
                .date(new Date(System.currentTimeMillis()))
                .price(billDTO.getPrice())
                .paid(billDTO.getPaid())
                .user(user)
                .build();
    }

    public BillDTO convertToDTO(Bill bill) {
        return BillDTO.builder()
                .id(bill.getId())
                .email(bill.getUser().getEmail())
                .price(bill.getPrice())
                .paid(bill.getPaid())
                .build();
    }

    public List<BillDTO> convertToListDTO(List<Bill> bills) {
        return bills.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

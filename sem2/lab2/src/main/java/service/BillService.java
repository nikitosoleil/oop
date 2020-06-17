package service;

import dao.BillDAO;
import entity.Bill;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BillService {
    private final BillDAO billDAO;

    public List<Bill> findBillsByUser(Long userId) {
        return billDAO.findBillsByUser(userId);
    }

    public boolean isPaid(Long billId) {
        return billDAO.isPaid(billId);
    }

    public void pay(Long billId) {
        billDAO.updateBillPay(true, billId);
    }

    public void billUser(Long userId, Bill bill) {
        billDAO.save(userId, bill);
    }
}

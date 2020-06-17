package service;

import dao.ServiceDAO;
import entity.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ServiceService {
    private final ServiceDAO serviceDAO;

    public List<Service> getServices() {
        return serviceDAO.findServices();
    }

    public List<Service> getServiceByUser(Long userId) {
        return serviceDAO.findServicesByUser(userId);
    }

    public Service getServiceById(Long serviceId){
        return serviceDAO.findServiceById(serviceId);
    }

    public boolean isSubscribed(Long userId, Long serviceId) {
        return serviceDAO.isSubscribed(userId, serviceId);
    }

    public void subscribe(Long userId, Long serviceId) {
        serviceDAO.insertUserService(userId, serviceId);
    }

    public void unsubscribe(Long userId, Long serviceId) {
        serviceDAO.deleteUserService(userId, serviceId);
    }

    public void addService(Service service) {
        serviceDAO.save(service);
    }
}

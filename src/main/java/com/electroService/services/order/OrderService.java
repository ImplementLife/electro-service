package com.electroService.services.order;

import com.electroService.entitys.order.Comment;
import com.electroService.entitys.order.Order;
import com.electroService.entitys.order.Status;
import com.electroService.services.defaultRestData.AlreadyExistException;
import com.electroService.services.defaultRestData.DefaultRestServiceImpl;
import com.electroService.services.defaultRestData.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
public class OrderService extends DefaultRestServiceImpl<Order> {

    @Autowired
    private StatusService statusService;

    @Override
    public Order create(Order order) throws AlreadyExistException {
        order.setDateCreate(new Date());
        Status status = new Status();
        status.setId(1L);
        order.setStatus(status);
        return super.create(order);
    }

    public Collection<Order> getByStatusId(Long statusId) {
        Collection<Order> all = dao.findAll();
        Collection<Order> res = new ArrayList<>();
        for (Order order : all) if (order.getStatus().getId().equals(statusId)) res.add(order);
        return res;
    }

    private String getCurrentUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth.getName();
        } catch (Exception e) {
            log.warn("username not found");
            return "null";
        }
    }

    private String addMakerToString(String value) {
        return String.format("Maker: '%s' -> '%s'", getCurrentUsername(), value);
    }

    public boolean addComment(Long id, String comment) throws NotFoundException {
        Order order = getById(id);
        order.getComments().add(createComment(comment));
        return super.update(order);
    }

    private Comment createComment(String value) {
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setValue(addMakerToString(value));
        return comment;
    }

    public boolean updateStatus(Long orderId, Long statusId) throws NotFoundException {
        Order order = getById(orderId);

        Status status = new Status();
        status.setId(statusId);
        order.setStatus(status);

        Comment comment = createComment("set status: " + statusService.getById(statusId).getName());
        order.getComments().add(comment);
        return super.update(order);
    }
}

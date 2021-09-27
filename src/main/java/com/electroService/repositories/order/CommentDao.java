package com.electroService.repositories.order;

import com.electroService.entitys.order.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {
}

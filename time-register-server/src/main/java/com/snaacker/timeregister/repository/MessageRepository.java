package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}

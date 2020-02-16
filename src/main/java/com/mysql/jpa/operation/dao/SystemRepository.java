package com.mysql.jpa.operation.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysql.jpa.operation.model.System;
@Repository
public interface SystemRepository extends CrudRepository<System,Long> {
}

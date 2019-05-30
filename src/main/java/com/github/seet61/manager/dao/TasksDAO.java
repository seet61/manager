package com.github.seet61.manager.dao;

import com.github.seet61.manager.model.Tasks;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TasksDAO extends CrudRepository<Tasks, Long>{
    @Transactional(readOnly = true)
    @Query(value = "select * from Tasks t where t.name  = '?0' and t.status = 'init'",
            nativeQuery=true
    )
    List<Tasks> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Tasks u set u.status = ?1 where u.name = ?2")
    void setStatusByName(String status, String name);
}

package com.famipam.security.service;

import com.famipam.security.entity.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@MappedSuperclass
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseRepoService<T extends BaseEntity> {

    public abstract <JPA extends JpaRepository> JPA repository();

    public Optional<T> findById(long id) {
        return repository().findById(id);
    }

    public List<T> findAll() {
        return repository().findAll();
    }

    public void delete(Long id) {
        repository().deleteById(id);
    }

    @Transactional
    public T save(T obj) {
        return (T) repository().save(obj);
    }


}

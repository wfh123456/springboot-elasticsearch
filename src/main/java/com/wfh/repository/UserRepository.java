package com.wfh.repository;


import com.wfh.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User,Long> {
}

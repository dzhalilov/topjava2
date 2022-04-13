package ru.javaops.topjava2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javaops.topjava2.model.Vote;

public interface VoteRepository extends CrudRepository<Vote, Integer> {
}

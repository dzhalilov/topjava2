package ru.javaops.topjava2.repository;

import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;

public interface VoteRepository extends BaseRepository<Vote> {

    Vote findByUserIdAndDate(int userId, LocalDate date);
}

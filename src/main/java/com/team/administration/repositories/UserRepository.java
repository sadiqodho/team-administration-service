package com.team.administration.repositories;

import com.team.administration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true " +
            "ELSE false END " +
            "FROM User u WHERE u.gitHubProfileURL = :gitHubUrl")
    Boolean doesGitHubProfileURLExits(String gitHubUrl);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true " +
            "ELSE false END " +
            "FROM User u WHERE u.gitHubProfileURL = :gitHubUrl AND u.id != :userId")
    Boolean doesGitHubProfileURLExits(String gitHubUrl, Long userId);
}
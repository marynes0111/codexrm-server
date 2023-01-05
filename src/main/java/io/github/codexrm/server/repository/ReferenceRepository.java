package io.github.codexrm.server.repository;

import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
        List<Reference> findByUser(User user);
        List<Reference> findByAuthorAndUser(String author, User user);
        List<Reference> findByTitleAndUser(String title, User user);
}

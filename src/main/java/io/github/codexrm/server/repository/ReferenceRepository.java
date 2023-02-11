package io.github.codexrm.server.repository;

import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {

        Page<Reference> findByUser(User user, Pageable pageable);
        Page<Reference> findByUserAndAuthorContaining(User user, String author, Pageable pageable);
        Page<Reference> findByUserAndTitleContaining(User user, String title, Pageable pageable);
        Page<Reference> findByUserAndAuthorContainingOrTitleContaining(User user, String author,String title, Pageable pageable);
}

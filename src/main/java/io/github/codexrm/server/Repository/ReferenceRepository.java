package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.Reference;
import io.github.codexrm.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
        List<Reference> findByUserid(User userid);
}

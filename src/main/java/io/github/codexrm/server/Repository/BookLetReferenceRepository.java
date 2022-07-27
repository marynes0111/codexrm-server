package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.BookLetReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLetReferenceRepository extends JpaRepository<BookLetReference, String> {
}

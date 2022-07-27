package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.ArticleReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleReferenceRepository extends JpaRepository<ArticleReference, String> {
}

package com.bemobi.UrlShortener.repository;

import com.bemobi.UrlShortener.model.Url;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByAlias(String alias);

    List<Url> findTop10ByOrderByAccessCountDesc();

}

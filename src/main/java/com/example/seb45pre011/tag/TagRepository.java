package com.example.seb45pre011.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Tag findByName(String name);
}

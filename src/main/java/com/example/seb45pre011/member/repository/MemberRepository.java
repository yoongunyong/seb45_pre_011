package com.example.seb45pre011.member.repository;

import com.example.seb45pre011.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}

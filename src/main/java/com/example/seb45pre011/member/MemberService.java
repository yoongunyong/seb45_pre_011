package com.example.seb45pre011.member;

import com.example.seb45pre011.exception.BusinessLogicException;
import com.example.seb45pre011.exception.ExceptionCode;
import com.example.seb45pre011.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    private  MemberRepository repository;
    private  PasswordEncoder passwordEncoder;
    private  JwtProvider jwtProvider;

    public MemberService(MemberRepository repository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public Member createMember(Member member){
        verifyExist(member.getEmail());
        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setRoles(member.getEmail());
        return repository.save(member);
    }


    public String loginMember(Member member) {
        Optional<Member> findMember = repository.findByEmail(member.getEmail());
        Member existMember = findMember.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (passwordEncoder.matches(member.getPassword(), existMember.getPassword())) {
            return jwtProvider.createToken(member.getEmail(), member.getRoles());
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

    public Member resetPassword(Member member){
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        return repository.updatePasswordByEmail(member.getEmail(),member.getPassword());
    }

    public Member findPassword(Member member){
        Optional<Member> optionalMember = repository.findByEmailAndUsername(member.getEmail(),member.getUsername());
        if(optionalMember.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }else{
            return optionalMember.get();
        }
    }



    public void verifyExist(String email) {
        Optional<Member> findUser = repository.findByEmail(email);

        findUser.ifPresent(user->{ throw new IllegalArgumentException("이미 가입된 아이디 입니다.");
        });
        if (findUser.isPresent() && findUser.get() != null) {
            throw new IllegalArgumentException("이미 가입된 아이디 입니다.");
        }

        if (!findUser.isPresent()) {
            return;
        }
    }


}

package com.example.seb45pre011.member;

import com.example.seb45pre011.exception.BusinessLogicException;
import com.example.seb45pre011.exception.ExceptionCode;
import com.example.seb45pre011.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MemberService {

    private  MemberRepository repository;
    private  PasswordEncoder passwordEncoder;
    private  JwtProvider jwtProvider;
    private TokenBlackList blackList;

    public MemberService(MemberRepository repository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,TokenBlackList blackList){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.blackList = blackList;

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
            String createToken = jwtProvider.createToken(member.getEmail(), member.getRoles());
            return createToken;

        } else {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

    public void logoutMember(String accessToken){
        blackList.addBlackList(accessToken);
    }



    public Member findMemberByEmail(String email){
        Optional<Member> optionalMember = repository.findByEmail(email);
        Member foundMebmer =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return foundMebmer;
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
    public Member getUserByAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName()!= null) {
            Member userDetails = (Member) authentication.getPrincipal();
            String email = userDetails.getEmail();
            Member member = findMemberByEmail(email);
            if (member == null) {
                throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
            }
            return member;
        } else {
            System.out.println("No logged-in user found.");
            return null;
        }
    }
}

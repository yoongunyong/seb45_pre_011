package com.example.seb45pre011.member;

import com.example.seb45pre011.member.MemberDto.login;
import com.example.seb45pre011.member.MemberDto.post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T16:41:47+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostDtoToMember(post postDto) {
        if ( postDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( postDto.getEmail() );
        member.setUsername( postDto.getUsername() );
        member.setPassword( postDto.getPassword() );
        member.setGender( postDto.getGender() );
        member.setPhone( postDto.getPhone() );
        member.setNick( postDto.getNick() );

        return member;
    }

    @Override
    public Member memberloginDtoToMember(login loginDto) {
        if ( loginDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( loginDto.getEmail() );
        member.setPassword( loginDto.getPassword() );

        return member;
    }
}

package com.example.seb45pre011.member;


import com.example.seb45pre011.post.Post;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.stream.Collectors;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 100, nullable = false,unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String nick;

    @CreationTimestamp
    private LocalDateTime createAt = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    @Column
    private String roles;

//    @OneToMany(mappedBy = "member")
//    private List<Post> posts;
//
//    @OneToMany(mappedBy = "member")
//    private List<Comment> comments;


    public void setRoles(String email){
        if(email.equals("yoongunyong@naver.com")||email.equals("abc@naver.com")){   //관리자 계정 이메일 넣으면 됨.
            roles = "ADMIN";
        }else{
            roles = "USER";
        }
    }
    @Enumerated(EnumType.STRING)
    @Column
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;


    @Override   //사용자의 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면계정"),
        MEMBER_EXIT("회원탈퇴");

        private String memberStatus;

        private String getMemberStatus(){
            return memberStatus;
        }

        MemberStatus(String status){
            this.memberStatus = status;
        }
    }

}

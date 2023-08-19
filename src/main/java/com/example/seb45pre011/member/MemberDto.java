package com.example.seb45pre011.member;

import lombok.Getter;

public class MemberDto {

    @Getter
    public static class Post{
        private String email;
        private String username;
        private String password;
        private String gender;
        private String phone;
        private String nick;

    }

    @Getter
    public static class Login{
        private String email;
        private String password;
    }

    @Getter
    public static class Patch{
        private String password;
        private String phone;
        private String nick;
    }
}
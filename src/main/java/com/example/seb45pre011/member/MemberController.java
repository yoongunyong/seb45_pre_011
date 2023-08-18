package com.example.seb45pre011.member;


import com.example.seb45pre011.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService service;

    @PostMapping("/users/signup")
    public ResponseEntity postMember(@RequestBody MemberDto.post postDto){
        Member saveMember = service.createMember(mapper.memberPostDtoToMember(postDto));
        return new ResponseEntity(saveMember.getNick(), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity loginMember(@RequestBody MemberDto.login loginDto){
        String jwtToken = service.loginMember(mapper.memberloginDtoToMember(loginDto));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-TOKEN", "Bearer " + jwtToken);

        return ResponseEntity.ok()
                .headers(headers).body("login Successful");
    }

    @PostMapping("/users/findpassword")
    public ModelAndView findPassword(@RequestParam("email")String email,@RequestParam("username")String username){
        Member member = new Member();
        member.setEmail(email);
        member.setUsername(username);
        Member findMember = service.findPassword(member);
        ModelAndView modelAndView = new ModelAndView("users/reset.html");
        modelAndView.addObject("member",member);

        return modelAndView;
    }

    @PostMapping("/users/password/resetion")
    public ResponseEntity resetPassword(@RequestParam("email")String email,@RequestParam("password")String password){
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        Member saveMember = service.resetPassword(member);
        return new ResponseEntity(saveMember.getNick(),HttpStatus.OK);

    }

//    @PatchMapping
//    public RequestParam patchMember(@RequestBody MemberDto.patch patchDto){
//
//    }


}
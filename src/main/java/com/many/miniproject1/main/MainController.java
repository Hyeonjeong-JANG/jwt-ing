package com.many.miniproject1.main;

import com.many.miniproject1._core.utils.ApiUtil;
import com.many.miniproject1.apply.ApplyResponse;
import com.many.miniproject1.offer.Offer;
import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.scrap.Scrap;
import com.many.miniproject1.scrap.ScrapResponse;
import com.many.miniproject1.user.SessionUser;
import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final HttpSession session;
    private final MainService mainService;


    //맞춤 공고 - 기업이 보는 매칭 이력서

    /**
     * TODO: company/matching의 검색 버튼을 누르면 스트링을 인터저로 변환하지 못 해서 생기는 에러가 뜨는데 로직을 날려서 그런 것인지 원래 있던 문제인지 몰라서 남겨둠.
     *  그 문제는 company/match로 넘어가는 과정에서 터지는 것이다.
     *  /person/matching도 마찬가지이니 담당자는 반드시 체크할 것!!!
     */


    //메인 구직 공고
    // 04-02 YSH
    @GetMapping("/resumes")
    public ResponseEntity<?> resumes() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");

        List<MainResponse.mainResumesDTO> respDTO = mainService.mainResumes();

        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<?> resumeDetailForm(@PathVariable Integer id) {

        // 현재 로그인한 사용자가 회사인 경우에만 해당 회사가 작성한 채용 공고 목록 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        boolean isCompany = false;
        if (sessionUser != null) {
            String role = sessionUser.getRole();
            if (role.equals("company")) {
                isCompany = true;
            }
            Integer companyId = sessionUser.getId();
            List<Post> postList = mainService.getPostsByCompanyId(companyId);
        }

        Resume resume = mainService.resumeDetailForm(id);

        //resume만 아니라 postList도 같이 넘겨야함
        return ResponseEntity.ok(new ApiUtil<>(resume));

    }

    @PostMapping("/api/resumes/{id}/offer")
    public ResponseEntity<?> companyResumeOffer(@PathVariable int id,int postChoice) {
        Offer offer = mainService.sendPostToResume(id, postChoice);
        return ResponseEntity.ok(new ApiUtil<>(offer));
    }

    @PostMapping("/api/resumes/{id}/scrap")
    public ResponseEntity<?> companyResumeScrap(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Scrap scrap = mainService.companyScrap(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(scrap));
    }
    // YSH
    // ┳━┳ ノ( ゜-゜ノ)

    //메인 채용 공고
    @GetMapping({"/posts", "/"})
    public ResponseEntity<?> posts() {
        List<MainResponse.mainPostsDTO> respDTO = mainService.getPostList();

        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<?> postDetail(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 목적: 로그인 하지 않아도 회사에서 올린 공고가 보임
        MainResponse.PostDetailDTO respDTO = mainService.getPostDetail(id);
        if (sessionUser != null) {
            if(sessionUser.getRole().equals("person")) {
                List<MainResponse.ApplyListDTO> resumeList = mainService.getResumeId(sessionUser.getId());
                return ResponseEntity.ok(new ApiUtil<>(respDTO, resumeList));
            }
        }
        //resumeList도 같이 dto에 담아서 넘길 예정
        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }

    // 지원하기 버튼 안 보임
    @PostMapping("/api/posts/{id}/apply")
    public ResponseEntity<?> personPostApply(@PathVariable int id, @RequestBody MainRequest.ResumeChoiceDTO resumeChoice) {
        System.out.println("resumeChoice = " + resumeChoice);
        ApplyResponse.PostApplyDTO respDTO=mainService.personPostApply(id, resumeChoice.getResumeChoice());
        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }

    @PostMapping("/api/posts/{id}/scrap")
    public ResponseEntity<?> personPostScrap(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ScrapResponse.PostScrapSaveDTO respDTO = mainService.personPostScrap(sessionUser.getId(), id);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }


    @GetMapping("/api/posts/matching")
    public ResponseEntity<?> matchingPosts() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<MainResponse.PosteMatchingChoiceDTO> postList = mainService.findByUserIdPost(sessionUser.getId());
        Integer postChoice = (Integer) session.getAttribute("postChoice");
        if (postChoice != null) {
            List<MainResponse.MainPostMatchDTO> respDTO = mainService.matchingResume(postChoice);
            //resumeList와 posts 와 DTO담아서 넘ㄱ기기
            return ResponseEntity.ok(new ApiUtil<>(respDTO, postList));
        }
        //
        return ResponseEntity.ok(new ApiUtil<>(postList));


    }

    @PostMapping("/api/posts/match")
    public ResponseEntity<?> matchingPost(@RequestBody MainRequest.PostChoiceDTO postChoiceDTO) {
        session.setAttribute("postChoice", postChoiceDTO.getPostChoice());
        int respDTO=postChoiceDTO.getPostChoice();

        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }

    //맞춤 공고 - 개인이 보는 매칭 공고
    @GetMapping("/api/resumes/matching")
    public ResponseEntity<?> matchingResumes() {
        //공고 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<MainResponse.ResumeeMatchingChoiceDTO> resumeList = mainService.findByUserIdResume(sessionUser.getId());
        Integer resumeChoice = (Integer) session.getAttribute("resumeChoice");
        if (resumeChoice != null) {
            List<MainResponse.MainResumeMatchDTO> postList = mainService.matchingPost(resumeChoice);
            //resumeList와 함께 DTO에 담기
            return ResponseEntity.ok(new ApiUtil<>(resumeList,postList));
        }

        return ResponseEntity.ok(new ApiUtil<>(resumeList));

    }

    @PostMapping("/api/resumes/match")
    public ResponseEntity<?> matchingResume(@RequestBody MainRequest.ResumeChoiceDTO resumeChoiceDTO) {
        session.setAttribute("resumeChoice", resumeChoiceDTO.getResumeChoice());
        int respDTO=resumeChoiceDTO.getResumeChoice();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));

    }
}




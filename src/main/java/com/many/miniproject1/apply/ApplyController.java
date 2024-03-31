package com.many.miniproject1.apply;


import com.many.miniproject1._core.utils.ApiUtil;
import com.many.miniproject1.post.PostJPARepository;
import com.many.miniproject1.resume.ResumeJPARepository;
import com.many.miniproject1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplyController {
    private final HttpSession session;
    private final ApplyService applyService;

    // 기업에서 받은 이력서 관리
    // TODO: 회사가 받은 이력서 목록 조회 API 필요 -> @GetMapping("/api/company/resumes")
    @GetMapping("/api/company/resumes")
    public ResponseEntity<?> companyResumes() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<Apply> applyList = applyService.companyResumes(sessionUser.getId());

        return ResponseEntity.ok(new ApiUtil(applyList));
    }

    // TODO: 회사가 받은 이력서 상세 보기 API 필요 -> @GetMapping("/api/company/resumes/{id}")
    @GetMapping("/api/company/resumes/{id}")
    public ResponseEntity<?> companyResumeDetail(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Apply apply = applyService.findById(id);
        applyService.companyResumeDetail(id);

        return ResponseEntity.ok(new ApiUtil(apply));
    }

    @PutMapping("/api/company/resumes/{id}/is-pass")
    public ResponseEntity<?> companyPass(@PathVariable int id, @RequestBody ApplyRequest.UpdateIsPass reqDTO) {
        Apply apply = applyService.isPassResume(reqDTO);

        return ResponseEntity.ok(new ApiUtil(apply));
    }

    // 개인이 지원한 이력서 목록
    // TODO: 개인이 지원한 이력서 목록 조회 API 필요 -> @GetMapping("/person/applies")
    @GetMapping("/api/person/applies")
    public ResponseEntity<?> personApply() {
        User sessionUser=(User) session.getAttribute("sessionUser");
        List<Apply> applyList = applyService.getApplyList(sessionUser.getId());

        return ResponseEntity.ok(new ApiUtil(applyList));
    }

    // TODO: 개인이 지원한 이력서 상세 보기 API 필요 -> @GetMapping("/person/applies/{id}")
    @GetMapping("/api/person/applies/{id}") // 내가 지원한 공고 디테일
    public ResponseEntity<?> personApply(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Apply apply = applyService.getPostDetail(sessionUser.getId(), id);

        return ResponseEntity.ok(new ApiUtil(apply));
    }

    // TODO: 개인이 지원한 이력서 조회 API 필요 -> @GetMapping("/person/applies/{id}")

    @DeleteMapping("/api/person/applies/{id}")
    public ResponseEntity<?> appliedDelete(@PathVariable int id) {
        Apply apply = applyService.findById(id);
        applyService.deleteApplyPost(id);

        return ResponseEntity.ok(new ApiUtil(apply));
    }
}
package com.many.miniproject1.main;

import com.many.miniproject1.post.Post;
import com.many.miniproject1.resume.Resume;
import com.many.miniproject1.skill.Skill;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MainResponse {

    // 04-02 YSH resumes
    @Data
    public static class mainResumesDTO{
        int resumeId;
        String profile;
        String title;
        String career;
        String simplerIntroduce;
        List<SkillDTO> sklls = new ArrayList<>();

        @Data
        public class SkillDTO{
            private int id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }

        public mainResumesDTO(Resume resume) {
            this.resumeId = resume.getId();
            this.profile = resume.getProfile();
            this.title = resume.getTitle();
            this.career = resume.getCareer();
            this.simplerIntroduce = resume.getSimpleIntroduce();
            this.sklls = resume.getSkillList().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class mainPostsDTO {
        int id;
        String profile;
        String companyName;
        String title;
        String task;
        String career;
        String workingArea;
        List<SkillDTO> skills = new ArrayList<>();

        public mainPostsDTO(Post post) {
            this.id = post.getId();
            this.profile = post.getProfile();
            this.companyName = post.getUser().getCompanyName();
            this.title = post.getTitle();
            this.task = post.getTask();
            this.career = post.getCareer();
            this.workingArea = post.getWorkingArea();
            this.skills = post.getSkillList().stream().map(skill -> new SkillDTO(skill)).toList();
        }

        @Data
        public class SkillDTO{
            private int id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }
    }

    @Data
    public static class PostDetailDTO {
        int id;
        String profile;
        String career;
        String pay;
        String deadline;
        String companyName;
        String task;
        String workStartTime;
        String workEndTime;
        String workingArea;
        String workCondition;
        Boolean isCompany;
        List<SkillDTO> skills = new ArrayList<>();

        public PostDetailDTO(Post post) {
            this.id = post.getId();
            this.profile = post.getProfile();
            this.career = post.getCareer();
            this.pay = post.getPay();
            this.deadline = post.getDeadline();
            this.companyName = post.getUser().getCompanyName();
            this.task = post.getTask();
            this.workStartTime = post.getWorkStartTime();
            this.workEndTime = post.getWorkEndTime();
            this.workingArea = post.getWorkingArea();
            this.workCondition = post.getWorkCondition();
            this.skills = post.getSkillList().stream().map(skill -> new SkillDTO(skill)).toList();
        }

        @Data
        public class SkillDTO{
            private int id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }
    }
    @Data
    public static class ApplyListDTO{
        int resumeId;
        String resumeTitle;

        public ApplyListDTO(Resume resume) {
            this.resumeId = resume.getId();
            this.resumeTitle = resume.getTitle();
        }
    }

    public static class OfferListDTO{
        int postId;
        String postTitle;

        public OfferListDTO(Post post) {
            this.postId = post.getId();
            this.postTitle = post.getTitle();
        }
    }
    @Data
    public static class ResumeSkillDTO {
        int resumeId;
        int score;
        public ResumeSkillDTO(int resumeId, int i) {
            this.resumeId = resumeId;
            this.score = i;
        }

    }

    @Data
    public static class PostSkillDTO {
        int postId;
        int score;
        public PostSkillDTO(int postId, int i) {
            this.postId = postId;
            this.score = i;
        }

    }
    @Data
    public static class MainPostMatchDTO {
        int id;
        String profile;
        String userName;
        String career;
        String simpleIntroduce;
        List<SkillDTO> skills = new ArrayList<>();

        public MainPostMatchDTO(Resume resume) {
            this.id = resume.getId();
            this.profile = resume.getProfile();
            this.userName = resume.getUser().getName();
            this.career = resume.getCareer();
            this.simpleIntroduce = resume.getSimpleIntroduce();
            this.skills = resume.getSkillList().stream().map(skill -> new SkillDTO(skill)).toList();
        }
        @Data
        public class SkillDTO{
            private int id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }

    }

    @Data
    public static class PosteMatchingChoiceDTO {
        int postId;
        String postTitle;

        public PosteMatchingChoiceDTO(Post post) {
            this.postId = post.getId();
            this.postTitle = post.getTitle();
        }
    }
    @Data
    public static class ResumeeMatchingChoiceDTO {
        int resumeId;
        String resumeTitle;

        public ResumeeMatchingChoiceDTO(Resume resume) {
            this.resumeId = resume.getId();
            this.resumeTitle = resume.getTitle();
        }
    }

    @Data
    public static class MainResumeMatchDTO {
        int id;
        String profile;
        String companyName;
        String title;
        String task;
        String career;
        String workingArea;
        List<SkillDTO> skills = new ArrayList<>();

        public MainResumeMatchDTO(Post post) {
            this.id = post.getId();
            this.profile = post.getProfile();
            this.companyName = post.getUser().getCompanyName();
            this.title = post.getTitle();
            this.task = post.getTask();
            this.career = post.getCareer();
            this.workingArea = post.getWorkingArea();
            this.skills = post.getSkillList().stream().map(skill -> new SkillDTO(skill)).toList();
        }

        @Data
        public class SkillDTO{
            private int id;
            private String skill;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.skill = skill.getSkill();
            }
        }
    }
}

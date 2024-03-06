package com.many.miniproject1.resume;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResumeResponse {
    @Data
    public static class resumeDTO {
        private Integer id;
        private Integer personId;
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
        private List<String> skill;
        private Timestamp createdAt;

        public resumeDTO(Resume resume, List<String> skill) {
            this.id = resume.getId();
            this.personId = resume.getPersonId();
            this.title = resume.getTitle();
            this.profile = resume.getProfile();
            this.portfolio = resume.getPortfolio();
            this.introduce = resume.getIntroduce();
            this.career = resume.getCareer();
            this.simpleIntroduce = resume.getSimpleIntroduce();
            this.createdAt = resume.getCreatedAt();

            this.skill=skill;
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private Integer personId;
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
        private List<String> skill = new ArrayList<>();
        private Timestamp createdAt;

        public DetailDTO(Resume resume, List<String> skill) {
            this.id = resume.getId();
            this.personId = resume.getPersonId();
            this.title = resume.getTitle();
            this.profile = resume.getProfile();
            this.portfolio = resume.getPortfolio();
            this.introduce = resume.getIntroduce();
            this.career = resume.getCareer();
            this.simpleIntroduce = resume.getSimpleIntroduce();
            this.createdAt = resume.getCreatedAt();
        }

        public DetailDTO() {
        }
    }
    @Data
    public static class UpdateDTO {
        private Integer id;
        private Integer personId;
        private String title;
        private String profile;
        private String portfolio;
        private String introduce;
        private String career;
        private String simpleIntroduce;
        private List<String> skill = new ArrayList<>();
        private Timestamp createdAt;
    }
}

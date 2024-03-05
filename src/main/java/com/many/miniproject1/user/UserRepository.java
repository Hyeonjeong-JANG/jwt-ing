package com.many.miniproject1.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public List<User> findAll() {
        Query query = em.createNativeQuery("select * from user_tb", User.class);

        return query.getResultList();
    }

    public User findById(int id) {
        Query query = em.createNativeQuery("select * from user_tb where id=?", User.class);
        query.setParameter(1, id);

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void companySave(UserRequest.JoinDTO requestDTO) {
        Query query = em.createNativeQuery("insert into user_tb(role, company_name, company_num, username, email, password, created_at) values('company',?,?,?,?,?, now())");
        query.setParameter(1, requestDTO.getCompanyName());
        query.setParameter(2, requestDTO.getCompanyNum());
        query.setParameter(3, requestDTO.getUsername());
        query.setParameter(4, requestDTO.getEmail());
        query.setParameter(5, requestDTO.getPassword());
        query.executeUpdate();
    }

    @Transactional
    public void personSave(UserRequest.JoinDTO requestDTO) {
        Query query = em.createNativeQuery("insert into user_tb(role, username, email, password, created_at) values('person',?,?,?, now())");
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getEmail());
        query.setParameter(3, requestDTO.getPassword());
        query.executeUpdate();
    }

    @Transactional
    public void companyUpdate(UserRequest.UpdateDTO requestDTO, int id) {
        Query query = em.createNativeQuery("update user_tb set company_name =? , company_num=?, company_address=?, username=?, tel=?, email=?, password =? where id = ?");
        query.setParameter(1, requestDTO.getCompanyName());
        query.setParameter(2, requestDTO.getCompanyNum());
        query.setParameter(3, requestDTO.getCompanyAddress());
        query.setParameter(4, requestDTO.getUsername());
        query.setParameter(5, requestDTO.getTel());
        query.setParameter(6, requestDTO.getEmail());
        query.setParameter(7, requestDTO.getPassword());
        query.setParameter(8, id);

        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        Query query = em.createNativeQuery("delete from user_tb where id = ?");
        query.setParameter(1, id);

        query.executeUpdate();
    }

    public User findByEmailAndPassword(UserRequest.LoginDTO requestDTO) {
        Query query = em.createNativeQuery("select * from user_tb where email=? and password=?", User.class);
        query.setParameter(1, requestDTO.getEmail());
        query.setParameter(2, requestDTO.getPassword());
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public UserResponse.DetailDTO findByIdWithUser(int id) {
        Query query = em.createNativeQuery("select * from user_tb where id = ?");
        query.setParameter(1, id);

        Object[] row = (Object[]) query.getSingleResult();
        String role = (String) row[0];
        String email=(String) row[1];
        String password=(String) row[2];
        String username=(String) row[3];
        String tel=(String) row[4];
        String profile=(String) row[5];

        UserResponse.DetailDTO responseDTO = new UserResponse.DetailDTO();

        responseDTO.setRole(role);
        responseDTO.setEmail(email);
        responseDTO.setPassword(password);
        responseDTO.setUsername(username);
        responseDTO.setTel(tel);
        responseDTO.setProfile(profile);
        return responseDTO;
    }
}
package com.moreira.smartstock.repositories;

import com.moreira.smartstock.entities.User;
import com.moreira.smartstock.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
            FROM tb_user
            JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
            JOIN tb_role ON tb_user_role.role_id = tb_role.id
            WHERE tb_user.email = :email
            """)
    List<UserDetailsProjection> searchUserByUsername(String email);

    Optional<User> findByEmail(String email);
}

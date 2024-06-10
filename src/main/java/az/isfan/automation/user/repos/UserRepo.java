package az.isfan.automation.user.repos;

import az.isfan.automation.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(value="SELECT u FROM User u WHERE u.nickName=:nick_name")
    User getByNickName(@Param("nick_name") String nickName);

    @Query(value="SELECT u FROM User u WHERE u.email=:email")
    User getByEmail(@Param("email") String email);
}
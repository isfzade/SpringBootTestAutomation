package az.isfan.automation.user.repos;

import az.isfan.automation.user.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @Autowired
    private UserRepo userRepo;

    @Test
    public void correctVariables_saves() {
        User user = User.builder()
                        .nickName(nickname)
                        .password(password)
                        .email(email)
                        .build();
        var savedUser = userRepo.saveAndFlush(user);
        assertNotNull(savedUser, "Saved user should not be null");
        assertEquals(user.getNickName(), savedUser.getNickName(), "Nickname is incorrect");
        assertEquals(user.getEmail(), savedUser.getEmail(), "Email is incorrect");
        assertEquals(user.getPassword(), savedUser.getPassword(), "Password is incorrect");
    }

    @Test
    public void getUserByNickname_returnsCorrectUser() {
        User user = User.builder()
                .nickName(nickname)
                .password(password)
                .email(email)
                .build();
        userRepo.saveAndFlush(user);
        var userFromDb = userRepo.getByNickName(nickname);
        assertNotNull(userFromDb, "Saved user should not be null");
        assertEquals(user.getNickName(), userFromDb.getNickName(), "Nickname is incorrect");
    }

    @Test
    public void getUserByEmail_returnsCorrectUser() {
        User user = User.builder()
                .nickName(nickname)
                .password(password)
                .email(email)
                .build();
        userRepo.saveAndFlush(user);
        var userFromDb = userRepo.getByEmail(email);
        assertNotNull(userFromDb, "Saved user should not be null");
        assertEquals(user.getEmail(), userFromDb.getEmail(), "Email is incorrect");
    }

    @Test
    public void getNotExistingUserByNickname_returnsNull() {
        var userFromDb = userRepo.getByNickName("incorrectNickname");
        assertNull(userFromDb, "No user was expected");
    }

    @Test
    public void getNotExistingUserEmail_returnsNull() {
        var userFromDb = userRepo.getByEmail("incorrectEmail");
        assertNull(userFromDb, "No user was expected");
    }

    @Test
    public void saveWithDuplicateNickName_throwsException() {
        User user1 = User.builder()
                .nickName(nickname)
                .password(password)
                .email(email)
                .build();
        User user2 = User.builder()
                .nickName(nickname)
                .password(password)
                .email("email")
                .build();
        assertDuplicateField(user1, user2);
    }

    @Test
    public void saveWithDuplicateEmail_throwsException() {
        User user1 = User.builder()
                .nickName(nickname)
                .password(password)
                .email(email)
                .build();
        User user2 = User.builder()
                .nickName("nickname")
                .password(password)
                .email(email)
                .build();
        assertDuplicateField(user1, user2);
    }

    @Test
    public void saveWithNullFields_throwsException() {
        // Nickname
        User user = User.builder()
                .nickName(null)
                .password(password)
                .email(email)
                .build();
        assertNullField(user);

        // Password
        user = User.builder()
                .nickName(nickname)
                .password(null)
                .email(email)
                .build();
        assertNullField(user);

        // Email
        user = User.builder()
                .nickName(nickname)
                .password(password)
                .email(null)
                .build();
        assertNullField(user);
    }

    private void assertDuplicateField(User user1, User user2) {
        userRepo.saveAndFlush(user1);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> { userRepo.saveAndFlush(user2); }
        );
    }

    private void assertNullField(User user) {
        assertThrows(
                DataIntegrityViolationException.class,
                () -> { userRepo.saveAndFlush(user); }
        );
    }
}
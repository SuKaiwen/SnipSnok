package snipsnok.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{
	List<User> findByUsername(@Param("username") String username);
	List<User> findByUsernameIgnoreCaseContaining(String username);
}

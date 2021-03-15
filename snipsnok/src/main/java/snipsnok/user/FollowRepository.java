package snipsnok.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	List<Follow> findByUserId(@Param("user_id") int userId);


	List<Follow> findByFollowId(@Param("followId") Integer id);

	List<Follow> findByUserIdAndFollowId(@Param("user_id") int userId, @Param("followId") Integer id);

}
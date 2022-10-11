package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.post.UserPost;
@Repository
public interface PostModelDao extends JpaRepository<UserPost, Integer>{

	@Query(value="SELECT u from UserPost u WHERE u.userid=?1")
	List<UserPost> getAllPostByUserId(Integer userid);
	
	@Modifying
	@Query(value ="update user_post up set up.postname= ?3, up.postdescription= ?4, last_modify=CURDATE() where up.id = ?2 and up.userid=?1",nativeQuery = true)
	int updateMyPostById(Integer userid,Integer postId,String postName,String postDescription);

}

package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.category.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query(value="select *  from offer as o  left join sub_category sc on(o.sub_category_id=sc.id) "
			+ "left join category c on(c.id=sc.category_id) "
			+ "left join grade as g on(o.grade_id=g.id)"
			+ " left join user u on(g.id=u.grade_id) where sc.id=?1", nativeQuery = true)
	List<Offer> getAllPostByUserId(Integer id);
	
	
	
	@Query(value="select *  from offer  as o  inner join sub_category sc on(o.sub_category_id=sc.id) inner join category c on(c.id=sc.category_id) where c.id=?1", nativeQuery = true)
	List<Offer> getOfferByCategory(Integer categoryId);
	
	
	@Query(value="select *  from offer  as o  inner join sub_category sc on(o.sub_category_id=sc.id) inner join category c on(c.id=sc.category_id)  inner join grade as g on(g.id=o.grade_id) where g.userid=?1", nativeQuery = true)
	List<Offer> getOfferByUserId(Integer userId);
	
	@Query(value="select *  from offer  as o  inner join sub_category sc on(o.sub_category_id=sc.id) inner join category c on(c.id=sc.category_id)  inner join grade as g on(g.id=o.grade_id) where o.created_date>= ( CURDATE() - INTERVAL 10 DAY )", nativeQuery = true)
	List<Offer> getAllOfferAfterTenday();
	
	
	
	


}

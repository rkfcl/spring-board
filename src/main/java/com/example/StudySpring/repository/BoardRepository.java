package com.example.StudySpring.repository;

import com.example.StudySpring.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
    @Query("SELECT b FROM Board b ORDER BY b.commentCnt DESC")
    Page<Board> findAllOrderByCommentCnt(Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:searchKeyword% ORDER BY b.commentCnt DESC")
    Page<Board> findByTitleContainingOrderByCommentCnt(@Param("searchKeyword") String searchKeyword, Pageable pageable);
    @Modifying
    @Query(value = "update Board b set b.views=b.views+1 where b.id=:id")
    void updateViews(@Param("id") Integer id);

    @Query("SELECT b FROM Board b ORDER BY b.likes DESC")
    Page<Board> findAllOrderByLikes(Pageable pageable);
}

package com.plf.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.plf.mp.model.Blog;

/**
 * @author plf
 */
@Repository
public interface BlogMapper {

    /**
     * 添加博客
     * 
     * @param blog
     *            博客
     * @param userId
     *            用户id
     * @return 行
     */
    int addBlog(@Param("blog") Blog blog, @Param("userId") Long userId);

    /**
     * 查询总数
     * 
     * @param atIndex
     *            是否展示在首页
     * @param userId
     *            用户id
     * @return 总数
     */
    Integer getTotal(@Param("atIndex") Boolean atIndex, @Param("userId") String userId);

    /**
     * 获取博客列表
     * 
     * @param start
     *            分页开始
     * @param size
     *            分页大小
     * @param userId
     *            用户id
     * @param atIndex
     *            是否展示在首页
     * @return 博客列表
     */
    List<Blog> getBlogList(int start, int size, String userId, Boolean atIndex);

    /**
     * 通过id获取博客
     * 
     * @param blogId
     *            id
     * @return 博客
     */
    Blog getBlogById(String blogId);

    /**
     * 修改博客
     * 
     * @param blogId
     *            id
     * @param blog
     *            博客
     * @return 行
     */
    int updateBlog(String blogId, Blog blog);

    /**
     * 删除博客
     * 
     * @param blogId
     *            id
     * @return 行
     */
    int deleteBlogById(String blogId);
}

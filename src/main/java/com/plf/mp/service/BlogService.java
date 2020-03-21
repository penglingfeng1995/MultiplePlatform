package com.plf.mp.service;

import java.util.List;

import com.plf.mp.model.Blog;

/**
 * @author plf
 */
public interface BlogService {
    /**
     * 添加博客
     * 
     * @param blog
     *            博客实体
     * @param userId
     *            用户id
     * @return 是否成功
     */
    boolean addBlog(Blog blog, Long userId);

    /**
     * 获取博客列表
     * 
     * @param page
     *            第几页
     * @param userId
     *            用户id
     * @param atIndex
     *            是否展示首页
     * @return 博客列表
     */
    List<Blog> getBlogList(Integer page, String userId, Boolean atIndex);

    /**
     * 查询过滤后的博客总数
     * 
     * @param atIndex
     *            是否展示在首页
     * @param userId
     *            用户id
     * @return 总数
     */
    Integer getTotal(Boolean atIndex, String userId);

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
     * @return 是否成功
     */
    boolean updateBlog(String blogId, Blog blog);

    /**
     * 删除博客
     * 
     * @param blogId
     *            id
     * @return 是否删除成功
     */
    boolean deleteBlogById(String blogId);
}

package com.plf.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plf.mp.mapper.BlogMapper;
import com.plf.mp.model.Blog;
import com.plf.mp.service.BlogService;

/**
 * @author plf
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public boolean addBlog(Blog blog, Long userId) {
        int count = blogMapper.addBlog(blog, userId);
        return count != 0;
    }

    @Override
    public List<Blog> getBlogList(Integer page, String userId, Boolean atIndex) {
        int start = 0;
        int size = 10;
        return blogMapper.getBlogList(start, size, userId, atIndex);
    }

    @Override
    public Integer getTotal(Boolean atIndex, String userId) {
        return blogMapper.getTotal(atIndex, userId);
    }

    @Override
    public Blog getBlogById(String blogId) {
        return blogMapper.getBlogById(blogId);
    }

    @Override
    public boolean updateBlog(String blogId, Blog blog) {
        int count = blogMapper.updateBlog(blogId, blog);
        return count != 0;
    }

    @Override
    public boolean deleteBlogById(String blogId) {
        int count = blogMapper.deleteBlogById(blogId);
        return count != 0;
    }
}

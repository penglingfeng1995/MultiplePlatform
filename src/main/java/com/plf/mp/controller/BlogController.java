package com.plf.mp.controller;

import com.plf.mp.model.Blog;
import com.plf.mp.model.MpUser;
import com.plf.mp.model.Result;
import com.plf.mp.model.Status;
import com.plf.mp.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客
 * 
 * @author plf
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AuthController authController;

    /**
     * 获取播客列表
     * 
     * @param page
     *            第几页
     * @param userId
     *            用户id，可以不指定
     * @param atIndex
     *            是否首页展示
     * @return 博客列表
     */
    @GetMapping
    public Result<List<Blog>> getBlogList(Integer page, String userId, Boolean atIndex) {
        Result<List<Blog>> result = new Result<>();
        try {
            List<Blog> blogList = blogService.getBlogList(page, userId, atIndex);
            Integer total = blogService.getTotal(atIndex, userId);
            result.setStatus(Status.OK.getCode());
            result.setData(blogList);
            result.setMsg("获取成功");
            result.setPage(page);
            result.setTotal(total);
            int totalPage = total / 10;
            result.setTotalPage(totalPage == 0 ? 1 : totalPage);
        } catch (Exception e) {
            log.error("查询博客列表异常", e);
            result.setMsg("系统异常");
            result.setStatus(Status.FAIL.getCode());
        }
        return result;
    }

    /**
     * 添加博客
     * 
     * @param blog
     *            博客实体
     * @return 添加结果
     */
    @PostMapping
    public Result<Object> addBlog(@RequestBody Blog blog) {
        Result<MpUser> auth = authController.auth();
        Result<Object> result = new Result<>();
        if (auth.getIsLogin()) {
            Long userId = auth.getData().getId();
            boolean createSuccess = blogService.addBlog(blog, userId);
            if (createSuccess) {
                result.setStatus(Status.OK.getCode());
                result.setMsg("创建成功");
                return result;
            }
        }
        result.setStatus(Status.FAIL.getCode());
        result.setMsg("登录后才能操作");
        return result;
    }

    /**
     * 根据博客id获取博客
     * 
     * @param blogId
     *            id
     * @return 博客
     */
    @GetMapping("/{blogId}")
    public Result<Blog> getBlogById(@PathVariable String blogId) {
        Result<Blog> result = new Result<>();
        try {
            Blog blog = blogService.getBlogById(blogId);
            result.setData(blog);
            result.setStatus(Status.OK.getCode());
            result.setMsg("获取成功");
        } catch (Exception e) {
            log.error("查询博客byId异常", e);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("系统异常");
        }
        return result;
    }

    /**
     * 修改博客
     * 
     * @param blogId
     *            id
     * @param blog
     *            博客
     * @return 博客
     */
    @PatchMapping("/{blogId}")
    public Result<Blog> updateBlog(@PathVariable String blogId, @RequestBody Blog blog) {
        Result<Blog> result = new Result<>();
        try {
            boolean updateSuccess = blogService.updateBlog(blogId, blog);
            if (updateSuccess) {
                result.setStatus(Status.OK.getCode());
                result.setMsg("修改成功");
                Blog blog2 = blogService.getBlogById(blogId);
                result.setData(blog2);
            } else {
                throw new RuntimeException("修改失败");
            }
        } catch (Exception e) {
            log.error("修改失败", e);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("修改失败");
        }
        return result;
    }

    @DeleteMapping("/{blogId}")
    public Result<Object> deleteBlog(@PathVariable String blogId) {
        Result<Object> result = new Result<>();
        try {
            boolean deleteSuccess = blogService.deleteBlogById(blogId);
            if (deleteSuccess) {
                result.setStatus(Status.OK.getCode());
                result.setMsg("删除成功");
            }
        } catch (Exception e) {
            log.error("删除博客异常", e);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("删除失败");
        }
        return result;
    }
}

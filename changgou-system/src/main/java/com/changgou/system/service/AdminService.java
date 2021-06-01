package com.changgou.system.service;

import com.changgou.system.pojo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:Admin业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface AdminService {

    /***
     * Admin多条件分页查询
     * @param admin
     * @param page
     * @param size
     * @return
     */
    PageInfo<Admin> findPage(Admin admin, int page, int size);

    /***
     * Admin分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Admin> findPage(int page, int size);

    /***
     * Admin多条件搜索方法
     * @param admin
     * @return
     */
    List<Admin> findList(Admin admin);

    /***
     * loginName搜索方法
     * @param loginName
     * @return
     */
    Admin findByLoginName(String loginName);

    /***
     * 删除Admin
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Admin数据
     * @param admin
     */
    void update(Admin admin);

    /***
     * 新增Admin
     * @param admin
     */
    void add(Admin admin);

    /**
     * 根据ID查询Admin
     * @param id
     * @return
     */
     Admin findById(Integer id);

    /***
     * 查询所有Admin
     * @return
     */
    List<Admin> findAll();
}

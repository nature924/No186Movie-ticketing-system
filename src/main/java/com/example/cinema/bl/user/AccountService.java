package com.example.cinema.bl.user;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import com.example.cinema.po.historyItem;
import com.example.cinema.po.User;
/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    public UserVO login(UserForm userForm);

    public ResponseVO getHistoryByUserId(int userId);

    public ResponseVO insertHistory(historyItem history);

    public ResponseVO getUserById(int id);

    public ResponseVO updateUser(User user);

    public ResponseVO getAllUser();

    public ResponseVO deleteUser(int id);
}

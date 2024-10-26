package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.data.user.HistoryMapper;
import com.example.cinema.po.User;
import com.example.cinema.po.historyItem;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.omg.DynamicAny.DynArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {//kind是账户的类型，0、1、2分别对应老板、管理员、观众
            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword(),userForm.getKind());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        //System.out.println(user.getUsername()+" "+user.getKind());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }
    @Override
    public ResponseVO getHistoryByUserId(int userId){
        try{
            List<historyItem> list=historyMapper.getHistoryByUserId(userId);
            /*for(historyItem it:list){
                System.out.println(it.getMoney());
            }*/
            return(ResponseVO.buildSuccess(list));
        }
        catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }
    }

    @Override
    public ResponseVO insertHistory(historyItem history){
        try{//System.out.println("61!");
            historyMapper.insertHistory(history);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }
    }

    @Override
    public ResponseVO getUserById(int id){
        try{
            User user=accountMapper.getAccountById(id);
            //System.out.println(user.getUsername()+"  "+user.getPassword());
            return ResponseVO.buildSuccess(user);
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }
    }
    @Override
    public ResponseVO updateUser(User user){
        try{
            accountMapper.updateUser(user);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }
    }
    @Override
    public ResponseVO getAllUser(){
        try{
            return ResponseVO.buildSuccess(accountMapper.getAllUser());
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }

    }
    @Override
    public ResponseVO deleteUser(int id){
        try{
            accountMapper.deleteUser(id);
            return ResponseVO.buildSuccess(accountMapper);
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseVO.buildFailure("失败"));
        }
    }
}

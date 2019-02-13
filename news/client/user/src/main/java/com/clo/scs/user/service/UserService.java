package com.clo.scs.user.service;

import com.clo.scs.common.domain.result.Code;
import com.clo.scs.common.domain.result.SingleResult;
import com.clo.scs.common.interfaces.BaseService;
import com.clo.scs.user.bean.UserBean;
import com.clo.scs.user.mapper.UserMapper;
import com.clo.scs.user.request.LoginRequest;
import com.clo.scs.user.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService extends BaseService {
    @Resource
    private UserMapper userMapper;

    public SingleResult<TokenResponse> login(LoginRequest loginRequest) {
        List<UserBean> userList = userMapper.queryUser(loginRequest.getMobile(), loginRequest.getPassword());
        if(!CollectionUtils.isEmpty(userList)) {
            String token = getToken(loginRequest.getMobile(), loginRequest.getPassword());
            TokenResponse response = new TokenResponse();
            response.setToken(token);
            return SingleResult.buildSuccess(response);
        } else {
            return SingleResult.buildFailure(Code.Error, "手机号码或密码输入错误！");
        }
    }
}

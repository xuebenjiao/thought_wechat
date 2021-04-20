package com.thoughtwork.comment.component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.thoughtwork.comment.acitivity.LoginActivity;

/**
 * Time :2019/11/5
 * Author:xbj
 * Description :
 */
public class LoginComponent  implements IComponent {
    @Override
    public String getName() {
        return "Login";
    }
    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName){
            case "getLoginActivity":
                CCResult ccResult = new CCResult();
                ccResult.addData("Activity",new LoginActivity());
                CC.sendCCResult(cc.getCallId(),ccResult);
                return true;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}

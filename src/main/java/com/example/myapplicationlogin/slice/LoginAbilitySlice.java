package com.example.myapplicationlogin.slice;

import com.example.myapplicationlogin.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import ohos.agp.window.dialog.ToastDialog;
import ohos.agp.window.service.WindowManager;

public class LoginAbilitySlice extends AbilitySlice {
    TextField textField;
    Button btnLogin;
    String username;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        //1、获取TextField和Button组件对象
        textField = (TextField) findComponentById(ResourceTable.Id_password);
        btnLogin = (Button) findComponentById(ResourceTable.Id_btn_login);
        //2、通过Intent对象获取username
        username = intent.getStringParam("username");

        getWindow().clearFlags(WindowManager.LayoutConfig.MARK_FULL_SCREEN);

        //3、设置Button的单击监听
        btnLogin.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //4、在单击监听的方法里 获取username和password，并判断username和password是否正确
                // 如果正确 就跳转到 WelcomeAbility，如果错误，跳转到MainAbility
                String password = textField.getText();
                //创建Intent对象
                Intent intent1 = new Intent();
                Operation operation;
                if ("ZJC".equals(username) && "0582".equals(password)) {
                    //用户名和密码正确，跳转WelcomeAbility
                    operation = new Intent.OperationBuilder()
                            .withBundleName("com.example.myapplicationlogin")
                            .withAbilityName("com.example.myapplicationlogin.WelcomeAbility")
                            .build();
                    //如果跳转WelcomeAbility，需要带过去参数用户名和密码，WelcomeAbility展示数据
                    intent1.setParam("username",username);
                    intent1.setParam("password",password);
                } else {
                    //提示用户名或者密码错误，请重新登录
                    //提示对话框对象
                    ToastDialog dialog = new ToastDialog(getContext());
                    //设置对话框文本值
                    dialog.setText("用户名或者密码错误，请重新登录");
                    //显示对话框
                    dialog.show();

                    //用户名和密码错误，跳转MainAbility
                    operation = new Intent.OperationBuilder()
                            .withBundleName("com.example.myapplicationlogin")
                            .withAbilityName("com.example.myapplicationlogin.MainAbility")
                            .build();
                }
                intent1.setOperation(operation);
                //跳转
                startAbility(intent1);
            }
        });

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}

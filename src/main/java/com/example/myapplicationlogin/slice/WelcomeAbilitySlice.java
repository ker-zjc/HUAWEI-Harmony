package com.example.myapplicationlogin.slice;

import com.example.myapplicationlogin.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.app.dispatcher.TaskDispatcher;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeAbilitySlice extends AbilitySlice {
    Text textInfo;
    Button btnReturnLogin;

    private Timer mTimer;
    private Boolean isButtonStartPressed = false;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_welcome);
        //1、获取Text和Button组件对象
        Text textInfo = (Text) findComponentById(ResourceTable.Id_text_info);
        textInfo.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        textInfo.startAutoScrolling();
        textInfo.setAutoScrollingCount(-1);


        TaskDispatcher uiTaskDispatcher = getUITaskDispatcher();
        Button mButton = (Button) findComponentById(ResourceTable.Id_button);
        btnReturnLogin = (Button) findComponentById(ResourceTable.Id_btn_return_login);
        Text mText = (Text) findComponentById(ResourceTable.Id_tick_timer);
        //2、设置Text的文本值
        //获取username和password
        String username = intent.getStringParam("username");
        String password = intent.getStringParam("password");
        //拼接信息
        String info = " " ;
        textInfo.setText(info);
        if (mButton != null) {
            // 为按钮设置点击回调
            mButton.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    final int[] currentTime = {0};
                    if (isButtonStartPressed) {
                        System.out.println("| |》》》》》》》");
                        isButtonStartPressed = false;
                        uiTaskDispatcher.asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                mButton.setText("▶");
                            }
                        });
                        mTimer.cancel();
                    } else {
                        System.out.println("▶》》》》》》》");
                        isButtonStartPressed = true;
                        mTimer = new Timer();
                        mTimer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                currentTime[0] += 1;
                                uiTaskDispatcher.asyncDispatch(new Runnable() {
                                    @Override
                                    public void run() {
                                        mText.setText(TimeFormatUtil.toDisplayString(currentTime[0]));
                                        mButton.setText("| |");
                                    }
                                });
                            }
                        }, 0, 10);
                    }
                }
            });
        }
        //3、设置Button单击监听
        btnReturnLogin.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //4、跳转MainAbility
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withBundleName("com.example.myapplicationlogin")
                        .withAbilityName("com.example.myapplicationlogin.MainAbility")
                        .build();
                intent1.setOperation(operation);
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

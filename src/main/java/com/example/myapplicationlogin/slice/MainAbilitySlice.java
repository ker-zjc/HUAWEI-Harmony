package com.example.myapplicationlogin.slice;

import com.example.myapplicationlogin.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.service.WindowManager;
import ohos.app.dispatcher.TaskDispatcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainAbilitySlice extends AbilitySlice {
    TextField textField;
    Button btnNext;

    private Timer mTimer;
    private Boolean isButtonStartPressed = false;



    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button mButton = (Button) findComponentById(ResourceTable.Id_button);
        Text mText = (Text) findComponentById(ResourceTable.Id_tick_timer);
        TaskDispatcher uiTaskDispatcher = getUITaskDispatcher();
        getWindow().clearFlags(WindowManager.LayoutConfig.MARK_FULL_SCREEN);
        textField = (TextField) findComponentById(ResourceTable.Id_username);
        btnNext = (Button) findComponentById(ResourceTable.Id_btn_next);
        if (mButton != null) {
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
        btnNext.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String username = textField.getText();
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withBundleName("com.example.myapplicationlogin")
                        .withAbilityName("com.example.myapplicationlogin.LoginAbility")
                        .build();
                intent1.setParam("username", username);
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

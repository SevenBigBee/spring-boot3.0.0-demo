package com.laijava.others;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 监听到空格键点击事件后点击一直点击回车键盘直到delete事件的触发
 */
public class Robot implements NativeKeyListener {
    private boolean flag = true;
    static InterruptThread1 t =  new InterruptThread1();
    public static void main(String args[]) throws AWTException {


        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Robot());


    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {

            t.start();

        } else if (e.getKeyCode() == NativeKeyEvent.VC_DELETE) {
            System.exit(1);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    static class InterruptThread1 extends Thread{

        @Override
        public void run() {
            super.run();
            java.awt.Robot robot = null;
            try {
                robot = new java.awt.Robot();
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            while (true){
                robot.keyPress(KeyEvent.VK_ENTER);// 回车
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(1);// 延迟发送，不然会一次性全发布出去，因为电脑的处理速度很快，每次粘贴发送的速度几乎是一瞬间，所以给人的感觉就是一次性发送了全部。这个时间可以自己改，想几秒发送一条都可以
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}

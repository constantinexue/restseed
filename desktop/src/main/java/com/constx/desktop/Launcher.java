package com.constx.desktop;

import com.constx.Environment;
import com.constx.Environment.Profile;
import com.constx.desktop.ui.ProgressMonitorDemo;
import com.constx.desktop.ui.Window;

public class Launcher {

    public static void main(String[] args) {
        Environment.config(Profile.DEVELOPMENT);

        Window win = new Window();
        win.startup();
//        ProgressMonitorDemo demo = new ProgressMonitorDemo();
//        demo.startup();
    }
}

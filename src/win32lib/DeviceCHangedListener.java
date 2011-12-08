package win32lib;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;

import flashsystem.HexDump;
import gui.FlasherGUI;

import javax.swing.JDialog;

import linuxlib.LinuxPhoneThread;

import org.adb.AdbUtility;
import org.logger.MyLogger;
import org.system.Device;
import org.system.DeviceIdent;
import org.system.OS;

public class DeviceCHangedListener implements MessageListener {

	public static final int WM_DEVICECHANGE = 0x0219;
	String pid = "";
	public static LinuxPhoneThread usbwatch;
	
	public DeviceCHangedListener(JDialog root) {
		if (OS.getName()=="windows") {
			HWND hWnd = new HWND();
	    	hWnd.setPointer(Native.getWindowPointer(root));
	    	User32RW.MYINSTANCE.SetWindowLong(hWnd, User32RW.GWLP_WNDPROC, this);
		}
		else {
			usbwatch = new LinuxPhoneThread();
			usbwatch.start();
		}
	}

	public int callback(HWND hWnd, int uMsg, WPARAM uParam, LPARAM lParam)
    {
        if (uMsg == WM_DEVICECHANGE)
        {
        	switch (uParam.intValue()) {
	        	case 0x8004:
	        		Device.identDevice();
	        		return 0;
	        	case 0x8000:
	        		Device.identDevice();
	        		return 0;
        	}
        }
        return User32RW.MYINSTANCE.DefWindowProc(hWnd, uMsg, uParam, lParam);
    }	
	
}

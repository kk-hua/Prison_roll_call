package serial.utils;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

public class SerialPortFinder {

	private static final String TAG = "012";

	private Vector<Driver> mDrivers = null;

	public class Driver {
		public Driver(String name, String root) {
			mDriverName = name;
			mDeviceRoot = root;
		}

		private String mDriverName;
		private String mDeviceRoot;
		Vector<File> mDevices = null;

		public Vector<File> getDevices() {
			if (mDevices == null) {
				mDevices = new Vector<File>();
				
				
				File dev = new File("/dev");

				File[] files = dev.listFiles();
				if(files==null){
					mDevices.clear();
					for (int i = 0; i < 4; i++) {
					mDevices.add(new File(String.format("/dev/ttyMT%d", i)));
					Log.d(TAG, "Found new device: " + String.format("/dev/ttyMT%d", i));
					}
					
				}
			
				else{
				int i;
				for (i = 0; i < files.length; i++) {
					if (files[i].getAbsolutePath().startsWith(mDeviceRoot)) {
						Log.d(TAG, "Found new device: " + files[i]);
						mDevices.add(files[i]);
					}
				}
				}
			}
			return mDevices;
		}

		public String getName() {
			return mDriverName;
		}
	}

	Vector<Driver> getDrivers() throws IOException {
		if (mDrivers == null) {
			mDrivers = new Vector<Driver>();
			LineNumberReader r = new LineNumberReader(new FileReader(
					"/proc/tty/drivers"));
			String l;
			while ((l = r.readLine()) != null) {
				String drivername = l.substring(0, 0x15).trim();
				Log.d(TAG,"drivername:::::" +drivername);
				String[] w = l.split(" +");
				if ((w.length >= 5) && (w[w.length - 1].equals("serial"))&&w[w.length - 4].equals("/dev/ttyMT")) {
					Log.d(TAG, "Found new driver " + drivername + " on "
							+ w[w.length - 4]);
					mDrivers.add(new Driver(drivername, w[w.length - 4]));
				}
			}
			r.close();
		}
		return mDrivers;
	}

	public String[] getAllDevices() {
		Vector<String> devices = new Vector<String>();
		// Parse each driver
		Iterator<Driver> itdriv;
		
		try {
            itdriv = getDrivers().iterator();
        
            
            while (itdriv.hasNext()) {
                Driver driver = itdriv.next();

                Iterator<File> itdev = driver.getDevices().iterator();
                
                Log.d(TAG,"itdev:::::" +itdev);
                while (itdev.hasNext()) {
                    String device = itdev.next().getName();
                    String value = String.format("%s (%s)", device,
                            driver.getName());
                    
                    Log.d(TAG,"value:::::" +value);
                    devices.add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
			
		return devices.toArray(new String[devices.size()]);
	}

	public String[] getAllDevicesPath() {
		Vector<String> devices = new Vector<String>();
		// Parse each driver
		Iterator<Driver> itdriv;
		try {
			itdriv = getDrivers().iterator();
			while (itdriv.hasNext()) {
				Driver driver = itdriv.next();
				Iterator<File> itdev = driver.getDevices().iterator();
				while (itdev.hasNext()) {
					String device = itdev.next().getAbsolutePath();
					devices.add(device);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices.toArray(new String[devices.size()]);
	}
}
package kr.zchat;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

public class gg {

	public static void main(String[] args) {
		vv();
	}
	
	public static void vv() {
		
		SystemInfo systemInfo = new SystemInfo();
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.scheduleAtFixedRate(new Runnable() {

			public void run() {

				HardwareAbstractionLayer hal = systemInfo.getHardware();
			    //OperatingSystem os = si.getOperatingSystem();
				
				String cpu1 = String.format("CPU load: %.1f%% (counting ticks)%n", hal.getProcessor().getSystemCpuLoadBetweenTicks() * 100);
				String cpu2 =String.format("CPU load: %.1f%% (OS MXBean)%n", hal.getProcessor().getSystemCpuLoad() * 100);
				
				double[] load = hal.getProcessor().getProcessorCpuLoadBetweenTicks();
				StringBuilder procCpu = new StringBuilder("CPU load per processor:");
		        for (double avg : load) {
		            procCpu.append(String.format(" %.1f%%", avg * 100));
		        }
		        
		        
		        long total = hal.getMemory().getTotal();
				long available = hal.getMemory().getAvailable();
				String memory = "Memory : " + FormatUtil.formatBytes(total - available) + "/"+ FormatUtil.formatBytes(total) + "("  + String.format("%.1f%%", ((total - available) / (double)total) * 100) + ")";
				
				
				System.out.println(cpu1);
				
			}
		}, 0, 1, TimeUnit.SECONDS);
		
	}
	
}

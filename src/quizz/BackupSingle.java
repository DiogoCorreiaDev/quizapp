package quizz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Singleton
@LocalBean
@Startup
@Named("backupSingle")
public class BackupSingle {
	
	private int minutos;

    public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	@Resource
    private TimerService timerService;
	
	
	public void iniciaTimer() {       
	 TimerConfig timerConfig = new TimerConfig();
     timerConfig.setInfo("SingleActionTimerDemo_Info");
     int milliseconds = minutos*60000;
     timerService.createSingleActionTimer(milliseconds, timerConfig);
     System.out.println("Novo timer iniciado a: " + new Date());
     System.out.println("Irá realizar backup dentro de: " + minutos + " minutos.");
     FacesContext context = FacesContext.getCurrentInstance();
	 context.addMessage(null, new FacesMessage("Irá ser realizado um backup da base de dados dentro de "+minutos+" minutos."));
	}
	
	@Schedule(hour="*", info="Hourly Timer", persistent=false)
    public void automatic(Timer timer) {
		System.out.println("HOURLY Timer Service : " + timer.getInfo());
        System.out.println("HOURLY Execution Time : " + new Date());
        try {
			Database.exportaBD();
		} catch (ClassNotFoundException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	public void stopTimer(String timerName) {
	    for(Object obj : timerService.getTimers()) {
	        Timer t = (Timer)obj;
	        if (t.getInfo().equals(timerName)) {
	        t.cancel();
	        }
	    }
	}
	
    @PostConstruct
    private void init() {
    	//stopTimer("Hourly backup");
    	//long hourInMilliseconds = 60*60000;
    	//timerService.createTimer(1000, hourInMilliseconds, "Hourly backup");
    	/*
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("SingleActionTimerDemo_Info");
        int milliseconds = minutos*60000;
        timerService.createSingleActionTimer(milliseconds, timerConfig);
        System.out.println("INIT Time : " + new Date());
        */
    }

    @Timeout
    public void execute(Timer timer) {
        System.out.println("Timer Service : " + timer.getInfo());
        System.out.println("Execution Time : " + new Date());
        try {
			Database.exportaBD();
		} catch (ClassNotFoundException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
}
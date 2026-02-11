import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

class PomodoroAlarm {
    public static void main(String[] args) {
        int pomodoroDuration = 25 * 60 * 1000; // 25 minutes in milliseconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Play beep sound
                Toolkit.getDefaultToolkit().beep();
                // Print notification to console
                System.out.println("Pomodoro session complete! Take a break.");
                // Exit the program
                System.exit(0);
            }
        }, pomodoroDuration);

        System.out.println("Pomodoro timer started for 25 minutes. Work session in progress...");
    }
}

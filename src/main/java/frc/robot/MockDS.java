package frc.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MockDS {
  private Thread m_thread;


  private void generateEnabledDsPacket(byte[] data, short sendCount) {
    data[0] = (byte) (sendCount >> 8);
    data[1] = (byte) sendCount;
    data[2] = 0x01; // general data tag
    data[3] = 0x04; // teleop enabled
    data[4] = 0x10; // normal data request
    data[5] = 0x00; // red 1 station
  }

  @SuppressWarnings("JavadocMethod")
  public void start() {
    
    m_thread = new Thread(() -> {
      DatagramSocket socket;
      try {
        socket = new DatagramSocket();
      } catch (SocketException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        return;
      }
      InetSocketAddress addr = new InetSocketAddress("10.3.64.2", 1110);
      byte[] sendData = new byte[6];
      DatagramPacket packet = new DatagramPacket(sendData, 0, 6, addr);
      short sendCount = 0;
      int initCount = 0;
      while (!Thread.currentThread().isInterrupted()) {
          try {
            Thread.sleep(20);
            generateEnabledDsPacket(sendData, sendCount++);
            // ~50 disabled packets are required to make the robot actually enable
            // 1 is definitely not enough.
            if (initCount < 50) {
              initCount++;
              sendData[0] = 0;
            } 
            SmartDashboard.putNumber("init count", initCount);
            packet.setData(sendData);
            socket.send(packet);
          } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
          } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
          }
      }
      socket.close();
    });
    // Because of the test setup in Java, this thread will not be stopped
    // So it must be a daemon thread
    m_thread.setDaemon(true);
    System.out.println(Thread.activeCount());
    m_thread.start();

  }


  @SuppressWarnings("JavadocMethod")
  public void stop() {
    System.out.println("oof");

    if (m_thread == null) {
      return;
    }
    m_thread.interrupt();
    try {
      m_thread.join(1000);
    } catch (InterruptedException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }
}
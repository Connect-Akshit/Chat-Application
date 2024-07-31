
package chatting.application;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {
    
    JTextField text;
    JPanel chatArea;
    static Box vertical = Box.createVerticalBox();
    static JFrame frame = new JFrame();
    static DataOutputStream Outgoingdata;
    
    Server(){
        
        frame.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        frame.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2); 
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        
    });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/gojo.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5); 
        JLabel Profile = new JLabel(i6);
        Profile.setBounds(40, 10, 50, 50);
        p1.add(Profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8); 
        JLabel video = new JLabel(i9);
        video.setBounds(290, 20, 30, 30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11); 
        JLabel phone = new JLabel(i12);
        phone.setBounds(350, 20, 35, 30);
        p1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14); 
        JLabel moreoptions = new JLabel(i15);
        moreoptions.setBounds(410, 20, 10, 25);
        p1.add(moreoptions);
        
        JLabel name = new JLabel("Gojo");
        name.setBounds(110, 25, 100, 18);                            //adding person name on the panel
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Helvetica", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 45, 100, 18);                            //adding status
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Helvetica", Font.BOLD, 9));
        p1.add(status);
        
        
        chatArea = new JPanel();                          //creating the chat area.
        chatArea.setBounds(5,75,440,570);
        frame.add(chatArea);
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);                      //creating the field for writing messages.
        text.setFont(new Font("Helvetica", Font.PLAIN, 18));
        frame.add(text);
        
        
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Helvetica", Font.PLAIN, 16));
        frame.add(send);
        
         
        frame.setSize(450, 700);
        frame.setLocation(200,50);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);
        
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
        String result = text.getText();
        
        JPanel p2 = formatLabel(result);
        
        chatArea.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        chatArea.add(vertical, BorderLayout.PAGE_START);
        
        Outgoingdata.writeUTF(result);
        
        text.setText("");
        
        frame.repaint();
        frame.invalidate();
        frame.validate();
        
        }catch(Exception e){
            e.printStackTrace();
        }  
    }
    
    public static JPanel formatLabel(String result){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + result + "</p><html>");
        output.setFont(new Font("Helvetica", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(format.format(cal.getTime())); 
        panel.add(time);
        
        return panel;
    }
    
    
    public static void main(String[] args){
        new Server();
        
        try{
            ServerSocket socket = new ServerSocket(8080);
            while(true){
                Socket s = socket.accept();
                
                //Receiving messsages
                DataInputStream Incomingdata = new DataInputStream(s.getInputStream()); 
                
                //Sending messages
                Outgoingdata = new DataOutputStream(s.getOutputStream());
                
                
                
                while(true){
                    //protocol used to read messages is readUTF it returns a string.
                    
                    String message = Incomingdata.readUTF();
                    JPanel panel = formatLabel(message);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}

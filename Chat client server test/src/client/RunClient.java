package client;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javax.swing.JScrollPane;
import java.awt.Font;

public class RunClient {
	 
	public static void main(String[] args) throws IOException
	{		
	
	    
		LaunchWindow frame;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		frame = new LaunchWindow();
		frame.setVisible(true);
		JTextArea textBar = frame.getInputTextArea();
		JTextArea console = frame.getConsole();
		JTextArea board = frame.getBoard();
		
		Entered enter = new Entered();
		enter.setEntered(false);
	
		
		//player.addToConsoleOutput("welcome!");
		
		
		
		
		BufferedReader in = null;
	    PrintWriter out = null;
		try{
		String serverAddress = "10.79.51.153";
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e)
		{
			console.setText("Something broke while connecting");
		}
		
//		while (true) {
//			
//            String line = in.readLine();
//            if (line.startsWith("SUBMITNAME")) {
//                console.setText("test");
//            }
//            else if (line.startsWith("NAMEACCEPTED")) {
//                 console.setText(console.getText()+"Name Accepted");
//            }
//            else if (line.startsWith("MESSAGE")) {
//                console.append(line.substring(8) + "\n");
//            }
//            if(enter.getEntered())
//            	console.setText("true");
//            else
//            	console.setText("false");
//            
//            
//            
            
//            
//            
//            if(enter.getEntered())
//            {
//            	console.setText("in entered");
//            	//enter.setEntered(false);
//        		//out.println(textBar.getText());
//        		textBar.setText("");	
//            }
//	      	
//	       
//            
//            
//        }
		
		
		board.setText("Client start\n");
		board.setText(board.getText()+"Connected to server\n");
		long gameLaunchTime = Calendar.getInstance().getTimeInMillis();
		long startLoopTime = Calendar.getInstance().getTimeInMillis();
		long updateLoopTime = Calendar.getInstance().getTimeInMillis();
		boolean firstLoop = true;
		int tickCount = 0;
		textBarListener(textBar, enter, out);
		
		while (true) { // keep running
	    	startLoopTime = Calendar.getInstance().getTimeInMillis();
	        long tickCheck = startLoopTime - updateLoopTime;
	        long runTime = startLoopTime - gameLaunchTime;
	        String runTimeFormated = String.format("%02d:%02d:%02d", 
	        		TimeUnit.MILLISECONDS.toHours(runTime),
	        		TimeUnit.MILLISECONDS.toMinutes(runTime) -  
	        		TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runTime)), // The change is in this line
	        		TimeUnit.MILLISECONDS.toSeconds(runTime) - 
	        		TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runTime)));
	        if(firstLoop || tickCheck > 10)
	        {
	        	tickCount++;
	        	
	        	
	        
	        	
	        	
	        	//server check
	        	if(enter.getEntered())
	        	{
	        		pressedEnter(textBar, console, enter, out);
	        	}
	        	
	       
	        	String line = in.readLine();      
            if (line.startsWith("SUBMITNAME")) {
                console.append("Submit your name\n");
                }
	            else if (line.startsWith("NAMEACCEPTED")) {
	                 console.append("Name Accepted\n");
	            }
	            else if(line.startsWith("INVALIDNAME")){
	            	console.append("Name alread in use, please select another.\n");
	            }
	            else if (line.startsWith("MESSAGE")) {
	                board.append(line.substring(8) + "\n");
	            }
//	            if(enter.getEntered())
//	            	console.setText("true");
//	            else
//	            	console.setText("false");
	        
	        	
	        	
	        	firstLoop = false;
	        	updateLoopTime = Calendar.getInstance().getTimeInMillis();
	        }
	    }
	}
	

	public static void pressedEnter(JTextArea textBar,JTextArea console,Entered enter, PrintWriter out)
	{
		//console.setText(console.getText()+""+textBar.getText());
		textBar.setText("");
		enter.setEntered(false);
	}
	public static void textBarListener (JTextArea textBar, Entered enter, PrintWriter out)
	{
		textBar.addKeyListener(new KeyListener(){
		    @Override
		    public void keyPressed(KeyEvent e){
		    	switch (e.getKeyCode()){
		        case KeyEvent.VK_ENTER:
		        	enter.setEntered(true);
		        	out.println(textBar.getText());
		        	
		        }
		    }
		    @Override
		    public void keyTyped(KeyEvent e) {
		    }
		    @Override
		    public void keyReleased(KeyEvent e) {
		    }
		});
	}
}


class LaunchWindow extends JFrame {

	private JLayeredPane contentPane;
	JTextArea board;
	JTextArea console;
	JTextArea inputTextArea;
	private JScrollPane inputPane;
	
	public JTextArea getBoard(){return this.board;}
	public JTextArea getConsole(){return this.console;}
	public JTextArea getInputTextArea(){return this.inputTextArea;}
	
	public void setBoard(String boardIn){this.board.setText(boardIn);}
	public void setConsole(String consoleIn){this.console.setText(consoleIn);}
	public void setInputTextArea(String inputTextIn){this.console.setText(inputTextIn);}


	/**
	 * Create the frame.
	 */
	public LaunchWindow() {
		
		initComponents();
		createEvents();
		
	}

	private void initComponents() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 846, 517);
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane boardPane = new JScrollPane();
		boardPane.setAutoscrolls(true);
		
		JScrollPane consolePane = new JScrollPane();
		consolePane.setAutoscrolls(true);
		
		inputPane = new JScrollPane();
		inputPane.setAutoscrolls(true);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(boardPane, GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
				.addComponent(consolePane, GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
				.addComponent(inputPane, GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(boardPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(consolePane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(inputPane, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		
		inputTextArea = new JTextArea();
		inputPane.setViewportView(inputTextArea);
		
		console = new JTextArea();
		console.setEditable(false);
		consolePane.setViewportView(console);
		
		board = new JTextArea();
		board.setFont(new Font("Monospaced", Font.PLAIN, 11));
		board.setEditable(false);
		boardPane.setViewportView(board);
		contentPane.setLayout(gl_contentPane);
		
	}
	private void createEvents() 
	{
		// TODO Auto-generated method stub
		
	}
}

class Entered
{
	boolean entered;
	
	public boolean getEntered(){return entered;}
	
	public void setEntered(boolean input){this.entered = input;}
}

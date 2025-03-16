package SwingApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainScreen {

    private JFrame frame;
    private JLabel label;
    private JMenu clientMenu;
    private JMenu bookMenu;
    private JMenu saleMenu;
    private JMenu customMenu;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainScreen window = new MainScreen();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Biblioteca");
        frame.setResizable(false);

        label = new JLabel();
        label.setFont(new Font("Courier New", Font.PLAIN, 20));
        label.setText("Inicializando la Biblioteca");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 10, 300, 200);
        label.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.getContentPane().add(label);

        //==========================//
        // Creating menu bar options//
        //==========================//
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);



        /************************************************************
         * Create some options based on methods and class from them.*
         *     - private JMenu clientMenu;                          *
         *     - private JMenu bookMenu;                            *
         *     - private JMenu saleMenu;                            *
         *     - private JMenu customMenu;                          *
         ************************************************************/
        clientMenu = new JMenu("Clients");
        clientMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // new clientScreen();
            }
        });
        menuBar.add(clientMenu);


        bookMenu = new JMenu("Books");
        bookMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // new bookScreen();
            }
        });
        menuBar.add(bookMenu);


        saleMenu = new JMenu("Sale");
        saleMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // new saleScreen();
            }
        });
        menuBar.add(saleMenu);


        customMenu = new JMenu("Custom");
        customMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // new customMenu();
            }
        });
        menuBar.add(customMenu);
    }
}

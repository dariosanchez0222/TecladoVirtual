/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecladovirtualapp;

/**
 *
 * @author Hp
 */
public class TecladoVirtualApp {

    /**
     * @param args the command line arguments
     */
    public TecladoVirtualApp() {
        super("Teclado Virtual App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 250);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel tecladoPanel = new JPanel(new GridLayout(5, 11)); 
        String[] teclas = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ",
                "Z", "X", "C", "V", "B", "N", "M", ":", ";", "⇐",
                "@", "?", "<", ",", "Espacio", "/", ".", ">", "_", "ENTER"
        };

        for (String tecla : teclas) {
            JButton boton = new JButton(tecla);
            boton.addActionListener(new TeclaActionListener());
            tecladoPanel.add(boton);

           
            if (tecla.equals("ENTER")) {
                boton.addActionListener(new ComprobarActionListener());
            }
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

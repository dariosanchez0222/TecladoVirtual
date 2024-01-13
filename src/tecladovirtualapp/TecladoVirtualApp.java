/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecladovirtualapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Hp
 */
public class TecladoVirtualApp {
    private JTextArea textArea;
    private JLabel fraseLabel;
    private List<String> frases;
    private int pulsacionesCorrectas;
    private int pulsacionesIncorrectas;
    private Set<Character> letrasDificiles;
    /**
     * @param args the command line arguments
     */
    public TecladoVirtualApp() {
        super("Teclado Virtual App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 250);

        frases = leerPangramaDesdeArchivo("/pangramas.txt");
        Collections.shuffle(frases);

        fraseLabel = new JLabel(frases.get(0));
        add(fraseLabel, BorderLayout.NORTH);

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

        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(tecladoPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        reiniciar();
    }
    private List<String> leerPangramaDesdeArchivo(String ruta) {
        List<String> listaFrases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                listaFrases.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaFrases;
    }
    private class TeclaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
            String tecla = boton.getText();

            if (tecla.equals("Espacio")) {
                textArea.append(" ");
            } else if (tecla.equals("⇐")) {
                eliminarUltimaLetra();
            } else if (tecla.equals("ENTER")) {
                
            } else {
                textArea.append(tecla);
            }
        }
    }
    private class ComprobarActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String textoUsuario = textArea.getText().trim();
        String fraseActual = fraseLabel.getText().trim();

        char[] letrasUsuario = textoUsuario.toCharArray();
        char[] letrasFrase = fraseActual.toCharArray();

        int longitud = Math.min(letrasUsuario.length, letrasFrase.length);
        int coincidencias = 0;

        for (int i = 0; i < longitud; i++) {
            if (letrasUsuario[i] == letrasFrase[i]) {
                coincidencias++;
            } else {
                letrasDificiles.add(letrasFrase[i]);
                letrasDificiles.add(letrasUsuario[i]); // Agrega también la letra incorrecta
            }
        }

       
        for (char letra : letrasFrase) {
            if (fraseActual.indexOf(letra) == -1 && !letrasDificiles.contains(letra)) {
                letrasDificiles.add(letra);
            }
        }

        pulsacionesCorrectas += coincidencias;
        pulsacionesIncorrectas += Math.abs(letrasUsuario.length - coincidencias);

        
        JOptionPane.showMessageDialog(null,
                "Pulsaciones correctas: " + pulsacionesCorrectas +
                        "\nPulsaciones incorrectas: " + pulsacionesIncorrectas +
                        "\nLetras dificultosas: " + letrasDificiles,
                "Informe",
                JOptionPane.INFORMATION_MESSAGE);

        
        reiniciar();
    }
}
    private void eliminarUltimaLetra() {
        String texto = textArea.getText();
        if (!texto.isEmpty()) {
            textArea.setText(texto.substring(0, texto.length() - 1));
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.Resources;

import br.com.projeto.view.FrmProdutos;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe criada para
 *
 * @author Adilson Luz
 * @since Classe Criada em 03/07/2021, 23:03:25
 */
public class Helpers {
    FrmProdutos t = new FrmProdutos();
    private String ean;

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    //Método para limpar tela
    public void limpaTela(JPanel container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            }
            if (component instanceof JComboBox) {
                ((JComboBox) component).setSelectedIndex(-1);
            }
            if (component instanceof JDateChooser) {
                ((JDateChooser) component).setCalendar(null);
            }
        }        
    }

    public String dataHoraBr() {
        String date;
        // data/hora atual
        LocalDateTime agora = LocalDateTime.now();

        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = formatterData.format(agora);

        // formatar a hora
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String hora = formatterHora.format(agora);

        date = data + " - " + hora;

        return date;
    }

    public String dataHoraEUA() {
        String date;
        // data/hora atual
        LocalDateTime agora = LocalDateTime.now();

        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String data = formatterData.format(agora);

        // formatar a hora
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String hora = formatterHora.format(agora);

        date = data + " - " + hora;

        return date;
    }

    public String gerarCodBar(String codBarras) {

        int[] numeros = codBarras.chars().map(Character::getNumericValue).toArray();
        int somaPares = numeros[1] * 3 + numeros[3] * 3 + numeros[5] * 3 + numeros[7] * 3 + numeros[9] * 3 + numeros[11] * 3;
        int somaImpares = numeros[0] + numeros[2] + numeros[4] + numeros[6] + numeros[8] + numeros[10];
        int resultado = somaImpares + somaPares;
        int digitoVerificador = 10 - resultado % 10;

        if (digitoVerificador == 10) {
            digitoVerificador = 0;
        }
        ean = codBarras + Integer.toString(digitoVerificador);
        System.out.println(ean);

        return ean;
    }   

}//fim da classe

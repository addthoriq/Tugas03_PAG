import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;

public class CopyFile extends JFrame implements ActionListener{
    CopyFile(){
        setTitle("Copy File Apa Saja");
        setSize(480, 220);
        setLayout(null);

        lb_asal.setBounds(20, 20, 80, 30);
        lb_asal.setText("File Asal");
        lb_tujuan.setBounds(20, 80, 80, 30);
        lb_tujuan.setText("File Tujuan");
        
        tf_asal.setBounds(120, 20, 210, 35);
        tf_asal.setText("");
        tf_tujuan.setBounds(120, 80, 210, 35);
        tf_tujuan.setText("");

        lb_hasil.setBounds(20, 130, 180, 30);
        lb_hasil.setText("Hasil: " + ukuran);

        btn_copy.setBounds(360, 20, 80, 30);
        btn_copy.setText("COPY");
        btn_copy.addActionListener(this);

        add(lb_asal);
        add(lb_tujuan);
        add(lb_hasil);
        add(tf_asal);
        add(tf_tujuan);
        add(btn_copy);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new CopyFile();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumberBtn = e.getSource();
        if (sumberBtn == btn_copy){
            try {
                copyFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private void copyFile() throws IOException{
        String namaFile = tf_asal.getText();
        String lokasiAsal = dir + namaFile;
        String namaBaru = namaFile.substring(0, namaFile.indexOf("."));
        namaBaru += "2" + namaFile.substring(namaFile.indexOf("."));
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(lokasiAsal);
            fo = new FileOutputStream(dir + namaBaru);
            String s = "";
            int i = fi.available() * -1;
            while (i < fi.available()){
                s += (char) fi.read();
                i++;
            }

            char txtnya[] = s.toCharArray();
            for (int j = 0; j < txtnya.length; j++){
                fo.write(txtnya[j]);
            }

        } finally {
            if (fi != null)
                fi.close();
            if (fo != null)
                fo.close();
        }
        tf_tujuan.setText(dir + namaBaru);
        File f = new File(dir + namaBaru);
        ukuran = f.length() + " Byte";
        lb_hasil.setText("Hasil: " + ukuran);
    }

    JLabel lb_asal = new JLabel();
    JLabel lb_tujuan = new JLabel();
    JLabel lb_hasil = new JLabel();

    String ukuran = "";

    JTextField tf_asal = new JTextField();
    JTextField tf_tujuan = new JTextField();

    JButton btn_copy = new JButton();

    String dir = "/home/kang/NetBeansProjects/Tugas_PAG_03/src/";
}

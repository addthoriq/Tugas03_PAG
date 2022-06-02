import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.sql.*;

public class Dua extends JFrame implements ActionListener{

    Dua() throws ClassNotFoundException, SQLException, Exception{
        hub.koneksiMysql();
        setTitle("Baca, Simpan dan Hapus Record Tabel");
        setSize(940, 540);
        setLayout(null);
        tampilanLabel();
        tampilanForm();
        tambahkanFrame();
        tampilanTombol();
        tampilanTabel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected void tampilanLabel(){
        lb_kode.setBounds(40, 20, 120, 30);
        lb_kode.setText("Kode Barang");
        lb_nama.setBounds(40, 80, 120, 30);
        lb_nama.setText("Nama Barang");
        lb_satuan.setBounds(40, 140, 120, 30);
        lb_satuan.setText("Satuan");
        lb_harga.setBounds(380, 20, 120, 30);
        lb_harga.setText("Harga (Rp)");
        lb_stok.setBounds(380, 80, 120, 30);
        lb_stok.setText("Stok");
    }

    protected void tampilanForm(){
        txtKode.setBounds(170, 20, 120, 30);
        txtKode.setText("");
        txtNama.setBounds(170, 80, 160, 30);
        txtNama.setText("");
        intHarga.setBounds(510, 20, 160, 30);
        intHarga.setText("");
        intUnit.setBounds(510, 80, 120, 30);
        intUnit.setText("");
        cbSatuan.setBounds(170, 140, 90, 30);
    }

    protected void tampilanTombol(){
        btNEWRECORD.setBounds(740, 20, 150, 30);
        btNEWRECORD.setText("New Record");
        btNEWRECORD.addActionListener(this);
        btSIMPAN.setBounds(40, 450, 150, 30);
        btSIMPAN.setText("Simpan");
        btSIMPAN.addActionListener(this);
        btHAPUS.setBounds(200, 450, 150, 30);
        btHAPUS.setText("Hapus");
        btHAPUS.addActionListener(this);
        btKELUAR.setBounds(385, 450, 150, 30);
        btKELUAR.setText("Keluar");
        btKELUAR.addActionListener(this);
        btBACATABEL.setBounds(570, 450, 150, 30);
        btBACATABEL.setText("Baca Tabel");
        btBACATABEL.addActionListener(this);
        btCLEARTABEL.setBounds(730, 450, 150, 30);
        btCLEARTABEL.setText("Clear Tabel");
        btCLEARTABEL.addActionListener(this);
    }

    protected void tambahkanFrame(){
        add(lb_kode);
        add(lb_nama);
        add(lb_satuan);
        add(lb_harga);
        add(lb_stok);
        add(txtKode);
        add(txtNama);
        add(intHarga);
        add(intUnit);
        add(cbSatuan);
        add(btNEWRECORD);
        add(btSIMPAN);
        add(btHAPUS);
        add(btKELUAR);
        add(btBACATABEL);
        add(btCLEARTABEL);
    }
    
    protected void tampilanTabel(){
        tabel.setBounds(0,0, 860, 220);
        tabel.setFillsViewportHeight(true);
        JScrollPane sp = new JScrollPane(tabel);

        jp.setBounds(40, 200, 860, 220);
        jp.setLayout(new BorderLayout());
        jp.add(sp);
        add(jp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumberBtn = e.getSource();
        if (sumberBtn == btNEWRECORD){
            newRecord();
        } else if (sumberBtn == btSIMPAN){
            simpanTabel();
        } else if (sumberBtn == btBACATABEL){
            bacaTabel();
        } else if (sumberBtn == btCLEARTABEL){
            clearTabel();
        } else if (sumberBtn == btHAPUS){
            hapusTabel();
        } else {
            System.exit(0);
        }
    }
    
    private void newRecord() {
        txtKode.setText("");
        txtNama.setText("");
        intHarga.setText("");
        intUnit.setText("");
    }

    private void bacaTabel() {
        try {
            stt = hub.openConnection();
            ResultSet res = stt.executeQuery("SELECT * FROM barang");
            int baris = 0;
            int nomor = 1;
            while (res.next()) {
                String kodeBrg = res.getString("kode_barang");
                String namaBrg = res.getString("nama_barang");
                String satuan = res.getString("satuan");
                int harga = res.getInt("harga");
                int stok = res.getInt("stock");

                tabel.setValueAt(Integer.toString(nomor), baris, 0);
                tabel.setValueAt(kodeBrg, baris, 1);
                tabel.setValueAt(namaBrg, baris, 2);
                tabel.setValueAt(satuan, baris, 3);
                tabel.setValueAt(Integer.toString(harga), baris, 4);
                tabel.setValueAt(Integer.toString(stok), baris, 5);
                baris++;
                nomor++;
            }

            stt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void simpanTabel() {
        try {
            String kode = txtKode.getText();
            String nama = txtNama.getText();
            String satuan = cbSatuan.getSelectedItem().toString();
            int harga = Integer.parseInt(intHarga.getText());
            int unit = Integer.parseInt(intHarga.getText());

            prestt = hub.koneksi.prepareStatement("INSERT INTO barang(kode_barang, nama_barang, satuan, harga, stock) VALUES(?,?,?,?,?)");

            prestt.setString(1, kode);
            prestt.setString(2, nama);
            prestt.setString(3, satuan);
            prestt.setInt(4, harga);
            prestt.setInt(5, unit);
            prestt.executeUpdate();
            prestt.close();
        } catch (SQLException ex) {ex.printStackTrace();}

        // Refresh Tabel
        clearTabel();
        bacaTabel();
    }

    private void hapusTabel() {
        int selectRow = tabel.getSelectedRow();
        if (selectRow == -1){
            JOptionPane.showConfirmDialog(this, "Pilih baris yang mau dihapus", "Awas", 0);
        } else {
            try {
                String kode = tabel.getValueAt(selectRow, 1).toString().trim();
                prestt = hub.koneksi.prepareStatement("DELETE FROM barang WHERE kode_barang = ?");

                try {
                    prestt.setString(1, kode);
                    int konfirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?");
                    if (konfirm == 0){
                        prestt.executeUpdate();
                        tabel.clearSelection();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus");
                    txtKode.setFocusable(true);
                }
                prestt.close();
            } catch (SQLException e) {
                //TODO: handle exception
            }
        }

        // Perbarui Tabel
        clearTabel();
        bacaTabel();
    }
    
    private void clearTabel() {
        int rowCount = tabel.getRowCount();
        int kolomCount = tabel.getColumnCount();
        for (int index = 0; index < rowCount; index++){
            for (int j_index = 0; j_index < kolomCount; j_index++) {
                tabel.setValueAt(null, index, j_index);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new Dua();
    }

    private Statement stt;
    private PreparedStatement prestt;

    JLabel lb_kode = new JLabel();
    JLabel lb_nama = new JLabel();
    JLabel lb_satuan = new JLabel();
    JLabel lb_harga = new JLabel();
    JLabel lb_stok = new JLabel();

    JTextField txtKode = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField intHarga = new JTextField();
    JTextField intUnit = new JTextField();

    JButton btNEWRECORD = new JButton();
    JButton btSIMPAN = new JButton();
    JButton btHAPUS = new JButton();
    JButton btKELUAR = new JButton();
    JButton btBACATABEL = new JButton();
    JButton btCLEARTABEL = new JButton();

    String satuan[] = {"Unit", "buah", "rim", "batang"};
    JComboBox cbSatuan = new JComboBox(satuan);

    JPanel jp = new JPanel();

    String kolom[] = {"No", "Kode Barang", "Nama Barang", "Satuan", "Harga", "Stok"};
    Object modelData[][] = {
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
        {"", "", "", "", null, null},
    };
    JTable tabel = new JTable(modelData, kolom);

    Bridge hub = new Bridge();
}

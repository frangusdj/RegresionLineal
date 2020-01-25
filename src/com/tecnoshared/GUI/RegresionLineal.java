package com.tecnoshared.GUI;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RegresionLineal extends javax.swing.JFrame {
    private double X,Y,sy,sx,scx,sxy,ascx;
    public RegresionLineal() {
        initComponents();
        inhabilitar();
        DatosGenerales();
        InhabilitarIngreso();
        txtX.requestFocus();
        setX(0);
        setY(0);
        setAscx(0);
    }
    //Método paea que no se puedan editar ciertas cajas de texto al iniciar el constructor
    private void inhabilitar(){
        txtX.setEditable(true);
        txtY.setEditable(true);
        txtA.setEditable(false);
        txtB.setEditable(false);
        txtSyx.setEditable(false);
        txtR.setEditable(false);
        txtFac.setEditable(false);
        txtCar.setEditable(false);
        txtDoc.setEditable(false);
        txtEstu.setEditable(false);
        txtCod.setEditable(false);
        txtSX.setEnabled(false);
        txtSY.setEnabled(false);
        txtSCX.setEnabled(false);
        txtSXY.setEnabled(false);
    }
    //Método para inhabilitar ciertas partes cuando se presione el radiobutton ingreso
    private void InhabilitarIngreso(){
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCalcular.setEnabled(false);
        btnGraficar.setEnabled(false);
    }
    //Método para enviar los datos al encabezado
    private void DatosGenerales(){
        txtFac.setText("Informatica y Electronica");
        txtCar.setText("Software");
        txtDoc.setText("Narcisa Salazar");
        txtEstu.setText("Franklin Mendoza");
        txtCod.setText("6705");
    }
    //Mitar modulos cuando se presione el radiobutton detener
    private void detener(){
        txtX.setEditable(false);
        txtY.setEditable(false);
        btnCalcular.setEnabled(true);
        btnGraficar.setEnabled(true);
    }
    //Métodos Setter
    public void setX(double X) {
        this.X = X;
    }
    public void setY(double Y) {
        this.Y = Y;
    }

    public void setAscx(double ascx) {
        this.ascx = ascx;
    }
    
    //Métodos Getter
    public double getX1() {
        return X;
    }
    public double getY1() {
        return Y;
    }
    //Con este metodo enviamos a limpiar las cajas de texto
    private void Limpiar(){
        txtX.setText("");
        txtY.setText("");
    }

    public double getAscx() {
        return ascx;
    }
    
    //Metodo sumatoria x
    private double SumX(){
        double x;
        x=0;
        x=getX1()+Double.parseDouble(txtX.getText());
        setX(x);
        return x;
    }
    //Metodo para la sumatoria y
    private double SumY(){
        double y;
        y=0;
        y=getY1()+Double.parseDouble(txtY.getText());
        setY(y);
        return y;
    }
    //Método para sumatoria al cuadrado de X
    private double SumCX(){
        double sCx;
        sCx=Math.pow(Double.parseDouble(txtX.getText()), 2.0);
        return sCx;
    }
    //Método para calcular la sumatoria de x por y
    private double SumXY(){
        double sXy;
        sXy=Double.parseDouble(txtX.getText())*Double.parseDouble(txtY.getText());
        return sXy;
    }
    //Con este método validamos que todos los numeros sean positivos
    private boolean ValidarY(){
        if(txtY.getText().length()!=0){
            return true;
        }else{
            return false;
        }
    }
    private boolean ValidarX(){
        if(txtX.getText().length()!=0){
            return true;
        }else{
            return false;
        }
    }
    private void TablaFrecuencias(){
        DefaultTableModel modelo2 = (DefaultTableModel) TablaFrecuencias.getModel();//Creamos un objeto default table model llamado modelo2
        modelo2.addRow(new Object[]{txtX.getText(),txtY.getText(),Double.toString(SumX()),Double.toString(SumY()),Double.toString(SumCX()),Double.toString(SumXY())});//Al objeto modelo le añadimos un row y le pasamos un vector con los parametros que debe insertar
    }
    //Método generico para crear la sumatoria
    private double sumatoria(int f){
        double total=0;
        for(int i =0; i<TablaFrecuencias.getRowCount();i++){
            total+=Double.parseDouble(TablaFrecuencias.getValueAt(i, f).toString());
        }
        return total;
    }
    //metodo para retornar el valor del denominador de la formula a y b
    private double denominador(){
        double d,n,scx,sx;
        n=TablaFrecuencias.getRowCount();
        scx=Double.parseDouble(txtSCX.getText());
        sx=Double.parseDouble(txtSX.getText());
        d=n*scx-Math.pow(sx, 2.0);
        return d;
    }
    //Método para retornar el valor de A
    private double CalcA(){
        double a,sx,sy,sxy,scx,n;
        sx=Double.parseDouble(txtSX.getText());
        sy=Double.parseDouble(txtSY.getText());
        sxy=Double.parseDouble(txtSXY.getText());
        scx=Double.parseDouble(txtSCX.getText());
                
        a=(sy*scx-sx*sxy)/denominador();
        
        return a;
    }
    //Método para calcular B
    private double CalcB(){
        double b,sx,sy,sxy,n;
        sx=Double.parseDouble(txtSX.getText());
        sy=Double.parseDouble(txtSY.getText());
        sxy=Double.parseDouble(txtSXY.getText());
        n=TablaFrecuencias.getRowCount();
        b=(n*sxy-sx*sy)/denominador();
        return b;
    }
    //metodo para obtener el vector x
    private double[] vectX(){
        double [] x=new double[TablaFrecuencias.getRowCount()];
        for(int i=0; i<TablaFrecuencias.getRowCount();i++){
            x[i]=Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0));
        }
        return x;
    }
    //Método para obtener el vector y
    private double[] vectY(){
        double[] y = new double[TablaFrecuencias.getRowCount()];
        for(int j=0; j<TablaFrecuencias.getRowCount();j++){
            y[j]=Double.parseDouble((String) TablaFrecuencias.getValueAt(j, 1));
        }
        return y;
    }
    //Metodo para dibujar la nube de puntos
    private void NubePuntos(){
        double [] x=vectX();
        double[] y =vectY();
        XYSeries s = new XYSeries("");
        for(int i=0;i<x.length;i++){
            s.add(x[i],y[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s);
        JFreeChart chart = ChartFactory.createScatterPlot("Nube de puntos", "X", "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        Graficos.setLayout(new java.awt.BorderLayout());
        Graficos.add(panel);
        Graficos.validate();
    }
    //Agregar datos a la tabla
    private void AgregarDatosTabla(){
        if((ValidarX()==true)&&(ValidarY()==true)){
            if((Double.parseDouble(txtX.getText())>=0)&&(Double.parseDouble(txtY.getText())>= 0)){
                TablaFrecuencias();
                Limpiar();
                txtX.requestFocus();
                lblTreg.setText("Total de registros: " + TablaFrecuencias.getRowCount());//TablaFrecuencias.getRowCount() nos devuelve el numero de registros
                txtSX.setText(Double.toString(sumatoria(0)));
                txtSY.setText(Double.toString(sumatoria(1)));
                txtSCX.setText(Double.toString(sumatoria(4)));
                txtSXY.setText(Double.toString(sumatoria(5)));
            }else{
                JOptionPane.showMessageDialog(rootPane, "Ingrese solo números positivos");
                Limpiar();
                txtX.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo números positivos");
            Limpiar();
            txtX.requestFocus();
        }
    }
    //Método para buscar el mayor de todos los numeros
    private double nMayor(){
        double ma=Double.parseDouble((String) TablaFrecuencias.getValueAt(0, 0));
        for(int i=0;i<TablaFrecuencias.getRowCount();i++){
            if(ma<Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0))){
                ma=Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0));
            }
        }
        return ma;
    }
    //Método para buscar el menor de todos los numeros
    private double nMenor(){
        double me=Double.parseDouble((String) TablaFrecuencias.getValueAt(0, 0));
        for(int i=0;i<TablaFrecuencias.getRowCount();i++){
            if(me>Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0))){
                me=Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0));
            }
        }
        return me;
    }
    //Método para dibujar la curva
    private void Curva(){
        double[] lx={nMenor(),nMayor()};
        double[] ly= new double[lx.length];
        for(int i = 0; i<lx.length;i++){
            ly[i]=CalcA()+(CalcB()*lx[i]);
        }
        XYSeries l = new XYSeries("");
        for(int j = 0;j<lx.length;j++){
            l.add(lx[j],ly[j]);
        }
        XYSeriesCollection dts = new XYSeriesCollection();
        dts.addSeries(l);
        JFreeChart line = ChartFactory.createXYLineChart("Ecuacion de la recta", "X", "Y", dts, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(line);
        Graficos.setLayout(new java.awt.BorderLayout());
        Graficos.add(panel);
        Graficos.validate();
    }
    private double VarRegLin(){
        double syx;
        syx=Math.sqrt(((Math.pow(Double.parseDouble(txtSY.getText()), 2.0)-(CalcA()*Double.parseDouble(txtSY.getText()))-(CalcB()*Double.parseDouble(txtSXY.getText())))/(TablaFrecuencias.getRowCount())));
        return syx;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IngresoDeDatos = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtX = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtY = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rbtnIngresar = new javax.swing.JRadioButton();
        rbtnDetener = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtA = new javax.swing.JTextField();
        txtB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSyx = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtR = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFrecuencias = new javax.swing.JTable();
        lblTreg = new javax.swing.JLabel();
        txtSX = new javax.swing.JTextField();
        txtSY = new javax.swing.JTextField();
        txtSCX = new javax.swing.JTextField();
        txtSXY = new javax.swing.JTextField();
        Graficos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtFac = new javax.swing.JTextField();
        txtCar = new javax.swing.JTextField();
        txtDoc = new javax.swing.JTextField();
        txtEstu = new javax.swing.JTextField();
        txtCod = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Regresion Lineal y Correlación");

        jPanel3.setBackground(new java.awt.Color(39, 174, 96));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingreso de Datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("X:");

        txtX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtXKeyTyped(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(102, 102, 102));
        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");

        btnEliminar.setBackground(new java.awt.Color(102, 102, 102));
        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("Y:");

        txtY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYActionPerformed(evt);
            }
        });
        txtY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtYKeyPressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tecnoshared/Files/Data 128 x 128.png"))); // NOI18N

        IngresoDeDatos.add(rbtnIngresar);
        rbtnIngresar.setSelected(true);
        rbtnIngresar.setText("Ingresar");
        rbtnIngresar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rbtnIngresarFocusGained(evt);
            }
        });
        rbtnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtnIngresarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rbtnIngresarMousePressed(evt);
            }
        });
        rbtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnIngresarActionPerformed(evt);
            }
        });

        IngresoDeDatos.add(rbtnDetener);
        rbtnDetener.setText("Detener");
        rbtnDetener.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtnDetenerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbtnIngresar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rbtnDetener)))
                        .addGap(14, 14, 14)))
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtnIngresar)
                            .addComponent(rbtnDetener))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(39, 174, 96));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Regresion Lineal y Correlación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("f(x)=a+bx");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("a:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("b:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tecnoshared/Files/Syx.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Varianza de regresion lineal");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Coeficiente de Relacion de Correlarción");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("r:");

        btnCalcular.setBackground(new java.awt.Color(102, 102, 102));
        btnCalcular.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCalcular.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnGraficar.setBackground(new java.awt.Color(102, 102, 102));
        btnGraficar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGraficar.setForeground(new java.awt.Color(255, 255, 255));
        btnGraficar.setText("Graficar");
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(102, 102, 102));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tecnoshared/Files/F(x) 128x115.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnCalcular)
                                .addGap(43, 43, 43)
                                .addComponent(btnSalir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(btnGraficar))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtA)
                                            .addComponent(txtB))))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtSyx)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel7)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(49, 49, 49)
                                .addComponent(txtR))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8)))
                        .addGap(35, 35, 35))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtSyx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGraficar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCalcular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(46, 204, 113));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla de Frecuencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        TablaFrecuencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "x", "y", "∑x", "∑y", "∑x^2", "∑xy"
            }
        ));
        jScrollPane1.setViewportView(TablaFrecuencias);

        lblTreg.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTreg.setText("Total de Registros:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(lblTreg, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSX, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSY, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(txtSCX, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(txtSXY, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSCX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSXY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTreg)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Graficos.setBackground(new java.awt.Color(46, 204, 113));
        Graficos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Plano en 2D", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        javax.swing.GroupLayout GraficosLayout = new javax.swing.GroupLayout(Graficos);
        Graficos.setLayout(GraficosLayout);
        GraficosLayout.setHorizontalGroup(
            GraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 825, Short.MAX_VALUE)
        );
        GraficosLayout.setVerticalGroup(
            GraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(39, 174, 96));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estadística", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tecnoshared/Files/Estadistica 128x128.png"))); // NOI18N

        jLabel13.setText("Facultad:");

        jLabel14.setText("Carrera:");

        jLabel15.setText("Docente:");

        jLabel16.setText("Estudiante:");

        jLabel17.setText("Código:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFac)
                    .addComponent(txtCar)
                    .addComponent(txtDoc)
                    .addComponent(txtEstu)
                    .addComponent(txtCod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtFac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtEstu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Graficos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Graficos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYKeyPressed
    
    //Con este evento guardamos los datos en la row presionando enter
    private void txtYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYActionPerformed
        AgregarDatosTabla();
    }//GEN-LAST:event_txtYActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        NubePuntos();
        Curva();
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void txtXKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtXKeyTyped
        
    }//GEN-LAST:event_txtXKeyTyped

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        //Programamos el evento click para salir
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void rbtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnIngresarActionPerformed

    }//GEN-LAST:event_rbtnIngresarActionPerformed

    private void rbtnIngresarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rbtnIngresarFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_rbtnIngresarFocusGained

    private void rbtnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtnIngresarMouseClicked
        // TODO add your handling code here:
        InhabilitarIngreso();
    }//GEN-LAST:event_rbtnIngresarMouseClicked

    private void rbtnIngresarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtnIngresarMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_rbtnIngresarMousePressed

    private void rbtnDetenerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtnDetenerMouseClicked
        //Programamos que se inhabilite el ingreso de datos y habilitamos el resto de eventos
        detener();
    }//GEN-LAST:event_rbtnDetenerMouseClicked

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        txtA.setText(Double.toString(CalcA()));
        txtB.setText(Double.toString(CalcB()));
        txtSyx.setText(Double.toString(VarRegLin()));
    }//GEN-LAST:event_btnCalcularActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegresionLineal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegresionLineal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegresionLineal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegresionLineal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegresionLineal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Graficos;
    private javax.swing.ButtonGroup IngresoDeDatos;
    private javax.swing.JTable TablaFrecuencias;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTreg;
    private javax.swing.JRadioButton rbtnDetener;
    private javax.swing.JRadioButton rbtnIngresar;
    private javax.swing.JTextField txtA;
    private javax.swing.JTextField txtB;
    private javax.swing.JTextField txtCar;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtEstu;
    private javax.swing.JTextField txtFac;
    private javax.swing.JTextField txtR;
    private javax.swing.JTextField txtSCX;
    private javax.swing.JTextField txtSX;
    private javax.swing.JTextField txtSXY;
    private javax.swing.JTextField txtSY;
    private javax.swing.JTextField txtSyx;
    private javax.swing.JTextField txtX;
    private javax.swing.JTextField txtY;
    // End of variables declaration//GEN-END:variables
}

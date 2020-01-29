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
        txtSX.setEnabled(false);
        txtSY.setEnabled(false);
        txtSCX.setEnabled(false);
        txtSXY.setEnabled(false);
    }
    //Método para inhabilitar ciertas partes cuando se presione el radiobutton ingreso
    private void InhabilitarIngreso(){
        btnCalcular.setEnabled(false);
        btnGraficar.setEnabled(false);
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
    //Método para calcular la varianza de la regresion lineal
    private double VarRegLin(){
        double syx;
        syx=Math.sqrt(((Math.pow(Double.parseDouble(txtSY.getText()), 2.0)-(CalcA()*Double.parseDouble(txtSY.getText()))-(CalcB()*Double.parseDouble(txtSXY.getText())))/(TablaFrecuencias.getRowCount())));
        return syx;
    }
    //Método para calcular la media
    private double MediaX(){
        double Me;
        Me=Double.parseDouble(txtSX.getText())/TablaFrecuencias.getRowCount();
        return Me;
    }
    //Me´todo para calcular ma media de y
    private double MediaY(){
        double Mey;
        Mey=Double.parseDouble(txtSY.getText())/TablaFrecuencias.getRowCount();
        return Mey;
    }
    //Méteodo ara calcular la varianza de x
    private double VarX(){
        double vx;
        vx=0;
        for(int i=0;i<TablaFrecuencias.getRowCount();i++){
            vx+=Math.pow((Double.parseDouble((String) TablaFrecuencias.getValueAt(i,0))-MediaX()), 2.0);
        }
        vx=vx/(TablaFrecuencias.getRowCount()-1);
        vx=Math.sqrt(vx);
        return vx;
    }
    //Método para calcular la varianza de y
    private double VarY(){
        double vy;
        vy=0;
        for(int j=0;j<TablaFrecuencias.getRowCount();j++){
            vy+=Math.pow((Double.parseDouble((String) TablaFrecuencias.getValueAt(j,1))-MediaY()), 2.0);
        }
        vy=vy/(TablaFrecuencias.getRowCount()-1);
        vy=Math.sqrt(vy);
        return vy;
    }
    //Método para el vector zx
    private double[] Zx(){
        double[] zx = new double[TablaFrecuencias.getRowCount()];
        for(int i=0;i<zx.length;i++){
            zx[i]=(Double.parseDouble((String) TablaFrecuencias.getValueAt(i, 0))-MediaX())/VarX();
        }
        return zx;
    }
    //Método para el vector Zy
    private double[] Zy(){
        double[] zy = new double[TablaFrecuencias.getRowCount()];
        for(int i=0;i<zy.length;i++){
            zy[i]=(Double.parseDouble((String) TablaFrecuencias.getValueAt(i,1))-MediaY())/VarY();
        }
        return zy;
    }
    //Método para hallar la sumatoria de la multiplicacion de los dos vectores
    private double[] SZXY(){
        double[] zx=Zx();
        double[] zy=Zy();
        double [] szxy = new double[zx.length];
        for(int i=0; i<zx.length;i++){
            szxy[i]=zx[i]*zy[i];
        }
        return szxy;
    }
    //Método para calcular r
    private double CalcR(){
        double[] szxy = SZXY();
        double r=0;
        for(int i=0;i<szxy.length;i++){
            r+=szxy[i];
        }
        r=r/TablaFrecuencias.getRowCount();
       return r; 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IngresoDeDatos = new javax.swing.ButtonGroup();
        LineaOCurva = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFrecuencias = new javax.swing.JTable();
        lblTreg = new javax.swing.JLabel();
        txtSX = new javax.swing.JTextField();
        txtSY = new javax.swing.JTextField();
        txtSCX = new javax.swing.JTextField();
        txtSXY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtX = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtY = new javax.swing.JTextField();
        rbtnIngresar = new javax.swing.JRadioButton();
        rbtnDetener = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtA = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtB = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSyx = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtR = new javax.swing.JTextField();
        rbtnNube = new javax.swing.JRadioButton();
        rbtnCurva = new javax.swing.JRadioButton();
        btnCalcular = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        Graficos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Regresion Lineal y Correlación");

        jPanel5.setBackground(new java.awt.Color(102, 102, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        TablaFrecuencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "x", "y", "∑x", "∑y", "∑x^2", "∑xy"
            }
        ));
        TablaFrecuencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFrecuenciasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaFrecuencias);

        lblTreg.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTreg.setText("Total de Registros:");

        txtSCX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSCXActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("X:");

        txtX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtXKeyTyped(evt);
            }
        });

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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("a:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("b:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Varianza de regresion lineal");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tecnoshared/Files/Syx.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Coeficiente de Relacion de Correlarción");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("r:");

        LineaOCurva.add(rbtnNube);
        rbtnNube.setSelected(true);
        rbtnNube.setText("Nube de puntos");

        LineaOCurva.add(rbtnCurva);
        rbtnCurva.setText("Recta");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtX, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                            .addComponent(txtA))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtB))))
                                    .addComponent(jLabel7))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(rbtnIngresar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnDetener))
                                    .addComponent(rbtnNube)
                                    .addComponent(rbtnCurva)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSyx, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(btnCalcular)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                                .addComponent(btnGraficar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtR, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir)
                        .addGap(130, 130, 130)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblTreg, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(txtSX, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSY, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSCX, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSXY, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSCX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSXY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTreg))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtnIngresar)
                            .addComponent(rbtnDetener))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtnNube))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtnCurva))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtSyx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(btnCalcular)
                            .addComponent(btnGraficar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir)
                        .addGap(21, 21, 21))))
        );

        Graficos.setBackground(new java.awt.Color(153, 153, 255));
        Graficos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Plano en 2D", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        javax.swing.GroupLayout GraficosLayout = new javax.swing.GroupLayout(Graficos);
        Graficos.setLayout(GraficosLayout);
        GraficosLayout.setHorizontalGroup(
            GraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        GraficosLayout.setVerticalGroup(
            GraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Graficos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(Graficos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        if(rbtnNube.isSelected()== true){
            NubePuntos();
        }else if(rbtnCurva.isSelected()==true){
            Curva();
        }
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
        txtR.setText(Double.toString(CalcR()));
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void TablaFrecuenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFrecuenciasMouseClicked

    }//GEN-LAST:event_TablaFrecuenciasMouseClicked

    private void txtSCXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSCXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSCXActionPerformed

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
    private javax.swing.ButtonGroup LineaOCurva;
    private javax.swing.JTable TablaFrecuencias;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTreg;
    private javax.swing.JRadioButton rbtnCurva;
    private javax.swing.JRadioButton rbtnDetener;
    private javax.swing.JRadioButton rbtnIngresar;
    private javax.swing.JRadioButton rbtnNube;
    private javax.swing.JTextField txtA;
    private javax.swing.JTextField txtB;
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

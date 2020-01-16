package com.tecnoshared.GUI;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graficos {
    //Creamos el objeto para graficar la nube de puntos
    JFreeChart Grafico;
    //Creamos el objeto para almacenar las series que contendran los valores de la nube de puntos
    XYSeriesCollection dts = new XYSeriesCollection();
    //Declaramos variables necesarias para la construcción
    String titulo,tx,ty;
    //Declaracion de constantes
    final static int LINEAL=1;
    final static int DISPERSION=2;
    //Constructor con parametros
    public Graficos(int tipo, String titulo) {
        this.titulo=titulo;
        tipoGrafica(tipo);
    }
    //Método para crear los gráficos
    public void tipoGrafica(int tipo){
        switch(tipo){
            case LINEAL:
                Grafico=ChartFactory.createXYLineChart(titulo, tx, ty, dts, PlotOrientation.VERTICAL, true, true, true);
                break;
            case DISPERSION:
                Grafico=ChartFactory.createScatterPlot(titulo, tx, ty, dts, PlotOrientation.VERTICAL, true, true, true);
                break;
        }
    }
    //Método para agregar los datos 
    public void agregarGraficas(String id, double[] x, double[] y){
        XYSeries s = new XYSeries(id);
        int n=x.length;
        for(int i=0;i<n;i++){
            s.add(x[i],y[i]);
        }
        dts.addSeries(s);
    }
    public JPanel obtienePanel(){
        return new ChartPanel(Grafico);
    }
}

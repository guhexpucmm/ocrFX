package edu.pucmm.mineriadedatos2017.weka;

import edu.pucmm.mineriadedatos2017.alerta.Alerta;
import javafx.scene.control.Alert;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.util.ArrayList;

public class AlgoritmoBayesNet extends Algoritmo {
    private static AlgoritmoBayesNet instancia;

    public String estadistica = getEstadistica().toString();

    private AlgoritmoBayesNet() {

    }

    public static AlgoritmoBayesNet getInstancia() {
        if (instancia == null)
            instancia = new AlgoritmoBayesNet();

        return instancia;
    }

    public void ejecutarAlgortimo(ArrayList<Integer> pixeles) {
        try{
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("weka/ocr.arff");
            data = source.getDataSet();

            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            bayesNet =  new BayesNet();
            bayesNet.setBatchSize("100");

            fc = new FilteredClassifier();
            fc.setClassifier(bayes);

            inst = new DenseInstance(data.numAttributes());
            inst.setDataset(data);

            String valoresSet = new String();
            int cont = 1;
            for(Integer i : pixeles){
                valoresSet += i.toString();
                if(cont % 20 == 0) valoresSet += ",";
                cont++;
            }

            valoresSet += "Z";

            String[] valores = valoresSet.split(",");
            for(int i = 0; i < data.numAttributes(); i++){
                inst.setValue(i, valores[i]);
            }
            data.add(inst);

            String[] options = new String[2];
            options[0] = "-R";
            options[1] = "first-last";
            StringToNominal stn = new StringToNominal();
            stn.setOptions(options);
            stn.setInputFormat(data);
            Instances newData = Filter.useFilter(data, stn);
            fc.buildClassifier(newData);
            double pred = fc.classifyInstance(newData.instance(newData.numInstances() - 1));

            String resultado = newData.classAttribute().value((int) pred);

            System.out.println();

            new Alerta("Resultado", "Letra predecida: " + resultado, Alert.AlertType.INFORMATION).showAndWait();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StringBuilder getEstadistica() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("weka/ocr.arff");
            data = source.getDataSet();

            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            bayesNet =  new BayesNet();
            bayesNet.setBatchSize("100");

            fc = new FilteredClassifier();
            fc.setClassifier(bayes);

            inst = new DenseInstance(data.numAttributes());
            inst.setDataset(data);

            String[] options = new String[2];
            options[0] = "-R";
            options[1] = "first-last";
            StringToNominal stn = new StringToNominal();
            stn.setOptions(options);
            stn.setInputFormat(data);
            Instances newData = Filter.useFilter(data, stn);
            fc.buildClassifier(newData);

            stringBuilder.append(fc.toString());

            return stringBuilder;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}

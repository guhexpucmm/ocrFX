package edu.pucmm.mineriadedatos2017.weka;

import edu.pucmm.mineriadedatos2017.alerta.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AlgoritmoNaiveBayes {
    private static AlgoritmoNaiveBayes instancia;

    private Instances data;
    private NaiveBayes bayes;
    private FilteredClassifier fc;
    private Instance inst;

    public String estadistica = getEstadistica().toString();

    private AlgoritmoNaiveBayes() {
        try {
            prueba();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AlgoritmoNaiveBayes getInstancia() {
        if (instancia == null)
            instancia = new AlgoritmoNaiveBayes();

        return instancia;
    }

    public void ejecutarAlgortimo(ArrayList<Integer> pixeles) {
        try{
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("weka/ocr.arff");
            data = source.getDataSet();

            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            bayes =  new NaiveBayes();
            bayes.setBatchSize("100");

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

            bayes =  new NaiveBayes();
            bayes.setBatchSize("100");

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

    public void prueba() throws Exception {

    }
}

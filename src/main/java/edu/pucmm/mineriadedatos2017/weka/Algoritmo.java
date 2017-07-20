package edu.pucmm.mineriadedatos2017.weka;

import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class Algoritmo {
    private static Algoritmo instancia;

    protected Instances data;
    protected NaiveBayes bayes;
    protected J48 j48;
    protected BayesNet bayesNet;
    protected FilteredClassifier fc;
    protected Instance inst;
    protected String estadistica;

    protected Algoritmo() {

    }

    public static Algoritmo getInstancia() {
        if (instancia == null)
            instancia = new Algoritmo();

        return instancia;
    }
}

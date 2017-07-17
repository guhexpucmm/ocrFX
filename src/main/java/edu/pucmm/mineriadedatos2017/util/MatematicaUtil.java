package edu.pucmm.mineriadedatos2017.util;

public class MatematicaUtil {
    public static double valorSigmoid(Double arg) {
        return (1 / (1 + Math.exp(-arg)));
    }
}
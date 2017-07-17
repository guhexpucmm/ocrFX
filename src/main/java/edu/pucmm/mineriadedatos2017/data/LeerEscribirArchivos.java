package edu.pucmm.mineriadedatos2017.data;

import java.io.*;
import java.util.ArrayList;

public class LeerEscribirArchivos {
    private static LeerEscribirArchivos instancia;

    private LeerEscribirArchivos() {

    }

    public static LeerEscribirArchivos getInstancia() {
        if (instancia == null)
            instancia = new LeerEscribirArchivos();

        return instancia;
    }

    public void guardarEnArchivo(ArrayList<Integer> input, String filename) {
        try {
            File file = new File("weka/ocr.arff");

            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));

            if(file.length() == 0){
                escribirAtributos(pw);
            }

            int cont = 1;

            for (Integer i : input) {
                pw.write(Integer.toString(i));
                if(cont % 20 == 0) pw.write(",");
                cont++;
            }

            pw.write(filename);
            pw.write("\n");

            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void escribirAtributos(PrintWriter pa){
        pa.write("@relation ocr\n\n");
        for(int i=1;i<=20;i++){
            pa.write("@attribute fila" + i + " string\n");
        }

        pa.write("@attribute class {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z}\n\n");
        pa.write("@data\n");
    }
}
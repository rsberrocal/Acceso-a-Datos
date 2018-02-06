package Main;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    // Plantilla del fichero
    // ABM|TIPUS|DATA|MATRICULA|N_BASTIDOR|N_MOTOR|DNI|COGNOM_NOM|ADREÇA
    // Se pide al usuario esos tres valores
    // La primera letra solo puede ser A B o C
    // Luego se guarda en un fichero para luego leerlo

    static Scanner sc = new Scanner(System.in);
    static String file = "files/coches.txt";

    static void guardarCoche(char letra, String matricula, String nombre, String tipus, String data, String num_bastidor, String n_motor, String dni, String cognom, String adreca) {
        String finalString = letra + "|" + tipus + "|" + data + "|" + matricula + "|" + num_bastidor + "|" + n_motor + "|" + dni + "|" + cognom + "_" + nombre + "|" + adreca;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(finalString);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printCoche() {
        try {
            FileReader fr = new FileReader(new File(file));
            BufferedReader bufferreader = new BufferedReader(fr);

            String line = bufferreader.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufferreader.readLine();
            }
            bufferreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Fila> readFile(String path) {
        ArrayList<Fila> filas = new ArrayList();
        String[] obj;
        String[] noms;
        boolean firstLine = true;
        try {
            FileReader fr = new FileReader(new File(file));
            BufferedReader bufferreader = new BufferedReader(fr);

            String line = bufferreader.readLine();
            while (line != null) {
                Fila f = new Fila();
                if (!firstLine) {
                    obj = line.split("\\|");
                    System.out.println(obj[0]);
                    f.letra = obj[0].charAt(0);
                    f.tipus = obj[1];
                    f.data = obj[2];
                    f.matricula = obj[3];
                    f.num_bastidor = obj[4];
                    f.n_motor = obj[5];
                    f.dni = obj[6];
                    noms = obj[7].split("_");
                    f.cognom = noms[0];
                    f.nombre = noms[1];
                    f.adreca = obj[8];
                    filas.add(f);
                }
                firstLine = false;
                line = bufferreader.readLine();
            }
            bufferreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filas;
    }

    static void savexml() {
        try {
            ArrayList<Fila> contextFile;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   // pag 77
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "DGT", null);
            document.setXmlVersion("1.0");

            Element root = document.getDocumentElement();
            contextFile = readFile("dgt.xml");
            for (int i = 0; i < contextFile.size(); i++) {
                //MOV element
                Element MOV = document.createElement("MOV");
                Element ABM = document.createElement(String.valueOf(contextFile.get(i).letra));
                ABM.setAttribute("tipus",contextFile.get(i).tipus);
                Element DATA = document.createElement("DATA");
                Text data = document.createTextNode((String) contextFile.get(i).data);
                DATA.appendChild(data);
                Element TITULAR = document.createElement("TITULAR");
                Element DNI = document.createElement("DNI");
                data= document.createTextNode((String)contextFile.get(i).dni);
                DNI.appendChild(data);
                Element COGNOMS_NOM = document.createElement("COGNOMS_NOM");
                data= document.createTextNode((String)contextFile.get(i).cognom+"_"+contextFile.get(i).nombre);
                COGNOMS_NOM.appendChild(data);
                Element DOMICILI = document.createElement("DOMICILI");
                data= document.createTextNode((String)contextFile.get(i).adreca);
                DOMICILI.appendChild(data);
                TITULAR.appendChild(DNI);
                TITULAR.appendChild(COGNOMS_NOM);
                TITULAR.appendChild(DOMICILI);
                Element VEHICLE = document.createElement("VEHICLE");
                Element MATRICULA = document.createElement("MATRICULA");
                data= document.createTextNode((String)contextFile.get(i).matricula);
                MATRICULA.appendChild(data);
                Element N_BASTIDOR = document.createElement("N_BASTIDOR");
                data= document.createTextNode((String)contextFile.get(i).matricula);
                N_BASTIDOR.appendChild(data);
                Element N_MOTOR = document.createElement("N_MOTOR");
                data= document.createTextNode((String)contextFile.get(i).matricula);
                N_MOTOR.appendChild(data);
                VEHICLE.appendChild(MATRICULA);
                VEHICLE.appendChild(MATRICULA);
                VEHICLE.appendChild(MATRICULA);
                VEHICLE.appendChild(MATRICULA);
                MOV.appendChild(ABM);
                MOV.appendChild(DATA);
                MOV.appendChild(TITULAR);
                MOV.appendChild(VEHICLE);
                root.appendChild(MOV);
            }

            Source src = new DOMSource(document);
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File("dgt.xml"));  // pag 77  //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(src, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        char letra;
        String matricula;
        String nombre;
        Date date_now = new Date();
        String tipus;
        String num_bastidor;
        String dni;
        String n_motor;
        String cognom;
        String adreca;

        int menuOpt = 0;
        do {
            System.out.println("\tMenu");
            System.out.println("\t----");
            System.out.println("1)Introducir datos");
            System.out.println("2)Imprimir datos");
            System.out.println("3)Pasarlo a XML");
            System.out.println("0)Salir");
            menuOpt = sc.nextInt();
            sc.nextLine();

            switch (menuOpt) {
                case 1:
                    System.out.println("Introduce la letra A,B o C");
                    letra = sc.next().charAt(0);
                    sc.nextLine();
                    System.out.println("Introduce el tipus");
                    tipus = sc.nextLine();
                    System.out.println("Introduce la matricula");
                    matricula = sc.nextLine();
                    System.out.println("Introduce el numero de bastidor");
                    num_bastidor = sc.nextLine();
                    System.out.println("Introduce el numero de motor");
                    n_motor = sc.nextLine();
                    System.out.println("Introduce tu dni");
                    dni = sc.nextLine();
                    System.out.println("Introduce tu nombre");
                    nombre = sc.nextLine();
                    System.out.println("Introduce tu apellido");
                    cognom = sc.nextLine();
                    System.out.println("Introduce tu dirección");
                    adreca = sc.nextLine();
                    guardarCoche(letra, matricula, nombre, tipus, new SimpleDateFormat("dd-MM-yyyy").format(date_now), num_bastidor, n_motor, dni, cognom, adreca);
                    break;
                case 2:
                    printCoche();
                    break;
                case 3:
                    savexml();
                    break;
                case 0:
                    System.out.println("Vinga dew");
                    break;
                default:
                    System.out.println("Numero erroneo");
                    break;
            }

        } while (menuOpt != 0);

    }
}

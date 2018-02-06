import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String fileCotxes = "coches.txt";
    private static final String fileRandom = "random.txt";
    private static final String fileDGT = "DGTestruct.csv";
    static Scanner sc = new Scanner(System.in);

    static int getBytes(String s) {
        return Integer.parseInt(s.substring(1, s.length() - 1));
    }

    static DGTClass readDGT() {
        DGTClass dgt = new DGTClass();
        try {
            FileReader fr = new FileReader(new File(fileDGT));
            BufferedReader bufferreader = new BufferedReader(fr);
            String[] obj;
            int lineCount = 0;
            String line = bufferreader.readLine();
            while (line != null || lineCount < 8) {
                obj = line.split(",");
                switch (lineCount) {
                    case 0:
                        dgt.letra = getBytes(obj[1]);
                        break;
                    case 1:
                        dgt.tipus = getBytes(obj[1]);
                        break;
                    case 2:
                        dgt.data = getBytes(obj[1]);
                        break;
                    case 3:
                        dgt.matricula = getBytes(obj[1]);
                        break;
                    case 4:
                        dgt.num_bastidor = getBytes(obj[1]);
                        break;
                    case 5:
                        dgt.n_motor = getBytes(obj[1]);
                        break;
                    case 6:
                        dgt.dni = getBytes(obj[1]);
                        break;
                    case 7:
                        dgt.nombre_cognom = getBytes(obj[1]);
                        break;
                    case 8:
                        dgt.adreca = getBytes(obj[1]);
                        break;
                }
                lineCount++;
                line = bufferreader.readLine();
            }
            bufferreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dgt;

    }

    static String formatString(String s, int bytes, boolean mode) {

        //Mode true -> hay que rellenar bytes
        if (mode) {
            while (s.length() < bytes) {
                s = s.concat("*");
            }
        } else {
            //Mode false -> hay que recortar bytes
            s = s.substring(0, bytes);
        }
        return s;
    }

    static ArrayList<Fila> readFile(DGTClass dgt) {
        ArrayList<Fila> filas = new ArrayList();
        String[] obj;
        try {
            FileReader fr = new FileReader(new File(fileCotxes));
            BufferedReader bufferreader = new BufferedReader(fr);

            String line = bufferreader.readLine();
            while (line != null) {
                Fila f = new Fila();

                obj = line.split("\\|");
                f.letra = obj[0];
                if (f.letra.length() < dgt.letra) {
                    f.letra = formatString(f.letra, dgt.letra, true);
                } else if (f.letra.length() > dgt.letra) {
                    f.letra = formatString(f.letra, dgt.letra, false);
                }
                f.tipus = obj[1];
                if (f.tipus.length() < dgt.tipus) {
                    f.tipus = formatString(f.tipus, dgt.tipus, true);
                } else if (f.tipus.length() > dgt.tipus) {
                    f.tipus = formatString(f.tipus, dgt.tipus, false);
                }
                f.data = obj[2];
                if (f.data.length() < dgt.data) {
                    f.data = formatString(f.data, dgt.data, true);
                } else if (f.data.length() > dgt.data) {
                    f.data = formatString(f.data, dgt.data, false);
                }
                f.matricula = obj[3];
                if (f.matricula.length() < dgt.matricula) {
                    f.matricula = formatString(f.matricula, dgt.matricula, true);
                } else if (f.matricula.length() > dgt.matricula) {
                    f.matricula = formatString(f.matricula, dgt.matricula, false);
                }
                f.num_bastidor = obj[4];
                if (f.num_bastidor.length() < dgt.num_bastidor) {
                    f.num_bastidor = formatString(f.num_bastidor, dgt.num_bastidor, true);
                } else if (f.num_bastidor.length() > dgt.num_bastidor) {
                    f.num_bastidor = formatString(f.num_bastidor, dgt.num_bastidor, false);
                }
                f.n_motor = obj[5];
                if (f.n_motor.length() < dgt.n_motor) {
                    f.n_motor = formatString(f.n_motor, dgt.n_motor, true);
                } else if (f.n_motor.length() > dgt.n_motor) {
                    f.n_motor = formatString(f.n_motor, dgt.n_motor, false);
                }
                f.dni = obj[6];
                if (f.dni.length() < dgt.dni) {
                    f.dni = formatString(f.dni, dgt.dni, true);
                } else if (f.dni.length() > dgt.dni) {
                    f.dni = formatString(f.dni, dgt.dni, false);
                }
                f.nombre_cognom = obj[7];
                if (f.nombre_cognom.length() < dgt.nombre_cognom) {
                    f.nombre_cognom = formatString(f.nombre_cognom, dgt.nombre_cognom, true);
                } else if (f.nombre_cognom.length() > dgt.nombre_cognom) {
                    f.nombre_cognom = formatString(f.nombre_cognom, dgt.nombre_cognom, false);
                }
                f.adreca = obj[8];
                if (f.adreca.length() < dgt.adreca) {
                    f.adreca = formatString(f.adreca, dgt.adreca, true);
                } else if (f.adreca.length() > dgt.adreca) {
                    f.adreca = formatString(f.adreca, dgt.adreca, false);
                }
                filas.add(f);

                line = bufferreader.readLine();
            }
            bufferreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filas;
    }

    static void writeRAF() {
        try {
            RandomAccessFile file = new RandomAccessFile(fileRandom, "rw");

            DGTClass dgt = readDGT();

            ArrayList<Fila> f = readFile(dgt);

            for (int i = 0; i < f.size(); i++) {
                file.writeBytes(f.get(i).letra + f.get(i).tipus + f.get(i).data + f.get(i).matricula + f.get(i).num_bastidor + f.get(i).n_motor + f.get(i).dni + f.get(i).nombre_cognom + f.get(i).adreca + "\n");
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printRows() {
        try {
            FileReader fr = new FileReader(new File(fileCotxes));
            BufferedReader bufferreader = new BufferedReader(fr);

            String line = bufferreader.readLine();
            int countLines = 0;
            System.out.println("Estas son todas las lineas");
            while (line != null) {
                System.out.println(countLines + ") " + line);
                countLines++;
                line = bufferreader.readLine();
            }
            bufferreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void modifyFile() {
        int rowSelected;
        System.out.println("1)Letra");
        System.out.println("2)Tipo");
        System.out.println("3)Fecha");
        System.out.println("4)Matricula");
        System.out.println("5)Bastidor");
        System.out.println("6)Num Motor");
        System.out.println("7)DNI");
        System.out.println("8)Nombre y apellidos");
        System.out.println("9)Dirección");
        System.out.println("Que fila quieres modificar?");

        printRows();
        rowSelected = sc.nextInt();
        sc.nextLine();
        Fila f = searchRow(rowSelected);
        if (f != null) {
            System.out.println(f.letra + f.tipus);

        } else {
            System.out.println("La fila seleccionada no existe");
        }

    }

    static Fila searchRow(int row) {
        Fila f = null;
        try {
            RandomAccessFile file = new RandomAccessFile(fileRandom, "rw");
            DGTClass dgt = readDGT();
            if (row == 0) {
                file.seek(0);
            } else {
                file.seek(dgt.getRowLeght() * row + 1);
            }
            //System.out.println(dgt.getRowLeght());
            f = formatRow(file.readLine(), dgt);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    static Fila formatRow(String row, DGTClass dgt) {
        Fila f = new Fila();
        int num1, num2;
        //Lletra
        num1 = 0;
        num2 = dgt.letra;
        f.letra = row.substring(num1, num2);
        //Tipus
        num1 = num2;
        num2 += dgt.tipus;
        f.tipus = row.substring(num1, num2);
        //Data
        num1 = num2;
        num2 += dgt.data;
        f.data = row.substring(num1, num2);
        //Matricula
        num1 = num2;
        num2 += dgt.matricula ;
        f.matricula = row.substring(num1, num2);
        //Numero Bastidor
        num1 = num2;
        num2 += dgt.num_bastidor;
        f.num_bastidor = row.substring(num1, num2);
        //Numero motor
        num1 = num2;
        num2 += dgt.n_motor;
        f.n_motor = row.substring(num1, num2);
        //DNI
        num1 = num2;
        num2 += dgt.dni;
        f.dni = row.substring(num1, num2);
        //Nom y cognoms
        num1 = num2;
        num2 += dgt.nombre_cognom;
        f.nombre_cognom = row.substring(num1, num2);
        //Adreça
        num1 = num2;
        num2 += dgt.adreca;
        f.adreca = row.substring(num1, num2);

        return f;
    }

    /*static Fila searchRow(int r) {
        ArrayList<Fila> f = readFile();
        Fila row = null;
        if (r < f.size()) {
            row = f.get(r);
        }
        return row;

    }*/

    public static void main(String[] args) {
        int menuOpt = 0;
        do {
            System.out.println("\tMenu");
            System.out.println("\t----");
            System.out.println("1)Generar archivo");
            System.out.println("2)Modificar datos");
            System.out.println("0)Salir");
            menuOpt = sc.nextInt();
            sc.nextLine();

            switch (menuOpt) {
                case 1:
                    writeRAF();
                    break;
                case 2:
                    modifyFile();
                    //readFile();
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

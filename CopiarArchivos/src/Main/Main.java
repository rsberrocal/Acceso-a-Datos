package Main;


import java.io.File;
import java.nio.file.*;
import java.util.Scanner;


public class Main {

    public static void copyFiles(File path1,File path2) throws Exception{
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(path1.toPath(),path2.toPath(),options);
        System.out.println("Fichero copiado");
    }
    public static void main(String[] args) {
        String sourcePath;
        String destinationPath;

        File source;
        File[] sourceFiles;
        File destination;

        Scanner sc = new Scanner(System.in);

        System.out.println("Dame la ruta de la carpeta a copiar");
        sourcePath = sc.nextLine();
        source = new File(sourcePath);
        if (!source.exists()) {
            System.out.println("La carpeta " + sourcePath + " no existe!");
        } else {
            sourceFiles = source.listFiles();
            if (sourceFiles.length == 0) {
                System.out.println("No tienes ficheros en la carpeta " + sourcePath);
            } else {
                System.out.println("Dame la ruta donde copiar los ficheros");
                destinationPath=sc.nextLine();
                destination= new File(destinationPath);
                if(!destination.exists()){
                    System.out.println("Se creara un directorio en la ruta especificada");
                    destination.mkdir();
                }

                for(int i=0;i<sourceFiles.length;i++){
                    try {
                        copyFiles(sourceFiles[i],new File(destinationPath+"/"+sourceFiles[i].getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}

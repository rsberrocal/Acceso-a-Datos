public class DGTClass {
    public int letra;
    public int matricula;
    public int nombre_cognom;
    public int tipus;
    public int data;
    public int num_bastidor;
    public int n_motor;
    public int dni;
    public int adreca;

    DGTClass(){}

    public int getRowLeght() {
        return letra+matricula+nombre_cognom+tipus+dni+data+num_bastidor+adreca+n_motor;
    }
}

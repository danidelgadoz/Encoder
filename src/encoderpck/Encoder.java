package encoderpck;

/**
 *
 * @author Daniel Delgado Diaz
 */
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Encoder {

    private static final String IMAGE_FORMAT = "png";    
    
//    public static void main(String[] args) throws FileNotFoundException, IOException{
//        String x[] = new String[0];
//        x[0] = "123456789"; // string to encode
//        x[1] = "8"; // type of code (codebar types, qr, pdf)
//        x[2] = "400"; // width
//        x[3] = "200"; // height
//        main2(x);
//    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int arraysize = args.length;

        // Validamos cantidad de parametros ('0' o '3'  cantidad no permitida)
        if (arraysize == 0 || arraysize == 3 || arraysize > 4) {
            if (arraysize == 3) {
                System.out.println("");
                System.out.println("No es posible generar un codigo con 3 parametros");
                System.out.println("Siga las siguientes instrucciones:");
                System.out.println("");
            }
            if (arraysize > 4) {
                System.out.println("");
                System.out.println("No debe ingresar mas  de 4 parametros");
                System.out.println("");
            }
            help();
            return;
        }
        // Cantidad de parametros validos, entnces procedemos ...
                
        CodeGenerator codecreator = new CodeGenerator(args[0]); //Instanciamos codecreator a la clase cuyos metodos retornan imagenes(codigos)
        BufferedImage code = null;
        String filename = null;
        
        switch (arraysize) {
            case 1:
                {
                    if (args[0].equals("?")) {
                        help();
                    } else {
                        filename = "code_QR";
                        code = codecreator.getQR();
                    }       break;
                }
            case 2:
            case 4:
                {
                    int tipo = Integer.parseInt(args[1]);                    
                    
                    if(arraysize==4){                        
                        codecreator.setHeight(Integer.parseInt(args[3]));
                        codecreator.setWidth(Integer.parseInt(args[2]));
                    }
                    
                    switch (tipo) {
                        case 1:
                            filename = "code_QR";
                            code = codecreator.getQR();
                            break;
                        case 2:
                            filename = "code_CODE128";
                            code = codecreator.getBC(tipo);
                            break;
                        case 3:
                            filename = "code_CODE39";
                            code = codecreator.getBC(tipo);
                            break;
                        case 4:
                            filename = "code_CODABAR";
                            code = codecreator.getBC(tipo);
                            break;
                        case 5:
                            filename = "code_EAN13";
                            code = codecreator.getBC(tipo);
                            break;
                        case 6:
                            filename = "code_INTERLEAVED25";
                            code = codecreator.getBC(tipo);
                            break;
                        case 7:
                            filename = "code_PDF417";
                            code = codecreator.getPDF417();
                            break;
                        case 8:
                            filename = "code_CODE128";
                            code = codecreator.getCODE128();
                            break;
                        default:
                            // Validamos el tipo de codigo
                            System.out.println("");
                            System.out.println("N° de tipo de codigos permitidos: [1 - 8]");
                            System.out.println("");
                            help();
                            return;
                    }       break;
                }
            default: 
                break;
        }

        /* 
        //Cambia la orientacion de la imagen a 90° en sentido horario ...
        code = rotate(code);
        code = rotate(code);
        code = rotate(code);
        */

        File archivo = new File(filename + "." + IMAGE_FORMAT);
        ImageIO.write(code, IMAGE_FORMAT, archivo);
    }        
    
    static void help() throws FileNotFoundException, IOException {
        System.out.println("");
        System.out.println("Ejecutar el archivo \"encoderTCS.exe\" de la siguiente manera: ");
        System.out.println("");
        System.out.println("\"encoderTCS.exe parametro1 parametro2 parametro3 parametro4\".");
        System.out.println("");
        System.out.println("");
        System.out.println("PARAMETRO                Tipo                    ESTADO");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("1. Codigo a generar      String                  NO NULL");
        System.out.println("2. Tipo de codigo        Integer                 DEFAULT(1 / QR)");
        System.out.println("3. Ancho (pixeles)       Integer                 DEFAULT(variable)");
        System.out.println("4. Alto (pixeles)        Integer                 DEFAULT(variable)");
        System.out.println("");
        System.out.println("");
        System.out.println("TIPO DE CODIGO          DESCRIPCION");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("1. QR                   7.089 caracteres numericos; 4.296 alfanumericos");
        System.out.println("                        Longitud: Variable");
        System.out.println("");
        System.out.println("2. Code128              caracteres alfanuméricos");
        System.out.println("                        Longitud: Variable");
        System.out.println("");
        System.out.println("3. Code39               43 caracteres: A-Z, 0-9, espacio y -.$/+%.");
        System.out.println("                        Longitud: Variable");
        System.out.println("");
        System.out.println("4. Codabar              16 caracteres: 0-9, -.:$/+");
        System.out.println("                        Longitud: Variable");
        System.out.println("");
        System.out.println("5. Ean13                Solo caracteres numéricos");
        System.out.println("                        Longitud: Fija, 8 o 13 dígitos");
        System.out.println("");
        System.out.println("6. Interleaved25        Solo caracteres numéricos");
        System.out.println("                        Longitud: Variable");
        System.out.println("");
        System.out.println("7. Pdf147               Codifica todos los caracteres ASCII");
        System.out.println("                        1,850 texto ó 2,710 dígitos ó 1,108 bytes");
        System.out.println("");
        System.out.println("8. Code128(sin leyenda) caracteres alfanuméricos");
        System.out.println("                        Longitud: Variable");
        System.out.println("");

    }

    // Metodo que invierte en 90° imagen a 90° en sentido horario ...
    static BufferedImage rotate(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        //BufferedImage(width, height, imgType)
        BufferedImage newImage = new BufferedImage(height, width, img.getType());
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));
            }
        }

        return newImage;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encoderpck;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class NewTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Integer x[] = new Integer[4];
        
        x[1] = 10;
        x[2] = 20;
        x[3] = 40;
        
        ArrayList a;
        a = new ArrayList();
        a.add(1);
        a.add(5);               
        
        
//        foo("a", null, 2);
        Integer z, w;
        z = w = 2;
        
        System.out.println(z);
        System.out.println(w);
        
        
        
        
//        switch (z) {
//            case 1:
//                System.out.println("one");
//                break;
//            case 2:
//            case 4:
//                System.out.println("two or four");
//                break;
//            case 3:
//                System.out.println("three");
//                break;
//            default:
//                System.out.println("none");
//        }
        //System.out.println(x[1]);
    }
    
    static void foo(String a, Integer b, Integer c) {
        b = b != null ? b : 0;
        c = c != null ? c : 0;
        //...
        System.out.println(a);
        System.out.println(b); 
        System.out.println(c);
        
        String test = b!=null ? "no nulo" : "nulaso";
        
        System.out.println("");
        System.out.println(test);
    }
    
}

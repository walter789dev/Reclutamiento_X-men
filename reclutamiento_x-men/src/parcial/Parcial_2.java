package parcial;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
/*
 Mutantes: 
 1: AAAATC, AATCGG, ATACGT, ATGATG, TTTGGG, TGGTGG
 2: AAAAAA, AATAGC, ATAGCT, AATACG, ATCCAT, GTCGTA
 
 No Mutante:
 1: ATTCGA, TACGAT, CCTGCA, GGGGAT, ATTCCG, TACGTA
 2: CCCCCC, AATAGC, ATAGCT, TAGGCG, GCCTCG, AAGGTT
 */
public class Parcial_2 {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		Pattern option = Pattern.compile("[1|2]");
		boolean validate = true;
		
		while (validate) {
			System.out.print("¿Que desea realizar? \n1. Verificar posible mutante \n2.  Salir \n: ");
			String opcion = input.next();
			Matcher m_opcion = option.matcher(opcion);
			
			if(m_opcion.matches()) {
				switch (opcion) {
					case "1": {
						ArrayList<String> dna = get_dna();
						boolean mutante = is_mutant(dna);
						
						if (mutante) System.out.println("\nGen mutante Detectado\n");
						else System.out.println("\nGen mutante NO Encontrado\n");
						break;
					}
					case "2": {
						System.out.println("Que tenga un buen dia");
						validate = false;
						break;
					}
					default: System.out.println("La opción elegida no existe, intente nuevamente");
				}
			}
			else System.out.println("Debe ingresar un número que represente la decision a tomar, intente nuevamente");
		}
	}
    
    public static ArrayList<String> get_dna(){
    	Pattern dna = Pattern.compile("[A|T|C|G]{6}");
    	ArrayList<String> dna_list = new ArrayList<String>();
    	int count = 1;
    	
    	System.out.println("\nDebe ingresar 6 secuencias de ADN para verificar si es un Mutante o no. \nAclaración:");
    	System.out.println("Las secuencias deben estar compuestas de: A, T, C, G (representan bases hidrogenadas)");
    	System.out.println("Deben tener un tamaño de 6 caracteres");
    	System.out.println("Sera Mutante si se encuentra más de una secuencia de cuatro bases iguales en su ADN \n");
    	
    	while (count <= 6) {
    		System.out.print("Ingrese la secuencia número " + count + ": ");
    		String secuencia = input.next().toUpperCase().strip();
    		Matcher m_secuencia = dna.matcher(secuencia);
    		
    		if(m_secuencia.matches()) {
    			dna_list.add(secuencia);
    			count++;
    		}
    		else System.out.println("La secuencia debe contener: A, T, G, C y un tamaño de 6 caracteres. Intente nuevamente");
    	}
    	
    	return dna_list;
    }

    public static boolean is_mutant(ArrayList<String> A) {
    	ArrayList<String> reverse = new ArrayList<String>();
    	for (String string : A) {
			String elm = "";
			for (int i = string.length() - 1; i >= 0; i--) elm += string.charAt(i);
			reverse.add(elm);
		}
    	
    	int v_h = validate_horizontal(A);
    	int v_v = validate_vertical(A);
    	int v_d_i_d = validate_diagonal(A);
    	int v_d_d_i = validate_diagonal(reverse);
    	//System.out.println("H: " + v_h + ", V: " + v_v + ", ID: " + v_d_i_d + ", DI: " + v_d_d_i);
    	
    	if (v_h + v_v + v_d_i_d + v_d_d_i > 1) return true;
    	else return false;
    }
    
    public static int validate_horizontal(ArrayList<String> A) {
    	int coincidence = 0, count = 0;
    	for (String string : A) {
			char elm_coincidence = string.charAt(0);
			
			for (int i = 0; i < string.length(); i++) {
				if(count == 4) break;
				
				if(elm_coincidence == string.charAt(i)) count++;
				else {
					elm_coincidence = string.charAt(i);
					count = 1;
				}
			}
			if(count == 4 || count == 5) coincidence++;
			 count = 0;
		}
    	return coincidence;
    }
    
    public static int validate_vertical(ArrayList<String> A) {
    	int coincidence = 0, count = 0;
    	for (int x = 0; x < A.size(); x++) {
			char elm_coincidence = A.get(0).charAt(x);
			
			for (int y = 0; y < A.size(); y++) {
				if(count == 4) break;
				
				if(elm_coincidence == A.get(y).charAt(x)) count++;
				else {
					elm_coincidence = A.get(y).charAt(x);
					count = 1;
				}
			}
			if(count == 4 || count == 5) coincidence++;
			count = 0;
		}
    	return coincidence;
    }
    
    public static int validate_diagonal(ArrayList<String> A) {
    	int support = A.size() / 2;
    	int coincidence = 0, count = 0, cross_y = 0;
    	int max = 1, half = 0;
    	
    	for (int r = 0; r < A.size() - 1; r++) {
			if (max == 0) cross_y++;
			else {
				half = support + r;
				max = support - (r + 1);
			}
			
			char elm_coincidence = A.get(cross_y).charAt(max);
			
			for (int q = 0; q < (half + 1 - cross_y); q++) {
				int x = q + cross_y;
				int y = max + q;
				
				if(count == 4) break;
				
				if(elm_coincidence == A.get(x).charAt(y)) count++;
				else {
					elm_coincidence = A.get(x).charAt(y);
					count = 1;
				}
			}
			if (count == 4 || count == 5) coincidence++;
			count = 0;
		}
    	return coincidence;
    }
}




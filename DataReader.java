// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Hashtable;

// public class DataReader {
//     public static Hashtable<String, Integer> entities;
//     public static ArrayList <Vector> coordinates;
//     public static ArrayList <Vector> velocities;
//     public static ArrayList <Double> masses;
//     public static ArrayList <Double> radii;

//     public DataReader(){
//         entities =new Hashtable<String, Integer>() ;
//         coordinates = new ArrayList<>();
//         velocities = new ArrayList<>();
//         masses = new ArrayList<>();
//         radii = new ArrayList<>();
//     }

//     public static void main(String[] args) {

// 		try {
// 			BufferedReader br = new BufferedReader(new FileReader("values.txt"));
// 			//String line = br.readLine();
//             String line;
//             int i=0;
// 			while ((line = br.readLine()) != null) {
//                 String [] textLine = line.split(" ");
//                 for (int m=1; m<textLine.length; m++){
//                 }
                
//                 i++;
//                 double one= Double.parseDouble(textLine[1]);
//                 Vector a = new Vector(one , Double.parseDouble(textLine[2]), Double.parseDouble(textLine[3]));
//                 coordinates.add(a);
//                 Vector b = new Vector(Double.parseDouble(textLine[4]) , Double.parseDouble(textLine[5]), Double.parseDouble(textLine[6]));
//                 velocities.add(b);
//                 masses.add(Double.parseDouble(textLine[7]));
//                 radii.add(Double.parseDouble(textLine[8]));

// 			}
// 			br.close();
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 		}

//         for (int i=0; i<=masses.size(); i++){
//             System.out.println(masses.get(i));
//         }
//     }
// }

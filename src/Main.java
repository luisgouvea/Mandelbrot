import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class in the Mandelbrot project.  This will eventually, begin threads to
 * create different images of the Mandelbrot set.
 * @author Eric Mosher
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        // The number of threads to use.  We will use 4 because I have a
        // Quad-core processor.  (Intel i5)
        int numThreads = 4;
        // The threads used in this program
        //Thread[] threads = new Thread[numThreads];
        // The number of pictures per thread
        int picsPerThread = 3;
        // The names of the files will be mbrot1, mbrot2, etc.
        String fName = "mbrot";
        // The number we will attach to the file name
        int count = 1;
        // The beginning resolution
        int resolution = 500;
        // The image center
        double xCenter = -0.38;
        double yCenter = -0.665;
        // Number of total iterations
        int maxIterations = 1000;
        
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        ColorScheme color = new ColorScheme(maxIterations);
        
        System.out.print("alo");

        // For the number of threads being used...
        for (int a = 0; a < numThreads; a++) {
        	// Create an array to give to each thread
            MbrotParameter[] mbrots = new MbrotParameter[picsPerThread];
            // For every parameter in the array...
            for (int b = 0; b < picsPerThread; b++) {
                // Create the parameter and assign it to the place in the array
                mbrots[b] = new MbrotParameter(fName + String.valueOf(count),
                        resolution, xCenter, yCenter);
                // Update our data
                count++;
                resolution += 1000;
            }
            // Create a new thread with the appropriate array as the parameter.
            executor.execute(new MandelbrotRenderer(mbrots, color,
                    maxIterations));
        }

        executor.shutdown();
        
//         //Start the threads
//         for (Thread t : threads) {
//            t.start();
//        }
//
//        // Join the threads
//        for (Thread t : threads) {
//            try {
//            	
//                executor.execute(t);
//            	//executor.execute(arg0);            	
//            	//t.join();
//            }
//            catch (Exception e) {}
//        }
//    }
       executor.awaitTermination(2,TimeUnit.MINUTES);
    }
}

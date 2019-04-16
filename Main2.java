
/**
 * File: ReadWriteImage.java
 * Description:
 * Read and write image.
 */
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String args[]) throws IOException {

		// read image file
		int[][] im = readimage("C:/Users/user/Pictures/1.jpg");
		;

		// image processing
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				// System.out.println(im[i][j]);
				// im[i][j]=(i+j)%256;
			}
		}

		// write image
		// writeimage("C:\\Images\\test1.jpg", negative(im));
		// writeimage("C:\\Images\\test2.jpg", log(im,1));
		// writeimage("C:\\Images\\test3.jpg", law(im,1,0.5));
		double p[]=pmf(im);
		writeimage("C:/Users/user/Pictures/test7.jpg", drawHistogram(p,400));

	}// main() ends here

	public static int[][] negative(int[][] im) {
		int[][] a = new int[im.length][im[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = 255 - im[i][j];
			}
		}
		return a;
	}

	public static int[][] log(int[][] im, double c) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int[][] a = new int[im.length][im[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = (int) (c * Math.log(im[i][j] + 1));
				if (a[i][j] < min) {
					min = a[i][j];
				}
				if (a[i][j] > max) {
					max = a[i][j];
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = 255 * (a[i][j] - min) / (max - min);

			}
		}

		return a;
	}

	public static int [][] histogram(int[][] im) {
		double a[] = new double[256];
		int [][]q=new int[im.length][im[0].length];
		// PMF
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[0].length; j++) {
				a[im[i][j]]++;
			}
		}
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] / (im.length * im[0].length);

		}
		// CDF
		double a2[] = new double[a.length];
		a2[0] = a[0];
		for (int i = 1; i < a2.length; i++) {
			a2[i] = (a2[i - 1] + a[i]);
		}
		for (int i = 1; i < a2.length; i++) {
			a2[i] *= 255;
		}
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[0].length; j++) {
				q[i][j] = (int) a2[im[i][j]];
			}
		}
		return q;
	}
	public static double[]pmf(int[][]im){
		double a[] = new double[256];
		// PMF
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[0].length; j++) {
				a[im[i][j]]++;
			}
		}
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] / (im.length * im[0].length);

		}
		return a;
	}

	public static int[][] drawHistogram(double[] im,int h) {
		int[][] draw = new int[h][im.length];
		double max=Double.MIN_VALUE;
		for (int i = 0; i < im.length; i++) {
			max=Math.max(max, im[i]);
		}
		for (int i = im.length-1; i > 0; i--) {
			 int dist = (int) ((im[i] / max) * h);
			for (int j = 0; j < h-dist; j++) {
				draw[j][i] = 255;
			}
		}
	
		return draw;

	}

	public static int[][] law(int[][] im, double c, double g) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int[][] a = new int[im.length][im[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = (int) (c * Math.pow((im[i][j]), g));
				if (a[i][j] < min) {
					min = a[i][j];
				}
				if (a[i][j] > max) {
					max = a[i][j];
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = 255 * (a[i][j] - min) / (max - min);

			}
		}

		return a;
	}

	public static int[][] readimage(String path) {
		BufferedImage image = null;
		File f = null;
		try {
			f = new File(path);
			image = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return img2matrix(image);
	}

	public static void writeimage(String path, int[][] im) {
		File f = null;
		try {
			f = new File(path);
			ImageIO.write(matrix2img(im), path.substring(path.length() - 3), f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public static int[][] img2matrix(BufferedImage bi) {
		int p, a, r, g, b;
		int[][] C = new int[bi.getHeight()][bi.getWidth()];
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {
				p = bi.getRGB(j, i);
				a = (p >> 24) & 0xff;
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = p & 0xff;
				C[i][j] = (r + g + b) / 3;
			}
		}
		return C;
	}

	public static BufferedImage matrix2img(int[][] im) {
		BufferedImage bi = new BufferedImage(im[0].length, im.length, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				bi.setRGB(j, i, im[i][j] << 16 | im[i][j] << 8 | im[i][j]);
			}
		}
		return bi;
	}
}// class ends here

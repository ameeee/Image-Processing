
/**
 * File: ReadWriteImage.java
 *
 * Description:
 * Read and write image.
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String args[]) throws IOException {
		Scanner x = new Scanner(System.in);
		BufferedImage image = null;
		File f = null;

		// read image file
		try {
			f = new File("4.png");
			image = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

		int[][] im = img2matrix(image);

	//write image
		try {
			f = new File("4median7.jpg");
			ImageIO.write(matrix2img(im, image), "JPG", f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

	}// main() ends here

	public static int[][] img2matrix(BufferedImage bi) {
		int p, a, r, g, b;
		int[][] C = new int[bi.getHeight()][bi.getWidth()];
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {
				p = bi.getRGB(j, i);
				// a = (p>>24)&0xff;
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = p & 0xff;
				C[i][j] = (r + g + b) / 3;
			}
		}
		return C;
	}

	public static BufferedImage matrix2img(int[][] im, BufferedImage bi) {
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				// bi.setRGB(j, i,((((bi.getRGB(j, i)>>24)&0xff)<<24) | (im[i][j]<<16) |
				// im[i][j]<<8) | im[i][j]);
				bi.setRGB(j, i, im[i][j] << 16 | im[i][j] << 8 | im[i][j]);
			}
		}
		return bi;
	}

	public static void average(int[][] im, BufferedImage bi, int N, int[] a) {
		int count = 0;
		for (int i = N / 2 ; i < im.length - N / 2; i++) {
			for (int j = N / 2; j < im[i].length - N / 2; j++) {
				int sum = 0;
				for (int k = i - N / 2; k <= i + N / 2; k++) {
					for (int k2 = j - N / 2; k2 <= j + N / 2; k2++) {
						sum += im[k][k2];
						count++;
					}
				}
				im[i][j] = sum / (N * N);
			}
		}

	}

	public static void median(int[][] im, BufferedImage bi, int N, int a[]) {
		int c = 0;
		for (int i = N / 2; i < im.length - N / 2; i++) {
			for (int j = N / 2; j < im[i].length - N / 2; j++) {
				c=0;
				for (int k = i - N / 2; k <= i + N / 2; k++) {
					for (int k2 = j - N / 2; k2 <= j + N / 2; k2++) {
						a[c] = im[k][k2];
						c++;
					}
				}
				Arrays.sort(a);
				
				im[i][j] = a[N*N/ 2];
			}
			
		}

	}
	public static void alphatrimmed(int[][] im, BufferedImage bi, int N, int a[]) {
		int c = 0;
		for (int i = N / 2; i < im.length - N / 2; i++) {
			for (int j = N / 2; j < im[i].length - N / 2; j++) {
				c=0;
				for (int k = i - N / 2; k <= i + N / 2; k++) {
					for (int k2 = j - N / 2; k2 <= j + N / 2; k2++) {
						a[c] = im[k][k2];
						c++;
					}
				}
				Arrays.sort(a);
				int av=0;
				for (int m = 0; m < a.length; m++) {
					if(m!=0 && m!=a.length-1) {
						av+=a[m];
					}
				}
				av=av/(a.length-2);
				im[i][j]=av;
			}
			
		}

	}

}// class ends here
